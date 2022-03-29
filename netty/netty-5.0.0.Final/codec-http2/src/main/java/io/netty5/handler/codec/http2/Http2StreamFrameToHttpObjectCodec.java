/*
 * Copyright 2016 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.netty5.handler.codec.http2;

import io.netty5.buffer.api.Buffer;
import io.netty5.buffer.api.BufferAllocator;
import io.netty5.buffer.api.adaptor.ByteBufAdaptor;
import io.netty5.channel.Channel;
import io.netty5.channel.ChannelHandler;
import io.netty5.channel.ChannelHandler.Sharable;
import io.netty5.channel.ChannelHandlerContext;
import io.netty5.handler.codec.EncoderException;
import io.netty5.handler.codec.MessageToMessageCodec;
import io.netty5.handler.codec.http.DefaultHttpContent;
import io.netty5.handler.codec.http.DefaultLastHttpContent;
import io.netty5.handler.codec.http.FullHttpMessage;
import io.netty5.handler.codec.http.FullHttpResponse;
import io.netty5.handler.codec.http.HttpContent;
import io.netty5.handler.codec.http.HttpHeaderNames;
import io.netty5.handler.codec.http.HttpHeaderValues;
import io.netty5.handler.codec.http.HttpMessage;
import io.netty5.handler.codec.http.HttpObject;
import io.netty5.handler.codec.http.HttpRequest;
import io.netty5.handler.codec.http.HttpResponse;
import io.netty5.handler.codec.http.HttpResponseStatus;
import io.netty5.handler.codec.http.HttpScheme;
import io.netty5.handler.codec.http.HttpUtil;
import io.netty5.handler.codec.http.HttpVersion;
import io.netty5.handler.codec.http.LastHttpContent;
import io.netty5.handler.ssl.SslHandler;
import io.netty5.util.Attribute;
import io.netty5.util.AttributeKey;
import io.netty5.util.internal.UnstableApi;

import java.util.List;

import static io.netty5.buffer.api.adaptor.ByteBufAdaptor.extractOrCopy;

/**
 * This handler converts from {@link Http2StreamFrame} to {@link HttpObject},
 * and back. It can be used as an adapter in conjunction with {@link
 * Http2MultiplexCodec} to make http/2 connections backward-compatible with
 * {@link ChannelHandler}s expecting {@link HttpObject}
 *
 * For simplicity, it converts to chunked encoding unless the entire stream
 * is a single header.
 */
@UnstableApi
@Sharable
public class Http2StreamFrameToHttpObjectCodec extends MessageToMessageCodec<Http2StreamFrame, HttpObject> {

    private static final AttributeKey<HttpScheme> SCHEME_ATTR_KEY =
        AttributeKey.valueOf(HttpScheme.class, "STREAMFRAMECODEC_SCHEME");

    private final boolean isServer;
    private final boolean validateHeaders;

    public Http2StreamFrameToHttpObjectCodec(final boolean isServer,
                                             final boolean validateHeaders) {
        this.isServer = isServer;
        this.validateHeaders = validateHeaders;
    }

    public Http2StreamFrameToHttpObjectCodec(final boolean isServer) {
        this(isServer, true);
    }

    @Override
    public boolean acceptInboundMessage(Object msg) throws Exception {
        return msg instanceof Http2HeadersFrame || msg instanceof Http2DataFrame;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Http2StreamFrame frame) throws Exception {
        if (frame instanceof Http2HeadersFrame) {
            Http2HeadersFrame headersFrame = (Http2HeadersFrame) frame;
            Http2Headers headers = headersFrame.headers();
            Http2FrameStream stream = headersFrame.stream();
            int id = stream == null ? 0 : stream.id();

            final CharSequence status = headers.status();

            // 100-continue response is a special case where Http2HeadersFrame#isEndStream=false
            // but we need to decode it as a FullHttpResponse to play nice with HttpObjectAggregator.
            if (null != status && HttpResponseStatus.CONTINUE.codeAsText().contentEquals(status)) {
                final FullHttpMessage<?> fullMsg = newFullMessage(id, headers, ctx.bufferAllocator());
                ctx.fireChannelRead(fullMsg);
                return;
            }

            if (headersFrame.isEndStream()) {
                if (headers.method() == null && status == null) {
                    LastHttpContent<?> last = new DefaultLastHttpContent(ctx.bufferAllocator().allocate(0),
                            validateHeaders);
                    HttpConversionUtil.addHttp2ToHttpHeaders(id, headers, last.trailingHeaders(),
                                                             HttpVersion.HTTP_1_1, true, true);
                    ctx.fireChannelRead(last);
                } else {
                    FullHttpMessage<?> full = newFullMessage(id, headers, ctx.bufferAllocator());
                    ctx.fireChannelRead(full);
                }
            } else {
                HttpMessage req = newMessage(id, headers);
                if (!HttpUtil.isContentLengthSet(req)) {
                    req.headers().add(HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED);
                }
                ctx.fireChannelRead(req);
            }
        } else if (frame instanceof Http2DataFrame) {
            Http2DataFrame dataFrame = (Http2DataFrame) frame;
            if (dataFrame.isEndStream()) {
                ctx.fireChannelRead(new DefaultLastHttpContent(
                        extractOrCopy(ctx.bufferAllocator(), dataFrame.content().retain()), validateHeaders));
            } else {
                ctx.fireChannelRead(new DefaultHttpContent(extractOrCopy(ctx.bufferAllocator(),
                        dataFrame.content().retain())));
            }
        }
    }

    private void encodeLastContent(LastHttpContent<?> last, List<Object> out) {
        boolean needFiller = !(last instanceof FullHttpMessage) && last.trailingHeaders().isEmpty();
        final Buffer payload = last.payload();
        if (payload.readableBytes() > 0 || needFiller) {
            out.add(new DefaultHttp2DataFrame(ByteBufAdaptor.intoByteBuf(payload), last.trailingHeaders().isEmpty()));
        }
        if (!last.trailingHeaders().isEmpty()) {
            Http2Headers headers = HttpConversionUtil.toHttp2Headers(last.trailingHeaders(), validateHeaders);
            out.add(new DefaultHttp2HeadersFrame(headers, true));
        }
    }

    /**
     * Encode from an {@link HttpObject} to an {@link Http2StreamFrame}. This method will
     * be called for each written message that can be handled by this encoder.
     *
     * NOTE: 100-Continue responses that are NOT {@link FullHttpResponse} will be rejected.
     *
     * @param ctx           the {@link ChannelHandlerContext} which this handler belongs to
     * @param obj           the {@link HttpObject} message to encode
     * @param out           the {@link List} into which the encoded msg should be added
     *                      needs to do some kind of aggregation
     * @throws Exception    is thrown if an error occurs
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, HttpObject obj, List<Object> out) throws Exception {
        // 100-continue is typically a FullHttpResponse, but the decoded
        // Http2HeadersFrame should not be marked as endStream=true
        if (obj instanceof HttpResponse) {
            final HttpResponse res = (HttpResponse) obj;
            if (res.status().equals(HttpResponseStatus.CONTINUE)) {
                if (res instanceof FullHttpResponse) {
                    final Http2Headers headers = toHttp2Headers(ctx, res);
                    out.add(new DefaultHttp2HeadersFrame(headers, false));
                    return;
                } else {
                    throw new EncoderException(
                            HttpResponseStatus.CONTINUE + " must be a FullHttpResponse");
                }
            }
        }

        if (obj instanceof HttpMessage) {
            Http2Headers headers = toHttp2Headers(ctx, (HttpMessage) obj);
            boolean noMoreFrames = false;
            if (obj instanceof FullHttpMessage) {
                FullHttpMessage<?> full = (FullHttpMessage<?>) obj;
                noMoreFrames = full.payload().readableBytes() == 0 && full.trailingHeaders().isEmpty();
            }

            out.add(new DefaultHttp2HeadersFrame(headers, noMoreFrames));
        }

        if (obj instanceof LastHttpContent) {
            LastHttpContent<?> last = (LastHttpContent<?>) obj;
            encodeLastContent(last, out);
        } else if (obj instanceof HttpContent) {
            HttpContent<?> cont = (HttpContent<?>) obj;
            final Buffer payload = cont.payload();
            out.add(new DefaultHttp2DataFrame(ByteBufAdaptor.intoByteBuf(payload), false));
        }
    }

    private Http2Headers toHttp2Headers(final ChannelHandlerContext ctx, final HttpMessage msg) {
        if (msg instanceof HttpRequest) {
            msg.headers().set(
                    HttpConversionUtil.ExtensionHeaderNames.SCHEME.text(),
                    connectionScheme(ctx));
        }

        return HttpConversionUtil.toHttp2Headers(msg, validateHeaders);
    }

    private HttpMessage newMessage(final int id,
                                   final Http2Headers headers) throws Http2Exception {
        return isServer ?
                HttpConversionUtil.toHttpRequest(id, headers, validateHeaders) :
                HttpConversionUtil.toHttpResponse(id, headers, validateHeaders);
    }

    private FullHttpMessage<?> newFullMessage(final int id,
                                           final Http2Headers headers,
                                           final BufferAllocator alloc) throws Http2Exception {
        return isServer ?
                HttpConversionUtil.toFullHttpRequest(id, headers, alloc, validateHeaders) :
                HttpConversionUtil.toFullHttpResponse(id, headers, alloc, validateHeaders);
    }

    @Override
    public void handlerAdded(final ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);

        // this handler is typically used on an Http2StreamChannel. At this
        // stage, ssl handshake should've been established. checking for the
        // presence of SslHandler in the parent's channel pipeline to
        // determine the HTTP scheme should suffice, even for the case where
        // SniHandler is used.
        final Attribute<HttpScheme> schemeAttribute = connectionSchemeAttribute(ctx);
        if (schemeAttribute.get() == null) {
            final HttpScheme scheme = isSsl(ctx) ? HttpScheme.HTTPS : HttpScheme.HTTP;
            schemeAttribute.set(scheme);
        }
    }

    protected boolean isSsl(final ChannelHandlerContext ctx) {
        final Channel connChannel = connectionChannel(ctx);
        return null != connChannel.pipeline().get(SslHandler.class);
    }

    private static HttpScheme connectionScheme(ChannelHandlerContext ctx) {
        final HttpScheme scheme = connectionSchemeAttribute(ctx).get();
        return scheme == null ? HttpScheme.HTTP : scheme;
    }

    private static Attribute<HttpScheme> connectionSchemeAttribute(ChannelHandlerContext ctx) {
        final Channel ch = connectionChannel(ctx);
        return ch.attr(SCHEME_ATTR_KEY);
    }

    private static Channel connectionChannel(ChannelHandlerContext ctx) {
        final Channel ch = ctx.channel();
        return ch instanceof Http2StreamChannel ? ch.parent() : ch;
    }
}

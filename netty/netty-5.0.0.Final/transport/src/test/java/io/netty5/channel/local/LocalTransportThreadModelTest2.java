/*
 * Copyright 2012 The Netty Project
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
package io.netty5.channel.local;

import io.netty5.bootstrap.Bootstrap;
import io.netty5.bootstrap.ServerBootstrap;
import io.netty5.channel.Channel;
import io.netty5.channel.ChannelHandler;
import io.netty5.channel.ChannelHandler.Sharable;
import io.netty5.channel.ChannelHandlerContext;
import io.netty5.channel.MultithreadEventLoopGroup;
import io.netty5.util.ReferenceCountUtil;
import io.netty5.util.concurrent.Future;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalTransportThreadModelTest2 {

    private static final String LOCAL_CHANNEL = LocalTransportThreadModelTest2.class.getName();

    static final int messageCountPerRun = 4;

    @Test
    @Timeout(value = 15000, unit = TimeUnit.MILLISECONDS)
    public void testSocketReuse() throws Exception {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        LocalHandler serverHandler = new LocalHandler("SERVER");
        serverBootstrap
                .group(new MultithreadEventLoopGroup(io.netty5.channel.local.LocalHandler.newFactory()),
                        new MultithreadEventLoopGroup(io.netty5.channel.local.LocalHandler.newFactory()))
                .channel(LocalServerChannel.class)
                .childHandler(serverHandler);

        Bootstrap clientBootstrap = new Bootstrap();
        LocalHandler clientHandler = new LocalHandler("CLIENT");
        clientBootstrap
                .group(new MultithreadEventLoopGroup(io.netty5.channel.local.LocalHandler.newFactory()))
                .channel(LocalChannel.class)
                .remoteAddress(new LocalAddress(LOCAL_CHANNEL)).handler(clientHandler);

        serverBootstrap.bind(new LocalAddress(LOCAL_CHANNEL)).sync();

        int count = 100;
        for (int i = 1; i < count + 1; i ++) {
            Channel ch = clientBootstrap.connect().get();

            // SPIN until we get what we are looking for.
            int target = i * messageCountPerRun;
            while (serverHandler.count.get() != target || clientHandler.count.get() != target) {
                Thread.sleep(50);
            }
            close(ch, clientHandler);
        }

        assertEquals(count * 2 * messageCountPerRun, serverHandler.count.get() +
                clientHandler.count.get());
    }

    public void close(final Channel localChannel, final LocalHandler localRegistrationHandler) {
        // we want to make sure we actually shutdown IN the event loop
        if (localChannel.executor().inEventLoop()) {
            // Wait until all messages are flushed before closing the channel.
            if (localRegistrationHandler.lastWriteFuture != null) {
                localRegistrationHandler.lastWriteFuture.awaitUninterruptibly();
            }

            localChannel.close();
            return;
        }

        localChannel.executor().execute(() -> close(localChannel, localRegistrationHandler));

        // Wait until the connection is closed or the connection attempt fails.
        localChannel.closeFuture().awaitUninterruptibly();
    }

    @Sharable
    static class LocalHandler implements ChannelHandler {
        private final String name;

        public volatile Future<Void> lastWriteFuture;

        public final AtomicInteger count = new AtomicInteger(0);

        LocalHandler(String name) {
            this.name = name;
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            for (int i = 0; i < messageCountPerRun; i ++) {
                lastWriteFuture = ctx.channel().write(name + ' ' + i);
            }
            ctx.channel().flush();
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            count.incrementAndGet();
            ReferenceCountUtil.release(msg);
        }
    }
}

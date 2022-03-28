package stefan.learning.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.embedded.EmbeddedChannel;

import java.net.SocketAddress;

/**
 * @author stefan
 * @date 2022/3/28 14:24
 */
public class InHandlerTest {
    public static void main(String[] args) {
        ChannelInitializer initializer = new ChannelInitializer() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                channel.pipeline().addLast(new InHandlerDemo());
                channel.pipeline().addLast(new OutHandlerDemo());
            }
        };
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(initializer);

        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeInt(1);

        embeddedChannel.writeInbound(byteBuf);
        embeddedChannel.flush();
        embeddedChannel.writeOutbound(byteBuf);
        embeddedChannel.flush();

        embeddedChannel.close();

        // handlerAdded ---> channelRegistered ---> channelActive ---> channelRead ---> channelReadComplete
        // ---> channelInactive ---> channelUnregistered ---> handlerRemoved
        // 数据传输的入站回调过程为 channelRead ---> channelReadComplete

        // handlerAdded ---> read ---> write ---> flush ---> flush ---> handlerRemoved ---> close....

    }
    private static class OutHandlerDemo extends ChannelOutboundHandlerAdapter {
        public OutHandlerDemo() {
            super();
        }

        @Override
        public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
            super.bind(ctx, localAddress, promise);
            System.out.println("OutHandlerDemo::bind....");

        }

        @Override
        public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
            super.connect(ctx, remoteAddress, localAddress, promise);
            System.out.println("OutHandlerDemo::connect....");

        }

        @Override
        public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
            super.disconnect(ctx, promise);
            System.out.println("OutHandlerDemo::disconnect....");

        }

        @Override
        public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
            super.close(ctx, promise);
            System.out.println("OutHandlerDemo::close....");

        }

        @Override
        public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
            super.deregister(ctx, promise);
            System.out.println("OutHandlerDemo::deregister....");

        }

        @Override
        public void read(ChannelHandlerContext ctx) throws Exception {
            super.read(ctx);
            System.out.println("OutHandlerDemo::read....");

        }

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            super.write(ctx, msg, promise);
            System.out.println("OutHandlerDemo::write....");

        }

        @Override
        public void flush(ChannelHandlerContext ctx) throws Exception {
            super.flush(ctx);
            System.out.println("OutHandlerDemo::flush....");

        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            super.handlerAdded(ctx);
            System.out.println("OutHandlerDemo::handlerAdded....");

        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            super.handlerRemoved(ctx);
            System.out.println("OutHandlerDemo::handlerRemoved....");

        }
    }
    private static class InHandlerDemo extends ChannelInboundHandlerAdapter {
        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            super.handlerAdded(ctx);
            System.out.println("InHandlerDemo::handlerAdded....");

        }

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            ctx.fireChannelRegistered();
            System.out.println("InHandlerDemo::channelRegistered....");
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            ctx.fireChannelUnregistered();
            System.out.println("InHandlerDemo::channelUnregistered....");

        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.fireChannelActive();
            System.out.println("InHandlerDemo::channelActive....");
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            ctx.fireChannelInactive();
            System.out.println("InHandlerDemo::channelInactive....");
        }
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ctx.fireChannelRead(msg);
            System.out.println("InHandlerDemo::channelRead....");
        }
        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.fireChannelReadComplete();
            System.out.println("InHandlerDemo::channelReadComplete....");
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            ctx.fireUserEventTriggered(evt);
            System.out.println("InHandlerDemo::userEventTriggered....");

        }

        @Override
        public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
            ctx.fireChannelWritabilityChanged();
            System.out.println("InHandlerDemo::channelWritabilityChanged....");

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.fireExceptionCaught(cause);
            System.out.println("InHandlerDemo::exceptionCaught....");
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            super.handlerRemoved(ctx);
            System.out.println("InHandlerDemo::handlerRemoved....");

        }
    }
}

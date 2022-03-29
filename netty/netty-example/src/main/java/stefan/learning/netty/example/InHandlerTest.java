package stefan.learning.netty.example;

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
        // ChannelInitializer本身也是一个ChannelInboundHandlerAdapter
        // ChannelInitializer在完成了通道的初始化之后，为什么要将自己从流水线中删除呢？
        // 原因很简单，就是一条通道流水线只需要做一次装配工作。
        ChannelInitializer initializer = new ChannelInitializer() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
//                channel.pipeline().addLast(new InHandlerDemo("A"));
//                channel.pipeline().addLast(new InHandlerDemo("B"));
//                channel.pipeline().addLast(new InHandlerDemo("C"));


                channel.pipeline().addLast(new OutHandlerDemo("1"));
                channel.pipeline().addLast(new OutHandlerDemo("2"));

            }
        };
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(initializer);

        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeInt(1);

//        embeddedChannel.writeInbound(byteBuf);
//        embeddedChannel.flush();
        embeddedChannel.writeOutbound(byteBuf);
        embeddedChannel.flush();

        embeddedChannel.close();

        // handlerAdded ---> channelRegistered ---> channelActive ---> channelRead ---> channelReadComplete
        // ---> channelInactive ---> channelUnregistered ---> handlerRemoved
        // 数据传输的入站回调过程为 channelRead ---> channelReadComplete

        // handlerAdded ---> read ---> write ---> flush ---> flush ---> handlerRemoved ---> close....

    }
    private static class OutHandlerDemo extends ChannelOutboundHandlerAdapter {
        private String name;

        public OutHandlerDemo(String name) {
            this.name = name;
        }

        @Override
        public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
            System.out.println("OutHandlerDemo::bind...." + name);
//            super.bind(ctx, localAddress, promise);
        }

        @Override
        public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
            System.out.println("OutHandlerDemo::connect...." + name);
//            super.connect(ctx, remoteAddress, localAddress, promise);
        }

        @Override
        public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
            System.out.println("OutHandlerDemo::disconnect...." + name);
            super.disconnect(ctx, promise);
        }

        @Override
        public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
            System.out.println("OutHandlerDemo::close...." + name);
            super.close(ctx, promise);
        }

        @Override
        public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
            System.out.println("OutHandlerDemo::deregister...." + name);
//            super.deregister(ctx, promise);
        }

        @Override
        public void read(ChannelHandlerContext ctx) throws Exception {
            System.out.println("OutHandlerDemo::read...." + name);
//            super.read(ctx);
        }

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            System.out.println("OutHandlerDemo::write...." + name);
//            super.write(ctx, msg, promise);
        }

        @Override
        public void flush(ChannelHandlerContext ctx) throws Exception {
            System.out.println("OutHandlerDemo::flush...." + name);
            super.flush(ctx);
        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            System.out.println("OutHandlerDemo::handlerAdded...." + name);
            super.handlerAdded(ctx);
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            System.out.println("OutHandlerDemo::handlerRemoved...." + name);
            super.handlerRemoved(ctx);
        }
    }
    private static class InHandlerDemo extends ChannelInboundHandlerAdapter {
        private String name;

        public InHandlerDemo(String name) {
            this.name = name;
        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            System.out.println("InHandlerDemo::handlerAdded...." + name);
            super.handlerAdded(ctx);

        }

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            System.out.println("InHandlerDemo::channelRegistered...." + name);
            ctx.fireChannelRegistered();
            ctx.alloc().heapBuffer();
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            System.out.println("InHandlerDemo::channelUnregistered...." + name);

            ctx.fireChannelUnregistered();

        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("InHandlerDemo::channelActive...." + name);

            ctx.fireChannelActive();
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("InHandlerDemo::channelInactive...." + name);
            ctx.fireChannelInactive();
        }
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("InHandlerDemo::channelRead...." + name);
//            ctx.fireChannelRead(msg);
            super.channelRead(ctx, msg);
        }
        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            System.out.println("InHandlerDemo::channelReadComplete...." + name);
            ctx.fireChannelReadComplete();
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            System.out.println("InHandlerDemo::userEventTriggered...." + name);
            ctx.fireUserEventTriggered(evt);

        }

        @Override
        public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
            System.out.println("InHandlerDemo::channelWritabilityChanged...." + name);
            ctx.fireChannelWritabilityChanged();

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("InHandlerDemo::exceptionCaught...." + name);
            ctx.fireExceptionCaught(cause);
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            System.out.println("InHandlerDemo::handlerRemoved...." + name);
            super.handlerRemoved(ctx);

        }
    }
}

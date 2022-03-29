package stefan.learning.netty.example;

import io.netty.buffer.*;
import org.junit.Test;

import java.nio.charset.Charset;

/**
 * @author stefan
 * @date 2022/3/29 9:42
 */
public class ByteBufTest {
    public static void main(String[] args) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(9, 100);
        byteBuf.writeBytes(new byte[]{1, 2,3,4});
        System.out.println("writableBytes = " + byteBuf.writableBytes());
        System.out.println("isWritable = " + byteBuf.isWritable());

        System.out.println("readableBytes = " + byteBuf.readableBytes());

        System.out.println("alloc = " + byteBuf.alloc());

        byteBuf.retain();
    }

    @Test
    public void testHeapBuf() {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.heapBuffer();
        byteBuf.writeCharSequence("你好，徐同学", Charset.forName("utf-8"));
        if (byteBuf.hasArray()) {
            byte[] bytes = byteBuf.array();
            int offset = byteBuf.arrayOffset() + byteBuf.readerIndex();
            int len = byteBuf.readableBytes();
            System.out.println("arrayOffset = " + byteBuf.arrayOffset() + ", readerIndex=" + byteBuf.readerIndex() + ", readableBytes=" + byteBuf.readableBytes());
            String msg = new String(bytes, offset, len);
            System.out.println(msg);
        }
    }

    @Test
    public void testdirectBuf() {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.directBuffer();
        byteBuf.writeCharSequence("你好，徐同学", Charset.forName("utf-8"));
        if (byteBuf.hasArray()) {
            byte[] bytes = byteBuf.array();
            int offset = byteBuf.arrayOffset() + byteBuf.readerIndex();
            int len = byteBuf.readableBytes();
            System.out.println("arrayOffset = " + byteBuf.arrayOffset() + ", readerIndex=" + byteBuf.readerIndex() + ", readableBytes=" + byteBuf.readableBytes());
            String msg = new String(bytes, offset, len);
            System.out.println(msg);
            Unpooled.buffer();
        }
    }
}

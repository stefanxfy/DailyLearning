package stefan.learning.netty.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

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
    }
}

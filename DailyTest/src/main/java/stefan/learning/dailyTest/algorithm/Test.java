package stefan.learning.dailyTest.algorithm;

import java.nio.ByteBuffer;

public class Test {
    public static void main(String[] args) {
        // HeapByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // DirectByteBuffer
        ByteBuffer direct = ByteBuffer.allocateDirect(1024);
        // 直接 将一个byte数组放进 HeapByteBuffer
        ByteBuffer buffer1 = ByteBuffer.wrap("xxx".getBytes());
        buffer.put("xxx".getBytes());
        // 切换为读模式 limit = position;position=0
        buffer.flip();
        // 倒带 position=0
        buffer.rewind();
        // 做标记 mark = position
        buffer.mark();
        // mark重置 position = mark
        buffer.reset();
        // 清空 position = 0; limit = capacity
        buffer.clear();
        // 压缩，把读过的位置压缩一下
        // position = limit - position;limit = capacity
        buffer.compact();

    }
}

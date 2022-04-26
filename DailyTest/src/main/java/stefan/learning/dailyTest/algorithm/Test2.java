package stefan.learning.dailyTest.algorithm;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class Test2 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(1234));
    }

    public static class Acceptor implements Runnable {
        private ServerSocketChannel serverSocketChannel;
        @Override
        public void run() {
            while (true) {
                try {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    Selector selector = Selector.open();
                    socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    int select = selector.select();
                    if (select > 0) {
                        Set<SelectionKey> selectionKeys =  selector.selectedKeys();
                        for (SelectionKey key : selector.keys()) {
                            SocketChannel socketChannel1 = (SocketChannel) key.channel();
                            if (key.isReadable()) {
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                socketChannel1.read(buffer);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

package study.stefan.test;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author stefan
 * @date 2022/3/9 13:49
 */
public class Test {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        String connectString = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
        ZooKeeper zooKeeper = new ZooKeeper(connectString, 9999, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    System.out.println("会话建立成功");
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
//        String rt = zooKeeper.create("/node2", "helllo world".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        System.out.println(rt);
        CountDownLatch countDownLatch2 = new CountDownLatch(1);

        zooKeeper.create("/node5", "helllo world".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new AsyncCallback.StringCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, String name) {
                System.out.println(String.format("rc=%s;path=%s;ctx=%s;name=%s;", rc, path, ctx, name));
                countDownLatch2.countDown();
            }
        }, "context");
        countDownLatch2.await();
    }
}

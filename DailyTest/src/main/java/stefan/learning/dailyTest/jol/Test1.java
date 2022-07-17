package stefan.learning.dailyTest.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

public class Test1 {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(ClassLayout.parseClass(A.class).toPrintable());
        A a = new A();
        System.out.println("synchronized-before：");
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
//        synchronized (a) {
//            System.out.println("synchronized-lock：");
//            System.out.println(ClassLayout.parseInstance(a).toPrintable());
//        }
//        System.out.println("synchronized-unlock：");
//        System.out.println(ClassLayout.parseInstance(a).toPrintable());
        Thread t1 = new Thread() {
            @Override
            public void run() {
                synchronized (a) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("synchronized-thread-1-lock：");
                    System.out.println(ClassLayout.parseInstance(a).toPrintable());
                }
                System.out.println("synchronized-thread-1-un-lock：");
                System.out.println(ClassLayout.parseInstance(a).toPrintable());
            }
        };


        Thread t2 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a) {
                    System.out.println("synchronized-thread-2-lock：");
                    System.out.println(ClassLayout.parseInstance(a).toPrintable());
                }
                System.out.println("synchronized-thread-2-un-lock：");
                System.out.println(ClassLayout.parseInstance(a).toPrintable());
//                synchronized (a) {
//                    System.out.println("synchronized-thread-2-re-lock：");
//                    System.out.println(ClassLayout.parseInstance(a).toPrintable());
//                }
            }
        };

        Thread t3 = new Thread() {
            @Override
            public void run() {
                synchronized (a) {
                    System.out.println("synchronized-thread-3-lock：");
                    System.out.println(ClassLayout.parseInstance(a).toPrintable());
                }
            }
        };

        Thread t4 = new Thread() {
            @Override
            public void run() {
                synchronized (a) {
                    System.out.println("synchronized-thread-4-lock：");
                    System.out.println(ClassLayout.parseInstance(a).toPrintable());
                }
            }
        };

        Thread t5 = new Thread() {
            @Override
            public void run() {
                synchronized (a) {
                    System.out.println("synchronized-thread-5-lock：");
                    System.out.println(ClassLayout.parseInstance(a).toPrintable());
                }
                System.out.println("synchronized-thread-5-unlock：");
                System.out.println(ClassLayout.parseInstance(a).toPrintable());
            }
        };
        t1.start();
        t2.start();
        Thread.sleep(4000);
        t3.start();
        Thread.sleep(4000);
        t4.start();
        t5.start();


    }

    public static class A {
        boolean f;
    }
}

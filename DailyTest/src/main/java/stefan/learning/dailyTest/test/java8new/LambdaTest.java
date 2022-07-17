package stefan.learning.dailyTest.test.java8new;


import java.util.Set;
import java.util.TreeSet;

/**
 * lambda 语法
 */
public class LambdaTest {
    public static void main(String[] args) {
        Thread thread = new Thread(()->{
            System.out.println("测试l");

        });
        thread.start();
        Set<Integer> t1 = new TreeSet<>((o1, o2)-> {return o2-o1;});
        t1.add(1);
        t1.add(2);
        t1.add(3);
        System.out.println(t1);
    }
}

package stefan.learning.dailyTest.test.java8new;

import java.util.ArrayList;

/**
 * 引用已存在实现
 * 函数式接口的形参以及返回都需要和引用实现（静态方法、实例方法、构造器）保持一致
 */
public class ReferenceTest {
    public static void main(String[] args) {
//        MyFunction myFunction = ReferenceTest::pint;
//        myFunction.print(777);
//
//        MyFunction myFunction2 = System.out::println;
//        myFunction2.print(0000);
//
//        MyFunction myFunction3 = ArrayList::new;

        MyFunction myFunction4 = int[]::new;
        int[] arr = myFunction4.print(100);
        int[] ar = new int[100];
        System.out.println(arr.length);

    }

    public static void pint(Object s) {
        System.out.println(s);
    }
}

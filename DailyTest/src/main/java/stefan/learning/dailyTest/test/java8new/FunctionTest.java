package stefan.learning.dailyTest.test.java8new;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 函数式接口
 */
public class FunctionTest {
    public static void main(String[] args) {
        Consumer<Integer> consumer = (x) -> System.out.println("Consumer::" + x);
        consumer.accept(100);
        consumer.andThen((x) -> {x = x + 1;
            System.out.println("Consumer::andThen::" + x);}).accept(100);

        Supplier<Integer> supplier = () -> (int)(Math.random() * 100);
        System.out.println("supplier::" + supplier.get());

        Function<Integer, String> i2s = (i) -> i.toString();
        System.out.println(i2s.apply(100));
        System.out.println("Function::compose::" + i2s.compose((i) -> (Integer) (i = (Integer)i + 1)).apply(100));
        System.out.println("Function::andThen::" + i2s.andThen((s) -> s = s + "aaa").apply(100));

        Predicate<Integer> predicate = (i) -> i >= 100;
        System.out.println("Predicate::test::" + predicate.test(100));
        System.out.println("Predicate::negate::test::" + predicate.negate().test(100));
        System.out.println("Predicate::and::test::" + predicate.and((i) -> i < 200).test(201));
        System.out.println("Predicate::and::test::" + predicate.or((i) -> i < 200).test(201));

/*        MyFunction myFunction = (s) -> System.out.println("myFunction::" + s);
//        myFunction.print("jjsjskjdksjd");*/
    }
}

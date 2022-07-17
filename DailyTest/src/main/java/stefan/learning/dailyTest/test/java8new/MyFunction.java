package stefan.learning.dailyTest.test.java8new;

// @FunctionalInterface 声明式注解，
// 只是为了标识该接口是一个函数式接口，不加也可以
@FunctionalInterface
public interface MyFunction {
    int[] print(int s);
}

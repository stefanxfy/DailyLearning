package stefan.learning.dailyTest.test;

public class User implements Hum {
    private String name = "stefan";
    private static final String CITY = "xian";
    private int age = 26;

    public void play(String s) {
        String w = "stefan";
        int a = 1;
        int b = 2;
        int c = a + b;
        int f = 1;
        String d = "xxx";
        int e = run();
    }

    public int run() {
        System.out.println("run");
        return 1;
    }

    @Override
    public void eat() {
        System.out.println("eat");
    }
}

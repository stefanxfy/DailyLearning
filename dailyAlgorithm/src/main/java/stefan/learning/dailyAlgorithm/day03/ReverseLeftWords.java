package stefan.learning.dailyAlgorithm.day03;

/**
 * 剑指 Offer 58 - II. 左旋转字符串
 * 字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部。请定义一个函数实现字符串左旋转操作的功能。比如，输入字符串"abcdefg"和数字2，该函数将返回左旋转两位得到的结果"cdefgab"。
 *
 * 输入: s = "abcdefg", k = 2
 * 输出: "cdefgab"
 *
 * @author stefan
 * @date 2022/4/28 10:08
 */
public class ReverseLeftWords {
    /**
     * 遍历
     * @param s
     * @param n
     * @return
     */
    public String reverseLeftWords(String s, int n) {
        int len = s.length();

        if (n <= 0 || n >= len) {
            return s;
        }
        StringBuilder res = new StringBuilder();
        for (int i = n; i < len; i++) {
            res.append(s.charAt(i));
        }
        for (int i = 0; i < n; i++) {
            res.append(s.charAt(i));
        }
        return res.toString();
    }

    /**
     * 利用求余运算，可以简化代码。
     * 看着很酷，但是性能没有第一种好，因为多了%运算
     * @param s
     * @param n
     * @return
     */
    public String reverseLeftWords2(String s, int n) {
        StringBuilder res = new StringBuilder();
        for(int i = n; i < n + s.length(); i++) {
            res.append(s.charAt(i % s.length()));
        }
        return res.toString();
    }

    public static void main(String[] args) {
        ReverseLeftWords reverseLeftWords = new ReverseLeftWords();
        String res = reverseLeftWords.reverseLeftWords("abcdefg", 6);
        System.out.println(res);
    }
}

package stefan.learning.dailyTest.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 回文子串
 * 给你一个字符串 s ，请你统计并返回这个字符串中 回文子串 的数目。
 * 回文字符串 是正着读和倒过来读一样的字符串。
 * 子字符串 是字符串中的由连续字符组成的一个序列。
 * 具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被视作不同的子串。
 *
 * 示例 1：
 * 输入：s = "abc"
 * 输出：3
 * 解释：三个回文子串: "a", "b", "c"
 *
 * 示例 2：
 * 输入：s = "aaa"
 * 输出：6
 * 解释：6个回文子串: "a", "a", "a", "aa", "aa", "aaa"
 *
 * 提示：
 * 1 <= s.length <= 1000
 * s 由小写英文字母组成
 */
public class HuiWenTest {
    public static void main(String[] args) {
//        System.out.println(isHuiwen("a"));

        String s = "aaaaaa";
        List<String> list = huiwen(s);
        System.out.println(list.size());
    }

    public static List<String> huiwen(String s) {
        int length = s.length();
        List<String> rt = new ArrayList<String>();
        for (int i = 0; i < length; i++) {
            char c1 = s.charAt(i);
            // 一个字符的肯定是回文
            StringBuilder tmp = new StringBuilder();
            tmp.append(c1);
            rt.add(tmp.toString());
            // 字符组合
            for (int j = i + 1; j < length; j++) {
                char c2 = s.charAt(j);
                tmp.append(c2);
                String str = tmp.toString();
                if (isHuiwen(str)) {
                    rt.add(str);
                }
            }
        }
        return rt;
    }

    public static boolean isHuiwen(String s) {
        // aba, abc
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char cleft = s.charAt(i);
            char cright = s.charAt(len -i -1);
            if (cleft != cright) {
                return false;
            }
        }
        return true;
    }


}

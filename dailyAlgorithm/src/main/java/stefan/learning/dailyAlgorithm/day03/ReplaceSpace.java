package stefan.learning.dailyAlgorithm.day03;

/**
 * 剑指 Offer 05. 替换空格
 * 请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
 * 示例：
 * 输入：s = "We are happy."
 * 输出："We%20are%20happy."
 * https://leetcode-cn.com/leetbook/read/illustration-of-algorithm/50ywkd/
 *
 * @author stefan
 * @date 2022/4/28 9:59
 */
public class ReplaceSpace {
    public String replaceSpace(String s) {
        String replaceStr = "%20";
        int len = s.length();
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                res.append(replaceStr);
            } else {
                res.append(c);
            }
        }
        return res.toString();
    }

    public static void main(String[] args) {
        ReplaceSpace replaceSpace = new ReplaceSpace();
        String res = replaceSpace.replaceSpace("We are happy.");
        System.out.println(res);
    }
}

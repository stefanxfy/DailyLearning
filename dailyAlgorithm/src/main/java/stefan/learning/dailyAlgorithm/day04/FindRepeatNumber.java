package stefan.learning.dailyAlgorithm.day04;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FindRepeatNumber {
    /**
     * 执行用时：7 ms, 在所有 Java 提交中击败了14.00%的用户
     * 内存消耗：49.9 MB, 在所有 Java 提交中击败了35.89%的用户
     *
     * @param nums
     * @return
     */
    public int findRepeatNumber(int[] nums) {
        Set<Integer> set = new HashSet<Integer>();
        for (int num : nums) {
            if (set.contains(num)) {
                return num;
            }
            set.add(num);
        }
        return -1;
    }

    /**
     * 执行用时：2445 ms, 在所有 Java 提交中击败了5.01%的用户
     * 内存消耗：48.8 MB, 在所有 Java 提交中击败了62.10%的用户
     *
     * @param nums
     * @return
     */
    public int findRepeatNumber2(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    return nums[i];
                }
            }
        }
        return -1;
    }

    /**
     *
     * 执行用时：0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗：48.7 MB, 在所有 Java 提交中击败了64.34%的用户
     *
     * 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。
     * 所以 索引和num是1对多的关系，
     * 将num和它相等的索引位置的num交换，如果两个值相等，则直接返回
     *
     * @param nums
     * @return
     */
    public int findRepeatNumber3(int[] nums) {
        for (int i = 0; i < nums.length;) {
            int num = nums[i];
            if (num == i) {
                i++;
                continue;
            }
            int numTmp = nums[num];
            if (numTmp == num) {
                return num;
            }
            nums[i] = numTmp;
            nums[num] = num;
        }
        return -1;
    }
}

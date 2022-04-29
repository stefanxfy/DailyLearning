package stefan.learning.dailyAlgorithm.day04;

/**
 * 剑指 Offer 53 - II. 0～n-1 中缺失的数字
 * 一个长度为n-1的递增排序数组中的所有数字都是唯一的，并且每个数字都在范围0～n-1之内。在范围0～n-1内的n个数字中有且只有一个数字不在该数组中，请找出这个数字。
 *
 * 输入: [0,1,2,3,4,5,6,7,9,10]
 * 输出: 8
 * 思路：遍历 找到第一个索引和数字不相等，返回索引
 * @author stefan
 * @date 2022/4/29 16:46
 */
public class MissingNumber {
    public int missingNumber(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i) {
                return i;
            }
        }
        return nums.length;
    }

    public static int missingNumber2(int[] nums) {
        // [0,1,2,3,4,5,6,7,9,10,11]
        int i = 0, j = nums.length - 1;
        while (i <= j) {
            int mid = (i + j) >>> 1;
            if (nums[mid] == mid) {
                i = mid + 1;
            } else {
                j = mid - 1;
            }
        }
        return i;
    }

    public static void main(String[] args) {
        int[] nums = {0,1,2,3,4,5,6,7,9,10,11};
        int n = missingNumber2(nums);
        System.out.println(n);
    }

}

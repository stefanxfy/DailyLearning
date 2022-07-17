package stefan.learning.dailyAlgorithm.day04;

import java.util.HashMap;
import java.util.Map;

public class SearchCnt {
    public int search(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<Integer, Integer>();
        for (int num : nums) {
            Integer val = map.get(num);
            if (val == null) {
                val = 0;
            }
            map.put(num, val + 1);
        }
        Integer val = map.get(target);
        if (val == null) {
            return 0;
        }
        return val;
    }

    /**
     * 执行用时：0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗：44.4 MB, 在所有 Java 提交中击败了25.24%的用户
     * @param nums
     * @param target
     * @return
     */
    public int search2(int[] nums, int target) {
        int cnt = 0;
        int flag = 0;
        for (int num : nums) {
            if (flag == 2) {
                break;
            }
            if (num == target) {
                cnt++;
                flag = 1;
                continue;
            }
            if (flag == 1) {
                flag = 2;
            }
        }
        return cnt;
    }

    public static int search3(int[] nums, int target) {
        // 搜索右边界 right
        // 测试用例：
        //  0 1 2 3 4 5
        // [5,7,7,8,8,10]
        // target = 8
        int i = 0, j = nums.length - 1;
        while(i <= j) {
            int m = (i + j) / 2;
            if(nums[m] <= target) {
                i = m + 1;
            } else {
                j = m - 1;
            }
            // 当退出while时,j+1=i
        }
        int right = i;
        // 若数组中无 target ，则提前返回
        if(j >= 0 && nums[j] != target) return 0;
        // 搜索左边界 right
        i = 0; j = nums.length - 1;
        while(i <= j) {
            int m = (i + j) / 2;
            if(nums[m] < target) i = m + 1;
            else j = m - 1;
        }
        int left = j;
        return right - left - 1;
    }

    public static int search4(int[] nums, int target) {
        int low = 0, high = nums.length-1;
        // 使用二分查找法 找到 target 所在 index
        int targetIndex = -1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            if (nums[mid] > target) {
                high = mid -1;
            } else if (nums[mid] < target) {
                low = mid + 1;
            } else {
                targetIndex = mid;
                break;
            }
        }
        if (targetIndex == -1) {
            return 0;
        }
        // 遍历右边
        int rightCnt = 0;
        for (int i = targetIndex + 1; i < nums.length; i++) {
            if (nums[i] == target) {
                rightCnt++;
            } else {
                break;
            }
        }
        // 遍历左边
        int leftCnt = 0;
        for (int i = targetIndex - 1; i >= 0; i--) {
            if (nums[i] == target) {
                leftCnt++;
            } else {
                break;
            }
        }

        return rightCnt + leftCnt + 1;
    }

    public static void main(String[] args) {
        int[] nums = {5,7,7,8,8,10};
        int cnt = search3(nums, 8);
        System.out.println(cnt);
    }

}

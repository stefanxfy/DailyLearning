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

}

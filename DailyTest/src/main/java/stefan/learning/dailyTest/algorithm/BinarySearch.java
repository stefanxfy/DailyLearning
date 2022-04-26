package stefan.learning.dailyTest.algorithm;


public class BinarySearch {
    public static int binarySearch(int[] a, int key) {
        return binarySearch0(a, 0, a.length, key);
    }
    private static int binarySearch0(int[] a, int fromIndex, int toIndex, int key) {
        int low = fromIndex;
        int high = toIndex - 1;
        while (low <= high) {
            // 计算出 midVal
            int mid = (low + high) >>> 1;
            int midVal = a[mid];
            // key 大于 midVal，说明key可能在mid的右边
            if (midVal < key)
                low = mid + 1;
            else if (midVal > key)
                // key 小于 midVal 可能在mid左边
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1);  // key not found.
    }

}

package stefan.learning.dailyTest.suanfa;


public class BinarySearch2 {
    public static void main(String[] args) {
        int[] a = {0,1,2,4,5,6,7,8,9,10};
        int key = 4;
        int index = binarySearch(a, 0, a.length, key);
        System.out.println(index);
    }
    private static int binarySearch(int[] a, int fromIndex, int toIndex, int key) {
        int low = fromIndex;
        int high = toIndex -1;
        while (low <= high) {
            // 1. 计算出 mid
            int mid = (low + high) >>> 1;
            int midval = a[mid];

            if (key > midval) {
                low = mid +1;
            } else if (key < midval) {
                high = mid -1;
            } else {
                return mid;
            }
        }
        return -1;
    }

}

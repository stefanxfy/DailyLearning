package stefan.learning.dailyTest.algorithm;

public class QuickSort {
    public static void main(String[] args) {
        int[] arry = {2,10,4,6,5,9,0,7,1,8};
        quickSort(arry, 0, arry.length -1);
        for (int i : arry) {
            System.out.print(i + ",");
        }
    }
    /**
     * 快排的核心思想：随便找一个pivot，>=pivot放pivot右边，<=pivot放pivot左边
     * @param arry
     * @param l
     * @param r
     */
    public static void quickSort(int[] arry, int l, int r) {
        if (l >= r) {
            return;
        }
        int left = l,right= r;
        // 选择一个 pivot
        int pivot = arry[left];
        while (left < right) {
            // 先从右边开始比较，右边>pivot就将right向左移动
            while (left < right && arry[right] >= pivot) {
                //
                right--;
            }
            // 右边比pivot小，则将当前right对应的值与left对应值交换
            if (left < right) {
                arry[left] = arry[right];
            }
            // 比较左边，左边的值 <= pivot left就向右边移动
            while (left < right && arry[left] <= pivot) {
                left++;
            }
            // 左边比pivot大，则左边和右边交换
            if (left < right) {
                arry[right] = arry[left];
            }
            // 左右指针相撞，则将pivot赋值给left位置
            if (left >= right) {
                arry[left] = pivot;
            }
        }
        // 递归比较左半部分
        quickSort(arry, l, right-1);
        // 递归比较右半部分
        quickSort(arry, right+1, r);
    }


}

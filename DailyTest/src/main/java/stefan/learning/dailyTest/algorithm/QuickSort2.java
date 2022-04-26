package stefan.learning.dailyTest.algorithm;

public class QuickSort2 {
    public static void main(String[] args) {
        int[] arry = {2,10,4,6,5,9,0,7,1,8};
        quickSort(arry, 0, arry.length -1);
        for (int i : arry) {
            System.out.print(i + ",");
        }
    }

    public static void quickSort(int[] arry, int l, int r) {
        if (l >= r) {
            return;
        }
        int left = l, right = r;
        int pivot = arry[left];

        while (left < right) {
            while (left < right && arry[right] >= pivot) {
                right--;
            }
            if (left < right) {
                arry[left] = arry[right];
            }

            while (left < right && arry[left] <= pivot) {
                left++;
            }
            if (left < right) {
                arry[right] = arry[left];
            }

            if (left >= right) {
                arry[left] = pivot;
            }
        }

        quickSort(arry, l, right -1);
        quickSort(arry, right+1, r);
    }
}

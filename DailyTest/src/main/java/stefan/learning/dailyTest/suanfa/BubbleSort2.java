package stefan.learning.dailyTest.suanfa;

import java.util.Arrays;

public class BubbleSort2 {
    public static void main(String[] args) {
        int[] arry = {2,10,4,6,5,9,0,7,1,8};
        bubbleSort(arry);
        for (int i : arry) {
            System.out.print(i + ",");
        }
    }
    public static void bubbleSort(int a[]){
        // 1. 计算出至多需要多少轮排序
        int n = a.length - 1;
        for (int i = 0; i < n; i++) {
            // 可以不加flag，不加就会有无效排序
            // 如果一轮比较中没有交换数据，那么就认为都已经有序了，直接结束外面的循环。
            boolean flag = false;
            // 为什么要n-i，是因为每一轮最大的数字都被移到了最后
            for (int j = 0; j < n - i; j++) {
                if (a[j] > a[j+1]) {
                    // 前一个比后一个大，交换位置
                    a[j] = a[j] + a[j+1];
                    a[j+1] = a[j] - a[j+1];
                    a[j] = a[j] - a[j+1];
                    flag = true;
                }
            }
            if (!flag) break;
        }
    }
}

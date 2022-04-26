package stefan.learning.dailyTest.algorithm;

public class BubbleSort {
    public static void main(String[] args) {
        int[] arry = {2,10,4,6,5,9,0,7,1,8};
        bubbleSort(arry);
        for (int i : arry) {
            System.out.print(i + ",");
        }
    }
    public static void bubbleSort(int a[]){
        // 计算出最多需要多少轮排序
        int n = a.length -1;
        for(int i = 0; i< n;i++){
            boolean flag = false;
            for(int j=0;j<n-i;j++){
                if(a[j]>a[j+1]){
                    a[j] = a[j] + a[j+1];
                    a[j+1] = a[j] - a[j+1];
                    a[j] = a[j] - a[j+1];
                    flag = true;
                }
            }
            if(!flag)break;
        }
    }
}

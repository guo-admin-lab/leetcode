package sort;

import java.util.Arrays;

public class BubbleSort {

    public static void main(String[] args) {

        int[] arr = new int[]{3,2,3,4,9,10,8,7,7,4,1,0,-1,-3};
        int[] sort = sort(arr);
        for (int n : sort) {
            System.out.print(n);
            System.out.print(' ');
        }
    }

    public static int[] sort(int[] sourceArray) {
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
        // 1.控制循环次数，最多循环排序len-1次
        /** 1 2 3 4   len=4
         *  循环次数：i=1; i<=len-1; i++
         *  第1次交换排序时，max_j=len-1  [max_j-1] [max_j]
         *  第2次交换排序时，max_j=len-2  [max_j-1] [max_j]
         *  最后一次交换排序时，max_j=1=len-(len-1)  [0] [1]
         *  由于每次排序都会确定一个位置，所以max_j会依次递减：max_j<=len-i
         */
        for (int i=1; i<=arr.length-1; i++) {
            for (int j=1; j<=arr.length-i; j++) {
                if (arr[j-1] > arr[j]) {
                    int tmp = arr[j-1];
                    arr[j-1] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
        return arr;
    }

}

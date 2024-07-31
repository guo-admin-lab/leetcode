package sort;

import java.util.Arrays;

public class SelectionSort {

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
        /** 思路：选择排序
         *  核心：每次遍历数组，将最小|最大的数放到最前面
         *  数组初始状态：
         *      5 6 2 4 1 3
         *  最外层循环：
         *      控制待交换的起始位置, 最多到len-2即可
         *      i=0; i<=len-2; i++
         *  {
             *  第1次：j=0; j<=len-1; j++，
             *      找到arr[min_j]=arr[4]=1
             *      将arr[4]和arr[0]交换位置
             *      1 6 2 4 5 3
             *  第2次：j=1; j<=len-1; j++，
             *      找到arr[min_j]=arr[2]=2
             *      将arr[2]和arr[1]交换位置
             *      1 2 6 4 5 3
         *      总结：j=i; j<=len-1; j++,
         *          交换arr[min_j] arr[i]
         *  }
         *
         */
        int len = arr.length;
        for (int i=0; i<=len-2; i++) {
            // 设置初始最小值索引
            int minIndex = i;
            // 遍历寻找最小值索引
            for (int j=i+1; j<=len-1; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            // 交换最小值
            if (i != minIndex) {
                int tmp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = tmp;
            }
        }
        return arr;
    }

}

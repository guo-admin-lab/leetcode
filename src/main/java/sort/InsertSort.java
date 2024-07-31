package sort;

import java.util.Arrays;

public class InsertSort {

    public static void main(String[] args) {

        int[] arr = new int[]{-6,3,2,3,4,9,10,8,7,7,4,1,0,-1,-3};
        int[] sort = sort(arr);
        for (int n : sort) {
            System.out.print(n);
            System.out.print(' ');
        }

    }

    public static int[] sort(int[] sourceArray) {
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
        int len = arr.length;
        /** 思路：插入排序
         *  核心：每次遍历数组，将最小|最大的数放到最前面
         *  数组初始状态：
         *      5 6 2 4 1 3
         *  第1次：设置已排序范围：[0,0]  未排序起点索引：wsi=1
         *      遍历已排序范围：j=0; j>=0; j--，
         *          寻找arr[j] > arr[wsi]:
         *              交换 arr[j]、arr[wsi]
         *              更新索引：wsi=j
         *  第2次：设置已排序范围：[0,1]  未排序起点索引：wsi=2
         *      遍历已排序范围：j=1; j>=0; j--,
         *          同上
         *  第i次：设置已排序范围：[0,i-1]  未排序起点索引：wsi=i
         *      遍历已排序范围：j=i-1; j>=0; j--,
         *          同上
         *  i的取值范围：[1,len-1]
         */
        // 1.控制已排序|未排序区间段的分界线
        for (int i=1; i<=len-1; i++) {
            // 设置未排序起点索引
            int wsi = i;
            // 2.遍历已排序区间
            for (int j=i-1; j>=0; j--) {
                // 如果遇到更小的值，不用交换了，直接跳出
                if (arr[j] <= arr[wsi]) break;
                // 否则，wsi和j需要一直交换
                int tmp = arr[j];
                arr[j] = arr[wsi];
                arr[wsi] = tmp;
                // 更新索引
                wsi = j;
            }
        }
        return arr;
    }

}

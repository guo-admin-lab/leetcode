package sort;

import java.util.Arrays;

public class ShellSort {

    public static void main(String[] args) {

        int[] arr = new int[]{-1,-6,3,2,3,100,4,9,10,8,7,99,7,4,1,0,-1,-3, 0};
        int[] sort = sort1(arr);
        for (int n : sort) {
            System.out.print(n);
            System.out.print(' ');
        }

    }

    // 4层循环和3层循环的区别解释：https://blog.csdn.net/qq_38253837/article/details/79118631
    // https://blog.csdn.net/Currybeefer/article/details/107212477


    /** 思路：希尔排序
     *  1.按照折半长度分组
     *  2.组内进行插入排序
     *  初始数组状态：
     *       3 7 6 3 1 9 0   len=7
     *  第1步：step=7/2=3
     *  分组：3     3     0
     *         7     1
     *           6     9
     *  组内插入排序：
     *      第1组：
     *          第1次：已排序范围：[0,0]=[0,0+0*step]  未排序起始索引：wsi=0+1*step=3
     *              遍历已排序范围：j=0+0*step; j>=0; j=j-step
     *                  if arr[j] < arr[wsi]: break;
     *                  swap(arr[j], arr[wsi]);
     *                  wsi = j;
     *          第2次：已排序范围：[0,3]=[0,0+1*step]  未排序起始索引：wsi=0+2*step=6
     *              遍历已排序范围：j=0+1*step; j>=0; j=j-step
     *                  同上
     *          当 wsi索引>=数组长度时，当前组排序结束
     *      第2组：
     *          第1次：已排序范围：[1,1]=[1,1+0*step]  未排序起始索引：wsi=1+1*step=4
     *              遍历已排序范围：j=1+0*step; j>=0; j=j-step
     *                  同上
     *          第2次：已排序范围：[1,4]=[1,1+1*step]  未排序起始索引：wsi=1+2*step=7 (注意：索引7已经超过数组范围了)
     *              判断：if wsi >= len: 当前组排序结束
     *      总结：i、j均从1递增
     *      步长迭代规则：step=len/2; step>=1; step=step/2
     *      第i组：i控制分组个数：i=1; i<=step; i++ (分组个数一定不会超出步长step)
     *          j控制组内排序次数：j=1; j<=len-1; j++ (最多排序次数就是当step=1时候)
     *          第j次：已排序范围：[i-1,i-1]=[i-1,(i-1)+(j-1)*step]  未排序起始索引：wsi=(i-1)+j*step
     *              判断：if wsi >= len: 当前组排序结束，不需要再次排序
     *              遍历已排序范围：k=(i-1)+(j-1)*step; k>=0; k=k-step
     *                  if arr[k] < arr[wsi]: break;
     *                  swap(arr[k], arr[wsi])
     *                  wsi = k
     */
    public static int[] sort(int[] sourceArray) {
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
        int len = arr.length;
        // 步长迭代
        for (int step=len/2; step>=1; step/=2) {  // O(logn)
            // i控制分组个数
            for (int i=1; i<=step; i++) {  // O(logn)
                // j控制组内排序次数
                for (int j=1; j<=len-1; j++) {  // O(n)
                    // 未排序起始索引
                    int wsi = (i-1)+j*step;
                    // 如果未排序起始索引已超限，说明本组排序结束
                    if (wsi >= len) break;
                    // 遍历已排序范围
                    for (int k=(i-1)+(j-1)*step; k>=0; k-=step) {
                        // 如果已排序的最后一个数还比wsi小，说明wsi直接放到最后即可，不需要再排序了
                        if (arr[k] < arr[wsi]) break;
                        // 交换k和wsi的值，并更新wsi
                        int tmp = arr[k];
                        arr[k] = arr[wsi];
                        arr[wsi] = tmp;
                        wsi = k;
                    }
                }
            }
        }
        // 返回排序结果
        return arr;
    }

    /** 优化思路：上面方法有4层循环，是按照遍历每组排序。
     *  优化后，每个步长step的情况下，只遍历一次即可，交叉跨组排序
     *  样例如下：
     *  1 2 3 4 5 6 7 8 9
     *  第1次分组：step=9/2=4
     *  1       5       9
     *    2       6
     *      3       7
     *        4       8
     *  方法一：每组都遍历排序一遍
     *  第1组：
     *      第1次排序：1、5
     *      第2次排序：5、9
     *  第2组：
     *      第1次排序：2、6
     *  ...
     *  方法二：所有组看成一组，遍历排序一遍
     *  step=4
     *  第1次排序：1、5  未排序起始索引：wsi=0+step=4   已排序范围[0, wsi-step]=[0, (0+step)-step]=[0,0]
     *  第2次排序：2、6  未排序起始索引：wsi=1+step=5   已排序范围[0, wsi-step]=[0, (1+step)-step]=[0,1]
     *  第3次排序：3、7  未排序起始索引：wsi=2+step=6   已排序范围[0, wsi-step]=[0, (2+step)-step]=[0,2]
     *  第4次排序：4、8  未排序起始索引：wsi=3+step=7   已排序范围[0, wsi-step]=[0, (3+step)-step]=[0,3]
     *  第5次排序：5、9  未排序起始索引：wsi=4+step=8   已排序范围[0, wsi-step]=[0, (4+step)-step]=[0,4]
     *  第i次排序：      未排序起始索引：wsi=(i-1)+step 已排序范围[0, (i-1 + step)-step]=[0, i-1]
     *  总结：
     *      未排序起始索引最大值：wsi_max = len-1
     *          len - 1 = (i-1) + step
     *      解： len = i + step
     *          i = len - step
     *      i取值范围 [1, len-step]
     *
     *  所以，方法二比方法一少了一个循环
     */
    public static int[] sort1(int[] sourceArray) {
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
        int len = arr.length;
        // 步长迭代
        for (int step=len/2; step>=1; step/=2) {  // O(logn)
            // i控制分组个数（所有组看成一个组进行一次遍历排序）
            for (int i=1; i<=len-step; i++) {  // O(logn)
                // 未排序起始索引
                int wsi = (i-1)+step;
                // 遍历已排序范围
                for (int k=i-1; k>=0; k-=step) { // O(n)
                    // 如果已排序的最后一个数还比wsi小，说明wsi直接放到最后即可，不需要再排序了
                    if (arr[k] < arr[wsi]) break;
                    // 交换k和wsi的值，并更新wsi
                    int tmp = arr[k];
                    arr[k] = arr[wsi];
                    arr[wsi] = tmp;
                    wsi = k;
                }
            }
        }
        // 返回排序结果
        return arr;
    }

    /** 菜鸟教程的写法，和上面优化后的逻辑是一样的
     */
    public static void shellSort(int[] arr) {
        int length = arr.length;
        int temp;
        for (int step = length / 2; step >= 1; step /= 2) {
            for (int i = step; i < length; i++) {
                temp = arr[i];
                int j = i - step;
                while (j >= 0 && arr[j] > temp) {
                    arr[j + step] = arr[j];
                    j -= step;
                }
                arr[j + step] = temp;
            }
        }
    }

}

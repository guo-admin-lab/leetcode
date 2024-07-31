package Exam.HW;
import java.util.Scanner;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Test3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n = in.nextInt();
        long[] arr = new long[n];
        long sum = 0;
        for (int i=0; i<n; i++) {
            arr[i] = in.nextInt();
            sum += arr[i];
        }
        // 找到一个区间，让里面变化后的数字和变大，负数尽可能多
        // 如果全部是正数的话，不需要操作，直接输出全部和
        /**
         原数组和固定。
         选择区间内：
         变化后的和 < 变化前的和 （×）
         变化后的和 > 变化前的和  (√)
         记录最大值
         */
        int left = 0, right = 0;
        long maxNum = Integer.MIN_VALUE;  // 记录最大的差值
        int start = 0, end = 0;  // 记录使结果最大化操作区间的起点和终点
        long windowSum1 = 0;  // 变化前
        long windowSum2 = 0;  // 变化后
        while (right < n) {
            // 增大窗口
            long curNum = arr[right];
            windowSum1 += curNum;
            windowSum2 += Math.floor(curNum/2);  // 这里向下取整
            right++;

            // 当窗口内数字：sum(变化后) < sum(变化前)，缩小窗口
            while (left < right && windowSum2 < windowSum1) {
                long deleteNum = arr[left];
                windowSum1 -= deleteNum;
                windowSum2 -= Math.floor(deleteNum/2);
                left++;
            }

            // 执行到这里，一定满足：sum(变化后) > sum(变化前),记录最大值
            if (windowSum2 - windowSum1 > maxNum) {
                start = left;
                end = right-1;
                maxNum = windowSum2 - windowSum1;
            }
        }

        // 计算最终和
        /** sum(所有原值) = sum(其它数字原值) + sum(特定数字变化前)
         res = sum(其它数字原值) + sum(特定数字变化后)
         res - sum(所有原值) = maxNum;
         res = sum(所有原值) + maxNum;
         */
        System.out.println(sum + maxNum);

    }
}

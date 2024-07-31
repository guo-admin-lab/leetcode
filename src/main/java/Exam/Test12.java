package Exam;

import java.util.Scanner;

/**
 * 小美拿到了一个数组，她每次操作会将除了第x个元素的其余元素翻倍，一共操作了q次。请你帮小美计算操作结束后所有元素之和。
 * 由于答案过大，请对10^9+7取模。
 *
 * 第一行输入两个正整数n,q，代表数组的大小和操作次数。
 * 第二行输入n个正整数a_i，代表数组的元素。
 * 第三行输入一个正整数q，代表操作的次数。
 * 接下来的q行，每行输入一个正整数x_i，代表第i次操作未被翻倍的元素。
 * 1\leq n,q \leq 10^5
 * 1\leq x_i \leq n
 * 1\leq a_i \leq 10^9
 *
 * 一个整数，代表操作结束后所有元素之和模10^9+7的值。
 */

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Test12 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n = in.nextInt();  // 数组大小
        int q = in.nextInt();  // 操作次数
        long[] arr = new long[n];
        for (int i=0; i<n; i++) {
            arr[i] = in.nextLong();
        }
        long mod = 1000000007;
        for (int i=0; i<q; i++) {
            int indexSkip = in.nextInt() - 1;
            for (int j=0; j<n; j++) {
                if (indexSkip == j) continue;
                arr[j] = (2*arr[j]) % mod;
            }
        }
        long sum = 0;
        for (long num : arr) {
            sum = (sum + num) % mod;
        }
        System.out.print(sum);
    }
    /**
     public static long pow(long x, long n, long m) {
     if (n==0) return 1;
     long result = 1;
     while (n > 0) {
     if (n % 2 == 1) {
     result = (result * x) % m;
     }
     x = (x * x) % m;
     n = n/2;
     }
     return result;
     }
     */
}

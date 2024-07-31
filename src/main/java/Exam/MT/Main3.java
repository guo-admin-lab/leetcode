package Exam.MT;

import java.util.Scanner;

/**小美拿到了一个大小为n的数组，她希望删除一个区间后，使得剩余所有元素的乘积末尾至少有k个0。
 * 小美想知道，一共有多少种不同的删除方案？

        第一行输入两个正整数n,k。
        第二行输入n个正整数a_i，代表小美拿到的数组。
        1<= n,k <= 10^5
        1<= a_i <= 10^9

        一个整数，代表删除的方案数。

 思路：双指针
 末尾0的数量可以等价于数字中2的乘积数量和5的乘积数量的最小值，
 比如60 = 2^2 × 3 × 5^1，因此60的末尾0为min（1，2）= 1
 很显然，区间长度越大，末尾0的个数越多，
 因此符合单调性，可以考虑使用双指针算法求解
 我们首先统计整个数组的2的乘积数量和5的乘积数量，然后定义两个指针维护区间[l，r]
 若[l，r]区间中有min（cnt2，cnt5）> k，则答案累加n-r即可，l指针右移

 */

public class Main3 {
    // 方法一：滑动窗口方法
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 0.读取数据
        int arrSize = in.nextInt();
        int k = in.nextInt();
        int[] arr = new int[arrSize];
        int[] a2 = new int[arrSize];
        int[] a5 = new int[arrSize];
        for (int i=0; i<arrSize; i++) {
            arr[i] = in.nextInt();

            // 1.统计每个ai，能分解成2或5的因子个数
            while (arr[i] % 2 == 0) {
                arr[i] /= 2;
                a2[i]++;
            }
            while (arr[i] % 5 == 0) {
                arr[i] /= 5;
                a5[i]++;
            }
        }

        // 2.计算整个数组中，2和5分别的因子总数
        int cnt2 = 0, cnt5 = 0;
        for (int i=0; i<arrSize; i++) {
            cnt2 += a2[i];
            cnt5 += a5[i];
        }

        /**
         * a2 1 0 0 2 2
         * a5 0 1 0 0 1
         *    2 5 3 4 20
         * cnt2 = 5, cnt5 = 2   min = 2
         *
         *
         */

        // 2.滑动窗口
        int left = 0, right = 0;
        int ans = 0;
        while (right < arrSize) {
            // 扩大窗口
            right++;
            // 判断当前窗口内，2和5分别的因子总和是多少
            cnt2 -= a2[right];
            cnt5 -= a5[right];
            // 缩小窗口,条件：当窗口内数字使得所有元素乘积末尾0<k（等价于min(sum2,sum5)<k）
            while (left <= right && Math.min(cnt2, cnt5) < k) {
                cnt2 += a2[left];
                cnt5 += a5[left];
                left++;
            }
            // 这里的解都保证 min(cnt2,cnt5)>=k，所以能加入到结果中
            /**疑惑：这是一个可行解，为什么要把区间长度加进去呢？*/
            ans += right - left;
        }

        System.out.println(ans);

    }

}

// 方法二：前缀和 + 二分查找
class Main3_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 输入 n 和 k
        int n = scanner.nextInt();
        int k = scanner.nextInt();

        // 输入数组 A
        int[] A = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = scanner.nextInt();
        }

        // 计算每个元素的2和5的因子数量
        int[] f2s = new int[n];
        int[] f5s = new int[n];
        for (int i = 0; i < n; i++) {
            f2s[i] = getFactor2(A[i]);
            f5s[i] = getFactor5(A[i]);
        }

        // 计算所有元素的2和5的因子总数
        int all2 = sum(f2s);
        int all5 = sum(f5s);

        // 计算前缀和
        int[] pres2 = new int[n + 1];
        int[] pres5 = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            pres2[i] = pres2[i - 1] + f2s[i - 1];
            pres5[i] = pres5[i - 1] + f5s[i - 1];
        }

        int cnt = 0;
        for (int i = 0; i < n; i++) {
            int p2 = binarySearchRight(pres2, pres2[i] + all2 - k);
            int p5 = binarySearchRight(pres5, pres5[i] + all5 - k);
            cnt += Math.min(p2, p5) - i - 1;
        }

        System.out.println(cnt);
    }

    static int getFactor2(int a) {
        int f2 = 0;
        while (a != 0 && a % 2 == 0) {
            f2++;
            a /= 2;
        }
        return f2;
    }

    static int getFactor5(int a) {
        int f5 = 0;
        while (a != 0 && a % 5 == 0) {
            f5++;
            a /= 5;
        }
        return f5;
    }

    static int sum(int[] arr) {
        int result = 0;
        for (int num : arr) {
            result += num;
        }
        return result;
    }

    static int binarySearchRight(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }
}





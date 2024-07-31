public class Test70 {
}

class Solution70 {

    public int climbStairs(int n) {
        return method2(n);
    }

    // 方法一：反向动态规划
    /** 思路：
     dp[i]: 处在第i阶台阶处，有dp[i]种方法可以到达楼顶
     状态转移方程：第i台阶处，可以选择向上爬1个或2个楼梯
     dp[i] = dp[i+1] + dp[i+2]
     基础条件：
     dp[n] = 0
     dp[n-1] = 1
     dp[n-2] = 3
     */
    public int method1(int n) {
        /** 注意：这里由于最开始没有处在台阶上，所以需要有n+1个数 */
        int[] dp = new int[n+1];
        dp[n] = 1;
        dp[n-1] = 1;
        for (int i=n-2; i>=0; i--) {
            dp[i] = dp[i+1] + dp[i+2];
        }
        return dp[0];
    }

    // 方法二：正向动态规划
    /** 思路：
     dp[i]：爬到第i台阶，有dp[i]种办法
     状态转移方程：爬到第i台阶，可以从下面1个台阶或者2个台阶处爬上来
     dp[i] = dp[i-1] + dp[i-2]
     基础条件：
     dp[0] = 1
     dp[1] = 1
     dp[2] = dp[0] + dp[1] = 2
     */
    public int method2(int n) {
        if (n == 1) return 1;
        int p0 = 1, p1 = 1, p2 = 2;
        for (int i=3; i<=n; i++) {
            p0 = p1;
            p1 = p2;
            p2 = p0 + p1;
        }
        return p2;
    }

    // 方法三：递归版动态规划（labuladong的解法）
    public int method3(int n) {
        return 0;
    }

    // https://leetcode.cn/problems/climbing-stairs/solutions/286022/pa-lou-ti-by-leetcode-solution/?envType=study-plan-v2&envId=top-100-liked
    // 方法四：通项公式
    public int method4(int n) {
        return 0;
    }

    // 方法五：矩阵快速幂
    public int method5(int n) {
        return 0;
    }

}

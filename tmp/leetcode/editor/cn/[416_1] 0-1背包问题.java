class Solution {

    // https://labuladong.online/algo/dynamic-programming/knapsack1/#%E5%8A%A8%E8%A7%84%E6%A0%87%E5%87%86%E5%A5%97%E8%B7%AF

    // 矩阵填表案例：https://blog.csdn.net/weixin_41710054/article/details/108024053

    public int fun(int W, int N, int[] wt, int[] val) {
        /**
         * 横坐标表示重量，纵坐标表示物品个数，
         *  dp表格按照从上到下，从左到右填充
         *  第i行的值，都是从第i-1行中拿取
         *
         * dp[i][j]: 只放前i个物品时，当前j的背包容量可积累的最大价值
         * 状体转移方程：
         *    j < wt[i], dp[i][j] = dp[i-1][j]  当前容量j < 当前i物品体积，放不下，只能选择不放, 最大价值同上
         *    j >= wt[i], dp[i][j] = max ( dp[i-1][j-wt[i]] + val[i-1] , dp[i-1][j])  当前容量j>=当前i物品体积，可以放下，选择放物品i  或  不放物品i 的最大价值
         */
        int[][] dp = new int[N+1][W+1];
        // base case 当前0个物品，或背包容量为0时，最大价值为0
        for (int i=0; i<=N; i++) dp[i][0] = 0;
        for (int j=0; j<=W; j++) dp[0][j] = 0;

        for (int i=1; i<=N; i++) {  // 表示只放前i个物品时
            for (int j=1; j<=W; j++) {  // 表示当前背包容量为j时
                if (j < wt[i]) {
                    dp[i][j] = dp[i-1][j];
                } else {
                    dp[i][j] = Math.max(dp[i-1][j-wt[i]]+val[i-1], dp[i-1][j]);  // 注意，放入当前物品i的真实索引是i-1
                }
            }
        }
        return dp[N][W];
    }

}

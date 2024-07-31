
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public int coinChange(int[] coins, int amount) {
        return fun(coins, amount);
//        return method2(coins, amount);
    }

    public int fun(int[] coins, int amount) {
        /**
         * dp[i]: 组成 金额i 所需要的最少硬币数量
         * 状态转移方程：
         *  列举能凑成 金额i 的所有情况，找到最小数量
         *  for coin in coins
         *      preAmount = i - coin (如果凑成 金额i 的硬币中有 coin)
         *      看看 除去这个coin硬币 的总金额最少需要多少数量的硬币
         * base case:
         *  dp[0]=
         */
        int[] dp = new int[amount+1];
        // 每种 金额i 最多可以由 amount个[1元硬币]组成
        int maxAmount = amount+1;  // 设置一个不可能的数
        Arrays.fill(dp, maxAmount);
        // 填充dp表
        dp[0] = 0;
        for (int i=1; i<=amount; i++) {
            for (int coin : coins) {
                int preAmount = i - coin;
                if (preAmount < 0) continue;  // 说明coin>i，不可能存在这种情况
                dp[i] = Math.min(dp[i], dp[preAmount]+1);
            }
        }
        return dp[amount]==maxAmount ? -1 : dp[amount];
    }

    // 方法一：回溯

    // 方法二：动态规划（自顶向下）
    /**  思路：
     *      定义 mincnt = fun(amount)：凑出amount的金额，最少需要的硬币数量
     */
    int[] memo;
    public int method1(int[] coins, int amount) {
        int cLen = coins.length;
        // 初始化备忘录
        memo = new int[amount+1];  // 里面存放最小值
        Arrays.fill(memo, Integer.MAX_VALUE);  // 这个备忘录有点错误，因为最终超时了

        int mincnt = dp1(coins, amount);
        return mincnt == Integer.MAX_VALUE ? -1 : mincnt;
    }
    public int dp1(int[] coins, int amount) {
        // base case
        if (amount < 0) return -1;
        if (amount == 0) return 0;

        if (memo[amount] != Integer.MAX_VALUE) return memo[amount];
        // 试硬币
        for (int coin : coins) {
            int subcnt = dp1(coins, amount-coin);
            if (subcnt == -1) continue;
            memo[amount] = Math.min(memo[amount], subcnt + 1);
        }
        /** 检测更新备忘录的值，即使最终匹配失败了，还是需要更新掉初始值，不然下次还会重复计算一次 */
        if (memo[amount] == Integer.MAX_VALUE) memo[amount] = -1;
        return memo[amount];
    }

    // 方法三：动态规划（自底向上）
    /** 思路：
     *      定义 dp[i] : 凑出amount的金额，最少需要的硬币数量
     *      状态转移方程：
     *          dp[i] = min( dp[i-coin1]+1, dp[i-coin2]+1 )
     */
    public int method2(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        /** 注意：
         *      这里不要初始化成Integer.MAX_VALUE，因为Integer.MAX_VALUE+1就变成最小值了
         *      这里不要初始化成coins.length+1，因为coins是可以复选的，可以超过coins.length*/
        int maxcnt = amount+1;
        Arrays.fill(dp, maxcnt);
        dp[0] = 0;
        for (int curAmount=1; curAmount<=amount; curAmount++) {
            for (int coin : coins) {
                int preAmount = curAmount - coin;
                if (preAmount < 0) continue;
                dp[curAmount] = Math.min( dp[curAmount], dp[preAmount]+1 );
            }
        }
        return dp[amount]==maxcnt ? -1 : dp[amount];
    }

}
//leetcode submit region end(Prohibit modification and deletion)

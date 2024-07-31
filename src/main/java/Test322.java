import java.util.Arrays;

public class Test322 {

    public static void main(String[] args) {
        Solution322 solution322 = new Solution322();
        int[] coins = {186,419,83,408};
        int amount = 6249;
        int i = solution322.coinChange(coins, amount);
        System.out.println(i);
    }

}

class Solution3221 {
    int[] memo;

    public int coinChange(int[] coins, int amount) {
        memo = new int[amount + 1];
        // dp 数组全都初始化为特殊值
        Arrays.fill(memo, -666);
        return dp(coins, amount);
    }

    int dp(int[] coins, int amount) {
        if (amount == 0) return 0;
        if (amount < 0) return -1;
        // 查备忘录，防止重复计算
        if (memo[amount] != -666)
            return memo[amount];

        int res = Integer.MAX_VALUE;
        for (int coin : coins) {
            // 计算子问题的结果
            int subProblem = dp(coins, amount - coin);

            // 子问题无解则跳过
            if (subProblem == -1) continue;
            // 在子问题中选择最优解，然后加一
            res = Math.min(res, subProblem + 1);
        }
        // 把计算结果存入备忘录
        memo[amount] = (res == Integer.MAX_VALUE) ? -1 : res;
        return memo[amount];
    }
}


//leetcode submit region begin(Prohibit modification and deletion)
class Solution322 {

    public int coinChange(int[] coins, int amount) {
        return method1(coins, amount);
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
        Arrays.fill(memo, Integer.MAX_VALUE);

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
            if (subcnt != -1 && subcnt != Integer.MAX_VALUE) {
                memo[amount] = Math.min(memo[amount], subcnt + 1);
            }
        }
        return memo[amount];
    }

    // 方法三：动态规划（自底向上）



}
//leetcode submit region end(Prohibit modification and deletion)


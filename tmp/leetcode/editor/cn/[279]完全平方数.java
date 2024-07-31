
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public int numSquares(int n) {
        return fun(n);
//        return method1(n);
    }

    public int fun(int n) {
        /**
         * dp[i]: 和为 i 的完全平方数最小数量
         * 状态转移方程：
         *  for j=1; j*j<=i; j++ 列举和=i的所有情况
         *      当 j*j 这个平方数在组成和为 i 的平方数元素中时，
         *      需要看看 组成 i-j*j 这个数，最少可以由多少个元素组成
         *      在所有元素中选一个最小值
         * base case：
         *  dp[0] = 0
         */
        int[] dp = new int[n+1];
        /** 求最小值，初始化最大值
         *  某个数最多由n个1*1组成
         */
        Arrays.fill(dp, n);
        // 填充dp表
        dp[0] = 0;
        for (int i=1; i<=n; i++) {
            for (int j=1; j*j<=i; j++) {
                int prei = i - j*j;
                // 如果i - j*j小于0，说明组成i的元素中不可能含有j*j，跳过
                if (prei < 0) continue;
                dp[i] = Math.min(dp[i], dp[prei]+1);
            }
        }
        return dp[n];
    }

    // 方法一：动态规划（自底向上）
    /**  思路：
     *      类似于  零钱兑换 ，
     *      看 n 是否能由 1,4,9,16。。。等最少个数的数字组成
     *      定义 dp[i] : 最少用多少个完全平方数 可以 构成 数字i
     * */
    public int method1(int n) {
        int[] dp = new int[n+1];
        Arrays.fill(dp, n+1);
        dp[0] = 0;
        dp[1] = 1;
        for (int curi=2; curi<=n; curi++) {
            for (int subi=1; subi*subi<=curi; subi++) {
                int prei = curi - subi*subi;
                if (prei < 0) continue;
                dp[curi] = Math.min(dp[curi], dp[prei]+1);
            }
        }
        return dp[n];  // 由于最终一定能用1来构成结果，所以不需要判断
    }

    // 方法二：数学方法
    // https://leetcode.cn/problems/perfect-squares/solutions/822940/wan-quan-ping-fang-shu-by-leetcode-solut-t99c/?envType=study-plan-v2&envId=top-100-liked
    public int method2(int n) {
        return 0;
    }
}
//leetcode submit region end(Prohibit modification and deletion)


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int rob(int[] nums) {
        return fun(nums);
//        return method1(nums);
    }

    public int fun(int[] nums) {
        /**
         * dp[i]: 0..i范围内可以偷的最大金额
         * 状态转移方程：
         *  dp[i] = max (
         *      dp[i-2] + nums[i],  偷第i家
         *      dp[i-1]  不偷第i家
         *  )
         *  base case:
         *  dp[0]: nums[0]
         *  dp[1]: max(nums[0], nums[1])
         *  dp[2]:
         */

        if (nums.length == 1) return nums[0];

        int dp0 = nums[0];
        int dp1 = Math.max(nums[0], nums[1]);
        for (int i=2; i<nums.length; i++) {
            int dp2 = Math.max(
                    dp0 + nums[i],
                    dp1
            );
            dp0 = dp1;
            dp1 = dp2;
        }
        return dp1;
    }

    // 错误方法：间隔偷
    /**  错误原因：
     [2,1,1,2]
     间隔偷最大是 3，
     但偷两个2，最大是4
     */
    public int error_method(int[] nums) {
        int sum1 = 0;
        int sum2 = 0;
        for (int i=0; i<nums.length; i++) {
            if ((i&1) == 0) sum1 += nums[i];
            else sum2 += nums[i];
        }
        return Math.max(sum1, sum2);
    }

    // 方法一：动态规划：从小向大(正向遍历)
    /**  思路：
     *      dp[i]: 前i家（包括第i家）最多可以偷的钱
     *      状态转移方程：偷到第i家时，需要判断一下是否要偷这第i家，两种选择：
     *                      1.偷第i家，那么就不能偷第i-1家，dp[i] = dp[i-2] + nums[i]
     *                      2.不偷第i家，那么可以偷第i-1家，dp[i] = dp[i-1]
     *                      看看这两种方法哪种钱多
     *      基础条件：
     *          dp[1] = nums[0]
     *          dp[2] = max( nums[0], nums[1])
     *          dp[3] = max( dp[2], dp[1]+nums[2])
     */
    public int method1(int[] nums) {
        if (nums.length == 1) return nums[0];
        if (nums.length == 2) return Math.max(nums[0], nums[1]);

        int d0 = nums[0], d1 = Math.max(nums[0], nums[1]), d2 = Math.max( d0 + nums[2], d1);
        for (int i=2; i<nums.length; i++) {
            d2 = Math.max( d0 + nums[i], d1);
            d0 = d1;
            d1 = d2;
        }
        return d2;
    }

    // 方法二：动态规划:从小向大（倒着遍历）
    /** 思路：
     *  dp[i]: 从 第i家 开始偷，最多可以偷的钱
     *  状态转移方程：
     *      dp[i] = Math.max( dp[i+1], dp[i+2] + nums[i])
     *
     *  基础条件：
     *      dp[n-1] = nums[n-1]
     *      dp[n-2] = Math.max( nums[n-1], nums[n-2] )
     *      dp[n-3] = Math.max( dp[n-2], dp[n-1] + nums[n-3])
     */
    public int method2(int[] nums) {
        return 0;
    }

    // 方法三：labuladong类似于递归动态规划的写法(todo 还有 337-打家劫舍III)
    // https://labuladong.github.io/algo/di-er-zhan-a01c6/yong-dong--63ceb/yi-ge-fang-f3df7/
    /** 这种递归的写法，其实就是用【递归】代替【for循环】 */
    public int method3(int[] nums) {
        class Solution3 {
            // 备忘录
            private int[] memo;
            // 主函数
            public int rob(int[] nums) {
                // 初始化备忘录
                memo = new int[nums.length];
                Arrays.fill(memo, -1);
                // 强盗从第 0 间房子开始抢劫
                return dp(nums, 0);
            }

            // 返回 dp[start..] 能抢到的最大值
            private int dp(int[] nums, int start) {
                if (start >= nums.length) {
                    return 0;
                }
                // 避免重复计算
                if (memo[start] != -1) return memo[start];

                int res = Math.max(dp(nums, start + 1),
                        nums[start] + dp(nums, start + 2));
                // 记入备忘录
                memo[start] = res;
                return res;
            }
        }

        return -1;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

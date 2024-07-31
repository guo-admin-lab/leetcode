
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    // 区别于  53-最大子数组和

    public int maxProduct(int[] nums) {
        return fun(nums);
//        return method1(nums);
    }

    public int fun(int[] nums) {
        /**
         * dp[i]: 索引以i结尾的最大连续子数组和
         * 状态转移方程：
         *  dp[i] = max {
         *      dp[i-1] * dp[i],
         *      dp[i]
         *  }
         */

        if (nums.length == 1) return nums[0];

        /** 为什么需要tmp数组？解释如下
         * -2， 3，   -4
         *      i-1   i
         *  dp[i-1] = 3
         *  tmp[i-1] = -6
         *
         *  dp[i] = tmp[i-1] * dp[i]
         */
        int[] tmp = new int[nums.length];
        int[] dp = new int[nums.length];
        for (int i=0; i<dp.length; i++) {
            dp[i] = nums[i];
        }

        // 填充dp表
        dp[0] = nums[0];
        dp[1] = Math.max(dp[0]*nums[1], nums[1]);
        tmp[1] = Math.min(dp[0]*nums[1], nums[1]);
        for (int i=2; i<nums.length; i++) {
            dp[i] = Math.max(dp[i-1]*nums[i], tmp[i-1]*nums[i]);
            dp[i] = Math.max(dp[i], nums[i]);

            tmp[i] = Math.min(dp[i-1]*nums[i], tmp[i-1]*nums[i]);
            tmp[i] = Math.min(tmp[i], nums[i]);
        }

        // 找出最大值
        int maxRes = Integer.MIN_VALUE;
        for (int i=0; i<dp.length; i++) {
            maxRes = Math.max(maxRes, dp[i]);
        }

        return maxRes;
    }


    // 暴力求解（前缀积的效率和这个一样，因为也得计算两两的差值）
    public int method0(int[] nums) {
        int maxres = Integer.MIN_VALUE;
        for (int i=0; i<nums.length; i++) {
            int sum = nums[i];
            maxres = Math.max(maxres, sum);
            for (int j=i+1; j<nums.length; j++) {
                sum *= nums[j];
                maxres = Math.max(maxres, sum);
            }
        }
        return maxres;
    }

    // 方法一：动态规划
    /**  思路：
     *      dp[i] : 以 nums[i] 结尾的 最大子数组积 为 dp[i]
     *      状态转移方程：
     *          dp[i] = Math.max(dp[i-1]*nums[i], nums[i])
     *
     *   注意：不能用 nums[i] 的正负来判断状态转移，因为 负负 可以得正
     *
     *   疑惑：这样的状态转移方程，能得到 [0..j1,j2..i] j1..j2之间的数字吗？
     *   答案：可以，因为假如  dp[j1]=nums[j1]，dp[j2] = dp[j1]*nums[j2]，那这样的话，dp[j2] = nums[j1]*nums[j2]，这就是中间的乘积了
     *
     *   错误点原因：
     *      [-2,3,-4]
     *      -2, 3, -4（按照上述状态转移方程的话）
     *      -2, 3, 24（实际的 最大子数组积 的值）
     *   问题在于：负负得正
     *
     *   解决方法：
     *      在维护 【最大子数组积】的同时，维护【最小子数组积】，来应对 【负负得正】的情况
     *
     *   新的状态转移方程：
     *      dp_max[i] = max(dp_max[i-1]*nums[i], dp_min[i-1]*nums[i], nums[i]);
     *      dp_min[i] = min(dp_max[i-1]*nums[i], dp_min[i-1]*nums[i], nums[i]);
     */
    public int method1(int[] nums) {

        if (nums.length == 1) return nums[0];

        int dp_max0 = nums[0], dp_max1;
        int dp_min0 = nums[0], dp_min1;

        int maxres = dp_max0;

        for (int i=1; i<nums.length; i++) {
            dp_max1 = Math.max(Math.max(dp_max0*nums[i], dp_min0*nums[i]), nums[i]);
            dp_min1 = Math.min(Math.min(dp_max0*nums[i], dp_min0*nums[i]), nums[i]);

            maxres = Math.max(maxres, dp_max1);

            dp_max0 = dp_max1;
            dp_min0 = dp_min1;
        }

        return maxres;

    }

    // 方法二：贪心策略 + 滑动窗口
    /**  错误思路：
     *      扩大窗口：nums[i]>0
     *      缩小窗口：nums[i]<=0
     *   思路是错误的：因为负负还可以得正（区别于最大子数组和，因为求和的话，num<0就一定会产生负影响）
     *
     *   错误思路：
     *      扩大窗口：window_sum > 0
     *      缩小窗口：window_sum <= 0
     *   错误原因：因为负负还可以得正（区别于最大子数组和，因为求和的话，num<0就一定会产生负影响）
     */
    public int method2(int[] nums) {
        return 0;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

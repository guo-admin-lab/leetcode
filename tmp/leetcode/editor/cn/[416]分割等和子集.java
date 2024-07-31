
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    // 属于【子集背包】问题
    // https://labuladong.github.io/algo/di-er-zhan-a01c6/bei-bao-le-34bd4/jing-dian--43be3/

    public boolean canPartition(int[] nums) {
        return fun(nums);
//        return method3(nums);
    }

    public boolean fun(int[] nums) {
        /**
         * dp[i][j]: 只取前i个数字时，能否构成和为j？
         * 状态转移方程：
         *  dp[i][j] = dp[i-1][j] 当前数字和j < 当前数字大小nums[i-1]时候，无法加入
         *  dp[i][j] = dp[i-1][j] || dp[i-1][ j-nums[i-1] ]当前数字和j >= 当前数字大小nums[i-1]时，选择加入当前数字i, 或者不加入当前数字i
         */
        int sum = 0;
        for (int num: nums) sum += num;
        if ((sum&1) == 1) return false;
        int halfSum = sum / 2;

        boolean[][] dp = new boolean[nums.length+1][halfSum+1];
        // base case 注意：j是从1开始，因为j=0时候，dp[0][0] = true,因为数字和为0不需要任何数字
        for (int i=0; i<=nums.length; i++) dp[i][0] = true; // 当数字和j=0时候，任何前i个数字都可以构成
        for (int j=1; j<=halfSum; j++) dp[0][j] = false; // 当可选前i=0个数字的时候，都不可能构成数字和j>0

        for (int i=1; i<=nums.length; i++) {
            for (int j=1; j<=halfSum; j++) {
                if (j < nums[i-1]) {
                    dp[i][j] = dp[i-1][j];
                } else {
                    dp[i][j] = dp[i-1][j] || dp[i-1][ j-nums[i-1] ];
                }
            }
        }

        return dp[nums.length][halfSum];
    }

    // 方法一：回溯排列  超时，应该是因为指数级
    /**  思路：
     *      1.计算数组的总和sum（如果 sum 是 奇数，则一定不能分成两半）
     *      2.排序
     *      3.回溯组合（有重复+不可复选）
     *      4.找到回溯和等于sum/2，看剩余值的和是否等于sum/2
     */
    int half;
    boolean[] visited;
    public boolean method1(int[] nums) {
        int sum = 0;
        for (int num : nums) sum += num;
        if ((sum & 1) == 1) return false;

        half = sum/2;
        visited = new boolean[nums.length];

        Arrays.sort(nums);

        return track(nums, 0);

    }
    int trackSum = 0;
    // 回溯列举组合
    public boolean track(int[] nums, int start) {

        // base case
        if (trackSum > half) return false;
        if (trackSum == half) {
            int otherSum = 0;
            for (int i=0; i<nums.length; i++) {
                if (visited[i]) continue;
                otherSum += nums[i];
            }
            return otherSum==half;
        }

        // 回溯
        for (int i=start; i<nums.length; i++) {
            if (i > start && nums[i] == nums[i-1]) continue;
            // 做选择
            visited[i] = true;
            trackSum += nums[i];
            // 回溯
            boolean ok = track(nums, i+1);
            if (ok) return true;
            // 取消选择
            trackSum -= nums[i];
            visited[i] = false;
        }

        return false;
    }

    // 方法二：动态规划
    /** 思路：
     *      1. dp[i][j] = true : 前i个数字中，存在一种选择，使得目标和=j
     *      2. 状态转移方程：
     *          dp[i][j]
     */
    public boolean method2(int[] nums) {
        int nLen = nums.length;
        int sum = 0;
        for (int num : nums) sum += num;
        if ((sum & 1) == 1) return false;
        sum /= 2;
        boolean[][] dp = new boolean[nLen+1][sum+1];
        // 1.初始化dp
        for (int i=0; i<=nLen; i++) dp[i][0] = true;  // 当目标和=0时，任何数字组合都能使其成立
        for (int j=1; j<=sum; j++) dp[0][j] = false;  // 当没有候选数字的时候，不可能使得目标和=j(j>0)
        // 2.动态规划填充dp表
        /** 过程演示：
         *  [1,2,3,6]  target = sum/2 = 6
         *  i|j 0 1 2 3 4 5 6
         *  0   t f f f f f f
         *  1   t t f f f f f
         *  2   t t t
         *  3   t
         *  4   t
         */
        for (int i=1; i<=nLen; i++) {
            for (int j=1; j<=sum; j++) {
                if (j - nums[i - 1] < 0) {
                    // 背包容量不足，不能装入第 i 个物品
                    dp[i][j] = dp[i - 1][j];
                } else {
                    // 装入或不装入背包
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i - 1]];
                }
            }
        }
        // 返回结果
        return dp[nLen][sum];
    }

    // 方法三：动态规划空间压缩
    // 因为每次dp只用到了上一行的数据
    public boolean method3(int[] nums) {
        int nLen = nums.length;
        int sum = 0;
        for (int num : nums) sum += num;
        if ((sum & 1) == 1) return false;
        sum /= 2;
        boolean[] dp = new boolean[sum+1];
        /** 过程演示：
         *  [1,2,3,6]  target = sum/2 = 6
         *  i|j 0 1 2 3 4 5 6
         *  0   t f f f f f f
         *  1
         *  2
         *  3
         *  4
         */
        // 1.初始化dp
        dp[0] = true;
        for (int i=1; i<=sum; i++) dp[i] = false;
        // 2.填充dp表,遍历行(i)
        for (int i=1; i<=nLen; i++) {
            for (int j = sum; j >= 0; j--) {
                if (j - nums[i-1] >= 0) {
                    dp[j] = dp[j] || dp[j - nums[i-1]];
                }
            }
        }
        return dp[sum];
    }
}
//leetcode submit region end(Prohibit modification and deletion)

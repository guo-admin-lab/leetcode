
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int maxSubArray(int[] nums) {
        return method7(nums);
    }

    public int fun(int[] nums) {
        /**
         * dp[i]: 以索引i结尾
         */
    }

    // 滑动窗口、动态规划、前缀和
    // https://labuladong.github.io/algo/di-er-zhan-a01c6/zi-xu-lie--6bc09/dong-tai-g-ce18b/#%E5%89%8D%E7%BC%80%E5%92%8C%E6%80%9D%E8%B7%AF
    // 分治法
    // https://leetcode.cn/problems/maximum-subarray/solutions/9058/dong-tai-gui-hua-fen-zhi-fa-python-dai-ma-java-dai/?envType=study-plan-v2&envId=top-100-liked

    // 方法一：暴力循环  O(n^2)  Time Limit Exceeded
    public int method1(int[] nums) {
        int maxRes = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                maxRes = Math.max(maxRes, sum);
            }
        }
        return maxRes;
    }

    // 方法二：前缀和 + 遍历  O(n^2)  Time Limit Exceeded
    public int method2(int[] nums) {
        int maxRes = Integer.MIN_VALUE;
        int nSize = nums.length;
        int[] preSum = new int[nSize+1];
        int pSize = preSum.length;
        // 初始化前缀和数组，preSum[i]表示: [0..i-1]的和 = [0..i-2] + nums[i-1]
        preSum[0] = 0;
        for (int i=1; i<pSize; i++) {
            preSum[i] = preSum[i-1] + nums[i-1];
        }
        // 遍历perSum数组，利用差值依次找出最大值
        // preSum[j] - preSum[i] = target
        int startIndex = 0;
        while (startIndex < pSize) {  // 控制起始索引
            for (int i=startIndex+1; i<pSize; i++) {
                int sum = preSum[i] - preSum[startIndex];
                maxRes = Math.max(maxRes, sum);
            }
            startIndex++;
        }
        return maxRes;
    }

    // 方法三：前缀和 + 哈希表
    /** 不可行，没法用哈希表优化查询，因为提前不知道最大值是多少，无法构造 preSum[i] - preSum[j] = 最大值
     *  具体区别于【560、和为K的子数组】 */
    public int method3(int[] nums) {
        return 0;
    }

    // 方法四：前缀和 + 求nums[i]结尾的最大子数组和  O(n) O(n)
    public int method4(int[] nums) {
        int nSize = nums.length;
        int[] preSum = new int[nSize+1];
        int pSize = preSum.length;
        // 1.初始化preSum数组   preSum[i]: 前i个元素的和，即nums[0..i)之和
        /**
         *  preSum[0]表示前0个元素的和，也就是零和。
         *  preSum[1]表示前1个元素的和，也就是nums[0]。
         *  preSum[2]表示前2个元素的和，也就是nums[0] + nums[1]，以此类推。
         */
        preSum[0] = 0;
        for (int i=1; i<=nSize; i++) {  // 注意：这里是i<=nSize
            preSum[i] = preSum[i-1] + nums[i-1];
        }
        /** 遍历preSum数组
         *  寻找以nums[i]结尾的最大子数组和：
         *      preSum[i+1]: nums[0..i]的总和
         *      min(preSum[0..i])中的最小值
         *  因为，preSum[i+1]的值固定，减去前面的值越小，最终得到的数越大
         *  所以，以nums[i]结尾的子数组最大值 = preSum[i+1] - min(preSum[0..i])
         */
        // 2.遍历nums数组，寻找以nums[i]结尾的最大子数组和
        int maxRes = Integer.MIN_VALUE;
        int minVal = Integer.MAX_VALUE;
        for (int i=0; i<nSize; i++) {
            // 维护 minVal 是 preSum[0..i] 的最小值
            minVal = Math.min(minVal, preSum[i]);  // minVal存储的和，一定都是从0开始增的
            // 以 nums[i] 结尾的最大子数组和就是 preSum[i+1] - min(preSum[0..i])
            maxRes = Math.max(maxRes, preSum[i+1] - minVal);
        }
        return maxRes;
    }

    // 方法五：滑动窗口  O(2n)  O(1)
    /**
     * 如果发现窗口内元素的和小于0（即windowSum < 0），则说明窗口内元素对最大子数组和产生了负贡献，此时需要缩小窗口并移动左侧边界，以去除负贡献的元素。
     * 每次缩小窗口前，更新窗口内元素的和（windowSum -= nums[left]）；
     * 将左侧边界向右移动一位（left++）；
     * 继续循环，直到窗口内元素的和变为非负数。
     */
    /** 核心思路：什么时候需要收缩窗口？
     *  当【窗口内的元素之和 < 0】时，需要收缩窗口，
     *  因为，此时窗口内元素对最大子数组和产生了【负贡献】，如果继续向窗口内添加元素，windowSum只会变小，不会变大，
     *  所以，此时需要收缩窗口，直到windowSum可以产生【非负贡献】为止。
     */
    public int method5(int[] nums) {
        int nSize = nums.length;

        int maxRes = Integer.MIN_VALUE;
        int windowSum = 0;

        int left = 0, right = 0;
        while (right < nSize) {
            windowSum += nums[right++];
            // 更新最大值
            maxRes = Math.max(maxRes, windowSum);
            // 当windowSum<0时，对结果产生了负贡献，需要收缩窗口
            while (windowSum < 0) {
                windowSum -= nums[left++];
            }
        }
        return maxRes;
    }

    // 方法六：动态规划  O(n)  O(n)
    /** 核心思路：如何定义dp[i]的含义？如何定义状态转移方程？
     * 思路一：
     *      定义：nums[0..i] 中的「最大的子数组和」为 dp[i]。
     *      状态转移方程：dp[i+1] = max( dp[i],  dp[i] + nums[i+1] )
     *      错误问题：子数组一定是连续的，但是按照当前 dp 数组定义，并不能保证 nums[0..i] 中的最大子数组与 nums[i+1] 是相邻的，也就没办法从 dp[i] 推导出 dp[i+1]。
     *      详情见：resources/img/53-最大子数组和.png
     * 思路二：
     *      定义：以 nums[i] 为结尾的「最大子数组和」为 dp[i]。
     *      状态转移方程：
     *          dp[i+1] 有两种「选择」，要么与前面的相邻子数组连接，形成一个和更大的子数组；要么不与前面的子数组连接，自成一派，自己作为一个子数组。
     *          dp[i+1] = max( dp[i] + nums[i+1],  nums[i+1] )
     */
    public int method6(int[] nums) {
        int nSize = nums.length;

        // dp[i]: 以nums[i]结尾的【最大子数组和】
        int[] dp = new int[nSize];
        int dSize = dp.length;

        // 填充dp[i] = max( dp[i-1] + nums[i],  nums[i] )
        dp[0] = nums[0];
        for (int i=1; i<dSize; i++) {
            dp[i] = Math.max(dp[i-1]+nums[i], nums[i]);
        }

        // 遍历dp，筛选最大值
        int maxRes = Integer.MIN_VALUE;
        for (int i=0; i<dSize; i++) {
            maxRes = Math.max(maxRes, dp[i]);
        }
        return maxRes;
    }

    // 方法七：动态规划 + 临时dp  O(n)  O(1)
    public int method7(int[] nums) {
        int nSize = nums.length;

        // dp_0: 以nums[i-1]结尾的【最大子数组和】
        int dp_0 = nums[0];
        int maxRes = Math.max(Integer.MIN_VALUE, dp_0);
        for (int i=1; i<nSize; i++) {
            // dp_1: 以nums[i]结尾的【最大子数组和】
            // dp[i] = max( dp[i-1] + nums[i],  nums[i] )
            int dp_1 = Math.max(dp_0+nums[i], nums[i]);
            dp_0 = dp_1;
            maxRes = Math.max(maxRes, dp_1);
        }
        return maxRes;
    }

    // 方法八：分治法  todo 待做
    public int method8(int[] nums) {
        // https://leetcode.cn/problems/maximum-subarray/solutions/9058/dong-tai-gui-hua-fen-zhi-fa-python-dai-ma-java-dai/?envType=study-plan-v2&envId=top-100-liked
        return 0;
    }




}
//leetcode submit region end(Prohibit modification and deletion)

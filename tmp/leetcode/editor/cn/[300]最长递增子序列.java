
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    // todo 拓展到二维：354. 俄罗斯套娃信封问题

    public int lengthOfLIS(int[] nums) {
        return fun(nums);
//        return method1(nums);
    }

    public int fun(int[] nums) {
        /**
         * dp[i]: 以i结尾的，最长递增子序列长度
         * 状态转移方程：
         *  dp[i] = {
         *      遍历之前所有的dp表，找到可与当前nums[i]组成的最长子序列长度
         *  }
         */
        if (nums.length == 1) return 1;

        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);

        // 填充dp表
        dp[0] = 1;
        for (int i=1; i<nums.length; i++) {
            // 遍历i之前的所有dp值，找最大值
            for (int pre=0; pre<i; pre++) {
                if (nums[i] > nums[pre]) {
                    dp[i] = Math.max(dp[i], dp[pre] + 1);
                }
            }
        }

        // 遍历，找出最大值
        int maxRes = 1;
        for (int val : dp) {
            maxRes = Math.max(maxRes, val);
        }
        return maxRes;
    }

    // 方法一：动态规划（自底向上） O(n^2)
    /**  思路：
     *      dp[i] : 以i结尾的最长递增子序列
     *      状态转移方程：
     *          dp[i] = nums[0.j.i)<num[i] ? dp[j]+1 : 1
     *      为什么要遍历去找 [0.j.i)呢/
     *      1 2 3 4 5 4 3 8
     *              5   3
     *      上述例子：如果只比较前一个数3，那么到8的最长递增子序列只是4，而前面的5可以让8的递增子序列更长
     */
    public int method1(int[] nums) {
        // 1.定义dp: dp[i]:以nums[i]结尾的数字的最长子序列长度
        int[] dp = new int[nums.length];
        // 2.base case : dp数组全部填充为1
        Arrays.fill(dp, 1);  // 初始化，每个数至少有自己作为递增序列
        // 状体转移方程
        for (int i=1; i<nums.length; i++) {
            for (int j=0; j<i; j++) {
                if (nums[j] < nums[i]) dp[i] = Math.max(dp[i], dp[j]+1);
            }
        }

        /** 注意：dp的最后一个数不一定是最长的子序列，需要再遍历一遍找最大值 */
        int maxres = 0;
        for (int i=0; i<dp.length; i++) {
            maxres = Math.max(maxres, dp[i]);
        }
        return maxres;
    }

    // 方法二：贪心 + 二分查找  O(n*logn)
    public int method2(int[] nums) {
        // https://leetcode.cn/problems/longest-increasing-subsequence/solutions/147667/zui-chang-shang-sheng-zi-xu-lie-by-leetcode-soluti/?envType=study-plan-v2&envId=top-100-liked
        // https://labuladong.github.io/algo/di-er-zhan-a01c6/zi-xu-lie--6bc09/dong-tai-g-6ea57/
        return 0;
    }

    /** 枚举遍历：错误方法
     错误原因：
     [0,1,0,3,2,3]
     对于这个数组，从第一个元素遍历的时候有多种递增子序列
     0,1,3（代码逻辑给出的结果）
     0,1,2,3（正解）
     也就是说如果选择了当前的元素，没办法保证是最长的
     */
    public int err_method(int[] nums) {
        if (nums.length == 0) return 0;
        int maxRes = 1;
        for (int i=0; i<nums.length; i++) {
            int tmpMax = nums[i];
            int cnt = 1;
            for (int j=i+1; j<nums.length; j++) {
                if (nums[j] > tmpMax) {
                    tmpMax = nums[j];
                    cnt++;
                }
            }
            if (cnt > maxRes) {
                maxRes = cnt;
            }
        }
        return maxRes;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

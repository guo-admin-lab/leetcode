
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    /**
    动态规划--回文串问题_回文字符串动态规划_写个堆排的博客-CSDN博客
    https://blog.csdn.net/weixin_61518137/article/details/131872359
    */

    public int countSubstrings(String s) {
        return method3(s);
    }

    // 方法一：暴力枚举 O(n^2) + 判断是否为回文串O(n) = O(n^3)

    // 方法二：中心扩散法O(n^2)

    // 方法三：动态规划O(n^2)
    public int method3(String s) {
        int n = s.length();
        char[] chars = s.toCharArray();
        // dp[i][j]: i..j子串是否为回文子串
        boolean[][] dp = new boolean[n][n];
        // 枚举子串，通过dp表直接判断是否为回文串
        /**
         * babbb
         *  i
         *    j
         * 注意，这里必须从后向前遍历
         *  因为，dp[i][j] 要用到 dp[i+1][j-1],
         *  所以，在遍历到 i 的时候，一定要先把 i+1的计算了，所以需要i从后向前遍历
         */
        int res = 0;
        for (int i=n-1; i>=0; i--) {
            for (int j=i; j<n; j++) {
                if (chars[i] != chars[j]) {
                    dp[i][j] = false;
                } else {
                    // 如果子串两端点字符相同，则分三种情况
                    if (i == j || i+1 == j || dp[i+1][j-1]) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = false;
                    }
                }
                if (dp[i][j]) {
                    res += 1;
                }
            }
        }

        // 返回结果
        return res;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

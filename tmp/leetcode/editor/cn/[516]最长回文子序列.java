
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public int longestPalindromeSubseq(String s) {
        return method1(s);
    }

    // 动态规划
    public int method1(String s) {
        int n = s.length();
        char[] chars = s.toCharArray();
        // dp[i][j]: i..j之间的最长回文子序列长度
        int[][] dp = new int[n][n];
        // dp[i][i]: 一个字符的最长回文子序列为1
        for (int i=0; i<n; i++) {
            dp[i][i] = 1;
        }
        // 填充dp表，从后向前填充，原因见【647-回文子串】中的动态规划逻辑
        for (int i=n-1; i>=0; i--) {
            for (int j=i+1; j<n; j++) {
                if (chars[i] == chars[j]) {
                    dp[i][j] = dp[i+1][j-1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
                }
            }
        }

        // 返回结果
        return dp[0][n-1];
    }
}
//leetcode submit region end(Prohibit modification and deletion)

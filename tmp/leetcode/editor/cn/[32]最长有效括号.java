
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int longestValidParentheses(String s) {

        // 方法一：动态规划
        public int method1(String s) {
            Stack<Integer> stk = new Stack<>();
            // dp[i] 的定义：记录以 s[i-1] 结尾的最长合法括号子串长度
            int[] dp = new int[s.length() + 1];
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '(') {
                    // 遇到左括号，记录索引
                    stk.push(i);
                    // 左括号不可能是合法括号子串的结尾
                    dp[i + 1] = 0;
                } else {
                    // 遇到右括号
                    if (!stk.isEmpty()) {
                        // 配对的左括号对应索引
                        int leftIndex = stk.pop();
                        // 以这个右括号结尾的最长子串长度
                        int len = 1 + i - leftIndex + dp[leftIndex];
                        dp[i + 1] = len;
                    } else {
                        // 没有配对的左括号
                        dp[i + 1] = 0;
                    }
                }
            }
            // 计算最长子串的长度
            int res = 0;
            for (int i = 0; i < dp.length; i++) {
                res = Math.max(res, dp[i]);
            }
            return res;
        }

        // 方法二：栈
        // 方法三：不需要额外的空间
        // https://leetcode.cn/problems/longest-valid-parentheses/solutions/314683/zui-chang-you-xiao-gua-hao-by-leetcode-solution/?envType=study-plan-v2&envId=top-100-liked

    }
}
//leetcode submit region end(Prohibit modification and deletion)

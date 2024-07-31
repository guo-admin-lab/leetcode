
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    // https://labuladong.github.io/algo/di-er-zhan-a01c6/dong-tai-g-a223e/dong-tai-g-f0a05/

    public boolean wordBreak(String s, List<String> wordDict) {
        return fun(s, wordDict);
//        return method2(s, wordDict);
    }

    public boolean fun(String s, List<String> wordDict) {
        /**
         * dp[i]:
         *  s[0..i)能否利用字典单词拼接出来
         *  等价于 (前i个字符) 能否利用字典单词拼接出来
         * 状态转移方程：类似 零钱兑换，但是字符串有顺序，不能用扣除法
         *  列举 s[0..j] j<i 范围，d[j]是否可行 && 单词字典中有没有s[j..i]这个单词
         */
        Set<String> wordSet = new HashSet<>(wordDict);

        boolean[] dp = new boolean[s.length()+1];
        Arrays.fill(dp, false);

        // 填充dp表
        dp[0] = true;
        for (int i=1; i<=s.length(); i++) {
            /** 注意：
             *  这里必须从j=0开始，首个搜索条件是 [true] && [leetcode有这个单词吗？]，这是对的
             *  如果从j=1开始，首个搜索条件是 [l能否被拼接?] && [eetcode有这个单词吗?]，这是不对的
             *  */
            for (int j=0; j<i; j++) {
                if (dp[j] && wordSet.contains(s.substring(j,i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

    // 方法一：回溯全排列（无重复，可复选）  超时
    StringBuilder builder = new StringBuilder();
    public boolean method1(String s, List<String> wordDict) {

        // base case（不做剪枝）
        // if (builder.toString().equals(s)) return true;
        // if (builder.length() > s.length()) return false;

        // base case（剪枝）
        if ("".equals(s)) return true;

        // 回溯递归
        for (String word : wordDict) {

            // base case（剪枝）
            if (word.length() > s.length()) continue;

            /** 剪枝思路：如果word是s的前缀，再去回溯；如果不是前缀，没必要去回溯 */
            boolean flag = true;
            for (int i=0; i<word.length(); i++) {
                if (word.charAt(i) != s.charAt(i)) {
                    flag = false;
                    break;
                }
            }
            if (!flag) continue;

            // 做选择
            builder.append(word);

            // 回溯
            boolean ok = method1(s.substring(word.length()), wordDict);
            if (ok) return true;

            // 取消选择
            builder.delete(builder.length()-word.length(), builder.length());
        }

        return false;
    }

    // 方法二：动态规划（自顶向下）
    /** 思路：
     *      ok = fun(s, starti) : s的子串是否能够被拼出
     *      */
    Map<String, Boolean> memo = new HashMap<>();
    public boolean method2(String s, List<String> wordDict) {
        // base case
        if ("".equals(s)) return true;

        // 检查备忘录
        if (memo.get(s) != null) return memo.get(s);

        // 遍历字典
        for (String word : wordDict) {

            if (word.length() > s.length()) continue;

            // 首先找到s的前缀word
            boolean flag = true;
            for (int i=0; i<word.length(); i++) {
                if (word.charAt(i) != s.charAt(i)) {
                    flag = false;
                    break;
                }
            }
            if (!flag) continue;

            // 判断去掉word前缀的s，是否能匹配成功
            boolean ok = method2(s.substring(word.length()), wordDict);
            if (ok) return true;
        }

        // 设置备忘录
        memo.put(s, false);

        return false;
    }

    // 方法三：动态规划（自底向上）
    /** 思路：
     *      dp[s] : 字符串s是否能被匹配出来
     *      状态转移方程：
     *          dp[s] = dp[s-word1] or dp[s-word2]
     *  换一种说法：
     *      dp[i] : 从0到i的s子串，能否被拼出来（不包含i）
     *      状态转移方程：
     *          dp[i] = dp[0..j] && 字典中有s[j..i]
     *          例如：
     *              abcdefg
     *              0  j i
     *            求：dp[i] : 看 s[0:i]是否能被拼出来
     *            dp[j] = true ：abcd可以被拼出来
     *            字典中包含 def : 所以dp[i] = true
     * */
    public boolean method3(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);
        /** 定义：dp[i] : [0,i) 的子串是否能被拼接出来 */
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;

        for (int i=1; i<=s.length(); i++) {
            // 遍历j in 0..i，找到 dp[j] = true && 字典中有j..i的前缀的值
            for (int j=0; j<i; j++) {
                if (dp[j] && wordSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[s.length()];
    }
}
//leetcode submit region end(Prohibit modification and deletion)

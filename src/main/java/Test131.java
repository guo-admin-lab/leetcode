import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Test131 {

    public static void main(String[] args) {

    }

}

class Solution131 {

    // 力扣官网
    // https://leetcode.cn/problems/palindrome-partitioning/solutions/639633/fen-ge-hui-wen-chuan-by-leetcode-solutio-6jkv/?envType=study-plan-v2&envId=top-100-liked

    // csdn详细讲解
    // https://blog.csdn.net/weixin_61518137/article/details/131872359

    // 自己总结
    // src/main/doc/回文串问题.docx

    public List<List<String>> partition(String s) {
        return method1(s);
    }

    // 方法一：回溯法
    List<List<String>> res = new ArrayList<>();
    LinkedList<String> onPath = new LinkedList<>();
    public List<List<String>> method1(String s) {

        // 初始化find数组，用于记忆化搜索
        find = new int[s.length()][s.length()];

        // 初始化动态规划dp数组
        dp = new boolean[s.length()][s.length()];
        for (int i=s.length()-1; i>=0; i--) {
            for (int j=i; j<s.length(); j++) { // j=i保证j在i的后面
                if (s.charAt(i) != s.charAt(j)) {
                    dp[i][j] = false;
                } else {
                    if (i == j || i+1 == j || dp[i+1][j-1]) dp[i][j] = true;
                    else dp[i][j] = false;
                }
            }
        }

        // 回溯
        dfs(s, 0);

        return res;

    }
    // 回溯
    public void dfs(String s, int start) {
        // base case
        if (start == s.length()) {
            res.add(new ArrayList<>(onPath));
            return;
        }

        // 从start开始，进行回溯
        for (int i=start; i<s.length(); i++) {
            // 如果当前子串不是回文串，跳过
            // if (!isFun1(s, start, i)) continue; // 基础判断方法
            // if (!isFun2(s, start, i)) continue;  // 记忆化搜索方法
            if (!isFun3(s, start, i)) continue;  // 动态规划预处理方法
            // 做选择
            onPath.add(s.substring(start, i+1));
            // 回溯
            dfs(s, i+1);
            // 撤销选择
            onPath.removeLast();
        }

    }

    // 判断是否为回文串——基础方法
    /** 会有重复计算 */
    public boolean isFun1(String s, int lo, int hi) {
        // if (lo > hi) raise error;
        if (lo == hi) return true;
        while (lo < hi) {
            if (s.charAt(lo) != s.charAt(hi)) return false;
            lo++; hi--;
        }
        return true;
    }

    // 判断是否为回文串——记忆化搜索
    /** 将已经判断过是否是回文串的坐标记录下来，避免重复计算 */
    int[][] find; // f[i][j] = 0 表示未搜索，1 表示是回文串，-1 表示不是回文串
    public boolean isFun2(String s, int lo, int hi) {
        // if (lo > hi) raise error;

        // 如果当前子串已经判断过是否为回文串
        if (find[lo][hi] != 0) {
            if (find[lo][hi] == 1) return true;
            else if (find[lo][hi] == -1) return false;
        }

        int _lo = lo;
        int _hi = hi;
        while (_lo < _hi) {
            if (s.charAt(_lo) != s.charAt(_hi)) {
                find[lo][hi] = -1;
                return false;
            }
            _lo++; _hi--;
        }
        find[lo][hi] = 1;
        return true;
    }
    
    // 判断是否为回文串——动态规划预处理
    boolean[][] dp;  // dp[i][j] 表示从i到j的这个子串，是否为回文串
    public boolean isFun3(String s, int i, int j) {
        return dp[i][j];
    }
}

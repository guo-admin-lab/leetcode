import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Test139 {

    public static void main(String[] args) {

        boolean[] xx = new boolean[2];
        System.out.println(xx[0]);

//        String ss = "aaaa";
//        String bb = "aa";
//
//        String substring = ss.substring(ss.length()+1);
//        System.out.println(substring);
//
//        StringBuilder builder = new StringBuilder();
//        builder.append("aaa");
//        builder.delete(builder.length()-3+1,builder.length());
//        System.out.println(builder);
    }

}


//leetcode submit region begin(Prohibit modification and deletion)
class Solution139 {

    public boolean wordBreak(String s, List<String> wordDict) {
        return method1(s, wordDict);
    }

    // 方法一：回溯全排列（无重复，可复选）
    StringBuilder builder = new StringBuilder();
    public boolean method1(String s, List<String> wordDict) {

        // base case
        if (builder.toString().equals(s)) return true;
        if (builder.length() > s.length()) return false;

        // 回溯递归
        for (String word : wordDict) {
            // 做选择
            builder.append(word);

            // 回溯
            boolean ok = method1(s, wordDict);
            if (ok) return true;

            // 取消选择
            builder.delete(builder.length()-word.length()+1, builder.length());
        }

        return false;
    }
}
//leetcode submit region end(Prohibit modification and deletion)


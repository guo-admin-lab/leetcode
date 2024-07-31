import java.util.ArrayList;
import java.util.List;

public class Test22 {

    public static void main(String[] args) {
        Solution22 solution22 = new Solution22();
        List<String> strings = solution22.generateParenthesis(3);
        for (String string : strings) {
            System.out.println(string);
        }
    }

}

class Solution22 {

    public List<String> generateParenthesis(int n) {
        return method2(n);
    }

    // 方法一：回溯枚举法
    public List<String> method2(int n) {
        // 1.生成所需数量的括号
        /** 注意：一定要保证kuohao字符串的有序性 */
        StringBuilder temp = new StringBuilder();
        for (int i=0; i<n; i++) temp.append('(');
        for (int i=0; i<n; i++) temp.append(')');
        String kuohao = temp.toString();

        // 2.初始化变量
        used = new boolean[kuohao.length()];

        // 3.回溯枚举
        dfs(kuohao);

        // 4.返回结果
        return res;

    }
    // 回溯枚举
    /** 全排列问题
     *  有重复；不可复选；
     */
    List<String> res = new ArrayList<>();
    StringBuilder onPath = new StringBuilder();
    boolean[] used;
    int cnt = 0; // 待匹配的(括号数量
    public void dfs(String kuohao) {

        // base case
        if (onPath.length() == kuohao.length()) {
            res.add(onPath.toString());
            return;
        }

        // 回溯枚举
        for (int i=0; i<kuohao.length(); i++) {
            // base case
            if (used[i]) continue;
            char cur = kuohao.charAt(i);
            // 如果当前待匹配的括号数量cnt为0，则说明路径前面没有(，所有不能在路径上添加)
            if (cur == ')' && cnt == 0) continue;
            // 如果当前半边括号和之前的相同了，为避免重复子树，需要跳过
            if (i>0 && kuohao.charAt(i-1) == kuohao.charAt(i) && !used[i-1]) continue;

            // 树枝前序
            used[i] = true;
            onPath.append(kuohao.charAt(i));
            if (cur == '(') cnt++;
            else if (cur == ')') cnt--;

            // dfs
            dfs(kuohao);

            // 树枝后序
            used[i] = false;
            onPath.deleteCharAt(onPath.length()-1);
            if (cur == '(') cnt--;
            else if (cur == ')') cnt++;

        }

    }

}

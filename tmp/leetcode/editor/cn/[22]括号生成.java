
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    // https://leetcode.cn/problems/generate-parentheses/solutions/192912/gua-hao-sheng-cheng-by-leetcode-solution/?envType=study-plan-v2&envId=top-100-liked
    // https://labuladong.github.io/algo/di-san-zha-24031/bao-li-sou-96f79/hui-su-sua-89170/

    public List<String> generateParenthesis(int n) {
        return method2(n);
    }

    // 方法一：回溯枚举法 + 看作特殊的全排列问题
    /** 效率不高，因为存在无效遍历 */
    public List<String> method1(int n) {
        // 1.生成所需数量的括号
        /** 注意：一定要保证kuohao字符串的有序性 */
        StringBuilder temp = new StringBuilder();
        for (int i=0; i<n; i++) temp.append('(');
        for (int i=0; i<n; i++) temp.append(')');
        String kuohao = temp.toString();

        // 2.初始化变量
        used = new boolean[kuohao.length()];

        // 3.回溯枚举
        dfs1(kuohao);  // 效率不高

        // 4.返回结果
        return res;

    }
    // 回溯枚举——效率不高
    /** 全排列问题
     *  有重复；不可复选；
     */
    List<String> res = new ArrayList<>();
    StringBuilder onPath = new StringBuilder();
    boolean[] used;
    int cnt = 0; // 待匹配的(括号数量
    public void dfs1(String kuohao) {

        // base case
        if (onPath.length() == kuohao.length()) {
            res.add(onPath.toString());
            return;
        }

        // 回溯枚举
        /** 这里效率不高，
         *  没必要去遍历整个字符串，
         *  因为树分支最多只有两条，( 或者 )
         *  */
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
            dfs1(kuohao);

            // 树枝后序
            used[i] = false;
            onPath.deleteCharAt(onPath.length()-1);
            if (cur == '(') cnt--;
            else if (cur == ')') cnt++;

        }

    }

    // 方法二：回溯枚举法 —— 看作二叉树
    int cnt2;
    int leftCnt = 0;  // 路径中左括号的数量
    int rightCnt = 0;  // 路径中右括号的数量
    public List<String> method2(int n) {
        // 1.初始化变量
        cnt2 = n;
        // 2.二叉树遍历
        dfs2('(');
        // 3.返回结果
        return res;
    }
    // 回溯枚举——效率高
    public void dfs2(char s) {
        /** s表示 '(' 或者 ')' */

        // base case写在下面

        // 前序
        onPath.append(s);
        if (s == '(') leftCnt++;
        else if(s == ')') rightCnt++;
        if (onPath.length() == 2*cnt2) {
            res.add(onPath.toString());
        }

        // 回溯枚举
        /** 这里没必要去遍历整个字符串，
         *  因为树分支最多只有两条，'(' 或者 ')'
         *  */
        // 1.满足加入'('的条件
        if (leftCnt < cnt2) dfs2('(');
        // 2.满足加入')'的条件
        if (rightCnt < leftCnt) dfs2(')');


        // 后序
        onPath.deleteCharAt(onPath.length()-1);
        if (s == '(') leftCnt--;
        else if (s == ')') rightCnt--;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

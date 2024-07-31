import com.sun.deploy.util.StringUtils;

import java.util.Stack;

public class Test394 {

    public static void main(String[] args) {

        String s1 = "3[a]2[bc]";
        String s2 = "3[a2[c]]";
        String s3 = "2[abc]3[cd]ef";
        String s4 = "abc3[cd]xyz";
        String s5 = "100[leetcode]";
        String s6 = "3[z]2[2[y]pq4[2[jk]e1[f]]]ef";
        Solution394 solution394 = new Solution394();
        System.out.println(solution394.decodeString(s6));
    }

}

class Solution394 {

    public String decodeString(String s) {
        return method2(s);
    }

    // 方法一：回溯递归
    public String method1(String s) {
        return null;
    }

    // 方法二：栈（其实就是把递归的方式变成了迭代的方式）
    public String method2(String s) {
        StringBuilder res = new StringBuilder();
        class Info {
            int cnt;
            StringBuilder vals = new StringBuilder();
        }
        Stack<Info> sk = new Stack<>();
        char[] chars = s.toCharArray();
        for (int i=0; i<chars.length; ) {
            // 如果是数字，就放到下一个 条件分支 中处理
            if (Character.isDigit(chars[i])) {
                i++;
            }
            // 压栈操作，针对3[ab]这种类型，最终保证下一个字符是]
            else if (chars[i] == '[') {
                Info info = new Info();

                // 1.向前面找当前循环的完整次数数字
                int prei = i - 1;
                StringBuilder digits = new StringBuilder();
                while (prei >= 0 && Character.isDigit(chars[prei])) digits.append(chars[prei--]);
                info.cnt = Integer.parseInt(digits.reverse().toString());

                // 2.向后面找要拼接的字符串
                /**  3[a2[c]] 需要截取的部分：
                 *      a
                 * */
                int nxti = i + 1;
                while(nxti<chars.length && chars[nxti] != ']' && chars[nxti] != '[' && !Character.isDigit(chars[nxti])) {
                    info.vals.append(chars[nxti++]);
                }

                // 3.入栈
                sk.push(info);

                // 4.索引更新
                i = nxti;
            }
            // 出栈，组织最终字符串，并把当前组织好的字符串赋值给上一个栈元素
            /**
             *   3[a2[c]]
             *   首先变成
             *   3[acc]
             * */
            else if (chars[i] == ']') {
                // if (sk.size() == 0) raise; // 异常情况
                // 分情况进行判断
                if (sk.size() == 1) {  // 说明当前info元素就是最外层的元素
                    Info info = sk.pop();
                    for (int j=0; j<info.cnt; j++) {
                        res.append(info.vals);
                    }
                } else {  // 此时栈中至少有两个元素
                    Info cur = sk.pop();
                    Info pre = sk.peek();  // 这两个顺序不能变，因为pop后的peek才是原先的倒数第二层
                    for (int j=0; j<cur.cnt; j++) {
                        pre.vals.append(cur.vals);
                    }
                }
                // 索引更新
                i++;
            }
            // 针对abc这种不需要循环次数拼接的字符串
            else if (chars[i] >= 'a' && chars[i] <= 'z') {
                // 分情况进行判断（看当前的字符是否是套在一个 '[' 的里面）
                if (sk.isEmpty()) {
                    res.append(chars[i]);
                } else {
                    Info info = sk.peek();
                    info.vals.append(chars[i]);
                }
                // 索引更新
                i++;

            }
            // 其它：抛异常
            else {
                // raise
            }

        }

        // 异常
        // if (!sk.isEmtpy()) raise
        return res.toString();
    }
}


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public boolean isValid(String s) {
        return method1(s);
    }

    // 错误方法：匹配个数记录法
    /** 错误原因：
     * 比如说只有一个括号的情况下 (()) 是有效的，但是多种括号的情况下， [(]) 显然是无效的。
     * 仅仅记录每种左括号出现的次数已经不能做出正确判断了。
     * 需要使用 【栈】这种数据结构。
     */
    public boolean method_error(String s) {
        Map<Character, Integer> left2cnt = new HashMap<Character, Integer>(){{
            put('(', 0);
            put('{', 0);
            put('[', 0);
        }};
        Map<Character, Character> right2left = new HashMap<Character, Character>(){{
            put(')', '(');
            put('}', '{');
            put(']', '[');
        }};
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (left2cnt.containsKey(c)) left2cnt.put(c, left2cnt.get(c)+1);
            else if (right2left.containsKey(c)) {
                char leftChar = right2left.get(c);
                if (left2cnt.get(leftChar) == 0) return false;
                else left2cnt.put(leftChar, left2cnt.get(leftChar)-1);
            }
        }
        return true;
    }

    // 方法一：栈 + 栈顶匹配
    /**
     *  ([]{})((()))
     */
    public boolean method1(String s) {
        Map<Character, Character> right2left = new HashMap<>(){{
            put(')', '(');
            put(']', '[');
            put('}', '{');
        }};
        Set<Character> lefts = new HashSet<>(right2left.values());
        Set<Character> rights = right2left.keySet();

        Stack<Character> sk = new Stack<>();

        char[] chars = s.toCharArray();
        for (char c : chars) {
            // 1.如果是左边括号，入栈
            if (lefts.contains(c)) {
                sk.push(c);
            }
            // 2.如果是右边括号，匹配并出栈
            else if (rights.contains(c)) {
                char left = right2left.get(c);
                // 查看【栈顶元素】是否和【当前右括号需要的左括号】相同
                if (sk.isEmpty()) return false; // 当前栈中没有元素了，右括号没有左括号与之匹配
                if (sk.peek() != left) return false; // 如果不匹配
                // 如果匹配，那么将栈顶元素出栈
                sk.pop();
            }
        }

        if (sk.isEmpty()) return true;
        // 3.最终如果栈不为空，说明存在左括号没有右括号与之匹配的情况，false
        else return false;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

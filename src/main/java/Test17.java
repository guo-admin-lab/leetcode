import java.util.*;

public class Test17 {

    public static void main(String[] args) {
        String digits = "";
        Solution17 solution17 = new Solution17();
        List<String> strings = solution17.letterCombinations(digits);
        System.out.println(strings);

    }

}

class Solution17 {

    public List<String> letterCombinations(String digits) {
        if (digits.equals("")) return null;
        method1(digits, 0);
        return res;
    }

    static Map<Character, String> digit2chars = new HashMap<Character, String>() {{
        put('2', "abc");
        put('3', "def");
        put('4', "ghi");
        put('5', "jkl");
        put('6', "mno");
        put('7', "pqrs");
        put('8', "tuv");
        put('9', "wxyz");
    }};

    // 方法一：回溯法
    /** 相当于规定了 每一层可选结点 的组合
     */
    List<String> res = new ArrayList<>();
    StringBuilder strTmp = new StringBuilder();
    public void method1(String digits, int index) {
        /** index规定：当前遍历的结点从 digit2char 中的哪一层来取 */

        // base case
        // 当遍历到最后一层时，添加结果
        if (index == digits.length()) {
            res.add(strTmp.toString());
            return;
        }

        char digit = digits.charAt(index);
        String charStr = digit2chars.get(digit);
        char[] chars = charStr.toCharArray();
        // 遍历当前层可选的字符
        for (int i=0; i<chars.length; i++) {
            // 树枝前序
            strTmp.append(chars[i]);

            method1(digits, index+1);

            // 树枝后序
            strTmp.deleteCharAt(strTmp.length()-1);
        }

    }
}

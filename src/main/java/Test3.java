import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Test3 {

    public static void main(String[] args) {
        /**
         * E B (B A N C) F A C B
         * A B C
         */
    }

}

class Solution3 {
    public int lengthOfLongestSubstring(String s) {
        return method1(s);
    }

    // 方法一：暴力遍历滑动  超时  O(n^4)
    public int method1(String s) {
        char[] chars = s.toCharArray();
        int cSize = chars.length;
        // 1.更新当前寻找的子串长度
        int maxLen = cSize;
        while (maxLen > 0) {
            List<Character> tmp = new ArrayList<>();
            // 2.更新当前滑动窗口的起始索引
            for (int i=0; i<cSize-maxLen+1; i++) {  // i+sLen不能超过字符串长度
                int start = i;
                int end = i + maxLen - 1;
                // 3.从索引i开始寻找长度为sLen的无重复子串
                tmp.clear();
                int j=start;
                for (; j<=end; j++) {
                    if (tmp.contains(chars[j])) break; // 如果有重复字符
                    tmp.add(chars[j]);
                }
                // 3.1.如果搜索到最后，则说明有值
                if (j > end) {
                    return maxLen;
                }
            }
            // 更新最大长度
            --maxLen;
        }
        return 0;
    }
}

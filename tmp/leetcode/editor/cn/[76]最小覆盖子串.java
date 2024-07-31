
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public String minWindow(String s, String t) {
        return method1(s, t);
    }

    // 方法一：滑动窗口 + 左右指针
    public String method1(String s, String t) {
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        int sSize = sChars.length;
        int tSize = tChars.length;

        int begin = 0;  // 记录最小覆盖子串的起始索引
        /** 注意：minLen的初始值一定要设最大*/
        int minLen = Integer.MAX_VALUE;  // 记录最小子串的长度

        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        int valid = 0;
        int left = 0, right = 0;
        // 1.初始化need
        for (char c : tChars) {
            need.put(c, need.getOrDefault(c,0)+1);
        }
        // 2.遍历s字符串
        while (right < sSize) {
            char c = sChars[right++];
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c,0)+1);
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }
            // 3.当window中包含了need中所需要的所有字符，开始收缩窗口
            /** 注意：valid==tSize是错误的，因为tChars中可能有重复的值*/
            while (valid == need.size()) {
                // 4.窗口内是满足条件的情况，在这里进行结果的更新
                if (right-left+1 < minLen) {
                    minLen = right-left+1;
                    begin = left;
                }
                char d = sChars[left++];
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) {
                        valid--;
                    }
                    window.put(d, window.get(d)-1);
                }
            }
        }
        // 5.返回结果
        return minLen==Integer.MAX_VALUE ? "" : s.substring(begin, begin+minLen-1);
    }
}
//leetcode submit region end(Prohibit modification and deletion)

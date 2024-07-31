
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int lengthOfLongestSubstring(String s) {
        return method2(s);
    }

    // 方法一：暴力遍历滑动  超时  O(n^4)
    public int method1(String s) {
        char[] chars = s.toCharArray();
        int cSize = chars.length;
        // 1.更新当前寻找的子串长度
        int maxLen = cSize;
        while (maxLen > 0) {
//            List<Character> tmp = new ArrayList<>();
            Set<Character> tmp = new HashSet<>();  // 这里可以用set存储加快查找速度，优化至O(n^3)，但仍然超时
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

    // 方法二：双指针收缩窗口滑动 O(n) 每个元素最多遍历到两次
    public int method2(String s) {
        int maxLen = 0;
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0;
        char[] chars = s.toCharArray();
        int cSize = chars.length;
        // 1.右指针滑动，收集元素
        while (right < cSize) {
            char c = chars[right];
            window.put(c, window.getOrDefault(c,0)+1);
            right++;
            // 2.当窗口map中有重复元素时，左指针滑动去除
            while (window.get(c) > 1) {
                char d = chars[left];
                window.put(d, window.get(d)-1);
                left++;
            }
            // 3.执行到这里，一定是没有重复元素的，更新最大无重复元素长度
            maxLen = Math.max(maxLen, right-left);
        }
        return maxLen;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

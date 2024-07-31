
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        return method1(s, p);
    }

    // 方法一：左右指针滑动窗口
    public List<Integer> method1(String s, String p) {
        List<Integer> res = new ArrayList<>();

        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();

        int left = 0, right = 0;
        int valid = 0;  // 标识有几个元素已经符合要求了，最终和pSize长度进行匹配

        char[] sChars = s.toCharArray();
        char[] pChars = p.toCharArray();
        int sSize = sChars.length;
        int pSize = pChars.length;
        // 0.初始化need
        for (char pc : pChars) {
            need.put(pc, need.getOrDefault(pc,0)+1);
        }
        // 1.右指针滑动，收集元素
        while (right < sSize) {
            char c = sChars[right];
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c,0)+1);
                if (need.get(c).equals(window.get(c))) {  // 如果c字符个数满足了
                    valid++;
                }
            }
            right++;
            // 2.当左右指针范围长度和目标字符串长度相同时，滑动左指针
            while (right - left == pSize) {
                // 3.记录匹配子串的起始索引
                /**
                 * 注意：这里不是 valid == pSize
                 * 测试用例: s="baa"  p="aa"  valid=1
                 */
                if (valid == need.size()) {
                    res.add(left); // 添加匹配索引
                }
                char d = sChars[left];
                if (need.containsKey(d)) {
                    if (need.get(d).equals(window.get(d))) {
                        valid--;
                    }
                    window.put(d, window.get(d) - 1);
                }
                left++;
            }
        }
        return res;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

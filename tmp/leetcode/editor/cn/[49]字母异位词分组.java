
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        // 思路：找到异位词的统一哈希编码
        // 方法一：排序（基本方法）
        /*Map<String, List<String>> resMap = new HashMap<>();
        for (String str : strs) {
            // 1.对 str 进行字母排序
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            // 2.将排序后的字符串作为唯一哈希key
            String key = new String(chars);
            List<String> values = resMap.getOrDefault(key, new ArrayList<>());
            values.add(str);
            resMap.put(key, values);
        }
        List<List<String>> res = new ArrayList<>(resMap.values());
        return res;*/

        // 方法二：排序（stream方法）
        /*Map<String, List<String>> resMap = Arrays.stream(strs).collect(Collectors.groupingBy(str -> {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            return new String(chars);
        }));
        List<List<String>> res = new ArrayList<>(resMap.values());
        return res;*/

        // 方法三：利用每个字符的出现次数进行编码（abcda->a2b1c1d1）
        Map<String, List<String>> codeToGroup = new HashMap<>();
        for (String str: strs) {
            // 1.对字符串进行编码
            String code = encode(str);
            // 2.把编码相同的字符串放到一起
            codeToGroup.putIfAbsent(code, new ArrayList<>());
            codeToGroup.get(code).add(str);
        }
        // 3.组织结果
        List<List<String>> res = new ArrayList<>(codeToGroup.values());
        return res;
    }

    // 根据字符串中每个字符出现的次数进行编码
    private String encode(String str) {
        char[] count = new char[26];
        char[] chars = str.toCharArray();
        for (char c : chars) {
            int index = c - 'a';
            count[index]++;
        }
        return new String(count);
    }
}
//leetcode submit region end(Prohibit modification and deletion)

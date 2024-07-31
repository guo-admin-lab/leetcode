
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public List<Integer> partitionLabels(String s) {
        return method1(s);
    }

    // 方法一：贪心策略
    // https://leetcode.cn/problems/partition-labels/solutions/455703/hua-fen-zi-mu-qu-jian-by-leetcode-solution/?envType=study-plan-v2&envId=top-100-liked
    public List<Integer> method1(String s) {
        int[] last = new int[26];
        int length = s.length();
        for (int i = 0; i < length; i++) {
            last[s.charAt(i) - 'a'] = i;
        }
        List<Integer> partition = new ArrayList<Integer>();
        int start = 0, end = 0;
        for (int i = 0; i < length; i++) {
            end = Math.max(end, last[s.charAt(i) - 'a']);
            if (i == end) {
                partition.add(end - start + 1);
                start = end + 1;
            }
        }
        return partition;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

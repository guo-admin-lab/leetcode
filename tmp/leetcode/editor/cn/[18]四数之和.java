
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 注意：这里target的类型需要为long，因为nums[i]=10^9=1,000,000,000，int类型=2^31=2,147,483,648
     * 所以 三个num[i]加起来就可能超过int的最大值了
     */
    public List<List<Integer>> fourSum(int[] nums, long target) {
        // 排序
        Arrays.sort(nums);
        // 递归 + 双指针
        return nSum(nums, 4, 0, target);
    }

    /**
     * n数之和
     * @param n 表示n数之和
     */
    public List<List<Integer>> nSum(int[] nums, int n, int start, long target) {
        List<List<Integer>> res = new ArrayList<>();
        int sz = nums.length;
        // 至少是 2Sum，且数组大小不应该小于 n
        // if (n < 2 || sz < n) return res;
        // base case
        if (n == 2) {
            int left = start, right = sz-1;
            while (left < right) {
                int leftNum = nums[left], rightNum = nums[right];
                int sum = leftNum + rightNum;
                if (sum < target) {
                    while (left < right && nums[left] == leftNum) left++;
                } else if (sum > target) {
                    while (left < right && nums[right] == rightNum) right--;
                } else {
                    res.add(new ArrayList<>(Arrays.asList(leftNum, rightNum)));
                    while (left < right && nums[left] == leftNum) left++;
                    while (left < right && nums[right] == rightNum) right--;
                }
            }
            return res;
        } else {
            /**
             * 注意：这里的 i=0 是错误的
             */
            // 1.遍历确定第一个数
            for (int i=start; i<sz; i++) {
                // 2.递归确定后面的数
                List<List<Integer>> resTmp = nSum(nums, n-1, i+1, target-nums[i]);
                // 3.添加结果
                for (List<Integer> tmp : resTmp) {
                    tmp.add(nums[i]);
                    res.add(tmp);
                }
                /**
                 * 4.注意：一定要去重
                 */
                while (i < sz-1 && nums[i] == nums[i+1]) i++;
            }
            return res;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)

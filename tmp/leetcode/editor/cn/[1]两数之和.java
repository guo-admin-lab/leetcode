
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] twoSum(int[] nums, int target) {
        return method3(nums, target);
    }

    // 方法一：双重遍历暴露求解 O(n^2)
    public int[] method1(int[] nums, int target) {
        for (int i=0; i<nums.length; i++) {
            for (int j=i+1; j<nums.length; j++) {
                if (nums[i] + nums[j] == target){
                    return new int[]{i,j};
                }
            }
        }
        return new int[0];
    }

    /** 分析方法一的缺点：
     * 查找target-i的时间复杂度=O(n)，如何降低查找的时间复杂度呢？
     * 可以使用哈希set或者哈希map，由于题中要求返回索引值，所以需要使用map来存储 <值，索引>
     */
    // 方法二：哈希表 O(n)
    // https://leetcode.cn/problems/two-sum/solutions/434597/liang-shu-zhi-he-by-leetcode-solution/?envType=study-plan-v2&envId=top-100-liked
    public int[] method2(int[] nums, int target) {
        // 注意：因为nums中可能有相同的num，所以不能提前构造，因为map的key是唯一的。需要【一边遍历】【一边构造】
        Map<Integer, Integer> num2index = new HashMap<>();
        for (int i=0; i<nums.length; i++) {
            if (num2index.containsKey(target-nums[i])) {
                return new int[]{num2index.get(target-nums[i]), i};
            }
            num2index.put(nums[i], i);
        }
        return new int[0];
    }

    /** 不可行，因为排序会破坏索引
     */
    // 方法三：排序 + 双指针  O（nlogn）
    // https://labuladong.github.io/algo/di-ling-zh-bfe1b/yi-ge-fang-894da/
    public int[] method3(int[] nums, int target) {
        // 1.排序
        Arrays.sort(nums);
        // 2.双指针搜索
        int left = 0, right = nums.length-1;
        while (left < right) {
            int sum = nums[left] + nums[right];
            if (sum < target) {
                left++;
            } else if (sum > target) {
                right--;
            } else { // 相等的情况
                return new int[]{left, right};
            }
        }
        return new int[]{};
    }

    /**
     * 不可行，因为排序破环了索引
     */
    // 方法三延伸：统计所有不重复的值
    vector<vector<int>> twoSumTarget(vector<int>& nums, int target) {
        // nums 数组必须有序
        sort(nums.begin(), nums.end());
        int lo = 0, hi = nums.size() - 1;
        vector<vector<int>> res;
        while (lo < hi) {
            int sum = nums[lo] + nums[hi];
            int left = nums[lo], right = nums[hi];
            if (sum < target) {
                while (lo < hi && nums[lo] == left) lo++;
            } else if (sum > target) {
                while (lo < hi && nums[hi] == right) hi--;
            } else {
                res.push_back({left, right});
                while (lo < hi && nums[lo] == left) lo++;
                while (lo < hi && nums[hi] == right) hi--;
            }
        }
        return res;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

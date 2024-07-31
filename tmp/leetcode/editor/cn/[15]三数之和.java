
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public List<List<Integer>> threeSum(int[] nums) {
//        return method1(nums, 0);
        return method2(nums, 3, 0);
    }

    /**
     * 方法一：排序 + 遍历 + 双指针
     */
    public List<List<Integer>> method1(int[] nums, int target) {
        // 0.排序
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        // 1.遍历确定第一个数
        for (int i=0; i<nums.length; i++) {
            // 2.使用twoSum筛选后两个数（注意：可能有多组）
            List<List<Integer>> tuples = twoSum(nums, i+1, target-nums[i]);
            // 3.添加到结果中
            for (List<Integer> tuple : tuples) {
                tuple.add(nums[i]);
                res.add(tuple);
            }
            // 4.过滤掉相同数字
            while (i < nums.length-1 && nums[i] == nums[i+1]) i++;
        }
        return res;
    }

    /**
     * 两数之和
     */
    public List<List<Integer>> twoSum(int[] nums, int start, int target) {
        List<List<Integer>> tuple = new ArrayList<>();
        int left = start, right = nums.length-1;
        while (left < right) {
            // 注意：过滤相同数字不能放到这里，因为会将前一个数字漏掉
            int leftNum = nums[left], rightNum = nums[right];
            int sum = leftNum + rightNum;
            if (sum < target) {
                while (left < right && nums[left] == leftNum) left++;  // 过滤相同数字
            } else if (sum > target) {
                while (left < right && nums[right] == rightNum) right--; // 过滤相同数字
            } else {
                // 将结果放入tuple
                tuple.add(new ArrayList<>(Arrays.asList(leftNum,rightNum)));
                // 过滤相同数字
                while (left < right && nums[left] == leftNum) left++;
                while (left < right && nums[right] == rightNum) right--;
            }
        }
        return tuple;
    }

    /**
     * 方法二：排序 + 递归 + 双指针
     */
    public List<List<Integer>> method2(int[] nums, int n, int target) {
        // 1.排序（必须在递归函数外做）
        Arrays.sort(nums);
        // 2.递归
        return nSum(nums, n, 0, target);
    }

    /**
     * n数之和
     * @param n 表示n数之和
     */
    public List<List<Integer>> nSum(int[] nums, int n, int start, int target) {  // 四数之和中，target需要用long声明
        List<List<Integer>> res = new ArrayList<>();
        int sz = nums.length;
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
            // 1.遍历确定第一个数
            for (int i=start; i<sz; i++) {  // 注意：这里的i=0是错误的
                // 2.递归确定后面的数
                List<List<Integer>> resTmp = nSum(nums, n-1, i+1, target-nums[i]);
                // 3.添加结果
                for (List<Integer> tmp : resTmp) {
                    tmp.add(nums[i]);
                    res.add(tmp);
                }
                // 4.注意：一定要去重
                while (i < sz-1 && nums[i] == nums[i+1]) i++;
            }
            return res;
        }
    }

}
//leetcode submit region end(Prohibit modification and deletion)

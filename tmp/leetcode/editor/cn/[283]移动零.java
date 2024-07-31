
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public void moveZeroes(int[] nums) {
        method1(nums);
    }

    // 方法一：遍历 + 创建新数组 + 覆盖旧数组
    public void method1(int[] nums) {
        int[] res = new int[nums.length];
        int index = 0; // res数组的索引
        // 填充新数组
        for (int num : nums) {
            if (num != 0) {
                res[index++] = num;
            }
        }
        // 覆盖旧数组
        Arrays.fill(nums, 0);
        for (int i=0; i<res.length; i++) {
            nums[i] = res[i];
        }
    }

    // 方法二：快慢指针
    public void method2(int[] nums, int targetNum) {
        int slow = 0, fast = 0;
        // 移除零元素
        while (fast < nums.length) {
            if (nums[fast] != targetNum) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        // slow之后的数字填充0
        for (int i=slow; i<nums.length; i++) {
            nums[slow] = 0;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)

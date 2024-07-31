
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public boolean canJump(int[] nums) {
        return method1(nums);
    }

    // https://leetcode.cn/problems/jump-game/?envType=study-plan-v2&envId=top-100-liked
    // 方法一：贪心算法
    public boolean method1(int[] nums) {
        int n = nums.length;
        int rightmost = 0;  // 记录当前可以到达的最远索引位置
        for (int i=0; i<n; i++) {
            // 如果当前索引i位置可达，则可以继续更新最远可达位置
            if (i <= rightmost) {
                rightmost = Math.max(rightmost, i+nums[i]);
                // 更新索引后，如果
                if (rightmost >= n-1) return true;
            }
        }
        return false;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

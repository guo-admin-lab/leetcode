
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public int search(int[] nums, int target) {
        return method2(nums, target);
    }

    // 方法一：暴力遍历求解

    // 方法二：二分搜索 + 断崖判断
    public int method2(int[] nums, int target) {
        // 规定左右都闭的区间
        int left = 0;
        int right = nums.length-1;
        while (left <= right) {
            // 判断 mid 落在 断崖的哪边
            int middle = ((right - left) >> 1) + left;
            // 1.mid落在断崖左边，此时 nums[left .. mid有序]
            if (nums[middle] >= nums[left]) {
                // 1.1.如果 target 比 nums[left]还小，说明target在断崖下边，需要让mid往右靠
                if (target < nums[left]) left = middle + 1;
                // 1.2.说明 target 就在 断崖上边，在断崖上边的左半部分，继续在这个范围找即可
                else if (nums[left] <= target && target < nums[middle]) right = middle - 1;
                // 1.3.如果 target 比 nums[middle]还大，说明target在断崖上边的右半部分，需要让mid往右靠
                else if (target > nums[middle]) left = middle + 1;
                // 1.4.找到目标值
                else return middle;
            }
            // 2.mid落在断崖右边，此时 nums[mid .. right] 有序
            else {
                if (target > nums[right]) right = middle - 1;
                else if (nums[right] >= target && target > nums[middle]) left = middle + 1;
                else if (target < nums[middle]) right = middle - 1;
                else return middle;
            }
        }
        // 没有找到目标值
        return -1;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

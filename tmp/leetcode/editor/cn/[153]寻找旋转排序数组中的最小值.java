
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public int findMin(int[] nums) {
        return method1(nums);
    }

    // 方法一：断崖二分搜索
    public int method1(int[] nums) {

        if (nums.length == 1) return nums[0];

        int left = 0;
        int right = nums.length-1;

        // 1.判断nums是正常有序数组  还是  断崖有序数组
        if (nums[0] < nums[nums.length-1]) { // 正常数组
            return nums[0];
        }

        // 断崖数组
        while (left <= right) {
            int mid = ((right - left) >> 1) + left;
            // 1.如果 mid 在 断崖 上面
            if (nums[mid] >= nums[left]) {
                if (nums[mid+1] < nums[left]) return nums[mid+1];
                else left = mid + 1;
            }
            // 2. mid 在断崖下面
            else if (nums[mid] < nums[left]){
                if (nums[mid-1] > nums[left]) return nums[mid];
                else right = mid - 1;
            }
        }
        // 理论上不可能执行到这里，只为了编译通过
        return Integer.MIN_VALUE;
    }
}

/**
 0 1 2 3 4 5 6
 [4,5,6,7,0,1,2]

 left = 0; right = 6; middle = 3
 left = 4; right = 6; middle = 5

 0 1 2 3 4
 [4 5 1 2 3]

 left = 0; right = 4; mid = 2
 left =

 */
//leetcode submit region end(Prohibit modification and deletion)

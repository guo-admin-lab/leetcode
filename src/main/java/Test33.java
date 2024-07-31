public class Test33 {
}

class Solution33 {

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
                // 1.1.target在nums[left .. mid)之间
                if (target < nums[middle]) right = middle - 1;
                    // 1.2.target在nums(mid .. ?] nums(? .. right)之间
                else if (target > nums[middle]) left = middle + 1;
                    // 1.3.target == nums[mid]
                else return middle;
            }
            // 2.mid落在断崖右边，此时 nums[mid .. right] 有序
            else {
                // 2.1.target在nums[mid .. right]之间
                if (target > nums[middle]) left = middle + 1;
                    // 2.2.target在nums[left .. ?] [? .. mid]之间
                else if (target < nums[middle]) right = middle - 1;
                    // 2.3.找到目标值
                else return middle;
            }
        }
        // 没有找到目标值
        return -1;
    }
}

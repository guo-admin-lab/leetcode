public class Test35 {
}

class Solution35 {
    public int searchInsert(int[] nums, int target) {
        // return method1(nums, 0, nums.length-1, target);
        return method2(nums, target);
    }

    // 递归
    public int method1(int[] nums, int left, int right, int target) {
        // base case
        // 说明没有找到目标值，需要返回当前索引
        /**
         1 2 3  len=3
         left=0, right=2, middle=1
         left=2, right=2, middle=2   (target=5)
         left=3, right=2
         left=0, middle=0, middle=0  (target=0)
         left=1, right=0
         */
        if (left > right) { // 此时，left和right最多相差1
            if (right < 0) return 0;
            if (nums[right] > target) return Math.max(0, right-1);
            else return Math.min(nums.length, right+1);
        }

        int middle = (right - left) / 2 + left;
        if (nums[middle] < target) {
            return method1(nums, middle+1, right, target);
        } else if (nums[middle] > target) {
            return method1(nums, left, middle-1, target);
        } else {
            return middle;
        }
    }

    // 迭代
    public int method2(int[] nums, int target) {
        int left = 0;
        int right = nums.length-1;
        while (left <= right) {
            int middle = (right - left) / 2 + left;
            if (nums[middle] < target) {
                left = middle + 1;
            } else if (nums[middle] > target) {
                right = middle - 1;
            } else {
                return middle;
            }
        }
        // 执行到这里，left > right，说明没有目标值
        if (right < 0) return 0;
        if (nums[right] < target) return Math.min(nums.length, right+1);
        else return Math.max(0, right-1);
    }

}

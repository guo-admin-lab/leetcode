public class Test34 {
}

class Solution34 {

    public int[] searchRange(int[] nums, int target) {
        return method2(nums, target);
    }

    // 方法一：二分查找 + 左右扩散搜索  时间复杂度O(n + logn)
    public int[] method1(int[] nums, int target) {
        // 1.二分查找索引
        int index = binarySearch(nums, 0, nums.length-1, target);
        if (index == -1) return new int[]{-1,-1};
        // 2.向左扩散找起点索引
        int start = index;
        while (start >= 0 && nums[start] == target) start--;
        start += 1;
        // 3.向右扩散找终点索引
        int end = index;
        while (end < nums.length && nums[end] == target) end++;
        end -= 1;
        // 4.返回结果
        return new int[]{start, end};
    }
    // 普通二分查找（目标索引）
    public int binarySearch(int[] nums, int left, int right, int target) {
        while (left <= right) {
            int middle = ((right - left) >> 1) + left;
            if (nums[middle] < target) {
                left = middle + 1;
            } else if (nums[middle] > target) {
                right = middle -1;
            } else {
                return middle;
            }
        }
        return -1;
    }

    // 方法二：分两次二分查找 + 分别二分式寻找左边界和右边界  时间复杂度O(logn)
    public int[] method2(int[] nums, int target) {
        if (nums.length == 0) return new int[]{-1,-1};
        if (nums.length == 1) {
            int index = nums[0] == target ? 0 : -1;
            return new int[]{index, index};
        }
        int leftIndex = boundBinarySearch(nums, target, "left");
        int rightIndex = boundBinarySearch(nums, target, "right");
        return new int[]{leftIndex,rightIndex};
    }
    // 特殊二分查找（边界索引）
    public int boundBinarySearch(int[] nums, int target, String flag) {
        /** flag: left or right */
        int left = 0;
        int right = nums.length-1;
        // 二分查找
        while (left <= right) {
            int middle = ((right - left) >>1) + left;
            if (nums[middle] > target) {
                right = middle - 1;
            } else if (nums[middle] < target) {
                left = middle + 1;
            } else {
                if (flag.equals("left")) right = middle - 1;
                else if (flag.equals("right")) left = middle + 1;
            }
        }
        // 边界处理
        // 执行到这里，left > right，其值对应关系如下：
        // right范围 [-1, nums.length-1]
        // left范围  [0,  nums.length]
        /**
         情况一：
         -1     0
         right  left
         target
         情况二：
         len-1  len
         right  left
         target
         */

        // 左边界情况
        if (flag.equals("left")) {
            if (left == nums.length || nums[left] != target) return -1;
            return left;
        }
        // 右边界情况
        if (flag.equals("right")) {
            if (right == -1 || nums[right] != target) return -1;
            return right;
        }
        // 不可能存在的情况，为了编译通过
        return -1;
    }

}


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {


    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

    }

    // https://leetcode.cn/problems/median-of-two-sorted-arrays/solutions/8999/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by-w-2/?envType=study-plan-v2&envId=top-100-liked
    // https://leetcode.cn/problems/median-of-two-sorted-arrays/solutions/258842/xun-zhao-liang-ge-you-xu-shu-zu-de-zhong-wei-s-114/?envType=study-plan-v2&envId=top-100-liked

    // 方法一：归并排序数组 O(m+n)
    /**
     * 使用归并的方式，合并两个有序数组，得到一个大的有序数组。大的有序数组的中间位置的元素，即为中位数。
     * */

    // 方法二：根据两数组长度直接找中位数  O(m+n)
    /**
     * 不需要合并两个有序数组，只要找到中位数的位置即可。由于两个数组的长度已知，因此中位数对应的两个数组的下标之和也是已知的。维护两个指针，初始时分别指向两个数组的下标 000 的位置，每次将指向较小值的指针后移一位（如果一个指针已经到达数组末尾，则只需要移动另一个数组的指针），直到到达中位数的位置。
     * */

    // 方法三：二分查找  O( log(m+n) )
}
//leetcode submit region end(Prohibit modification and deletion)


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int maxArea(int[] height) {
        return method2(height);
    }

    // 方法一：双重循环遍历最大值求解  超时
    public int method1(int[] height) {
        int maxCap = 0;
        for (int i=0; i<height.length; i++) {
            for (int j=i+1; j<height.length; j++) {
                int cap = (j-i) * Math.min(height[i], height[j]);
                maxCap = Math.max(maxCap, cap);
            }
        }
        return maxCap;
    }

    // 方法二：左右指针 + 贪心
    // https://labuladong.github.io/algo/di-san-zha-24031/jing-dian--a94a0/ru-he-gao--0d5eb/#%E4%B8%80%E3%80%81%E6%A0%B8%E5%BF%83%E6%80%9D%E8%B7%AF
    public int method2(int[] height) {
        int left = 0, right = height.length - 1;
        int res = 0;
        while (left < right) {
            // [left, right] 之间的矩形面积
            int cur_area = Math.min(height[left], height[right]) * (right - left);
            res = Math.max(res, cur_area);
            // 双指针技巧，移动较低的一边
            if (height[left] < height[right]) left++;
            else right--;
        }
        return res;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

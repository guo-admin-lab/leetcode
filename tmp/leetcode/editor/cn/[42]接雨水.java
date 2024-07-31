
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public int trap(int[] height) {
        return method3(height);
    }

    // 方法一：暴力遍历 time:O(n^2) space:O(1)
    public int method1(int[] height) {
        int hSize = height.length;
        int sum = 0;
        // 1.遍历每个宽度为1的柱子，并计算此柱子上方可以盛放多少水
        for (int i=0; i<hSize; i++) {
            // 2.计算i柱子左边最高的柱子LMAX
            int lMaxHeight = 0;
            for (int j=i; j>=0; j--) {
                lMaxHeight = Math.max(lMaxHeight, height[j]);
            }
            // 3.计算i柱子右边最高的柱子RMAX
            int rMaxHeight = 0;
            for (int j=i; j<hSize; j++) {
                rMaxHeight = Math.max(rMaxHeight, height[j]);
            }
            // 4.计算i柱子左右两边LMAX & RMAX中的最小值
            // 5.计算i柱子上方可以盛多少水
            sum += 1 * ( Math.min(lMaxHeight, rMaxHeight) - height[i] );  // 宽 * 长
        }
        return sum;
    }

    /**
     * 方法一中的O（n^2）是由于寻找LMAX和RMAX造成的
     * 如果能提前将LMAX和RMAX记录下来，就可以实现O(1)查找
     */
    // 方法二：遍历 + 备忘录  time: O(n) space: O(n)
    public int method2(int[] height) {
        int hSize = height.length;
        int sum = 0;
        // 1.记录第i柱子的左边最大柱子高度和右边最大高度  lMaxMemos[i]: i柱子左边的最大高度
        int[] lMaxMemos = new int[hSize], rMaxMemos = new int[hSize];
        lMaxMemos[0] = height[0];
        for (int i=1; i<hSize; i++) {
            /**
             * 这里 lMaxMemos[i] = Math.max(lMaxMemos[i-1], lMaxMemos[i]); 是不对的
             */
            lMaxMemos[i] = Math.max(lMaxMemos[i-1], height[i]);
        }
        rMaxMemos[hSize-1] = height[hSize-1];
        for (int i=hSize-2; i>=0; i--) {
            rMaxMemos[i] = Math.max(rMaxMemos[i+1], height[i]);
        }
        // 2.遍历计算
        for (int i=0; i<hSize; i++) {
            sum += 1 * ( Math.min(lMaxMemos[i], rMaxMemos[i]) - height[i] );
        }
        return sum;
    }

    /**
     * 方法二中提前记录了所有i的LMAX和RMAX
     * 利用左右指针，可以让left_i只关注LMAX；right_i只关注RMAX；将space降到O(1)
     */
    // 方法三：左右指针
    public int method3(int[] height) {
        int hSize = height.length;
        int sum = 0;
        int left = 0, right = hSize-1;
        int lMax = 0;  // 从left遍历到当前柱子i的左边最高值
        int rMax = 0;  // 从right遍历到当前柱子i的右边最高值
        while (left < right) {
            // 1.分别计算 left柱子左侧的最高值 和 right柱子右侧的最高值
            lMax = Math.max(lMax, height[left]);
            rMax = Math.max(rMax, height[right]);
            /**
             * 核心：
             * 只要守住一个左边或者右边的最大值，那么就可以用单侧的最大值计算当前柱子i的存储值
             * 左右开弓，同时计算
             */
            // 2.比较 lMax 和 rMax
            if (lMax < rMax) {
                sum += lMax - height[left];
                left++;
            } else if (lMax >= rMax) {
                sum += rMax - height[right];
                right--;
            }
        }
        return sum;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

public class Test240 {

    public static void main(String[] args) {
        int[][] matrix = new int[][]{{1,4},{2,5}};
        boolean b = method3(matrix, 2);
        System.out.println(b);
    }


    /** 这种解法是错的
     *  特殊样例：
     *  1 4
     *  2 5
     *  target=2
     *  第一次水平折半时，找到1 < 2，然后将left++，定位到了最右列
     *  第二次垂直折半时，找到4 > 2，然后将bottom--，定位到最上行
     *  第三次水平折半时，又找到4 > 2，然后将right--，定位到了最左列  此时left > right了，不满足条件了
     *
     *  错误核心原因：
     *  如果matric_nums < target时，可能在左侧，也可能在上侧，这两种可能不能确定
     */
    public static boolean method3(int[][] matrix, int target) {
        /**  一维数组的二分查找
         * 情况一：奇数个数字
         * 1 2 3 4 5   target=2
         * 第一步 left<=right
         * left=0, right=4
         * 【 middle = (0+4)/2 = 2 】 -> 【 nums[middle]=3 > target=2 】 说明在左侧
         * 第二步 left<=right
         * left=0不变, right=middle-1=1
         * 【 middle = (0+1)/2 = 0 】 -> 【 nums[middle]=1 < target=2 】 说明在右侧
         * 第三步 left<=right
         * left=middle+1=1, right=1不变
         * 【 middle = (1+1)/2 = 1 】 -> 【 nums[middle]=2 == target=2 】 说明nums[middle]为要找的值
         *
         * 情况二：偶数个数字
         * 1 2 3 4 5 6  target=2
         * 第一步 left<=right
         * left=0, right=5
         * 【 middle = (0+5)/2 = 2 】 -> 【 nums[middle]=3 > target=2 】 说明在左侧
         * 第二步 left<=right
         * left=0不变，right=middle-1=1
         * 【 middle = () 】 和情况一变化相同
         * */
        /** 二维矩阵的二分查找
         * 第1行中间数nums[middle_ri_0] > target
         *      第middle_ri_0-1列中间数nums[middle_ci_mri0]
         *
         * 总结：先行折半，然后列折半，然后再行折半...
         * */
        int rows = matrix.length;
        int cols = matrix[0].length;
        // 对于只有一维的矩阵特殊处理
        if (rows == 1) return binarySearch(matrix[0], target);
        if (cols == 1) {
            int[] tmp = new int[rows];
            for (int i=0; i<rows; i++) tmp[i] = matrix[i][0];
            return binarySearch(tmp, target);
        }
        // (0,0):水平折半，固定行索引为top  (0,1):水平折半，固定行索引为bottom  (1,0):垂直折半，固定列索引为left  (1,1):垂直折半，固定列索引为right
        int direction1 = 0;
        int direction2 = 0;
        int cLeft=0, cRight=cols-1, rTop=0, rBottom=rows-1;
        while (cLeft <= cRight && rTop <= rBottom) {
            if (direction1 == 0) { // 水平折半
                // 确定行索引
                int rIndex = direction2 == 0 ? rTop : rBottom;
                // if (rIndex < 0 || rIndex >= rows) break;
                int cMiddle = (cLeft + cRight) / 2;
                if (matrix[rIndex][cMiddle] > target) {
                    cRight = cMiddle - 1;
                    direction2 = 1;
                } else if (matrix[rIndex][cMiddle] < target) {
                    cLeft = cMiddle + 1;
                    direction2 = 0;
                } else {
                    return true;
                }
                direction1 = 1;  // 修改方向
            } else if (direction1 == 1) { // 垂直折半
                // 确定列索引
                int cIndex = direction2 == 0 ? cLeft : cRight;
                // if (cIndex < 0 || cIndex >= cols) break;
                int rMiddle = (rTop + rBottom) / 2;
                if (matrix[rMiddle][cIndex] > target) {
                    rBottom = rMiddle - 1;
                    direction2 = 1;
                } else if (matrix[rMiddle][cIndex] < target) {
                    rTop = rMiddle + 1;
                    direction2 = 0;
                } else {
                    return true;
                }
                direction1 = 0;  // 修改方向
            }
        }
        return false;
    }

    // 一维矩阵的二分搜索
    public static boolean binarySearch(int[] nums, int target) {
        int left = 0, right = nums.length-1;
        while (left <= right) {
            int middle = (right - left) / 2 + left;
            if (nums[middle] > target) {
                right = middle - 1;
            } else if (nums[middle] < target) {
                left = middle + 1;
            } else {
                return true;
            }
        }
        return false;
    }

}

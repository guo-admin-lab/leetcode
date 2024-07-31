
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public void rotate(int[][] matrix) {
        method3_2(matrix);
    }

    // 方法一：创建新矩阵 + 螺旋遍历(和54-螺旋矩阵的代码基本一致)
    // 缺点：较复杂，代码量太多
    public void method1(int[][] matrix) {
        // 按照螺旋遍历 + 螺旋赋值的方法
        // 两种螺旋的起始位置不同
    }

    // 方法二：创建新矩阵 + 寻找旋转前和旋转后的映射关系(整体上想，不要单个元素想)
    // 优点：适用于 n*n  和  m*n两种矩阵
    /**
     * 旋转前
     * 1 2 3
     * 4 5 6
     * 7 8 9
     * 第一行旋转后
     * 0 0 1
     * 0 0 2
     * 0 0 3
     * 第二行旋转后
     * 0 4 0
     * 0 5 0
     * 0 6 0
     * 第三行旋转后
     * 7 0 0
     * 8 0 0
     * 9 0 0
     * */
    public void method2(int[][] matrix) {
        /**
         * 旋转前的第一行 -> 旋转后的最后一列
         *      [ri_0,ci_0] -> [new_ri_0,new_ci_cols-1]
         *      [ri_0,ci_1] -> [new_ri_1,new_ci_cols-1]
         *      ...
         *      [ri_0固定, ci<cols] -> [new_ri<new_rols, new_ci_cols-1固定]
         * 旋转前的第二行 -> 旋转后的倒数第二列
         *      [ri_1,ci_0] -> [new_ri_0,new_ci_cols-2]
         *      [ri_1,ci_1] -> [new_ri_1,new_ci_cols-2]
         *      ...
         *      [ri_1固定, ci<cols] -> [new_ri<new_rols, new_ci_cols-2固定]
         * 总结：
         *      ri_0递增 < rows, new_ci_cols-1递减 >= 0
         *      [ri固定, ci<cols] -> [new_ri<new_rols, new_ci_cols-1固定]
         *      注意：这里的 ci<cols和new_ri<new_rols是一样的，可以简化成下面的公式
         *      [ri固定, ci<cols] -> [ci<cols, new_ci_cols-1固定]
         * */
        int rows = matrix.length;
        int cols = matrix[0].length;
        // 1.初始化新矩阵（行列数与原矩阵相反）
        int[][] newMatrix = new int[cols][rows];
        int newRows = newMatrix.length;
        int newCols = newMatrix[0].length;
        // 2.赋值新矩阵
        int ri = 0, tmpNewCols = newCols-1;
        while (ri < rows && tmpNewCols >= 0) {  // ri < rows && tmpNewCols >= 0 这两个条件应该是同步的，写一个即可

            for (int ci=0, new_ri=0; ci<cols; ci++, new_ri++) {
                newMatrix[new_ri][tmpNewCols] = matrix[ri][ci];
                // newMatrix[ci][tmpNewCols] = matrix[ri][ci];  // 可简化：不需要new_ri变量
            }

            ri++;
            tmpNewCols--;
        }
        // 3.覆盖原矩阵数据(因为原矩阵和新矩阵形状一样，下面直接赋值)
        for (int _ri=0; _ri<rows; _ri++) {
            for (int _ci=0; _ci<cols; _ci++) {
                matrix[_ri][_ci] = newMatrix[_ri][_ci];
            }
        }

    }

    // 方法三1：原地修改 + （水平上下翻转+主对角线翻转）|（主对角线翻转+每一行反转）
    // 缺点：只适用于n*n的方矩阵；不适用于m*n长矩阵(因为长矩阵的形状和原来不一样了，所以必须得创建一个新矩阵)
    public void method3_1(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        // 1.水平上下翻转
        int top = 0, bottom = rows-1;
        while (top < bottom) {
            // 交换【ri=top行】和【ri=bottom行】
            for (int ci=0; ci<cols; ci++) {
                int tmp = matrix[top][ci];
                matrix[top][ci] = matrix[bottom][ci];
                matrix[bottom][ci] = tmp;
            }
            top++;
            bottom--;
        }
        // 2.主对角线翻转
        /** 仅针对于n*n的方阵讨论
         * 1 2 3        1  2  3  4
         * 4 5 6        5  6  7  8
         * 7 8 9        9 10 11 12
         *             13 14 15 16
         * ri==ci 不交换
         * 只遍历下三角即可（如果同时遍历下三角和上三角，等于没交换值）
         *      从第二行开始遍历即可
         *      第ri_1行：ci < 1
         *      第ri_2行：ci < 2
         *      第ri_rows-1行：ci < ci_cols-1 （ci的最大值和ri保持统一）
         * 总结：
         *      while (行ri_1递增 < rows)
         *          for (ci=0; ci<ri; ci++)
         *              对角线交换：[ri固定, ci] <-> [ci, ri固定]
         */
        int ri = 1;
        while (ri < rows) {
            for (int ci=0; ci<ri; ci++) {
                // 对角线交换
                int tmp = matrix[ri][ci];
                matrix[ri][ci] = matrix[ci][ri];
                matrix[ci][ri] = tmp;
            }
            ri++;
        }
    }

    // 方法三2：（主对角线翻转+每一行反转）
    public void method3_2(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        // 1.主对角线翻转
        int ri = 1;
        while (ri < rows) {
            for (int ci=0; ci<ri; ci++) {
                // 对角线交换
                int tmp = matrix[ri][ci];
                matrix[ri][ci] = matrix[ci][ri];
                matrix[ci][ri] = tmp;
            }
            ri++;
        }
        // 2.每一行反转
        for (int _ri=0; _ri<rows; _ri++) {
            reverse(matrix[_ri]);
        }
    }

    // 将一维矩阵进行元素反转
    public void reverse(int[] nums) {
        int nSize = nums.length;
        int left = 0, right = nSize-1;  // [left, right]
        while (left < right) {
            int tmp = nums[left];
            nums[left] = nums[right];
            nums[right] = tmp;

            left++;
            right--;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)

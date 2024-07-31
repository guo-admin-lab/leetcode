public class Test74 {

    public static void main(String[] args) {

        int left = 1;
        int right = 3;
        int middle = ((right - left) >> 1) + left;
        System.out.println(middle);

//        int[][] matrix = {{1,3,5,7},{10,11,16,20},{23,30,34,60}};
//        int target = 3;
//
//        Solution74 solution74 = new Solution74();
//        boolean b = solution74.searchMatrix(matrix, target);
//        System.out.println(b);
    }

}

class Solution74 {

    public boolean searchMatrix(int[][] matrix, int target) {
        return method3(matrix, target);
    }

    // 方法一：暴力搜索
    public boolean method1(int[][] matrix, int target) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        for (int ri=0; ri<rows; ri++) {
            for (int ci=0; ci<cols; ci++) {
                if (matrix[ri][ci] == target) return true;
            }
        }
        return false;
    }

    // 方法二：将二维矩阵拉成一维，进行二分查找
    public boolean method2(int[][] matrix, int target) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int leftId0 = 0;  // 0 * cols + 0
        int rightId0 = (rows-1) * cols + (cols-1);   // (rows-1) * cols + (cols-1)

        return binarySearch(matrix, leftId0, rightId0, target);
    }
    // 二分查找
    public boolean binarySearch(int[][] matrix, int leftId, int rightId, int target) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        while (leftId <= rightId) {
            int middle = ((rightId - leftId) >> 1) + leftId;
            if (matrix[middle/cols][middle%cols] < target) {
                leftId = middle + 1;
            } else if (matrix[middle/cols][middle%cols] > target) {
                rightId = middle - 1;
            } else {
                return true;
            }
        }

        return false;
    }

    // 方法三：Z字形查找
    public boolean method3(int[][] matrix, int target) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        /** 从右上角出发
         如果matrix[ri][ci] > target: ci--(向左搜索，数变小)
         如果matrix[ri][ci] < target: ri++(向下搜索，数变大)
         */
        int ri = 0;
        int ci = cols-1;
        while (ri < rows && ci >= 0) {
            if (matrix[ri][ci] > target) ci--;
            else if (matrix[ri][ci] < target) ri++;
            else return true;
        }
        return false;
    }


    /** 其它方法
     1. 两次二分查找（利用本题目的元素大小特殊性）
     由于每行的第一个元素大于前一行的最后一个元素，且每行元素是升序的，所以每行的第一个元素大于前一行的第一个元素，因此矩阵第一列的元素是升序的。
     我们可以对矩阵的第一列的元素二分查找，找到最后一个不大于目标值的元素，然后在该元素所在行中二分查找目标值是否存在。
     2. 对每一行进行二分查找
     */
}

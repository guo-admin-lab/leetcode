import java.util.ArrayList;
import java.util.List;

public class Test73 {

    public static void main(String[] args) {
        int[][] matrix = new int[][]{{0,1,2,0},{3,4,5,2},{1,3,1,5}};
        method1(matrix);
        List<int[]> ints = new ArrayList<>();
    }

    // 方法一：
    public static void method1(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        for (int ri=0; ri<rows; ri++) {
            for (int ci=0; ci<cols; ci++) {
                // 如果matrix[ri][ci]=0, 则将【ri不变，ci变】和【ri变，ci不变】的值都设置为0
                if (matrix[ri][ci] == 0) {
                    // 固定ri
                    for (int _ci=0; _ci<cols; _ci++) matrix[ri][_ci] = 0;
                    // 固定ci
                    for (int _ri=0; _ri<rows; _ri++) matrix[_ri][ci] = 0;
                }
            }
        }
    }

}

class Solution73 {

    public void setZeroes(int[][] matrix) {
        method2(matrix);
    }

    // 错误方法：不能边找边赋值，因为这样会遍历到新赋值成0的元素，最终把所有的元素都变成了0
    public void method_err(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        for (int ri=0; ri<rows; ri++) {
            for (int ci=0; ci<cols; ci++) {
                // 如果matrix[ri][ci]=0, 则将【ri不变，ci变】和【ri变，ci不变】的值都设置为0
                if (matrix[ri][ci] == 0) {
                    // 固定ri
                    for (int _ci=0; _ci<cols; _ci++) matrix[ri][_ci] = 0;
                    // 固定ci
                    for (int _ri=0; _ri<rows; _ri++) matrix[_ri][ci] = 0;
                }
            }
        }
    }

    // 方法一：记录0元素索引  +  遍历  O(m*n)  O(m+n)
    public void method1(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        // 1.找到所有0元素的索引值
        List<int []> zeroPos = new ArrayList<>();
        for (int ri=0; ri<rows; ri++) {
            for (int ci=0; ci<cols; ci++) {
                if (matrix[ri][ci] == 0) {
                    zeroPos.add(new int[]{ri, ci});
                }
            }
        }
        // 2.如果matrix[ri][ci]=0, 则将【ri不变，ci变】和【ri变，ci不变】的值都设置为0
        for (int i=0; i<zeroPos.size(); i++) {
            int ri = zeroPos.get(i)[0];
            int ci = zeroPos.get(i)[1];
            // 固定ri
            for (int _ci=0; _ci<cols; _ci++) matrix[ri][_ci] = 0;
            // 固定ci
            for (int _ri=0; _ri<rows; _ri++) matrix[_ri][ci] = 0;
        }
    }

    // 方法二：原数组第一行、第一列记录该行列是否有0 + 使用两个标记变量
    // https://leetcode.cn/problems/set-matrix-zeroes/solutions/6594/o1kong-jian-by-powcai/?envType=study-plan-v2&envId=top-100-liked
    public void method2(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        // 1.提前记录第一行是否有0（因为之后会更改第一行的值，作为该行是否有0的标志位，到时候不能判断第一行原先是否有0值了）
        boolean row0_zero = false;
        for (int ci=0; ci<cols; ci++) {
            if (matrix[0][ci] == 0) row0_zero = true;
        }
        // 2.提前记录第一列是否有0（同理）
        boolean col0_zero = false;
        for (int ri=0; ri<rows; ri++) {
            if (matrix[ri][0] == 0) col0_zero = true;
        }
        // 3.从(1,1)开始遍历matrix，将第一行、第一列作为该行该列是否有0的标志位
        for (int ri=1; ri<rows; ri++) {
            for (int ci=1; ci<cols; ci++) {
                if (matrix[ri][ci] == 0) {
                    matrix[ri][0] = matrix[0][ci] = 0;
                }
            }
        }
        // 4.根据标志位，将matrix置0
        /** 注意，仍然是从(1,1)开始的 */
        for (int ri=1; ri<rows; ri++) {
            for (int ci=1; ci<cols; ci++) {
                if (matrix[ri][0] == 0 || matrix[0][ci] == 0) {
                    matrix[ri][ci] = 0;
                }
            }
        }
        // 5.根据第一行、第一列是否有0的标志位，对第一行、第一列进行置0
        if (row0_zero) {
            for (int ci=0; ci<cols; ci++) matrix[0][ci] = 0;
        }
        if (col0_zero) {
            for (int ri=0; ri<rows; ri++) matrix[ri][0] = 0;
        }

    }

    // 方法三：进一步优化（使用一个标记变量）
    // https://leetcode.cn/problems/set-matrix-zeroes/solutions/669901/ju-zhen-zhi-ling-by-leetcode-solution-9ll7/?envType=study-plan-v2&envId=top-100-liked

}

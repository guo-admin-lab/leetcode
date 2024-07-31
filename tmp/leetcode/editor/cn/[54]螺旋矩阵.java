
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public List<Integer> spiralOrder(int[][] matrix) {
        return method2(matrix);
    }

    // labuladong解法：https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-48c1d/er-wei-shu-150fb/
    // 官方进阶写法：https://leetcode.cn/problems/spiral-matrix/solutions/275393/luo-xuan-ju-zhen-by-leetcode-solution/?envType=study-plan-v2&envId=top-100-liked

    // 方法一：模拟变化方向 + 遍历  O(m*n) O(1)
    // 方法一进阶：直接规定(当前方向，下一个方向)规则，例如int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    /**
     *  规定四种顺序
     *  满足条件一：指针向右访问 0
     *  满足条件二：指针向左访问 1
     *  满足条件三：指针向上访问 2
     *  满足条件四：指针向下访问 3
     for (int i=0; i<rows*cols; i++) {
         ---- colMin = 0  colMax = cols  rowMin = 0  rowMax = rows ----
         第一轮
         if (当前状态向右 && ci < cols-1) 保持向右 右++ 上下不变
         if (当前状态向右 && ci == cols-1) 变成向下 下++ 左右不变
         if (当前状态向下 && ri < rows-1) 保持向下 下++ 左右不变
         if (当前状态向下 && ri == rows-1) 变成向左 左++ 上下不变
         if (当前状态向左 && ci > 0) 保持向左 左++ 上下不变
         if (当前状态向左 && ci == 0) 变成向上 上++ 左右不变
         ---- colMin = 1  colMax = cols-1  rowMin = 1  rowMax = rows-1 ----
         if (当前状态向上 && ri > 1) 保持向上 上++ 左右不变
         if (当前状态向上 && ri == 1) 变成向右 右++ 上下不变
         第二轮
         if (当前状态向右 && ci < cols-2) 保持向右 右++ 上下不变
         if (当前状态向右 && ci == cols-2) 变成向下 下++ 左右不变
     }
     */
    public List<Integer> method1(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        List<Integer> res = new ArrayList<>();

        int numCnt = rows * cols;  // 定义一共要遍历多少次
        int colMin = 0, colMax = cols, rowMin = 0, rowMax = rows;
        int ri = 0;  // 定义 行指针
        int ci = 0;  // 定义 列指针
        int direction = 0;  // 初始向右访问
        for (int i=0; i<numCnt; i++) {
            // 1.记录当前值
            res.add(matrix[ri][ci]);
            // 2.改变方向 | 移动指针到下一个位置
            if (direction == 0 && ci < colMax-1) {
                ci++;
            } else if (direction == 0 && ci == colMax-1) {
                direction = 3;
                ri++;
            } else if (direction == 3 && ri < rowMax-1) {
                ri++;
            } else if (direction == 3 && ri == rowMax-1) {
                direction = 1;
                ci--;
            } else if (direction == 1 && ci > colMin) {
                ci--;
            } else if (direction == 1 && ci == colMin) {
                direction = 2;
                ri--;
                // 3.更新缩小后矩阵的长宽
                colMin++; colMax--; rowMin++; rowMax--;
            } else if (direction == 2 && ri > rowMin) {
                ri--;
            } else if (direction == 2 && ri == rowMin) {
                direction = 0;
                ci++;
            }
        }
        return res;
    }

    // 方法二：将矩阵分成多个空心矩阵 + 依次从左上角开始遍历
    /** 算法思路
     * 1.确定需要遍历几层空心矩阵
     * 2.依次从空心矩阵的左上角开始顺时针遍历
     * */
    public List<Integer> method2(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        /** 规定：横着为长度cols，竖着为宽度rows */
        int rows = matrix.length;
        int cols = matrix[0].length;
        int numCnt = rows * cols;
        // 1.确定需要遍历几层空心矩阵
        int rowLayers = (rows % 2 == 0) ? rows/2 : rows/2+1;  // 向上取整，等价于Math.ceil(rows/2)
        int colLayers = (cols % 2 == 0) ? cols/2 : cols/2+1;
        // 1.1.确定每一层空心矩阵的左上角索引位置和长宽
        int _ri = 0, _ci = 0, _rows = rows, _cols = cols;  // 初始空心矩阵的值
        while (_ri < rowLayers && _ci < colLayers) {

            /** 遍历思路
                1.2.遍历空心矩阵的上下两行（全部元素）
                固定行ri，移动列ci
                for (int i=ci起始索引; i<ci起始索引+空心矩阵长度cols; i++) {
                    固定上行：ri=空心矩阵左上角起始ri行索引
                    固定下行：ri=空心矩阵左上角起始ri行索引+空心矩阵宽度rows-1
                }
                遍历空心矩阵的左右两列（中间元素，因为其它元素已经被上一个循环遍历过了）
                固定列ci，移动行ri
                for (int i=ri起始索引+1; i<ri起始索引+空心矩阵宽度rows-1; i++) {
                    固定左列：ci=空心矩阵左上角起始ci列索引
                    固定右列：ci=空心矩阵左上角起始ci列索引+空心矩阵长度cols-1
                }
             */

            // 1.2.按照【从左到右——上行】【从上到下——右列】【从右到左——下行】【从下到上——左列】依次填数
            for (int i=_ci; i<_ci+_cols; i++) res.add(matrix[_ri][i]);
            if (res.size() == numCnt) break;  // 防止重复读取值
            for (int i=_ri+1; i<_ri+_rows-1; i++) res.add(matrix[i][_ci+_cols-1]);
            if (res.size() == numCnt) break;  // 防止重复读取值
            for (int i=_ci+_cols-1; i>=_ci; i--) res.add(matrix[_ri+_rows-1][i]);
            if (res.size() == numCnt) break;  // 防止重复读取值
            for (int i=_ri+_rows-2; i>=_ri+1; i--) res.add(matrix[i][_ci]);
            if (res.size() == numCnt) break;  // 防止重复读取值

            // 1.3.更新空心矩阵的参数值
            _ri++;
            _ci++;
            _rows -= 2;
            _cols -= 2;
        }

        return res;
    }

    // todo labuladong写的源代码，还没看
    // 方法二进阶：设置top bottom left right四个指针按层遍历
    List<Integer> spiralOrder(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int upper_bound = 0, lower_bound = m - 1;
        int left_bound = 0, right_bound = n - 1;
        List<Integer> res = new LinkedList<>();
        // res.size() == m * n 则遍历完整个数组
        while (res.size() < m * n) {
            if (upper_bound <= lower_bound) {
                // 在顶部从左向右遍历
                for (int j = left_bound; j <= right_bound; j++) {
                    res.add(matrix[upper_bound][j]);
                }
                // 上边界下移
                upper_bound++;
            }

            if (left_bound <= right_bound) {
                // 在右侧从上向下遍历
                for (int i = upper_bound; i <= lower_bound; i++) {
                    res.add(matrix[i][right_bound]);
                }
                // 右边界左移
                right_bound--;
            }

            if (upper_bound <= lower_bound) {
                // 在底部从右向左遍历
                for (int j = right_bound; j >= left_bound; j--) {
                    res.add(matrix[lower_bound][j]);
                }
                // 下边界上移
                lower_bound--;
            }

            if (left_bound <= right_bound) {
                // 在左侧从下向上遍历
                for (int i = lower_bound; i >= upper_bound; i--) {
                    res.add(matrix[i][left_bound]);
                }
                // 左边界右移
                left_bound++;
            }
        }
        return res;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

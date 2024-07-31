import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Test51 {

    public static void main(String[] args) {
        Solution51 solution51 = new Solution51();
        List<List<String>> lists = solution51.solveNQueens(5);
    }

}

class Solution51 {

    public List<List<String>> solveNQueens(int n) {
        return method1(n);
    }

    // 回溯法
    /** 思路：
     *  1.初始化 n*n 的矩阵
     *      在不能放置【皇后】的位置做好标记
     *  2.两条主对角线不能放【皇后】（这是错误的，对角线上也可以放【皇后】）
     *      i == j
     *      i+j == n-1
     *  3.遍历每一行rows，
     *      依次在每一行放置一个【皇后】
     *      同时，在矩阵中新增不能放【皇后】的位置做好标记
     */
    List<List<String>> res = new ArrayList<>();
    LinkedList<String> onPath = new LinkedList<>();
    /** 特别注意：
     *  在撤销选择的时候，可能把上层的选择也取消掉，
     *  所以需要采取做选择累加，取消选择减1的操作
     *  grid[i][j]:
     *      0：可以放皇后
     *      >0：不能放皇后（因为标记|撤销不能放置【皇后】的操作可能有多次）
     *      -1：已经放置皇后
     *  */
    int[][] grid;
    public List<List<String>> method1(int n) {
        // 边界情况
        if (n == 1) {
            res.add(Arrays.asList("Q"));
            return res;
        }

        // 1.初始化矩阵
        grid = new int[n][n];
        // 2.主对角线和次对角线也可以放皇后
//        for (int i=0; i<n; i++) {
//            for (int j=0; j<n; j++) {
//                if (i==j || i+j==n-1) grid[i][j] = 1;
//            }
//        }
        // 3.回溯遍历每一行
        /** 疑惑：为什么非要递归回溯遍历呢？
         *  因为，普通的for循环，只能找到一种可能，没办法做选择、撤销选择，for循环走过一遍就没了
         *  但，递归回溯可以走多条分支*/
        dfs(grid, 0);

        return res;
    }
    // 回溯枚举
    public void dfs(int[][] grid, int ri) {
        /** ri表示当前要处理哪一行 */

        // base case
        if (ri == grid.length) {
            List<String> paths = new ArrayList<>();
            for (int _ri=0; _ri<grid.length; _ri++) {
                StringBuilder tmp = new StringBuilder();
                for (int _ci=0; _ci<grid[0].length; _ci++) {
                    if (grid[_ri][_ci] == -1) tmp.append('Q');
                    else tmp.append('.');
                }
                paths.add(tmp.toString());
            }
            res.add(paths);
            return;
        }

        int n = grid.length;

        // 遍历ri这一行，看哪个格子可以放置【皇后】
        for (int ci=0; ci<grid[ri].length; ci++) {
            // 如果当前格子不能放置【皇后】，跳过
            if (grid[ri][ci] > 0) continue;

            // 树枝前序
            // 如果当前各自能放置【皇后】，
            // 1.放置皇后
            grid[ri][ci] = -1;
            /**
             *   0 1 2 3
             * 0 . Q . .
             * 1 × × × .
             * 2 . × . ×
             * 3 . × . .
             *
             * 1.竖着：grid[0..ri][ci] = 1
             * 2.左斜：
             *      while(ri+1 < n && ci-1 >=0) {
             *          grid[ri+1][ci-1] = 1
             *          ri = ri+1
             *          ci = ci-1
             *      }
             * 3.右斜：
             *      while(ri+1 < n && ci+1 < n) {
             *          grid[ri+1][ci+1] = 1
             *          ri = ri+1;
             *          ci = ci+1;
             *      }
             */
            // 2.更新不能放置【皇后】的格子，
            int _ri, _ci;
            for (_ri=ri+1; _ri<n; _ri++) {
                grid[_ri][ci] += 1;
            }
            _ri = ri; _ci = ci;
            while (_ri+1 < n && _ci-1 >=0) {
                grid[_ri+1][_ci-1] += 1;
                _ri += 1; _ci -= 1;
            }
            _ri = ri; _ci = ci;
            while (_ri+1 < n && _ci+1 < n) {
                grid[_ri+1][_ci+1] += 1;
                _ri += 1; _ci += 1;
            }
            // 3.然后dfs回溯下一行
            dfs(grid, ri+1);

            // 树枝后序，撤销选择
            /** 特别注意：
             *  在撤销选择的时候，可能把上层的选择也取消掉，
             *  所以需要采取做选择累加，取消选择减1的操作
             *  */
            grid[ri][ci] = 0;
            for (_ri=ri+1; _ri<n; _ri++) {
                grid[_ri][ci] -= 1;
            }
            _ri = ri; _ci = ci;
            while (_ri+1 < n && _ci-1 >=0) {
                grid[_ri+1][_ci-1] -= 1;
                _ri += 1; _ci -= 1;
            }
            _ri = ri; _ci = ci;
            while (_ri+1 < n && _ci+1 < n) {
                grid[_ri+1][_ci+1] -= 1;
                _ri += 1; _ci += 1;
            }
        }

    }
}


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    // 力扣上还有一种【基于位运算的回溯算法】，没看懂
    // https://leetcode.cn/problems/n-queens/solutions/398929/nhuang-hou-by-leetcode-solution/?envType=study-plan-v2&envId=top-100-liked

    // labuladong解法
    // https://labuladong.github.io/algo/di-san-zha-24031/bao-li-sou-96f79/hui-su-sua-c26da/

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
    /** 特别注意：
     *  在撤销选择的时候，可能把上层的选择也取消掉，
     *  方法一：
     *      所以需要采取做选择累加，取消选择减1的操作
     *      grid[i][j]:
     *          0：可以放皇后
     *          >0：不能放皇后（因为标记|撤销不能放置【皇后】的操作可能有多次）
     *          -1：已经放置皇后
     *  方法二：
     *      向上比较，这样就不需要管把之前的操作覆盖掉的问题了，比如labuladong写的这种方法：
     *      https://labuladong.github.io/algo/di-san-zha-24031/bao-li-sou-96f79/hui-su-sua-c26da/
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
        /** 注意：2.主对角线和次对角线也可以放皇后 */

        // 3.回溯遍历每一行
        /** 疑惑：为什么非要递归回溯遍历呢？
         *  因为，普通的for循环，只能找到一种可能，没办法做选择、撤销选择，for循环走过一遍就没了
         *  但，递归回溯可以走多条分支*/
//        dfs1(grid, 0);  // 最直观的方法，但有些繁琐，效率较低
        dfs2(grid, 0);  // 优化写法

        return res;
    }
    // 回溯枚举——最直观的方法
    public void dfs1(int[][] grid, int ri) {
        /** ri表示当前要处理哪一行 */
        int n = grid.length;

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

        // 遍历ri这一行，看哪个格子可以放置【皇后】
        for (int ci=0; ci<n; ci++) {
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
            chooseOrCancel(grid, ri, ci, 1);

            // 3.然后dfs回溯下一行
            dfs1(grid, ri+1);

            // 树枝后序，撤销选择
            /** 特别注意：
             *  在撤销选择的时候，可能把上层的选择也取消掉，
             *  所以需要采取做选择累加，取消选择减1的操作
             *  */
            grid[ri][ci] = 0;
            chooseOrCancel(grid, ri, ci, -1);
        }

    }
    // 回溯枚举——优化写法
    LinkedList<String> onPath = new LinkedList<>();
    public void dfs2(int[][] grid, int ri) {
        /** ri表示当前要处理哪一行 */
        int n = grid.length;

        // base case
        if (ri == grid.length) {
            res.add(new ArrayList<>(onPath));
            return;
        }

        // 遍历ri这一行，看哪个格子可以放置【皇后】
        for (int ci=0; ci<n; ci++) {
            // 如果当前格子不能放置【皇后】，跳过
            if (grid[ri][ci] > 0) continue;

            // 树枝前序
            // 如果当前各自能放置【皇后】，
            // 1.组织这一行的放置情况，并做选择
            StringBuilder tmp = new StringBuilder();
            for (int i=0; i<n; i++) {
                if (i != ci) tmp.append('.');
                else tmp.append('Q');
            }
            onPath.add(tmp.toString());

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
            chooseOrCancel(grid, ri, ci, 1);

            // 3.然后dfs回溯下一行
            dfs2(grid, ri+1);

            // 树枝后序，撤销选择
            onPath.removeLast();
            /** 特别注意：
             *  在撤销选择的时候，可能把上层的选择也取消掉，
             *  所以需要采取做选择累加，取消选择减1的操作
             *  */
            chooseOrCancel(grid, ri, ci, -1);
        }

    }
    // 做选择、撤销选择
    public void chooseOrCancel(int[][] grid, int ri, int ci, int flag) {
        /** flag: 1 -> 做选择； -1 -> 撤销选择 */
        int n = grid.length;
        int _ri, _ci;
        for (_ri=ri+1; _ri<n; _ri++) {
            grid[_ri][ci] += flag;
        }
        _ri = ri; _ci = ci;
        while (_ri+1 < n && _ci-1 >=0) {
            grid[_ri+1][_ci-1] += flag;
            _ri += 1; _ci -= 1;
        }
        _ri = ri; _ci = ci;
        while (_ri+1 < n && _ci+1 < n) {
            grid[_ri+1][_ci+1] += flag;
            _ri += 1; _ci += 1;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)

/**
    Q . . . .
    1 1 . . Q
    1 Q 1 1 1
    1 1 2 1 1
    1 2 × 1 2

 Q . . . .
 × × . . Q
 × Q × × ×
 × × × Q ×
 Q × × × ×
 */


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public int numIslands(char[][] grid) {
//        return method1_dfs(grid);
//        return method2_bfs3(grid);
        return method3_uf(grid);
    }

    // 方法一：dfs + 淹没算法
    /** 算法思路：
     *  从二维网格的每个元素出发，dfs遍历陆地，所经过的地方，全部淹没
     *  淹没算法相当于维护了一个visited数组
     */
    public int method1_dfs(char[][] grid) {
        int count = 0;
        // 从二维网格的每个元素出发
        for (int ri=0; ri<grid.length; ri++) {
            for (int ci=0; ci<grid[0].length; ci++) {
                /** 注意：岛屿数量的增加在这里 */
                if (grid[ri][ci] == '1') {
                    count++;
                    dfs1(grid, ri, ci);
                }
            }
        }
        return count;
    }

    public void dfs1(char[][] grid, int ri, int ci) {
        int maxRi = grid.length-1;
        int maxCi = grid[0].length-1;
        // base case
        if (ri < 0 || ri > maxRi || ci < 0 || ci > maxCi) return; // 索引越界
        if (grid[ri][ci] == '0') return; // 遇到水

        // 前序：淹没当前的陆地
        grid[ri][ci] = '0';

        // 初级写法：dfs遍历上下左右4个方向
//        dfs1(grid, ri-1, ci);  // 上
//        dfs1(grid, ri+1, ci);  // 下
//        dfs1(grid, ri, ci-1);  // 左
//        dfs1(grid, ri, ci+1);  // 右
        // 高级写法：dfs遍历上下左右4个方向
        int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int[] direction : directions) {
            dfs1(grid, ri+direction[0], ci+direction[1]);
        }
    }

    // 方法二：bfs + 淹没算法  超时
    public int method2_bfs1(char[][] grid) {
        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        int maxRi = rows-1;
        int maxCi = cols-1;
        // 从二维网格的每个元素出发
        for (int ri=0; ri<rows; ri++) {
            for (int ci=0; ci<cols; ci++) {
                if (grid[ri][ci] == '0') continue;
                // 因为只有两种可能，到这里一定是陆地1，所以岛屿数量要加一
                count++;
                // bfs遍历四个方向，每次扩展周围一圈
                Queue<Integer> q = new LinkedList<>();
                q.offer(ri);
                q.offer(ci);
                while (!q.isEmpty()) {
                    int _ri = q.poll();
                    int _ci = q.poll();
                    // 所经过的岛屿全部淹没
                    grid[_ri][_ci] = '0';
                    // 将当前元素周围的四个【陆地元素】入队
                    int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
                    /** 超时分析：
                     *   1  [1] 1 0
                     *  [1] (1) 0 0
                     *   1   0  0 0
                     *
                     *   上面两个[1]通过下面的for，会把(1)添加两次，所有造成了很多重复的遍历，从而超时
                     * */
                    for (int[] direct : directions) {
                        int nxtRi = _ri+direct[0];
                        int nxtCi = _ci+direct[1];
                        if (nxtRi < 0 || nxtRi > maxRi || nxtCi < 0 || nxtCi > maxCi) continue;
                        if (grid[nxtRi][nxtCi] == '0') continue;
                        // 此时一定为有效的 陆地元素
                        q.offer(nxtRi);
                        q.offer(nxtCi);
                    }
                }
            }
        }

        return count;
    }

    // 方法二时间优化：bfs + 淹没算法  时间优化
    public int method2_bfs2(char[][] grid) {
        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        int maxRi = rows-1;
        int maxCi = cols-1;
        // 从二维网格的每个元素出发
        for (int ri=0; ri<rows; ri++) {
            for (int ci=0; ci<cols; ci++) {
                if (grid[ri][ci] == '0') continue;
                // 因为只有两种可能，到这里一定是陆地1，所以岛屿数量要加一
                count++;
                // bfs遍历四个方向，每次扩展周围一圈
                Queue<Integer> q = new LinkedList<>();
                q.offer(ri);
                q.offer(ci);
                grid[ri][ci] = '0';
                while (!q.isEmpty()) {
                    int _ri = q.poll();
                    int _ci = q.poll();
                    // 将当前元素周围的四个【陆地元素】入队
                    int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
                    /** 超时优化：
                     *   1  [1] 1 0
                     *  [1] (1) 0 0
                     *   1   0  0 0
                     *
                     *  下面for中，遍历的时候就把 陆地 变成 海水，就可以避免重复添加队列了
                     * */
                    for (int[] direct : directions) {
                        int nxtRi = _ri+direct[0];
                        int nxtCi = _ci+direct[1];
                        if (nxtRi < 0 || nxtRi > maxRi || nxtCi < 0 || nxtCi > maxCi) continue;
                        if (grid[nxtRi][nxtCi] == '0') continue;
                        // 此时一定为有效的 陆地元素
                        q.offer(nxtRi);
                        q.offer(nxtCi);
                        // 淹没陆地
                        grid[nxtRi][nxtCi] = '0';
                    }
                }
            }
        }

        return count;
    }

    // 方法二空间优化：bfs + 淹没算法 + id编码  空间优化
    /** id编码分析
     *  1 2 3
     *  4 5 6
     *
     *  rows = 2   cols = 3
     *
     *  1: 0 * cols + 0   ri = 1/cols = 0 , ci = 0%cols = 0
     *  2: 0 * cols + 1   ri =
     *  3: 0 * cols + 2
     *  4: 1 * cols + 0
     *  5: 1 * cols + 1
     *  6: 1 * cols + 2
     *
     *  根据上述的编码规则，每个id元素的 ri = id/cols , ci = id%cols
     */
    public int method2_bfs3(char[][] grid) {
        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        int maxRi = rows-1;
        int maxCi = cols-1;
        // 从二维网格的每个元素出发
        for (int ri=0; ri<rows; ri++) {
            for (int ci=0; ci<cols; ci++) {
                if (grid[ri][ci] == '0') continue;
                // 因为只有两种可能，到这里一定是陆地1，所以岛屿数量要加一
                count++;
                // bfs遍历四个方向，每次扩展周围一圈
                Queue<Integer> q = new LinkedList<>();
                q.offer(ri * cols + ci);  // 队列中存储二维数组的id
                grid[ri][ci] = '0';
                while (!q.isEmpty()) {
                    int id = q.poll();
                    int _ri = id / cols;
                    int _ci = id % cols;
                    // 将当前元素周围的四个【陆地元素】入队
                    int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
                    for (int[] direct : directions) {
                        int nxtRi = _ri+direct[0];
                        int nxtCi = _ci+direct[1];
                        if (nxtRi < 0 || nxtRi > maxRi || nxtCi < 0 || nxtCi > maxCi) continue;
                        if (grid[nxtRi][nxtCi] == '0') continue;
                        // 此时一定为有效的 陆地元素
                        q.offer(nxtRi * cols + nxtCi);
                        // 循环中将陆地淹没，防止重复入队
                        grid[nxtRi][nxtCi] = '0';
                    }
                }
            }
        }

        return count;
    }

    // 方法三：并查集
    /**  思路分析
     *      为了求出岛屿的数量，我们可以扫描整个二维网格。
     *      如果一个位置为 1，则将其与相邻四个方向上的 1 在并查集中进行合并。
     *      最终岛屿的数量就是并查集中连通分量的数目。
     */
    public int method3_uf(char[][] grid) {
        // 1.初始化并查集
        UF uf = new UF(grid);

        // 2.遍历整个二维网格数组，将上下左右的陆地元素连通到一起
        int rows = grid.length;
        int cols = grid[0].length;
        for (int ri=0; ri<rows; ri++) {
            for (int ci=0; ci<cols; ci++) {
                // 水，不需要处理
                if (grid[ri][ci] == '0') continue;
                // 陆地，将其上下左右的【陆地】进行连通
                int curId = ri * cols + ci;
                int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
                for (int[] direct : directions) {
                    int nxtRi = ri + direct[0];
                    int nxtCi = ci + direct[1];

                    if (nxtRi < 0 || nxtRi >= rows || nxtCi < 0 || nxtCi >= cols) continue;
                    if (grid[nxtRi][nxtCi] == '0') continue;

                    int nxtId = nxtRi * cols + nxtCi;
                    // 如果已经连通，不需要进行连通了
                    if (uf.connect(nxtId, curId)) continue;
                    // 连通两个分量
                    uf.union(nxtId, curId);
                }
            }
        }

        // 3.返回结果
        return uf.count();
    }

    class UF {
        int count = 0;
        int[] parent;

        // 初始化并查集连通分量
        UF(char[][] grid) {
            int rows = grid.length;
            int cols = grid[0].length;
            parent = new int[rows*cols];
            for (int ri=0; ri<rows; ri++) {
                for (int ci=0; ci<cols; ci++) {
                    if (grid[ri][ci] == '0') continue;
                    // 只初始化 【陆地元素】
                    parent[ri * cols + ci] = ri * cols + ci;  // 表示当前结点的父节点位置就是自己的位置
                    count++;
                }
            }
        }

        // 将连通两个元素
        void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            // 如果已经连通，不用再做处理
            if (rootP == rootQ) return;
            // 连通两个元素
            parent[rootP] = rootQ;
            count--;
        }

        // 判断两个元素是否连通
        boolean connect(int p, int q) {
            return find(p) == find(q);
        }

        // 找根节点
        int find(int x) {
            // base case
            if (parent[x] == x) return x;
            // 更新parent
            parent[x] = find(parent[x]);
            return parent[x];
        }

        // 获取连通分量的个数
        int count() {
            return count;
        }

    }


}
//leetcode submit region end(Prohibit modification and deletion)

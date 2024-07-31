import java.util.LinkedList;
import java.util.Queue;

/**
 * 994.腐烂的橘子
 */
public class Test994 {
}

class Solution994 {

    public int orangesRotting(int[][] grid) {
        return method1(grid);
    }

    // 方法一：bfs层级遍历
    /** 思路：
     1.遍历grid，找到所有腐烂橘子的位置；统计所有正常橘子的个数
     2.bfs遍历
     2.1.初始化：将所有腐烂的橘子入队
     2.2.将当前层级的橘子四周全部扩散一圈后，time加一
     bfs结束条件：
     当新腐烂的橘子数目 == 最初所有正常橘子的数目
     grid的层数就是时间数目
     3.检查所有橘子是否都已经腐烂

     */
    public int method1(int[][] grid) {

        int rows = grid.length;
        int cols = grid[0].length;

        // 1.找到所有腐烂橘子的位置，入队
        Queue<Integer> q = new LinkedList<>();
        int orangeNum = 0;
        for (int ri=0; ri<rows; ri++) {
            for (int ci=0; ci<cols; ci++) {
                if (grid[ri][ci] == 1 || grid[ri][ci] == 2) orangeNum++;
                if (grid[ri][ci] == 2) q.offer(ri * cols + ci);
            }
        }

        // 如果没有橘子，返回0
        if (orangeNum == 0) return 0;


        // 2.bfs层级遍历
        int time = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i=0; i<size; i++) {
                int id = q.poll();
                int ri = id / cols;
                int ci = id % cols;
                // 将当前腐烂橘子四周的正常橘子进行腐烂
                int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
                for (int[] direct : directions) {
                    int nxtRi = ri + direct[0];
                    int nxtCi = ci + direct[1];
                    // 边界判定
                    if (nxtRi < 0 || nxtRi >= rows || nxtCi < 0 || nxtCi >= cols) continue;
                    // 没有橘子的位置，跳过
                    if (grid[nxtRi][nxtCi] == 0) continue;
                    // 已经是腐烂橘子了，跳过（因为之前已经入队过了）
                    if (grid[nxtRi][nxtCi] == 2) continue;
                    // 正常橘子，进行腐烂，并入队
                    grid[nxtRi][nxtCi] = 2;
                    q.offer(nxtRi * cols + nxtCi);
                }
            }
            time++;
        }

        // 3.检查是否所有的橘子都已经腐烂
        for (int ri=0; ri<rows; ri++) {
            for (int ci=0; ci<cols; ci++) {
                if (grid[ri][ci] == 1) return -1;
            }
        }

        // 4.返回所需时间
        return time-1;
    }

}

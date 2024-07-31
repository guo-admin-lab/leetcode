import java.util.LinkedList;
import java.util.Queue;

public class Test200 {

    public static void main(String[] args) {
        char[][] grid = {{'1','1','1','1','0'},{'1','1','0','1','0'},{'1','1','0','0','0'},{'0','0','0','0','0'}};
        int i = method2_bfs1(grid);
        System.out.println(i);
    }

    public static int method2_bfs(char[][] grid) {
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
                    for (int[] direct : directions) {
                        if (_ri+direct[0] < 0 || _ri+direct[0] > maxRi || _ci+direct[1] < 0 || _ci+direct[1] > maxCi) continue;
                        if (grid[_ri+direct[0]][_ci+direct[1]] == '0') continue;
                        // 此时一定为有效的 陆地元素
                        q.offer(_ri+direct[0]);
                        q.offer(_ci+direct[1]);
                    }
                }
            }
        }

        return count;
    }

    public static int method2_bfs1(char[][] grid) {
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
                while (!q.isEmpty()) {
                    int id = q.poll();
                    int _ri = id / cols;
                    int _ci = id % cols;
                    // 所经过的岛屿全部淹没
                    grid[_ri][_ci] = '0';
                    // 将当前元素周围的四个【陆地元素】入队
                    int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
                    for (int[] direct : directions) {
                        if (_ri+direct[0] < 0 || _ri+direct[0] > maxRi || _ci+direct[1] < 0 || _ci+direct[1] > maxCi) continue;
                        if (grid[_ri+direct[0]][_ci+direct[1]] == '0') continue;
                        // 此时一定为有效的 陆地元素
                        q.offer(_ri+direct[0] * cols + _ci+direct[1]);
                    }
                }
            }
        }

        return count;
    }

}

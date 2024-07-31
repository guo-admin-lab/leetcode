package graph.路径规划;

import java.util.*;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class ChunYou {

    // 对于郊区春游问题——解法一：回溯列举(排列；无重复；不可复选)
    static int trackSum = 0;  // 遍历路径的花费和
    static int trackCnt = 0;  // 遍历路径上的地点个数
    static int minres = Integer.MAX_VALUE;
    static boolean[] used;

    static int[][] dis;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int R = sc.nextInt();
        dis = new int[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dis[i], Integer.MAX_VALUE);
            dis[i][i] = 0;
        }
        int[] tar = new int[R + 1];
        for (int i = 1; i <= R; i++) {
            tar[i] = sc.nextInt();
        }
        for (int i = 0; i < m; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            int c = sc.nextInt();
            dis[x][y] = c;
            dis[y][x] = c;
        }
        //floyd （无向图写法：两点之间互相的距离相同）
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = i + 1; j <= n; j++) {  // i+1：保证了不会重复计算两点之间的更小距离
                    if (dis[i][k] != Integer.MAX_VALUE && dis[k][j] != Integer.MAX_VALUE) {
                        dis[i][j] = Math.min(dis[i][j], dis[i][k] + dis[k][j]);
                        dis[j][i] = dis[i][j];
                    }
                }
            }
        }
        // floyd （有向图写法：两点之间互相的距离不同）
//        for(int k = 1; k <= n; k++) {
//            for(int i = 1; i <= n; i++) {
//                for(int j = 1; j <= n; j++) {
//                    if(dis[i][k] != Integer.MAX_VALUE && dis[k][j] != Integer.MAX_VALUE) {
//                        dis[i][j] = Math.min(dis[i][j], dis[i][k] + dis[k][j]);
//                    }
//                }
//            }
//        }

        int res = jiaoquchunyou1(tar, n);
        System.out.println(res);
    }

    static int jiaoquchunyou1(int[] places, int n) {
        // places: 要去访问的地点
        used = new boolean[n+1];  /** 注意：这里不能new boolean[places.length+1]，因为used的索引要使用place的值，并不是places的索引 */
        // 回溯列举
        dfs1(places, -1);  /** 注意：-1代表起始点没有 */
        // 返回最小花销
        return minres;
    }
    static void dfs1(int[] places, int prePlace) {
        // places: 要去访问的地点；prePlace：上一个访问的地方
        // base case
        if (trackCnt == places.length-1) {  /** 要-1是因为places中存在一个没有实际意义的值，0 */
            // 更新最小花费
            minres = Math.min(minres, trackSum);
            return;
        }

        // 回溯遍历
        for (int place : places) {

            /** 过滤掉数组的头索引，因为不代表实际意义，没用。地点最少也是1 */
            if (place == 0) continue;

            // 防止重复访问
            if (used[place]) continue;

            if (prePlace == -1) prePlace = place;  // 如果起点坐标为-1，说明没有起点，初始化成当前节点

            // 如果这段距离为无穷大，那么不会考虑这条路径，如果trackCnt加上无穷大，会溢出
            if (dis[prePlace][place] == Integer.MAX_VALUE) continue;
            // 做选择
            used[place] = true;
            trackCnt++;
            trackSum += dis[prePlace][place];

            // 回溯
            dfs1(places, place);

            // 取消选择
            used[place] = false;
            trackCnt--;
            trackSum -= dis[prePlace][place];
        }
    }
}

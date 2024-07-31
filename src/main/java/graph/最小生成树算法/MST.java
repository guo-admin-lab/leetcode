package graph.最小生成树算法;

import common.UF;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/** 最小生成树算法
 *  https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-03a72/kruskal-zu-e6b5b/
 *   Kruskal 算法
 *   Prim 算法
 */

public class MST {

    public static void main(String[] args) {
        // 1135. 最低成本联通所有城市 | 力扣  | LeetCode  |
        System.out.println("场景1：");
        int n1 = 3; // 有3个城市，从1开始编号
        int[][] connections1 = {{1,2,5},{1,3,6},{2,3,1}}; // 起点，终点，权重
        int n2 = 4;
        int[][] connections2 = {{1,2,3},{3,4,4}}; // 起点，终点，权重
        System.out.println("Kruskal算法：");
        int i1 = Kruskal.minimumCost(n1, connections1);  // 6
        int i2 = Kruskal.minimumCost(n2, connections2);  // -1
        System.out.println(i1 + ":" + i2);
        System.out.println("Prim算法：");
        int i3 = Prim.minimumCost(n1, connections1);  // 6
        int i4 = Prim.minimumCost(n2, connections2);  // -1
        System.out.println(i3 + ":" + i4);

    }

}

/** 克鲁斯卡尔算法：（并查集）
 *   贪心策略：
 *      1.边排序（从小到大）
 *      2.前提：边两端点不构成联通分量
 *          将边加入生成树集合
 *      3.按照如上规则，得出最小生成树
 */
class Kruskal {

    static int minimumCost(int n, int[][] connections) {
        // 1.城市编号为 1...n，所以初始化大小为 n + 1
        UF uf = new UF(n+1);
        // 2.对所有边按照权重从小到大排序
        Arrays.sort(connections, (a,b)->(a[2]-b[2]));
        // 3.执行算法，记录最小生成树的权重之和
        int mst = 0;
        for (int[] edge : connections) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            // 如果这条边会产生环，则不能加入mst
            if (uf.connect(u, v)) continue;
            // 如果这条边不会产生环，则属于最小生成树
            mst += w;
            uf.union(u, v);
        }
        // 保证所有节点都被连通
        // 按理说 uf.count() == 1 说明所有节点被连通
        // 但因为节点 0 没有被使用，所以 0 会额外占用一个连通分量
        return uf.count() == 2 ? mst : -1;
    }

}

class Prim {
    static int minimumCost(int n, int[][] connections) {
        // 转化成无向图邻接表的形式
        List<int[]>[] graph = buildGraph(n, connections);
        // 执行 Prim 算法
        PrimX prim = new PrimX(graph);

        if (!prim.allConnected()) {
            // 最小生成树无法覆盖所有节点
            return -1;
        }

        return prim.getWeightSum();
    }

    static List<int[]>[] buildGraph(int n, int[][] connections) {
        // 图中共有 n 个节点
        List<int[]>[] graph = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new LinkedList<>();
        }
        for (int[] conn : connections) {
            // 题目给的节点编号是从 1 开始的，
            // 但我们实现的 Prim 算法需要从 0 开始编号
            int u = conn[0] - 1;
            int v = conn[1] - 1;
            int weight = conn[2];
            // 「无向图」其实就是「双向图」
            // 一条边表示为 int[]{from, to, weight}
            graph[u].add(new int[]{u, v, weight});
            graph[v].add(new int[]{v, u, weight});
        }
        return graph;
    }
}

/**  普利姆算法：（优先队列）
 *     贪心策略：
 *        1.初始化：随机选取结点A加入mst集合；将结点A的横切边加入队列中
 *        2.mst集合中所有结点看作整体，对整体边做切分，
 *             将最小边连接的结点B加入mst
 *             将结点B的横切边加入到队列中，方便下一次候选
 *        3.按照如上规则，得出最小生成树
 */
class PrimX {
    // 核心数据结构，存储「横切边」的优先级队列
    private PriorityQueue<int[]> pq;
    // 类似 visited 数组的作用，记录哪些节点已经成为最小生成树的一部分
    private boolean[] inMST;
    // 记录最小生成树的权重和
    private int weightSum = 0;
    // graph 是用邻接表表示的一幅图，
    // graph[s] 记录节点 s 所有相邻的边，
    // 三元组 int[]{from, to, weight} 表示一条边
    private List<int[]>[] graph;

    public PrimX(List<int[]>[] graph) {
        // 1.初始化类变量
        this.graph = graph;
        this.pq = new PriorityQueue<>((a,b) -> a[2]-b[2]);  // 边权重从小到大排序
        this.inMST = new boolean[graph.length];
        int nodeNum = graph.length;

        // 2.算法执行
        inMST[0] = true; // 随机取一个结点进行切分
        cutAndOffer(0);  // 初始化优先队列数据
        while (!pq.isEmpty()) {
            int[] edge = pq.poll();
            int to = edge[1];
            int weight = edge[2];
            // 如果相邻边的结点to已经在最小生成树中，跳过
            if (inMST[to]) continue;  // todo 考虑是不是能删除
            // 增加权重
            weightSum += weight;
            // mst集合中添加to结点
            inMST[to] = true;
            // 切分to结点，产生更多横切边
            cutAndOffer(to);
        }
    }
    // 将结点s的横切边加入优先队列
    private void cutAndOffer(int s) {
        // 遍历s所有边
        for (int[] edge : graph[s]) {
            int to = edge[1];
            // 如果相邻边的结点to已经在最小生成树中，跳过
            if (inMST[to]) continue;
            // 加入横切边
            pq.offer(edge);
        }
    }

    // 最小生成树的权重和
    public int getWeightSum() {
        return weightSum;
    }

    // 判断最小生成树是否包含图中的所有结点
    public boolean allConnected() {
        for (int i = 0; i < inMST.length; i++) {
            if (!inMST[i]) {
                return false;
            }
        }
        return true;
    }

}




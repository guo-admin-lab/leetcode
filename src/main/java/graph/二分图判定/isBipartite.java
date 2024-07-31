package graph.二分图判定;

/** 二分图判定
 * https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-03a72/er-fen-tu--73400/
 */

import java.util.LinkedList;
import java.util.Queue;

/** 二分图的用途
 * 首先，二分图作为一种特殊的图模型，会被很多高级图算法（比如最大流算法）用到，不过这些高级算法我们不是特别有必要去掌握，有兴趣的读者可以自行搜索。
 *
 * 从简单实用的角度来看，二分图结构在某些场景可以更高效地存储数据。
 *
 * 比如说我们需要一种数据结构来储存电影和演员之间的关系：某一部电影肯定是由多位演员出演的，且某一位演员可能会出演多部电影。你使用什么数据结构来存储这种关系呢？
 *
 * 既然是存储映射关系，最简单的不就是使用哈希表嘛，我们可以使用一个 HashMap<String, List<String>> 来存储电影到演员列表的映射，如果给一部电影的名字，就能快速得到出演该电影的演员。
 *
 * 但是如果给出一个演员的名字，我们想快速得到该演员演出的所有电影，怎么办呢？这就需要「反向索引」，对之前的哈希表进行一些操作，新建另一个哈希表，把演员作为键，把电影列表作为值。
 *
 * 显然，如果用哈希表存储，需要两个哈希表分别存储「每个演员到电影列表」的映射和「每部电影到演员列表」的映射。但如果用「图」结构存储，将电影和参演的演员连接，很自然地就成为了一幅二分图：
 *
 * 每个电影节点的相邻节点就是参演该电影的所有演员，每个演员的相邻节点就是该演员参演过的所有电影，非常方便直观。
 */

public class isBipartite {

    public static void main(String[] args) {
        int[][] graph1 = {{1,2,3},{0,2},{0,1,3},{0,2}};  // false
        int[][] graph2 = {{1,3},{0,2},{1,3},{0,2}};  // true
        boolean b1 = isBipartite_dfs(graph2);
        boolean b2 = isBipartite_bfs(graph2);
        System.out.println(b1);
        System.out.println(b2);
    }

    /** 核心思路：判断相邻两结点是否可以全部染上不同颜色 */
    // dfs-递归法
    static boolean[] visited;
    static boolean[] color;
    static boolean ok = true;
    static boolean isBipartite_dfs(int[][] graph) {
        // 1.初始化辅助变量
        visited = new boolean[graph.length];
        color = new boolean[graph.length];

        // 2.将graph每个结点都作为起点进行遍历（因为并不是所有节点都相连）
        for (int i=0; i<graph.length; i++) {
            if (!visited[i] || ok) {
                traverse_dfs(graph, i);
            }
        }

        // 3.返回
        return ok;
    }
    // dfs遍历图
    static void traverse_dfs(int[][] graph, int s) {
        // base case
        if (!ok) return;  // 如果已经不是二分图了，不需要再去遍历
        // 前序
        visited[s] = true;
        // 遍历s每个子节点
        for (int t : graph[s]) {
            if (!visited[t]) { // 如果t没有被访问
                // t和s染上不同的颜色
                color[t] = !color[s];
                // 遍历t
                traverse_dfs(graph, t);
            } else { // 如果t被访问过
                // 判断与当前s的颜色是否相同
                if (color[t] == color[s]) {
                    ok = false;
                    return;
                }
            }
        }
    }

    // bfs-迭代法
    static boolean isBipartite_bfs(int[][] graph) {
        // 1.初始化辅助变量
        visited = new boolean[graph.length];
        color = new boolean[graph.length];

        // 2.将graph每个结点都作为起点进行遍历（因为并不是所有节点都相连）
        for (int i=0; i<graph.length; i++) {
            if (!visited[i] || ok) {
                traverse_bfs(graph, i);
            }
        }

        // 3.返回
        return ok;
    }
    // bfs遍历图
    static void traverse_bfs(int[][] graph, int s) {
        Queue<Integer> q = new LinkedList<>();
        q.offer(s);
        while (!q.isEmpty()) {
            Integer cur = q.poll();
            visited[cur] = true;
            for (int nxt : graph[cur]) {
                if (!visited[nxt]) {
                    color[nxt] = !color[cur];
                    q.offer(nxt);
                } else {
                    if (color[nxt] == color[cur]) {
                        ok = false;
                        return;
                    }
                }
            }
        }
    }

}

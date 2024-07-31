package graph.拓扑排序;

import java.util.*;

/**
 * 环检测 和 拓扑排序
 * https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-03a72/huan-jian--e36de/
 * dfs中：visited数组防止重复遍历结点；onPath数组检测环
 * bfs中：indegree数组防止重复遍历结点，且用来检测环
 */
public class TopologicalSorting {

    public static void main(String[] args) {
        int numCourses = 4;
        int[][] prerequesites1 = {{1,0}};  // 2
        int[][] prerequesites2 = {{1,0},{0,1}};  // 2
        int[][] prerequesites3 = {{1,0},{2,0},{3,1},{3,2}};  // 4
        boolean b = canFinish_bfs(4, prerequesites3);
        int[] order = findOrder_bfs(4, prerequesites3);
        System.out.println(b);
        for (int i : order) {
            System.out.println(i);
        }
    }

    /**  模拟场景：课程表
     *      numCourses = 2, prerequesites = [[1,0]]  表示：学习课程1之前，需要先完成课程0
     *      numCourses = 2, prerequesites = [[1,0],[0,1]]
     * */

    // 建图函数：邻接表
    static List<Integer>[] buildGraph(int numCourses, int[][] prerequisites) {
        // 图中共有 numCourses 个结点
        List<Integer>[] graph = new LinkedList[numCourses];
        // 初始化每个结点
        for (int i=0; i<numCourses; i++) {
            graph[i] = new LinkedList<>();
        }
        // 构造邻接矩阵
        for (int[] edge : prerequisites) {
            int from = edge[1], to = edge[0];
            // 添加一条从 from 指向 to 的有向边，边的方向是【被依赖】->【依赖】
            graph[from].add(to);
        }
        return graph;
    }

    // -----------------------------------------------------
    // dfs - 检测是否有环
    static boolean[] visited;
    static boolean[] onPath;
    static boolean hasCycle = false;
    static boolean canFinish_dfs(int numCourses, int[][] prerequisites) {
        // 1.建图
        List<Integer>[] graph = buildGraph(numCourses, prerequisites);
        // 2.每个结点都作为起点遍历（因为图中并不是所有结点都相连）
        visited = new boolean[numCourses];
        onPath = new boolean[numCourses];
        for (int i=0; i<numCourses; i++) {
            traverse1_dfs(graph, i);
        }
        return hasCycle;
    }
    // dfs遍历图，检测是否有环（利用onPath数组）
    static void traverse1_dfs(List<Integer>[] graph, int s) {
        // 如果遍历过程中, 路径中已经存在s，说明有环
        if (onPath[s]) hasCycle = true;

        // 如果已经访问过s，或者 已经发现环，就不再继续遍历了
        if (visited[s] || hasCycle) return;

        // 前序
        visited[s] = true;
        onPath[s] =true;
        // 遍历s的子节点
        for (int t : graph[s]) {
            traverse1_dfs(graph, t);
        }
        // 后序
        onPath[s] = false;
    }

    // -----------------------------------------------------
    // dfs - 拓扑排序，给出合理的上课顺序
    static int[] res;
    static int[] findOrder_dfs(int numCourses, int[][] prerequisites) {
        // 1.建图
        List<Integer>[] graph = buildGraph(numCourses, prerequisites);
        // 2.每个结点作为起点，遍历一次
        visited = new boolean[numCourses];
        onPath = new boolean[numCourses];
        res = new int[numCourses];
        for (int i=0; i<numCourses; i++) {
            traverse2_dfs(graph, i);
        }
        // 3.如果图中有环，则无解
        if (hasCycle) return new int[]{};
        // 4.如果无环，将后序遍历结果反序，则为最终的拓扑排序结果
        int li = 0, ri = res.length-1;
        while (li < ri) {
            int tmp = res[li];
            res[li] = res[ri];
            res[ri] = tmp;
            li++; ri--;
        }
        // 5.返回拓扑排序结果
        return res;
    }
    // 后序遍历记录反拓扑排序
    static int resIndex = 0;
    static void traverse2_dfs(List<Integer>[] graph, int s) {
        // base case
        if (onPath[s]) hasCycle = true;
        if (visited[s] || hasCycle) return;

        // 先序
        visited[s] = true;
        onPath[s] = true;
        for (int t : graph[s]) {
            traverse2_dfs(graph, t);
        }
        // 后序
        res[resIndex++] = s;
        onPath[s] = false;
    }

    // -----------------------------------------------------
    // bfs - 检测是否有环（利用indegree数组）
    static boolean canFinish_bfs(int numCourses, int[][] prerequisites) {
        // 1.建图
        List<Integer>[] graph = buildGraph(numCourses, prerequisites);
        // 2.构建入度数组
        int[] indegree = new int[numCourses];
        for (int[] edge : prerequisites) {
            int from = edge[1], to = edge[0];
            // 结点 to 的入读加一
            indegree[to]++;
        }
        // 3.初始化队列
        Queue<Integer> q = new LinkedList<>();
        for (int i=0; i<numCourses; i++) {
            // 将入度为 0 的结点入队
            if (indegree[i] == 0) q.offer(i);
        }
        // 4.开始 bfs 检测
        int count = 0; // 记录遍历的结点个数
        while (!q.isEmpty()) {
            int cur = q.poll();
            count++;
            for (int nxt : graph[cur]) {
                // 将 cur 指向的结点 入度减一
                indegree[nxt]--;
                // 如果 nxt 入度为0，进入队列
                if (indegree[nxt] == 0) q.offer(nxt);
            }
        }
        // 5.如果存在环，则必定有结点的入度不可能减为0，即不可能被遍历到
        return count != numCourses;
    }

    // -----------------------------------------------------
    // bsf - 拓扑排序
    static int[] findOrder_bfs(int numCourses, int[][] prerequisites) {
        // 1.建图
        List<Integer>[] graph = buildGraph(numCourses, prerequisites);
        // 2.构建入度数组
        int[] indegree = new int[numCourses];
        for (int[] edge : prerequisites) {
            int from = edge[1], to = edge[0];
            // 结点 to 的入读加一
            indegree[to]++;
        }
        // 3.初始化队列
        Queue<Integer> q = new LinkedList<>();
        for (int i=0; i<numCourses; i++) {
            // 将入度为 0 的结点入队
            if (indegree[i] == 0) q.offer(i);
        }
        // 4.开始 bfs 检测
        int count = 0; // 记录遍历的结点个数
        int[] res = new int[numCourses];
        while (!q.isEmpty()) {
            int cur = q.poll();
            res[count++] = cur; // cur 加入 结果数组
            for (int nxt : graph[cur]) {
                // 将 cur 指向的结点 入度减一
                indegree[nxt]--;
                // 如果 nxt 入度为0，进入队列
                if (indegree[nxt] == 0) q.offer(nxt);
            }
        }
        // 5.如果有环，返回空数组
        if (count != numCourses) return new int[0];
        // 6.如果没有环，返回结果数组
        else return res;
    }

}

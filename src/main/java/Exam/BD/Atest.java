package Exam.BD;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Atest {

    public static void main(String[] args) {
        // 0->2, 1->2, 2->3
        // 0.样例构建
        int nodeNum = 4;
        int[][] data = {{0,2},{1,2},{2,3}};
        // 1.执行
        int[] nums = sortBfs(nodeNum, data);
        for (int num : nums) {
            System.out.println(num);
        }
    }

    static public List<Integer>[] buildGraph(int num, int[][] data) {
        List<Integer>[] graph = new LinkedList[num];
        for (int i=0; i<num; i++) {
            graph[i] = new LinkedList<>();
        }
        // 构造邻接表
        for (int[] edge : data) {
            int from = edge[0], to = edge[1];
            graph[from].add(to);
        }
        return graph;
    }

    static public int[] sortBfs(int num, int[][] data) {
        // 1.建图
        List<Integer>[] graph = buildGraph(num, data);
        // 2.构造入度数组
        int[] indegree = new int[num];
        for (int[] edge : data) {
            int from = edge[0], to = edge[1];
            // 节点的入度++
            indegree[to]++;
        }
        // 3.初始化队列
        Queue<Integer> que = new LinkedList<>();
        // 3.1.筛选入度节点为0的节点
        for (int i=0; i<num; i++) {
            if (indegree[i] == 0) que.offer(i);
        }
        // 4.bfs
        int count = 0;
        int[] res = new int[num];
        while (!que.isEmpty()) {
            int cur = que.poll();
            res[count++] = cur;
            // 4.1.筛选新入队节点
            for (int nxt : graph[cur]) {
                indegree[nxt]--;
                if (indegree[nxt] == 0) que.offer(nxt);
            }
        }
        // 5.返回结果(如果有环的话需要抛出异常)
        return res;
    }

}

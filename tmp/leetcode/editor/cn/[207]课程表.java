
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public boolean canFinish(int numCourses, int[][] prerequisites) {

        // prerequisites[i] = [ai, bi] ，表示如果要学习课程 ai 则 必须 先学习课程 bi 。
//        return method1_dfs(numCourses, prerequisites);
        return method2_bfs(numCourses, prerequisites);

    }

    // --------------------------------------------------------------------------------
    // 方法一：递归dfs
    boolean[] visited;
    boolean[] onPath;
    public boolean method1_dfs(int numCourses, int[][] prerequisites) {
        // 1.构建图：邻接表
        List<Integer>[] graph = buildGraph(numCourses, prerequisites);
        // 2.初始化辅助变量
        visited = new boolean[numCourses];
        onPath = new boolean[numCourses];
        // 3.dfs遍历图，检测环
        boolean hasCycle = false;
        /** 注意：必须要从每个点进行出发遍历一次，因为不是所有的点都连通 */
        for (int i=0; i<numCourses; i++) {
            hasCycle = dfs(graph, i);
            if (hasCycle) break;
        }
        // 4.返回是否有环，有环true -> 不可能完成false；无环false -> 可以完成true
        return !hasCycle;
    }
    // dfs遍历图，检测环
    public boolean dfs(List<Integer>[] graph, int s) {
        // base case
        // 判断是否有环（必须在判断visited之前判断）
        if (onPath[s]) return true;  // 已经有环
        if (visited[s]) return false;  // 还没有环

        // 先序
        visited[s] = true;
        onPath[s] = true;

        // dfs
        for (int t : graph[s]) {
            boolean hasCycle = dfs(graph, t);
            // 如果已经有环，直接返回，不再重复遍历了
            if (hasCycle) return true;
        }

        // 后序
        onPath[s] = false;

        // 执行到这里，说明还没有检测出环
        return false;
    }
    // 建图
    public List<Integer>[] buildGraph(int numCourses, int[][] prerequisites) {
        // 1.初始化邻接表
        List<Integer>[] graph = new LinkedList[numCourses];
        for (int i=0; i<numCourses; i++) {
            graph[i] = new LinkedList<>();
        }
        // 2.添加邻接表元素
        for (int[] prerequire : prerequisites) {
            int to = prerequire[0];
            int from = prerequire[1];
            graph[from].add(to);
        }
        // 3.返回构建好的邻接表
        return graph;
    }

    // --------------------------------------------------------------------------------
    // 方法二：迭代bfs
    public boolean method2_bfs(int numCourses, int[][] prerequisites) {
        // 0. 建图（方便快速得到某点连接的其它边）
        List<Integer>[] graph = buildGraph(numCourses, prerequisites);
        // 1.构建 入度数组
        int[] indegrees = new int[numCourses];
        Arrays.fill(indegrees, 0);
        for (int[] edge : prerequisites) {
            int to = edge[0];
            indegrees[to]++;
        }
        // 2.bfs遍历
        Queue<Integer> q = new LinkedList<>();
        // 2.1.初始化队列（将所有入度为0的课程入队）
        for (int i=0; i<numCourses; i++) {
            if (indegrees[i] == 0) q.offer(i);
        }
        // 2.2.开始遍历
        while (!q.isEmpty()) {
            int from = q.poll();
            // 将from指向的所有元素的入度减1
            for (int to : graph[from]) {
                indegrees[to]--;
                // 如果to元素此时入度为0，入队
                if (indegrees[to] == 0) q.offer(to);
            }
        }

        // 3.判断入度数组中是否还存在入度不为0的元素，如果存在，说明存在环->不可能完成
        for (int indegree : indegrees) {
            if (indegree != 0) return false;
        }
        // 无环，可以完成
        return true;
    }


}
//leetcode submit region end(Prohibit modification and deletion)

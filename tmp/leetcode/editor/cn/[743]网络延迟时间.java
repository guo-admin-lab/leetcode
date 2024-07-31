
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public int networkDelayTime(int[][] times, int n, int k) {
        Dijkstra dijkstra = new Dijkstra();
        dijkstra.buildGraph(times,n);
        dijkstra.dijkstra(k);
        // 找到所有点到k最长的距离
        int max = Integer.MIN_VALUE;
        for (Dijkstra.Node node : dijkstra.id2Node.values()) {
            max = Math.max(max, node.minDistFromStart);
        }
        return max==Integer.MAX_VALUE ? -1 : max;
    }

    class Dijkstra {

        class Edge {
            Node fromNode;
            Node toNode;
            int weight;
            Edge (Node fromNode, Node toNode, int weight) {
                this.fromNode = fromNode;
                this.toNode = toNode;
                this.weight = weight;
            }
        }

        class Node {
            int id;  // 对应每个结点的序号
            List<Edge> edges = new LinkedList<>();  // 存储边集合
            int minDistFromStart;  // 每个结点到源点的最短距离
            Node (int id) {
                this.id = id;
            }

            Node(int id, int minDistFromStart) {
                this.id = id;
                this.minDistFromStart = minDistFromStart;
            }
        }

        Map<Integer, Node> id2Node = new HashMap<>(); // 存储已经创建的结点(包括所有节点)，避免重复创建
        /** 第一种输入矩阵
         * [
         *      [from, to, weight]
         * ]
         */
        void buildGraph(int[][] matrix, int n) {
            for (int[] edge : matrix) {
                int fromId = edge[0];
                int toId = edge[1];
                int weight = edge[2];
                // 获取结点
                Node fromNode = id2Node.get(fromId);
                Node toNode = id2Node.get(toId);
                if (fromNode == null) {
                    fromNode = new Node(fromId);
                    id2Node.put(fromId, fromNode);
                }
                if (toNode == null) {
                    toNode = new Node(toId);
                    id2Node.put(toId, toNode);
                }
                // 设置边关系
                fromNode.edges.add(new Edge(fromNode, toNode, weight));
            }

            // 如果edge中不包括所有的节点，说明有孤立节点，需要通过下面的逻辑进行创建
            // todo 注意是从0开始，还是1开始
            for (int i=1; i<=n; i++) {
                Node node = id2Node.get(i);
                if (node != null) continue;
                id2Node.put(i, new Node(i));  // 创建孤立节点，没有边
            }
        }

        /** 第二种输入矩阵
         * -1 1 3
         * 1 -1 4
         * 5 2 -1
         * matrix[from][to] = weight
         */
        void buildGraph2(int[][] matrix, int n) {
            int rows = matrix.length;
            int cols = matrix[0].length;
            for (int fromId=0; fromId<rows; fromId++) {
                for (int toId=0; toId<cols; toId++) {
                    if (fromId == toId) continue;  // 自己结点没有边
                    int weight = matrix[fromId][toId];
                    // 获取结点
                    Node fromNode = id2Node.get(fromId);
                    Node toNode = id2Node.get(toId);
                    if (fromNode == null) {
                        fromNode = new Node(fromId);
                        id2Node.put(fromId, fromNode);
                    }
                    if (toNode == null) {
                        toNode = new Node(toId);
                        id2Node.put(toId, toNode);
                    }
                    // 设置边关系
                    fromNode.edges.add(new Edge(fromNode, toNode, weight));
                }
            }

            // 如果edge中不包括所有的节点，说明有孤立节点，需要通过下面的逻辑进行创建
            // todo 注意是从0开始，还是1开始
            for (int i=1; i<=n; i++) {
                Node node = id2Node.get(i);
                if (node != null) continue;
                id2Node.put(i, new Node(i));  // 创建孤立节点，没有边
            }
        }

        /** 下面的逻辑是按照这里面的手算图来写的：
         *  https://www.yuque.com/u1589209/kdk87f/in2p1kynguzeh8v0
         */
        void dijkstra(int source) {

            // 初始化所有节点到源节点的初始距离
            for (Node node : id2Node.values()) {
                if (source == node.id) {
                    node.minDistFromStart = 0;  // 源点到源点的最短距离为0
                } else {
                    node.minDistFromStart = Integer.MAX_VALUE;
                }
            }

            /** 由于未确定最短路径的结点数量不断减少，所以使用队列结果存储
             * 由于每次都需要找 到源点路径最短的结点，所以用优先队列存储 */
            // 存放当前可供选择的中转点(小根堆)
            PriorityQueue<Node> priQue = new PriorityQueue<>((a1, a2)->{
                return a1.minDistFromStart - a2.minDistFromStart;
            });
            // 存放已经确定到源点最小距离的节点
            Set<Integer> haveIdSet = new HashSet<>();

            // 从源点开始bfs遍历
            Node sNode = id2Node.get(source);
            priQue.offer(sNode);
            while (!priQue.isEmpty()) {
                // 开始更新当前点的邻居结点的距离
                Node curNode = priQue.poll();
                haveIdSet.add(curNode.id); // 当前已经确定到源点最短距离的节点
                for (Edge edge : curNode.edges) {
                    Node nxtNode = edge.toNode;
                    int weight = edge.weight;
                    if (haveIdSet.contains(nxtNode.id)) continue;  // 已经确定最短距离的节点进行过滤
                    /** 贪心策略：
                     *  min_dist(s->cur) + weight(cur->nxt) < min_dist(s->nxt)
                     *      满足上述条件，更新
                     * */
                    if (curNode.minDistFromStart + weight < nxtNode.minDistFromStart) {
                        nxtNode.minDistFromStart = curNode.minDistFromStart + weight;
                        if (!priQue.contains(nxtNode)) {
                            priQue.offer(nxtNode);
                        } else {
                            // 对于优先队列中已经存在的节点，需要删除原先节点，再添加新节点(更短的dist)
                            /** 这里先删除再添加，是因为当前优先队列中的nxtNode的mindist值已经改变了，但是未重新排序，所有需要这个操作 */
                            priQue.remove(nxtNode);
                            priQue.offer(nxtNode);
                        }
                    }
                }
            }
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public double maxProbability(int n, int[][] edges, double[] succProb, int start_node, int end_node) {
        Dijkstra algo = new Dijkstra();
        algo.buildGraph0(edges, succProb, n);
        algo.dijkstra(start_node, end_node);
        Dijkstra.Node node = algo.id2Node.get(end_node);
        return node.distFromStart==Integer.MIN_VALUE ? 0 : node.distFromStart;
    }

    class Dijkstra {

        class Edge {
            Node fromNode;
            Node toNode;
            double weight;
            Edge (Node fromNode, Node toNode, double weight) {
                this.fromNode = fromNode;
                this.toNode = toNode;
                this.weight = weight;
            }
        }

        class Node {
            int id;  // 对应每个结点的序号
            List<Edge> edges = new LinkedList<>();  // 存储边集合
            double distFromStart;  // 每个结点到源点的距离(有时候表示最大距离，有时候表示最小距离)
            Node (int id) {
                this.id = id;
            }

            Node(int id, double distFromStart) {
                this.id = id;
                this.distFromStart = distFromStart;
            }
        }

        Map<Integer, Node> id2Node = new HashMap<>(); // 存储已经创建的结点(必须包括所有节点)，避免重复创建

        /** 第一种输入矩阵
         * [
         *      [from, to, weight]
         * ]
         */
        void buildGraph0(int[][] matrix, double[] succProb, int n) {
            for (int i=0; i<matrix.length; i++) {
                int[] edge = matrix[i];
                int fromId = edge[0];
                int toId = edge[1];
                double weight = succProb[i];
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
                /** 注意： 无向图需要加两条边*/
                fromNode.edges.add(new Edge(fromNode, toNode, weight));
                toNode.edges.add(new Edge(toNode, fromNode, weight));
            }

            // 如果edge中不包括所有的节点，说明有孤立节点，需要通过下面的逻辑进行创建
            // todo 注意是从0开始，还是1开始
            for (int i=0; i<n; i++) {
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
        void buildGraph2(int[][] matrix) {
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
        }

        /** 第三种输入矩阵
         * 1 2 3
         * 4 5 6
         * 7 8 9
         * 节点是每个坐标的元素，边是相邻元素的差值作为权重
         * 参考【1631-最小体力消耗路径】
         */
        void buildGraph3(int[][] matrix) {
            int rows = matrix.length;
            int cols = matrix[0].length;
            for (int x=0; x<rows; x++) {
                for (int y=0; y<cols; y++) {
                    int id = x*cols+y;  // 将坐标编码为id /** 解码方式： x = id/rows; y = id%rows */
                    Node fromNode = id2Node.get(id);
                    if (fromNode == null) {
                        fromNode = new Node(id);
                        id2Node.put(id, fromNode);
                    }
                    // 从当前坐标值出发，上下左右构建4条边
                    int[][] directions = new int[][]{{1,0},{-1,0},{0,1},{0,-1}};
                    for (int[] direction : directions) {
                        int toX = x + direction[0];
                        int toY = y + direction[1];
                        if (toX < 0 || toX >= rows || toY < 0 || toY >= cols) continue;
                        int toId = toX * cols + toY;  // 将坐标编码为id /** 解码方式： x = id/rows; y = id%rows */
                        Node toNode = id2Node.get(toId);
                        if (toNode == null) {
                            toNode = new Node(toId);
                            id2Node.put(toId, toNode);
                        }
                        // 添加边关系
                        fromNode.edges.add(new Edge(fromNode, toNode, Math.abs(matrix[x][y]-matrix[toX][toY])));
                    }
                }
            }
        }

        /** 下面的逻辑是按照这里面的手算图来写的：
         *  https://www.yuque.com/u1589209/kdk87f/in2p1kynguzeh8v0
         */
        void dijkstra(int source, int target) {

            // 初始化所有节点到源节点的初始距离
            for (Node node : id2Node.values()) {
                if (source == node.id) {
                    node.distFromStart = 1;  // 源点到源点的最短距离为0
                } else {
                    node.distFromStart = Double.MIN_VALUE;
                }
            }

            /** 由于未确定最短路径的结点数量不断减少，所以使用队列结果存储
             * 由于每次都需要找 到源点路径最短的结点，所以用优先队列存储 */
            // 存放当前可供选择的中转点(大根堆)
            PriorityQueue<Node> priQue = new PriorityQueue<>((a1, a2)->{
                if (a1.distFromStart - a2.distFromStart < 0) return 1;
                else if (a1.distFromStart - a2.distFromStart > 0) return -1;
                else return 0;
            });
            // 存放已经确定到源点最小距离的节点
            Set<Integer> haveIdSet = new HashSet<>();

            // 从源点开始bfs遍历
            Node sNode = id2Node.get(source);
            priQue.offer(sNode);
            while (!priQue.isEmpty()) {
                // 开始更新当前点的邻居结点的距离
                Node curNode = priQue.poll();

                // 对于求某个节点到源节点的最短距离，可以提前判断结束(判断条件需要根据题意修改)
                if (curNode.id == target) {
                    break;
                }

                haveIdSet.add(curNode.id); // 当前已经确定到源点最短距离的节点
                for (Edge edge : curNode.edges) {
                    Node nxtNode = edge.toNode;
                    double weight = edge.weight;
                    if (haveIdSet.contains(nxtNode.id)) continue;  // 已经确定最短距离的节点进行过滤
                    /** 贪心策略：
                     *  max_dist(s->cur) * weight(cur->nxt) > max_dist(s->nxt)
                     *      满足上述条件，更新
                     * */
                    if (curNode.distFromStart * weight > nxtNode.distFromStart) {
                        nxtNode.distFromStart = curNode.distFromStart * weight;
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

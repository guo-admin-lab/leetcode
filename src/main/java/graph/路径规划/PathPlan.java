package graph.路径规划;

import java.util.*;

/** 路径规划算法
 *  Labuladong算法
 *  https://labuladong.online/algo/data-structure/dijkstra/
 *
 *  必看，快速学会两种算法的思路
 *  https://www.yuque.com/u1589209/kdk87f/in2p1kynguzeh8v0
 *
 *  doc/图算法目录下有其它学习资料
 *
 */

public class PathPlan {

    public static void main(String[] args) {
        /**
         * [743-网络延迟时间]
         * [1514-概率最大的路径]
         * [1631-最小体力消耗路径]
         */
    }

}

/** 迪杰斯特拉算法：（优先队列）
 *      贪心策略：
 *          1. 初始化所有节点到源点S的距离为无穷大
 *              设置源节点S到源节点S的距离为0
 *              将源节点S加入队列（初始化队列）
 *          2. 遍历优先队列：根据每个结点到S的距离排序
 *              cur = pq.poll();
 *              curNode = cur.node
 *              curDistance = cur.distance // cur结点本身到S的距离
 *              // 更新与curNode相连的结点距离
 *              for (neighbor : neighbors[curNode]) {
 *                  neighbor.distance // 邻居结点本身到S的距离
 *                  curDistance + weight(cur, neighbor) // S到cur结点的距离 + cur到neighbor的距离
 *                  if (S到cur结点的距离 + cur到neighbor的距离 < 邻居结点本身到S的距离) {
 *                      // 更新neighbor本身到S的距离
 *                      neighbor.distance = curDistance + weight(cur, neighbor)
 *                      // 将neighbor加入优先队列（包括到S的距离）
 *                      pg.offer(nerghbor)
 *                  }
 *              }
 */
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
        double distFromStart;  // 每个结点到源点的最短距离或最大距离
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
    void buildGraph1(int[][] matrix, int n) {
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
            /** 注意： 无向图需要加两条边*/
            // toNode.edges.add(new Edge(toNode, fromNode, weight));
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
                /** 注意： 无向图需要加两条边*/
                // toNode.edges.add(new Edge(toNode, fromNode, weight));
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
                    /** 注意： 无向图需要加两条边*/
                    // toNode.edges.add(new Edge(toNode, fromNode, weight));
                }
            }
        }
    }

    /** 下面的逻辑是按照这里面的手算图来写的：
     *  https://www.yuque.com/u1589209/kdk87f/in2p1kynguzeh8v0
     */
    void dijkstra(int source) {

        // 初始化所有节点到源节点的初始距离
        // todo （最大最小路径转换时）修改点3 ：距离中的初始化最大|最小值
        for (Node node : id2Node.values()) {
            if (source == node.id) {
                node.distFromStart = 0;  // 源点到源点的最短距离为0 (如果是乘法相关，可能需要初始化为1)
            } else {
                node.distFromStart = Double.MAX_VALUE;
            }
        }

        /** 由于未确定最短路径的结点数量不断减少，所以使用队列结果存储
         * 由于每次都需要找 到源点路径最短的结点，所以用优先队列存储 */
        // 存放当前可供选择的中转点(小根堆)
        // todo （最大最小路径转换时）修改点1 ：大根堆和小根堆变化
        PriorityQueue<Node> priQue = new PriorityQueue<>((a1, a2)->{
            if (a1.distFromStart - a2.distFromStart < 0) return -1;
            else if (a1.distFromStart - a2.distFromStart > 0) return 1;
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
            /*if (curNode.id == id2Node.size()-1) {
                break;
            }*/

            haveIdSet.add(curNode.id); // 当前已经确定到源点最短距离的节点
            for (Edge edge : curNode.edges) {
                Node nxtNode = edge.toNode;
                int weight = edge.weight;
                if (haveIdSet.contains(nxtNode.id)) continue;  // 已经确定最短距离的节点进行过滤
                /** 贪心策略：
                 *  min_dist(s->cur) + weight(cur->nxt) < min_dist(s->nxt)
                 *      满足上述条件，更新
                 * */
                // todo （最大最小路径转换时）修改点2 ：根据题意修改if条件中的大于|小于号
                if (curNode.distFromStart + weight < nxtNode.distFromStart) {
                    nxtNode.distFromStart = curNode.distFromStart + weight;
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


/** 弗洛伊德算法：图用邻接矩阵存储
 *  https://www.bilibili.com/video/BV1p841147t2/?spm_id_from=333.337.search-card.all.click&vd_source=7b2276f29cc5fd65128f4c4c85074775
 *
 *  https://zhuanlan.zhihu.com/p/405188391?ivk_sa=1024320u
 *
 *  用folyd算法 解决 郊区春游的题目
 *  解法：https://blog.csdn.net/jahup/article/details/105812587
 *  牛客题目：https://www.nowcoder.com/practice/75b87bec7e5c4acaaad39d9ae093dc3d?tpId=230&tqId=38948&ru=/exam/oj
 */
class Floyd {

    int[][] dis;
    int n;  // 地点数量

    Floyd(int n) {
        // n: 地点数量
        dis = new int[n+1][n+1];  // 下标从1开始
        // 初始化两两地点间距离为无穷大（与自身的距离为0）
        for (int i=0; i<=n; i++) {
            Arrays.fill(dis[i], Integer.MAX_VALUE);
            dis[i][i] = 0;
        }
    }

    // 求多源之间的最短路径
    void floyd(List<int[]> inputs) {
        // input: [地点1, 地点2, 花费]，其中：1<=地点1,地点2<=n

        // 1.邻接矩阵填值
        for (int[] input: inputs) {
            int x = input[0];
            int y = input[1];
            int c = input[2];
            dis[x][y] = dis[y][x] = c;
        }

        // 2.floyd算法(状态压缩dp)更新最小边权
        // 遍历 中转结点
        for(int k = 1; k <= n; k++) {
            // 遍历 起点结点
            for(int i = 1; i <= n; i++) {
                // 遍历 到达结点
                for(int j = i + 1; j <= n; j++) {
                    // 如果 起点->中转结点 或 中转结点->终点  有一个的距离为无穷大（道路不通），则更新后的距离一定还是无穷大，不需要更新
                    if (dis[i][k] == Integer.MAX_VALUE || dis[k][j] == Integer.MAX_VALUE) continue;
                    // 更新由中转结点带来的更短距离
                    dis[i][j] = Math.min(dis[i][j], dis[i][k] + dis[k][j]);
                }
            }
        }
    }

    // 对于郊区春游问题——解法一：回溯列举(排列；无重复；不可复选)
    int trackSum = 0;  // 遍历路径的花费和
    int trackCnt = 0;  // 遍历路径上的地点个数
    int minres = Integer.MAX_VALUE;
    boolean[] used;
    int jiaoquchunyou1(int[] places) {
        // places: 要去访问的地点
        used = new boolean[places.length+1];  // 默认全false
        // 回溯列举
        dfs1(places, places[0]);
        // 返回最小花销
        return minres;
    }
    void dfs1(int[] places, int prePlace) {
        // places: 要去访问的地点；prePlace：上一个访问的地方
        // base case
        if (trackCnt == places.length) {
            // 更新最小花费
            minres = Math.min(minres, trackSum);
            return;
        }

        // 回溯遍历
        for (int place : places) {
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

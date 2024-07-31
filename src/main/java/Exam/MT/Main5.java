package Exam.MT;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 小美想知道某一时刻中，某两人是否可以通过朋友介绍互相认识？
 事件共有 2 种：
 1 u v：代表编号 u 的人和编号 v 的人淡忘了他们的朋友关系。
 2 u v：代表小美查询编号 u 的人和编号 v 的人是否能通过朋友介绍互相认识。

 注：介绍可以有多层，
 比如 2 号把 1 号介绍给 3 号，然后 3 号再把 1 号介绍给 4 号，这样 1 号和 4 号就认识了。

        第一行输入三个正整数n,m,q，代表总人数，初始的朋友关系数量，发生的事件数量。
        接下来的m行，每行输入两个正整数u,v，代表初始编号u的人和编号v的人是朋友关系。
        接下来的q行，每行输入三个正整数op,u,v，含义如题目描述所述。
        1<= n <= 10^9
        1<= m,q <= 10^5
        1<= u,v <= n
        1<= op <= 2
        保证至少存在一次查询操作。

        对于每次 2号操作，输出一行字符串代表查询的答案。
        如果编号 u 的人和编号 v 的人能通过朋友介绍互相认识，则输出"Yes"。否则输出"No"。

 样例:
 输入：
 5 3 5
 1 2
 2 3
 4 5
 1 1 5
 2 1 3
 2 1 4
 1 1 2
 2 1 3
 输出：
 Yes
 No
 No

 思路：逆向构图 + 并查集（因为并查集不支持删除边操作，所以逆向遍历操作，并将删除操作变成插入操作）

*/

public class Main5 {

    static int[][] edges;
    static List<int[]> deleteEdges;
    static int[][] op;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 1.读取数据
        int n = in.nextInt();
        int m = in.nextInt();
        int q = in.nextInt();

        // 初始化并查集
        UF uf = new UF(n + 1);
        // 初始化边集合
        edges = new int[m][2];
        for (int i=0; i<m; i++) {
            edges[i][0] = in.nextInt();
            edges[i][1] = in.nextInt();
        }
        // 初始化操作集合和待删除边集合
        op = new int[q][3];
        deleteEdges = new ArrayList<>();
        for (int i=0; i<q; i++) {
            op[i][0] = in.nextInt();
            op[i][1] = in.nextInt();
            op[i][2] = in.nextInt();

            if (op[i][0] == 1) {
                // 如果是删除边的操作, 并且这条边在原边中存在
                if (existInEdges(op[i][1], op[i][2])) {
                    deleteEdges.add(new int[]{op[i][1], op[i][2]});
                }
            }

        }

        // 逆向构图（如果某条边在之后要被删除，那么就不让他在图中连通）
        for (int[] edge : edges) {
            if (existInDeleteEdges(edge[0], edge[1])) {
                continue;
            }
            uf.union(edge[0], edge[1]);
        }

        // 逆向遍历操作（把之前的删除边操作，变成添加边操作）
        for (int i=op.length-1; i>=0; i--) {
            if (op[i][0] == 2) {
                System.out.println(uf.connect(op[i][1], op[i][2]));
            } else {
                // 如果是删除操作，需要看看最原始的边中有没有这条边
                if (existInEdges(op[i][1], op[i][2])) {
                    uf.union(op[i][1], op[i][2]);
                }
            }
        }
    }

    // 这里可以用set来优化
    static boolean existInDeleteEdges(int u, int v) {
        for (int[] edge : deleteEdges) {
            if ((edge[0]==u && edge[1]==v) || (edge[0]==v && edge[1]==u)) {
                return true;
            }
        }
        return false;
    }

    // 这里可以用set来优化
    static boolean existInEdges(int u, int v) {
        for (int[] edge : edges) {
            if ((edge[0]==u && edge[1]==v) || (edge[0]==v && edge[1]==u)) {
                return true;
            }
        }
        return false;
    }
}

class UF {
    int[] parent;
    int count;

    UF(int n) {
        // 1.初始化连通分量个数
        this.count = n;
        // 2.初始化每个节点的父节点为自己
        parent = new int[n];
        for (int i=0; i<n; i++) {
            parent[i] = i;
        }
    }

    void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX == rootY) return;
        parent[rootX] = rootY;
        count--;
    }

    boolean connect(int x, int y) {
        return find(x) == find(y);
    }

    int find(int i) {
        if (parent[i] != i) {
            parent[i] = find(parent[i]);
        }
        return parent[i];
    }
}

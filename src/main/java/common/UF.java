package common;

public class UF {

    // 连通分量个数
    private int count;
    // 存储每个节点的父节点
    private int[] parent;

    // n 为图中节点的个数
    public UF(int n) {
        // 1.初始化连通分量个数为节点个数n
        count = n;
        // 2.初始化每个节点的父节点为自己
        parent = new int[n];
        for (int i=0; i<n; i++) {
            parent[i] = i;
        }
    }

    // 连通p、q
    public void union(int p, int q){
        int rootP = find(p);
        int rootQ = find(q);
        // 如果p、q本身就连通，无需操作，直接返回
        if (rootP == rootQ) return;
        // 连通p、q，并减少连通分量
        parent[rootP] = rootQ;
        count--;
    }

    // 判断p、q是否连通
    public boolean connect(int p, int q) {
        return find(p) == find(q);
    }

    // 返回连通分量个数
    public int count() {
        return count;
    }

    // 寻找p、q的根
    int find(int i) {
        if (parent[i] != i) {
            parent[i] = find(parent[i]);
        }
        return parent[i];
    }
    // 另一种写法，作用同上
    int find1(int i) {
        // 如果i为根，直接返回
        if (parent[i] == i) return i;
        // 一路向上，并不断更新i的父节点为根
        parent[i] = find(parent[i]);  // 后序：返回上一个结点的父节点，并依次更新
        return parent[i];
    }
}

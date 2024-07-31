import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import common.TreeNode;

public class Test230 {

    public static void main(String[] args) {
        TreeNode n1 = new TreeNode(3);
        TreeNode n2 = new TreeNode(1);
        TreeNode n3 = new TreeNode(4);
        TreeNode n4 = new TreeNode(2);

        n1.left = n2;
        n1.right = n3;
        n2.right = n4;

        Solution230 solution230 = new Solution230();
        int res = solution230.method3(n1, 1);
        System.out.println(res);

    }

}

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */
class Solution230 {

    public int kthSmallest(TreeNode root, int k) {
        // return method1(root, k);
        method2(root, k);
        return res;
    }

    // 方法一：中序遍历-迭代  O(H+k) O(H)
    public int method1(TreeNode root, int k) {
        Stack<TreeNode> stk = new Stack<>();
        while (root != null || !stk.isEmpty()) {
            while (root != null) {
                stk.push(root);
                root = root.left;
            }
            TreeNode cur = stk.pop();
            // 中序
            k--;
            if (k == 0) return cur.val;
            root = cur.right;
        }
        return -1;
    }

    // 方法二：中序遍历-递归
    int cnt = 0;
    int res = 0;

    public void method2(TreeNode root, int k) {
        if (root == null) return;
        method2(root.left, k);
        cnt++;
        if (cnt == k) {
            res = root.val;
            return;
        }
        method2(root.right, k);
    }

    // https://leetcode.cn/problems/kth-smallest-element-in-a-bst/?envType=study-plan-v2&envId=top-100-liked
    // 方法三：记录子树的结点数（优化方法）
    Map<TreeNode, Integer> node2Num = new HashMap<>();
    public int method3(TreeNode root, int k) {
        // 1.计算每个node其左右子树的节点个数，存储在node2Num中
        countNoneNum(root);  // O(n)
        // 2.遍历整个树，根据节点个数判断第k小的值在左子树还是右子树
        TreeNode node = root;
        while (node != null) {
            // 如果node左子树结点数leftNodeNum < k
            int leftNodeNum = node2Num.get(node.left);
            if (leftNodeNum == k + 1) {
                // node.left就是要找的值
                return node.left.val;
            } else if (leftNodeNum > k + 1) {
                // 目标值在左子树中
                node = node.left;
            } else if (leftNodeNum < k + 1) {
                // 目标值在右子树中
                node = node.right;
            }
        }
        return -1;
    }

    // 统计以node为根结点的子树的结点数
    public int countNoneNum(TreeNode root) {
        // base case
        if (root == null) return 0;
        // 递归定义：子树节点个数 = fun(node.child)
        int leftNodeNum = countNoneNum(root.left);
        int rightNodeNum = countNoneNum(root.right);
        // 当前节点左右子树的总节点个数（包括自身）
        int nodeNum = leftNodeNum + rightNodeNum + 1;
        node2Num.put(root, nodeNum);
        // 返回当前总节点个数
        return nodeNum;
    }

    // https://leetcode.cn/problems/kth-smallest-element-in-a-bst/?envType=study-plan-v2&envId=top-100-liked
    // 方法四：平衡二叉搜索树（优化方法）
    public int method4(TreeNode root, int k) {
        return -1;
    }
}

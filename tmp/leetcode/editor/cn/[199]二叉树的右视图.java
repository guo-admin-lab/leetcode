
//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {

    public List<Integer> rightSideView(TreeNode root) {
        // return method1(root);
//        method2_1(root, 0);
//        method2_2(root);
//        return res;
        return method2_3(root);
    }

    // 方法一：bfs层序遍历，压入每一层的最后一个节点
    /** 思路分析
     *              1
     *          2       3
     *       4    5   6    7
     *                   8   9
     *                 1   2
     *                   3
     */
    public List<Integer> method1(TreeNode root) {
        if (root == null) return Collections.emptyList();
        List<Integer> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                TreeNode node = queue.poll();
                if (size == 1) res.add(node.val);
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
                size--;
            }
        }
        return res;
    }

    // 方法二1：递归dfs反先序遍历，记录每一层最右面的节点
    List<Integer> res = new ArrayList<>();
    Set<Integer> depthSet = new HashSet<>();  // 记录每一层是否已经有最右侧节点了
    public void method2_1(TreeNode root, int depth) {
        if (root == null) return;
        // 判断方法1：反先序遍历，如果这一层的深度还没有被记录，则说明是最右侧节点
        if (!depthSet.contains(depth)) {
            res.add(root.val);
            depthSet.add(depth);
        }
        // 判断方法2：反先序遍历，如果当前层数 等于 res的数量，说明这是这一层的最右侧节点（注意：root层=0）
        // if (res.size() == depth) res.add(root.val);
        method2_1(root.right, depth+1);
        method2_1(root.left, depth+1);
    }

    // 方法二2：递归dfs反先序遍历
    int depth = 0;
    public void method2_2(TreeNode root) {
        if (root == null) return;
        if (res.size() == depth) res.add(root.val);
        depth++;
        method2_2(root.right);
        method2_2(root.left);
        depth--;
    }

    // 方法二3：迭代dfs反先序遍历
    public List<Integer> method2_3(TreeNode root) {
        if (root == null) return Collections.emptyList();

        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> nodeStk = new Stack<>();
        Stack<Integer> depthStk = new Stack<>();
        nodeStk.push(root);
        depthStk.push(0);

        while (!nodeStk.isEmpty()) {
            TreeNode node = nodeStk.pop();
            int depth = depthStk.pop();
            if (res.size() == depth) res.add(node.val);
            if (node.left != null) {
                nodeStk.push(node.left);
                depthStk.push(depth+1);
            }
            if (node.right != null) {
                nodeStk.push(node.right);
                depthStk.push(depth+1);
            }
        }

        return res;
    }


}
//leetcode submit region end(Prohibit modification and deletion)

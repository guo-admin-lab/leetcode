
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

    public TreeNode invertTree(TreeNode root) {
        return method1_1(root);
//        method1_2(root);
//        return root;
//        return method2_1(root);
    }

    // 方法一1: dfs递归翻转遍历   从下往上翻转
    public TreeNode method1_1(TreeNode root) {
        if (root == null) return root;
        // 如果节点左右子节点都为null，不需要进行翻转
        if (root.left == null && root.right == null) return root;
        // 1.递归翻转左右子树
        TreeNode leftRoot = method1_1(root.left);  // 获取左子树翻转后的root
        TreeNode rightRoot = method1_1(root.right);  // 获取左子树翻转后的root
        // 2.将左右子节点进行翻转
        root.left = rightRoot;
        root.right = leftRoot;
        // 返回反转后的root
        return root;
    }

    // 方法一2: dfs递归翻转遍历   从上往下翻转
    public void method1_2(TreeNode root) {
        if (root == null) return;
        if (root.left == null && root.right == null) return;
        // 1.先将左右子节点进行翻转
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        // 2.递归翻转左右子树
        method1_2(root.left);
        method1_2(root.right);
    }

    // 方法二：栈迭代模拟递归（用队列也可以实现）
    public TreeNode method2_1(TreeNode root) {
        Stack<TreeNode> stk = new Stack<>();
        // 栈模拟递归
        stk.push(root);
        while (!stk.isEmpty()) {
            // 弹出下一个节点
            TreeNode node = stk.pop();
            // base case
            if (node == null || (node.left == null && node.right == null)) continue;
            // 1.先将左右子节点进行翻转
            TreeNode tmp = node.left;
            node.left = node.right;
            node.right = tmp;
            // 2.压栈翻转左右子树
            stk.push(node.left);
            stk.push(node.right);
        }
        return root;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

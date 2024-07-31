
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

    public List<Integer> inorderTraversal(TreeNode root) {
//        method1(root);
//        return res;
        return method2(root);
    }

    // 回溯递归法
    List<Integer> res = new ArrayList<>();
    public void method1(TreeNode root) {
        if (root == null) return;
        method1(root.left);
        res.add(root.val);
        method1(root.right);
    }

    // 动态规划递归法
    public List<Integer> method2(TreeNode root) {
        if (root == null) return Collections.emptyList();

        List<Integer> res = new ArrayList<>();
        List<Integer> leftRes = method2(root.left);  // 左子树中序遍历结果
        // 1.加入左子树中序遍历结果
        res.addAll(leftRes);
        // 2.加入根节点
        res.add(root.val);
        List<Integer> rightRes = method2(root.right);  // 右子树中序遍历结果
        // 3.加入右子树中序遍历结果
        res.addAll(rightRes);

        return res;
    }

    // 栈模拟迭代法
    public List<Integer> method3(TreeNode root) {
        return null;
    }

    // 空间复杂度O(1)的算法，还没看
    // https://leetcode.cn/problems/binary-tree-inorder-traversal/solutions/412886/er-cha-shu-de-zhong-xu-bian-li-by-leetcode-solutio/?envType=study-plan-v2&envId=top-100-liked
}
//leetcode submit region end(Prohibit modification and deletion)

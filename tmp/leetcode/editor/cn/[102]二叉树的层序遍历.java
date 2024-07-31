
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

    public List<List<Integer>> levelOrder(TreeNode root) {
        return method1(root);
    }

    // 方法一
    public List<List<Integer>> method1(TreeNode root) {
        if (root == null) return Collections.emptyList();
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> layerNodes = new ArrayList<>();
            while (size > 0) {
                /** 特别注意，如果队列中有null值，poll不会将null值弹出，而是将其永远留在队首，导致死循环了 */
                TreeNode node = queue.poll();
                // if (node == null) continue;
                layerNodes.add(node.val);
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
                size--;
            }
            res.add(layerNodes);
        }
        return res;
    }
}
//leetcode submit region end(Prohibit modification and deletion)


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

    public void flatten(TreeNode root) {
        method2(root);
    }

    /** 注意：本题目要求在原树上修改 */
    // 方法一：递归 + 中序位置展平处理
    /** 思路：将node.left插入node和node.right中间
     *   1 -> 5 -> 6
     *   \
     *    2 -> 4 -> 7
     *    \    \
     *     3    8
     *  交换步骤X：
     *      1. 找到 node.left的末尾节点 nodeLast
     *          tmpNode = node.left
     *          while(tmpNode.right != null) tmpNode = tmpNode.right
     *          nodeLast = tmpNode;
     *      2. 展开节点
     *      nodeLast.right = node.right
     *      node.right = node.left
     *      node.left = null
     *
     *  第1步：node = [2] 执行步骤X
     *  1 -> 5 -> 6
     *  \
     *   2 -> 3 -> 4 -> 7
     *              \
     *               8
     *
     *  第2步：node = [1] 执行步骤X
     *  1 -> 2 -> 3 -> 4 -> 5 -> 6
     *
     * */
    public void method1(TreeNode root) {
        if (root == null) return;
        if (root.left == null) {  // 如果左节点为空，则不需要进行翻转，直接遍历右子树即可
            method1(root.right);
        } else {  // 如果左节点不为空，需要先把左子树展平后，再遍历右子树
            method1(root.left);

            // --- 中序位置 ---
            // 1.找到已经展平的node.left链表的末尾节点 nodeLast
            TreeNode lastNode = root.left;
            while (lastNode.right != null) lastNode = lastNode.right;
            // 2.记录下当前节点的右节点，作为下一个处理根节点
            TreeNode nxtNode = root.right;
            // 3.展平节点
            lastNode.right = root.right;
            root.right = root.left;  // 这里root.right会发生变化
            root.left = null;

            // 4.处理下一个节点
            method1(nxtNode);
        }
    }

    // 方法二：递归 + 后序位置合并
    /** 思路：先将左右子树都展平后，再把左子树拼到右子树上面*/
    public void method2(TreeNode root) {
        if (root == null) return;

        // 将左右子树展平
        method2(root.left);
        method2(root.right);

        // 后序位置
        // 1.如果左节点为空，不需要处理
        if (root.left == null) return;
        // 将左子树拼接到root和root.right中间
        // 1.找到root.left的末尾节点
        TreeNode lastNode = root.left;
        while (lastNode.right != null) lastNode = lastNode.right;
        lastNode.right = root.right;
        root.right = root.left;
        root.left = null;
    }

    // 还有一些其它的方法没看
    // https://leetcode.cn/problems/flatten-binary-tree-to-linked-list/solutions/356853/er-cha-shu-zhan-kai-wei-lian-biao-by-leetcode-solu/?envType=study-plan-v2&envId=top-100-liked
}
//leetcode submit region end(Prohibit modification and deletion)

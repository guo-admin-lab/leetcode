
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

    public boolean isValidBST(TreeNode root) {
//        return method1(root, Long.MIN_VALUE, Long.MAX_VALUE);
        return method2(root);
    }

    // 方法一错误思路
    /**  递归思路
     *      0.base case
     *      if (root == null) return true;
     *      1.判断以当前节点为根的树，和左右子节点是否满足二叉搜索树的定义
     *          如果 root.left.val >= root.val  or  root.right.val < root.val: (即有一个条件不满足，则直接返回false，不用再向下比较了)
     *              return false;
     *      2.判断左右子树是否为 二叉搜索树
     *      b1 = fun(root.left)
     *      b2 = fun(root.right)
     *      3.返回左右子树的比较结果
     *      return b1 && b2;
     */
    public boolean method_err(TreeNode root) {
        // 0.base case
        if (root == null) return true;
        // 1.判断以当前节点为根的树，和左右子节点是否满足二叉搜索树的定义 (即有一个条件不满足，则直接返回false，不用再向下比较了)
        if (root.left == null && root.right == null) return true;
        if (root.left == null) {
            if (root.right.val <= root.val) return false;
        } else if (root.right == null) {
            if (root.left.val >= root.val) return false;
        } else {
            if (root.left.val >= root.val || root.right.val <= root.val) return false;
        }
        // 2.判断左右子树是否为 二叉搜索树
        boolean b1 = method1(root.left);
        boolean b2 = method1(root.right);
        // 3.返回左右子树的比较结果
        return b1 && b2;
    }

    // 方法一：递归
    /** 特别注意：左子树所有节点都要比root小，右子树所有节点都要比root大 */
    /** 过程分析
     *  (10, -inf, inf)
     *      (5, -inf, 10)
     *      (15, 10, inf)
     *          (12, 10, 15)
     *              (_, 10, 12)
     *              (_, 12, inf)
     *          (20, 15, inf)
     *              (_, 10, 20)
     *              (_, 20, inf)
     */
    /** 注意：由于这里root.val最小值可能会等于Integer.MIN_VALUE，
     *          所以，要用Long.MIN_VALUE来表示更小的数
     * */
    public boolean method1(TreeNode root, long minVal, long maxVal) {
        // base case
        if (root == null) return true;
        // 需要保证 minVal < root.val < maxVal
        if (root.val < minVal || root.val > maxVal) return false;
        // 递归判断左右子树是否满足条件
        boolean b1 = method1(root.left, minVal, root.val);
        boolean b2 = method1(root.right, root.val, maxVal);
        // 返回判断结果
        return b1 && b2;
    }

    // 方法二：中序遍历
    /** 由二叉搜索树的特性可得，中序遍历此二叉树，结果一定是升序排列的 */
    public boolean method2(TreeNode root) {
        long preVal = Long.MIN_VALUE;
        Stack<TreeNode> stk = new Stack<>();
        while (root != null || !stk.isEmpty()) {
            while (root != null) {
                stk.push(root);
                root = root.left;
            }
            TreeNode cur = stk.pop();
            // 中序
            if (cur.val <= preVal) return false;
            preVal = cur.val;
            root = cur.right;
        }
        return true;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

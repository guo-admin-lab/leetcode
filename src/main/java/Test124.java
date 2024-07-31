public class Test124 {

    public static void main(String[] args) {
//        TreeNode n_10 = new TreeNode(-10);
//        TreeNode n9 = new TreeNode(9);
//        TreeNode n20 = new TreeNode(20);
//        TreeNode n15 = new TreeNode(15);
//        TreeNode n7 = new TreeNode(7);
//
//        n_10.left = n9;
//        n_10.right = n20;
//        n20.left = n15;
//        n20.right = n7;
//
//        Solution124 solution124 = new Solution124();
//        int i = solution124.maxPathSum(n_10);
//        System.out.println(i);

    }

}

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

// todo 下面的代码因为遍历找不到TreeNode，暂注释掉
class Solution124 {

//    public int maxPathSum(TreeNode root) {
//        method1(root);
//        return maxLen;
//    }

    // 方法一: 后序 + 更新最大路径和
    /** 思路分析
     以root为根节点，其最大路径和有四种可能
     root.val
     leftLen + root.val
     rightLen + root.val
     leftLen + rightLen + root.val
     */
    int maxLen = Integer.MIN_VALUE;
//    public int method1(TreeNode root) {
//        if (root == null) return 0;
//        int leftMaxLen = method1(root.left);
//        int rightMaxLen = method1(root.right);
//        // 后序位置
//        int curMaxLen = root.val;
//        if (leftMaxLen > 0) curMaxLen += leftMaxLen;
//        if (rightMaxLen > 0) curMaxLen += rightMaxLen;
//        // 更新最大路径和
//        maxLen = Math.max(maxLen, curMaxLen);
//        // 返回当前最大路径和
//        /** 注意：这里只能返回一条子树分支的路径和 */
//        return root.val + Math.max(Math.max(leftMaxLen, 0), Math.max(rightMaxLen, 0));
//    }

}

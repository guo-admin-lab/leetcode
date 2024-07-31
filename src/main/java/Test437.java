public class Test437 {
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
class Solution437 {

    public int pathSum(TreeNode root, int targetSum) {
        return -1;
    }

    int allRes = 0;
    int pathSum = 0;
    int res = 0;

    // 方法一: 暴力统计
    /** 以每个节点作为根节点，依次遍历满足条件的路径个数 */
    public void method1(TreeNode root, int targetSum) {
        // 遍历整个树，以每个节点作为根节点，统计其满足条件的路径个数
        if (root == null) return;
        allRes += res;
        pathSum=0; res=0;
        countByTreeRoot(root, targetSum);  // 计算以当前节点作为根节点，满足条件的路径个数
        method1(root.left, targetSum);  // 遍历左子树
        method1(root.right, targetSum); // 遍历右子树
    }


    public void countByTreeRoot(TreeNode root, int targetSum) {
        if (root == null) return;
        // 1.先序
        // 1.1.判断路径和是否满足条件
        if (pathSum == targetSum) res++;
        // 1.2.更新路径和(增加)
        pathSum += root.val;
        // 2.递归统计左右子树满足条件的路径
        countByTreeRoot(root.left, targetSum);
        countByTreeRoot(root.right, targetSum);
        // 3.后序
        // 3.1.更新路径和(减少)
        pathSum -= root.val;
        // 3.2.返回当前子树整体满足条件的个数
    }
}

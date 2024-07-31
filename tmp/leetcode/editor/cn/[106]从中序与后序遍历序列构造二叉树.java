
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

    Map<Integer, Integer> val2Index = new HashMap<>();

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        // 从中序数组构建值-》索引的map
        for (int i=0;  i<inorder.length; i++) {
            val2Index.put(inorder[i],i);
        }

        return method1(inorder, 0, inorder.length-1, postorder, 0, postorder.length-1);
    }

    public TreeNode method1(int[] inorder, int inStart, int inEnd, int[] postorder, int postStart, int postEnd) {
        // base case
        if (postStart > postEnd) return null;
//        if (inStart > inEnd) return null;
        // 根据postEnd定位到当前根节点
        int rootVal = postorder[postEnd];
        TreeNode root = new TreeNode(rootVal);
        // 从定位根节点在inorder中的索引位置，划分左右子树
        int midIndex = val2Index.get(rootVal);
        // 计算左子树结点的数量
        int leftSize = midIndex - inStart;
        /** 左子树
         * inStart1 = inStart
         * inEnd1 = midIndex-1
         *
         * postStart1 = postStart;
         * postEnd1 = postStart+leftSize-1;
         *
          */
        TreeNode left = method1(inorder, inStart, midIndex-1, postorder, postStart, postStart+leftSize-1);
        /** 右子树
         * inStart2 = midIndex+1
         * inEnd2 = inEnd
         *
         * postStart2 = postStart+leftSize;
         * postEnd2 = postEnd-1;
         */
        TreeNode right = method1(inorder, midIndex+1, inEnd, postorder, postStart+leftSize, postEnd-1);
        // 连接父子结点
        root.left = left;
        root.right = right;
        return root;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

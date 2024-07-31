
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

    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        for (int i=0; i<postorder.length; i++) {
            val2Index.put(postorder[i], i);
        }
        return method1(preorder, postorder, 0, preorder.length-1, 0, postorder.length-1);
    }

    public TreeNode method1(int[] preorder, int[] postorder, int preStart, int preEnd, int postStart, int postEnd) {
        // base case
        if (preStart > preEnd) return null;
        // easy error, 防止下面的preStart+1索引溢出
        if (preStart == preEnd) return new TreeNode(preorder[preStart]);

        /** 由于前序+后序没办法确定唯一二叉树，所以可按照如下方法构造一棵二叉树
         *      1. 根据preStart确定根节点
         *      2. 将preStart的下一个元素作为左子树的根节点leftVal
         *      3. 在post数组中定位leftVal对应的索引位置X，将X两边作为左右子树来构建
         */
        // 根据preStart确定根节点
        int rootVal = preorder[preStart];
        TreeNode root = new TreeNode(rootVal);
        // （随机选择）将preStart的下一个元素作为左子树的根节点leftVal
        int leftVal = preorder[preStart+1];
        // 在post数组中定位leftVal对应的索引位置X，将X两边作为左右子树来构建
        int leftIndex = val2Index.get(leftVal);
        // 计算左右子树的大小
        int leftSize = leftIndex - postStart + 1;
        int rightSize = postEnd - leftIndex - 1;

        /** 左子树
         *  preStart1 = preStart+1
         *  preEnd1 = preStart+leftSize
         *
         *  postStart1 = postStart
         *  postEnd1 = leftIndex
         */
        TreeNode leftRoot = method1(preorder, postorder, preStart+1, preStart+leftSize, postStart, leftIndex);
        /** 右子树
         *  preStart2 = preEnd1+1
         *  preEnd2 = preEnd
         *
         *  postStart2 = leftIndex+1
         *  postEnd2 = postEnd-1
         *
         */
        TreeNode rightRoot = method1(preorder, postorder, preStart+leftSize+1, preEnd, leftIndex+1, postEnd-1);
        root.left = leftRoot;
        root.right = rightRoot;
        return root;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

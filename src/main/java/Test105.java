public class Test105 {

    public static void main(String[] args) {
        int[] pre = new int[]{2,1};
        int[] in = new int[]{1,2};
        TreeNode treeNode = buildTree(pre, in);
        System.out.println(treeNode);
    }

    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        return method1(preorder, inorder, 0, preorder.length-1, 0, inorder.length-1);
    }

    public static TreeNode method1(int[] preorder, int[] inorder, int pre_start, int pre_end, int in_start, int in_end) {
        if (preorder.length == 0) return null;
        if (preorder.length == 1) return new TreeNode(preorder[0]);
//        // base case
//        if (pre_start >= pre_end) return null;
        // 1.先序数组的首元素为根节点
        int rootVal = preorder[pre_start];
        TreeNode root = new TreeNode(rootVal);
        // 2.中序数组中，root的索引位置
        int inRootIndex = -1;
        for (int i=0; i<inorder.length; i++) {
            if (rootVal == inorder[i]) {
                inRootIndex = i;
                break;
            }
        }
        // 3.左子树节点个数
        int leftTreeNodeNum = inRootIndex - in_start;
        // 4.右子树节点个数
        int rightTreeNodeNum = in_end - inRootIndex;
        /** base case 特殊位置 */
        TreeNode leftRoot;
        TreeNode rightRoot;
        // 5.1.当左子树节点个数=1时，不需要再次递归创建了
        if (leftTreeNodeNum == 0) {
            leftRoot = null;
        } else if (leftTreeNodeNum == 1) {
            int leftNodeVal = inorder[inRootIndex-1];
            leftRoot = new TreeNode(leftNodeVal);
        } else {
            // 5.2.递归获取左子树的根节点
            leftRoot = method1(preorder, inorder, pre_start+1, pre_start+leftTreeNodeNum, in_start, inRootIndex-1);
        }
        // 5.3.当右子树节点个数=1时，不需要再次递归创建了
        if (rightTreeNodeNum == 0) {
            rightRoot = null;
        } else if (rightTreeNodeNum == 1) {
            int rightNodeVal = inorder[inRootIndex+1];
            rightRoot = new TreeNode(rightNodeVal);
        } else {
            // 5.4.递归获取右子树的根节点
            rightRoot = method1(preorder, inorder, pre_start+leftTreeNodeNum+1, pre_start+leftTreeNodeNum+rightTreeNodeNum-1, inRootIndex+1, inRootIndex+1+rightTreeNodeNum-1);
        }
        // 6.连接root和左右子树
        root.left = leftRoot;
        root.right = rightRoot;
        // 7.返回树根节点
        return root;
    }
}

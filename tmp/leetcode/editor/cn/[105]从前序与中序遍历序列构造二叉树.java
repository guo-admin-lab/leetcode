
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

    // 存储 inorder 中值到索引的映射
    HashMap<Integer, Integer> valToIndex = new HashMap<>();

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // 建立 inorder数组中，值到索引的映射
        for (int i = 0; i < inorder.length; i++) {
            valToIndex.put(inorder[i], i);
        }
        return method2(preorder, inorder, 0, preorder.length-1, 0, inorder.length-1);
    }

    // 方法一：递归 + 自己写的 + 细节弄的比较复杂
    /**  思路分析
     *   前序：3 9 20 15 7
     *   中序：9 3 15 20 7
     *
     *   前序数组首位置：root
     *   中序数组root索引左右两边属于 左右子树
     */
    /** 过程分析
     *  1 —— —— 3 —— 7
     *  \        \
     *   \        6
     *    2 —— 5
     *    \
     *     4
     *              0   1 2 3   4 5 6
     *  前序preArr: [1] (2 4 5) (3 6 7)
     *  中序inArr:  (4 2 5) [1] (6 3 7)
     *              0 1 2   3   4 5 6
     *  ----扩展后序+中序遍历----
     *                               0
     *  后序postArr: (4 5 2) (6 7 3) [1]
     *  中序inArr: (4 2 5) [1] (6 3 7)
     *             0 1 2   3   4 5 6
     *
     *  ----扩展前序+后序遍历----
     *  前序preArr: [1] (2 4 5) (3 6 7)
     *  后序postArr: (4 5 2) (6 7 3) [1]
     *
     *  总结：
     *      1.  pre_start = 0, pre_end = preArr.length-1 = 6,  in_start = 0, in_end = inArr.length-1=6
     *          root = preArr[0] = preArr[pre_start] = 1
     *          中序数组中，root的索引位置: inRootIndex = inArr.indexOf(root) = 3
     *          左子树节点个数：leftTreeNodeNum = inRootIndex - 0  = inRootIndex - in_start = 3
     *          右子树节点个数：rightTreeNodeNum = (inArr.length-1) - inRootIndex = in_end - inRootIndex = 6 - 3 = 3
     *          --- 以下为 递归 的参数 ---
     *          1.1.  pre_start1 = 0+1 = pre_start+1 = 1,  pre_end1 = 0+3 = pre_start+leftTreeNodeNum = 3,  in_start1 = in_start = 0, in_end1 = inRootIndex-1 = 2
     *              root = preArr[1] = preArr[pre_start1] = 2
     *              中序数组中，root的索引位置: inRootIndex = inArr.indexOf(root) = 1
     *              左子树节点个数：leftTreeNodeNum = inRootIndex - 0 = inRootIndex - in_start1 = 1 - 0 = 1
     *              右子树节点个数：rightTreeNodeNum = in_end1 - inRootIndex = 2 - 1 = 1
     *              --- 递归 ---
     *              1.1.1. pre_start11 = 2, pre_end11 = 2, in_start11 = 0, in_end11 = 0
     *              1.1.2. pre_start12 = 3, pre_end12 = 2, in_start12 = 2, in_end12 = 2
     *
     *          1.2.  pre_start2 = pre_end1+1 = 4,  pre_end2 = pre_end1+rightTreeNodeNum-1 = 4+3-1 = 6, in_start2 = inRootIndex+1 = 4, in_end2 = in_start2+rightTreeNodeNum-1=6
     *              root = preArr[4] = 3
     *              inRootIndex = 5
     *              leftTreeNodeNum = 1
     *              rightTreeNodeNum = 1
     *              --- 递归 ---
     *              1.2.1. pre_start21 = 5, pre_end22 = 5,
     */
    /**  特殊样例分析：
     *              0   1   2  3  4
     *  前序preArr: [3] (9) (20 15 7)
     *  中序inArr:  (9) [3] (15 20 7)
     *              0   1   2  3  4
     *  总结：
     *      1. p_s=0, p_e=4, i_s=0, i_e=4
     *          root = preArr[0] = 3
     *          inRootIndex = 1
     *          leftTreeNodeNum = inRootIndex - i_s = 1
     *          rightTreeNodeNum = i_e - inRootIndex = 4 - 1 = 3
     *          --- 递归 ---
     *          1.1. ps = ps+1 = 1, pe=ps+左子树节点个数=0+1=1, is=is=0, ie=inRootIndex-1=1-1=0;
     *
     */
    public TreeNode method1(int[] preorder, int[] inorder, int pre_start, int pre_end, int in_start, int in_end) {
        if (preorder.length == 0) return null;
//        if (preorder.length == 1) return new TreeNode(preorder[0]);
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

    // 方法二：递归 + labuladong细节处理后的代码很简单
    public TreeNode method2(int[] preorder, int[] inorder, int preStart, int preEnd, int inStart, int inEnd) {
        // base case
        /** 这里不能是 preStart>=preEnd, 因为当preStart=preEnd时，代表当前还有一个节点需要创建 */
        if (preStart > preEnd) return null;
        // 获取当前的根节点(前序数组的首节点)
        TreeNode root = new TreeNode(preorder[preStart]);
        // 找到中序数组中根节点的索引值
        int rootIndexOfInorder = valToIndex.get(root.val);
        // 计算左子树的节点个数
        int leftSize = rootIndexOfInorder - inStart;
        // 递归获取构建好的左右子树根节点
        /** 左子树
         *  preStart = preStart+1 （前序数组中：根节点的下一个节点）
         *  preEnd = preStart + (rootIndexOfInorder - inStart)（前序数组中：根节点索引 + 左子树结点个数）
         *  inStart = inStart（中序数组中：首节点）
         *  inEnd = rootIndexOfInorder-1（中序数组中：根节点索引的前一个节点）
         */
        TreeNode leftRoot = method2(preorder, inorder, preStart+1, preStart + leftSize, inStart, rootIndexOfInorder-1);
        /** 右子树
         *  preStart = preStart + (rootIndexOfInorder - inStart) + 1（前序数组中：根节点 + 左子树结点个数 + 1）
         *  preEnd = preEnd（前序数组中：末尾节点）
         *  inStart = rootIndexOfInorder+1（中序数组中：根节点索引的下一个节点）
         *  inEnd = inEnd（中序数组中：末尾节点）
         */
        TreeNode rightRoot = method2(preorder, inorder, preStart + leftSize +1, preEnd, rootIndexOfInorder+1, inEnd);
        // 连接根节点和左右子树
        root.left = leftRoot;
        root.right = rightRoot;
        // 返回根节点
        return root;
    }

    // todo 还没看
    // 方法三：迭代 + 官方给的示例
    // https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/solutions/255811/cong-qian-xu-yu-zhong-xu-bian-li-xu-lie-gou-zao-9/?envType=study-plan-v2&envId=top-100-liked
    public TreeNode method3(int[] preorder, int[] inorder) {
        return null;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

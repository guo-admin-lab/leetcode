
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

    int maxLen = 0;

    public int diameterOfBinaryTree(TreeNode root) {
//        method1(root);
//        return maxLen;
        return method2(root);
    }

    // 方法一：递归
    /**
     *  最长直径，一定是以某个非叶子节点为中心，其左右两棵子树最长长度之和
     *  递归函数定义：分别返回左右子树的最大长度
     *      left_max_len = fun(root.left)
     *      right_max_len = fun(root.right)
     *      // 更新最大值
     *      max_len = max(man_len, left_max_len+right_max_len+1)
     */
    public int method1(TreeNode root) {
        // base case
        if (root == null) return 0;
        // 分别返回左右子树的最大节点个数（注意这里不是返回直径，也不是返回长度）
        int leftMaxNodes = method1(root.left);
        int rightMaxNodes = method1(root.right);
        // 更新最大直径
        maxLen = Math.max(maxLen, leftMaxNodes-1 + rightMaxNodes-1 + 2);
        // 返回给上一层左右子树的最大节点个数（包括根节点）
        return Math.max(leftMaxNodes, rightMaxNodes) + 1;
    }

    // 方法二：迭代（颜色标记法 + 后序 + TreeNode1）
    class TreeNode1 {
        int val;
        TreeNode1 left;
        TreeNode1 right;
        int maxNodeCnt;
        TreeNode1() {}

        TreeNode1(int val) {
            this.val = val;
            this.maxNodeCnt = 0;
        }

        TreeNode1(int val, int maxNodeCnt) {
            this.val = val;
            this.maxNodeCnt = maxNodeCnt;
        }

        TreeNode1(int val, TreeNode1 left, TreeNode1 right, int maxNodeCnt) {
            this.val = val;
            this.left = left;
            this.right = right;
            this.maxNodeCnt = maxNodeCnt;
        }
    }
    public int method2(TreeNode root) {
        if (root == null) return 0;
        // --- 先以TreeNode1重新构造一遍树 ---
        Map<TreeNode, TreeNode1> old2new = new HashMap<>();
        Stack<TreeNode> stk1 = new Stack<>();
        stk1.push(root);
        while (!stk1.isEmpty()) {
            TreeNode node = stk1.pop();
            if (node == null) continue;
            old2new.put(node, new TreeNode1(node.val));
            stk1.push(node.left);
            stk1.push(node.right);
        }
        stk1.push(root);
        while (!stk1.isEmpty()) {
            TreeNode node = stk1.pop();
            TreeNode1 node1 = old2new.get(node);
            node1.left = old2new.get(node.left);
            node1.right = old2new.get(node.right);
            if (node.left != null) stk1.push(node.left);
            if (node.right != null) stk1.push(node.right);
        }
        // --------------------------------

        class FlagNode {
            TreeNode1 node;
            boolean flag;
            FlagNode(TreeNode1 node, boolean flag) {
                this.node = node;
                this.flag = flag;
            }
        }
        // List<Integer> maxNodeCnts = new ArrayList<>();  这个行不通, [1,2,3,4,5]树，依次存进来的是0，0，1，0，0，1，后两位并不是左右节点的值
        int maxLen = 0;
        Stack<FlagNode> stk = new Stack<>();
        stk.push(new FlagNode(old2new.get(root), false));
        while (!stk.isEmpty()) {
            FlagNode flagNode = stk.pop();
            TreeNode1 node = flagNode.node;
            boolean flag = flagNode.flag;
            if (node == null) continue;
            if (flag) {
                // 设置当前节点子节点中的最大值（包括自己）
                int maxLeftNodeCnt = node.left==null ? 0 : node.left.maxNodeCnt;
                int maxRightNodeCnt = node.right==null ? 0 : node.right.maxNodeCnt;
                node.maxNodeCnt = Math.max(maxLeftNodeCnt, maxRightNodeCnt) + 1;
                // 更新最大直径
                maxLen = Math.max(maxLen, maxLeftNodeCnt-1 + maxRightNodeCnt-1 + 2);
            } else {
                // 后序：左、右、中；倒着压栈
                stk.push(new FlagNode(node, true));
                stk.push(new FlagNode(node.right, false));
                stk.push(new FlagNode(node.left, false));
            }
        }
        return maxLen;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

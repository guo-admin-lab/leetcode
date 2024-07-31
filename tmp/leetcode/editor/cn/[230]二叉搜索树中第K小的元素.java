
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

    public int kthSmallest(TreeNode root, int k) {
        // return method1(root, k);
//        method2(root, k);
//        return res;
        return method3(root, k);
    }

    // 方法一：中序遍历-迭代  O(H+k) O(H)
    public int method1(TreeNode root, int k) {
        Stack<TreeNode> stk = new Stack<>();
        while (root != null || !stk.isEmpty()) {
            while (root != null) {
                stk.push(root);
                root = root.left;
            }
            TreeNode cur = stk.pop();
            // 中序
            k--;
            if (k == 0) return cur.val;
            root = cur.right;
        }
        return -1;
    }

    // 方法二：中序遍历-递归
    int cnt = 0;
    int res = 0;
    public void method2(TreeNode root, int k) {
        if (root == null) return;
        method2(root.left, k);
        cnt++;
        if (cnt == k) {
            res = root.val;
            return;
        }
        method2(root.right, k);
    }

    /** 如果你需要频繁地查找第 k 小的值，你将如何优化算法？
     *  优化思路：因为要频繁的查找第 k 小的值，所以可以使用【备忘录】操作记录一些信息，来减少下一次的查找时间复杂度
     * */
    // https://leetcode.cn/problems/kth-smallest-element-in-a-bst/solutions/1050055/er-cha-sou-suo-shu-zhong-di-kxiao-de-yua-8o07/?envType=study-plan-v2&envId=top-100-liked
    // 方法三：记录子树的结点数（优化方法）
    /**
     * 在方法一中，我们之所以需要中序遍历前 k 个元素，是因为我们不知道子树的结点数量，不得不通过遍历子树的方式来获知。
     *
     * 因此，我们可以记录下以每个结点为根结点的子树的结点数，并在查找第 k 小的值时，使用如下方法搜索：
     *
     * 令 node 等于根结点，开始搜索。
     *
     * 对当前结点 node 进行如下操作：
     *
     * 如果 node 的左子树的结点数 left 小于 k−1，则第 k 小的元素一定在 node 的右子树中，令 node 等于其的右子结点，k 等于 k−left−1，并继续搜索；
     * 如果 node 的左子树的结点数 left 等于 k−1，则第 k 小的元素即为 node，结束搜索并返回 node 即可；
     * 如果 node 的左子树的结点数 left 大于 k−1，则第 k 小的元素一定在 node 的左子树中，令 node 等于其左子结点，并继续搜索。
     *
     * 在实现中:
     *      我们既可以将以每个结点为根结点的子树的结点数存储在结点中，
     *      也可以将其记录在哈希表中。
     *
     * 作者：力扣官方题解
     * 链接：https://leetcode.cn/problems/kth-smallest-element-in-a-bst/
     */
    Map<TreeNode, Integer> node2Num = new HashMap<>();
    public int method3(TreeNode root, int k) {
        if (root == null) return -1;
        // 1.计算每个node其左右子树的节点个数，存储在node2Num中
        countNoneNum(root);  // O(n)
        // 2.遍历整个树，根据节点个数判断第k小的值在左子树还是右子树
        TreeNode node = root;
        while (node != null) {
            // 如果node左子树结点数leftNodeNum < k
            int leftNodeNum = node2Num.getOrDefault(node.left, 0);
            if (leftNodeNum == k-1) {
                // node就是要找的值
                return node.val;
            } else if (leftNodeNum > k-1) {
                // 目标值在左子树中
                node = node.left;
            } else if (leftNodeNum < k-1) {
                // 目标值在右子树中
                node = node.right;
                // 要减去左子树的节点数目
                k = k - leftNodeNum - 1;  // k - 左子树节点个数 - 当前节点
            }
        }
        return -1;
    }

    // 统计以node为根结点的子树的结点数
    public int countNoneNum(TreeNode root) {
        // base case
        if (root == null) return 0;
        // 递归定义：子树节点个数 = fun(node.child)
        int leftNodeNum = countNoneNum(root.left);
        int rightNodeNum = countNoneNum(root.right);
        // 当前节点左右子树的总节点个数（包括自身）
        int nodeNum = leftNodeNum + rightNodeNum + 1;
        node2Num.put(root, nodeNum);
        // 返回当前总节点个数
        return nodeNum;
    }

    // https://leetcode.cn/problems/kth-smallest-element-in-a-bst/solutions/1050055/er-cha-sou-suo-shu-zhong-di-kxiao-de-yua-8o07/?envType=study-plan-v2&envId=top-100-liked
    // 方法四：平衡二叉搜索树（优化方法）
    public int method4(TreeNode root, int k) {
        return -1;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

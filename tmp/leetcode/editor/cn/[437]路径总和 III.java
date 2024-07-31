
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

    public int pathSum(TreeNode root, int targetSum) {
//        method1(root, targetSum);
//        return res;
//        return method2(root, targetSum);
        return method3(root, targetSum);
    }

    // 方法一: 递归 + 暴力统计 + 全局变量
    /** 以每个节点作为根节点，依次遍历满足条件的路径个数 */
    public void method1(TreeNode root, int targetSum) {
        // 遍历整个树，以每个节点作为根节点，统计其满足条件的路径个数
        if (root == null) return;
//        countByTreeRoot1_1(root, targetSum);  // 计算以当前节点作为根节点，满足条件的路径个数
        countByTreeRoot2(root, targetSum);  // 计算以当前节点作为根节点，满足条件的路径个数
        method1(root.left, targetSum);  // 遍历左子树
        method1(root.right, targetSum); // 遍历右子树
    }
    // 统计从根节点出发，满足路径条件的个数 + 外部pathSum + 外部res变量
    /** 注意，由于pathSum的和最终加的可能比较大，所以这里必须要用long类型 */
    long pathSum = 0;
    int res = 0;
    public void countByTreeRoot1_1(TreeNode root, int targetSum) {
        if (root == null) return;
        // 1.先序
        // 1.1.更新路径和(增加)
        pathSum += root.val;
        // 1.2.判断路径和是否满足条件
        if (pathSum == targetSum) res++;
        // 2.递归统计左右子树满足条件的路径
        countByTreeRoot1_1(root.left, targetSum);
        countByTreeRoot1_1(root.right, targetSum);
        // 3.后序
        // 3.1.更新路径和(减少)
        pathSum -= root.val;
    }
    // 统计从根节点出发，满足路径条件的个数 + targetSum表示路径 + 外部res
    public void countByTreeRoot1_2(TreeNode root, long targetSum) {
        if (root == null) return;
        // 1.先序
        // 1.2.判断路径和是否满足条件
        if (root.val == targetSum) res++;  // 如果 (左子树节点值 == 目标值 - 根节点值) 成立，那说明就有1条满足条件的路径
        // 2.递归统计左右子树满足条件的路径
        countByTreeRoot1_2(root.left, targetSum-root.val);  /** 这里一路向下减法，变相的记录了路径和 */
        countByTreeRoot1_2(root.right, targetSum-root.val);
    }

    // 方法二：递归 + 暴力统计 + 局部变量
    public int method2(TreeNode root, int targetSum) {
        if (root == null) return 0;
        int ret = countByTreeRoot2(root, targetSum);
        ret += method2(root.left, targetSum);
        ret += method2(root.right, targetSum);
        return ret;
    }
    // 统计从根节点出发，满足路径条件的个数 + targetSum表示路径
    public int countByTreeRoot2(TreeNode root, long targetSum) {
        if (root == null) return 0;
        int res = 0;
        // 前序
        if (root.val == targetSum) res+=1;
        // 递归计算左右子树符合条件的路径个数
        int res1 = countByTreeRoot2(root.left, targetSum-root.val);
        int res2 = countByTreeRoot2(root.right, targetSum-root.val);
        // 返回当前节点下，符合条件的路径总个数
        return res + res1 + res2;
    }

    // 方法三：前缀和 + 递归
    public int method3(TreeNode root, int targetSum) {
        if (root == null) return 0;

        /** 注意：初始化前缀和 */
        preSumCount.put(0L, 1);

        countByTreeRoot3(root, targetSum);
        return _res;
    }
    // 统计从根节点出发，满足路径条件的个数 + targetSum表示路径
    // 定义：从二叉树的根节点开始，路径和为 pathSum 的路径有 preSumCount.get(pathSum) 个
    HashMap<Long, Integer> preSumCount = new HashMap<>();  // 记录前缀和
    long _pathSum = 0;
    int _res = 0;
    public void countByTreeRoot3(TreeNode root, int targetSum) {
        if (root == null) return;

        // 前序位置
        // 当前栈帧中，pathSum = 之前路径节点和 + root.val
        // 如果之前的路径和中，存在 之前某条路径和 = pathSum - targetSum = 当前路径和 - targetSum
        // 公式转换后，当前路径和 - 之前某条路径和 = targetSum
        // 说明，这两段路径和中间的这条路径和 = targetSum
        _pathSum += root.val;
        _res += preSumCount.getOrDefault(_pathSum - targetSum, 0);
        preSumCount.put(_pathSum, preSumCount.getOrDefault(_pathSum, 0)+1);

        // 递归计算左右子树符合条件的路径个数
        countByTreeRoot3(root.left, targetSum);
        countByTreeRoot3(root.right, targetSum);

        // 后序位置
        preSumCount.put(_pathSum, preSumCount.get(_pathSum)-1);
        _pathSum -= root.val;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

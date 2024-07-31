
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

    public TreeNode sortedArrayToBST(int[] nums) {
        return method1(nums, 0, nums.length-1);
    }

    // 方法一
    /**  递归思路：
     *      0. base case
     *          if (li > ri) return null;
     *      1. 首先获取前半段数组 和 后半段数组 构建好的 平衡搜索二叉树的根节点
     *          middle = (ri - li) / 2 + li
     *          leftRoot = fun(li, middle-1);
     *          rightRoot = fun(middle+1, ri);
     *      2. 然后处理当前的root节点
     *          root = new TreeNode( nums[middle] )
     *      2.1.根据数组的有序性，leftRoot.val一定比root.val小，rightRoot.val一定比root.val大
     *          root.left = leftRoot
     *          root.right = rightRoot
     *      3. 返回root
     */
    /**  过程分析
     *  nums = [-10, -3, 0, 5, 9]  len = 5
     *
     *  nums = [-10, -3, [0], 5, 9]  li = 0   ri = 4
     *  middle = 2
     *  nums = [-10, -3, [0], 5, 9]  li = 0   ri = 1    return [-10]
     *  nums = [-10, -3, [0], 5, 9]  li = 3   ri = 4
     *  [0] = new Node(middle)
     *  [0].left = [-10]
     *  return [0]
     *
     *  nums = [[-10], -3, 0, 5, 9]  li = 0   ri = 1
     *  middle = 0
     *  nums = [[-10], -3, 0, 5, 9]  li = 0   ri = -1   return null
     *  nums = [[-10], -3, 0, 5, 9]  li = 1   ri = 1    return null(×)  (1.这里是不对的，因为 [-3] 这个节点没有被创建)  正确的返回 return [-3](√)
     *  [-10] = new Node(middle)
     *  [-10].left = null
     *  [-10].right = [-3]
     *  return [-10]
     *
     *  2.所以还要有下面的这一层
     *  nums = [-10, [-3], 0, 5, 9]  li = 1   ri = 1
     *  middle = 1
     *  nums = [-10, [-3], 0, 5, 9]  li = 1   ri = -1  return null
     *  nums = [-10, [-3], 0, 5, 9]  li = 2   ri = 1  return null
     *  return [-3]
     *
     */
    public TreeNode method1(int[] nums, int li, int ri) {
        // base case
        /** 注意：这里不能写成 li>=ri，会少创建节点。具体原因见上述的过程分析 */
        if (li > ri) return null;
        // 1. 首先获取前半段数组 和 后半段数组 构建好的 平衡搜索二叉树的根节点
        int middle = (ri + li) / 2; // 等价于int middle = (ri - li) / 2 + li;
//        int middle = (ri + li + 1) / 2;  // 等价于int middle = (ri - li - 1) / 2 + li + 1;  // todo 这个不知道为啥是错误的
        TreeNode leftRoot = method1(nums, li, middle-1);
        TreeNode rightRoot = method1(nums, middle+1, ri);
        // 2. 然后处理当前的root节点
        // 2.1.根据数组的有序性，leftRoot.val一定比root.val小，rightRoot.val一定比root.val大
        TreeNode root = new TreeNode( nums[middle] );
        root.left = leftRoot;
        root.right = rightRoot;
        // 3.返回根节点
        return root;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

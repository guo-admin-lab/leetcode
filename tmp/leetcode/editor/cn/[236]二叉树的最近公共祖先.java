
//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
//        return method1(root, p, q);
//        return method2(root, p, q);
        return method3(root, p, q);
    }

    // 方法一：两次遍历 + 存储p\q的父节点 + 两个父节点列表比较
    /** 思路分析
     遍历树，找p，一路记录p的父节点 [n0, n1, n2, np]
     遍历树，找q，一路记录q的父节点 [n0, n1, nq]
     比较两个父节点备忘录，找到最后一个相同的节点，即为解
     */
    List<TreeNode> fatherNodes = new ArrayList<>();
    public TreeNode method1(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;

        // 遍历树，找p，一路记录p的父节点 [n0, n1, n2, np]
        fatherNodes.clear();
        traverse(root, p);
        List<TreeNode> fatherNodes1 = new ArrayList<>(fatherNodes); //浅拷贝
        // 遍历树，找q，一路记录q的父节点 [n0, n1, nq]
        fatherNodes.clear();
        traverse(root, q);
        List<TreeNode> fatherNodes2 = new ArrayList<>(fatherNodes); //浅拷贝
        // 比较两个父节点备忘录，找到最后一个相同的节点，即为解
        int minSize = Math.min(fatherNodes1.size(), fatherNodes2.size());
        int i = 0;
        while (i < minSize) {
            // 如果两列表的节点不同了，直接跳出循环
            if (fatherNodes1.get(i) != fatherNodes2.get(i)) {
                break;
            }
            i++;
        }
        if (i == 0) return null;
        else return fatherNodes1.get(i-1);
    }
    // 遍历树节点，一路记录父节点
    public boolean traverse1(TreeNode root, TreeNode target) {
        if (root == null) return false;
        // 先序
        fatherNodes.add(root);
        // 找到目标值直接返回
        if (root == target) return true;

        // 先去左子树中找，如果找到直接返回
        boolean b1 = traverse1(root.left, target);
        if (b1) return true;

        // 当左子树中没有找到目标结点时，再去右子树中找
        boolean b2 = traverse1(root.right, target);

        /** 后序：如果到这里还没找到目标值，则把当前节点移除掉(当前节点为最后一个) */
        if (!b2) fatherNodes.remove(fatherNodes.size()-1);

        // 返回是否找到目标结点
        return b2;
    }

    // 方法二：构造p\q和父节点的映射关系 + 依次从p\q向上存储父节点
    /** 思路分析
     *  我们可以用哈希表存储所有节点的父节点，
     *  然后我们就可以利用节点的父节点信息从 p 结点开始不断往上跳，并记录已经访问过的节点，
     *  再从 q 节点开始不断往上跳，如果碰到已经访问过的节点，
     *  那么这个节点就是我们要找的最近公共祖先。
     *
     *  算法分析
     *  从根节点开始遍历整棵二叉树，用哈希表记录每个节点的父节点指针。
     *  从 p 节点开始不断往它的祖先移动，并用数据结构记录已经访问过的祖先节点。
     *  同样，我们再从 q 节点开始不断往它的祖先移动，如果有祖先已经被访问过，即意味着这是 p 和 q 的深度最深的公共祖先，即 LCA 节点。
     */
    Map<Integer, TreeNode> parent = new HashMap<Integer, TreeNode>();
    Set<Integer> visited = new HashSet<Integer>();
    public TreeNode method2(TreeNode root, TreeNode p, TreeNode q) {
        build_traverse2(root);
        // 从p依次向上遍历父节点，并存储遍历到的父节点
        while (p != null) {
            visited.add(p.val);
            p = parent.get(p.val);
        }
        // 从q依次向上遍历父节点，如果某父节点也属于p，则此节点是最近公共父节点
        while (q != null) {
            if (visited.contains(q.val)) {
                return q;
            }
            q = parent.get(q.val);
        }
        return null;
    }
    // 构造p\q和父节点的映射
    public void build_traverse2(TreeNode root) {
        if (root.left != null) {
            parent.put(root.left.val, root);
            build_traverse2(root.left);
        }
        if (root.right != null) {
            parent.put(root.right.val, root);
            build_traverse2(root.right);
        }
    }

    // 方法三：一次递归遍历
    /** 递归定义：返回左右子树中，p或者q的父节点
     *
     *      如果 root == p 或者 root == q，
     *          说明root为p或者q的最近祖先节点
     *          直接返回当前root
     *      leftRoot = fun(root.left, p, q)
     *      rightRoot = fun(root.right, p, q)
     *      如果 leftRoot == null 和 rightRoot == null，
     *          说明p或q都不在root的左右子树中，
     *          返回null
     *      如果 leftRoot != null 和 rightRoot != null，
     *          说明p、q分别在root的左右两棵子树中，
     *          返回当前root
     *      到这里，leftRoot和rightRoot有一个为空，
     *          说明 root的左子树和右子树中，有一个不包括p，也不包括q
     *          返回不为空的xxRoot
     */
    public TreeNode method3(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        if (root == p || root == q) return root;

        TreeNode leftRoot = method3(root.left, p, q);
        TreeNode rightRoot = method3(root.right, p, q);

        if (leftRoot == null && rightRoot == null) return null;
        if (leftRoot != null && rightRoot != null) return root;
        return leftRoot!=null ? leftRoot : rightRoot;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

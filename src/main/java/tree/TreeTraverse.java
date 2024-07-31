package tree;

import sun.reflect.generics.tree.Tree;

import java.util.*;


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}


public class TreeTraverse {

    // 包含后序遍历的两种方法：https://leetcode.cn/problems/binary-tree-inorder-traversal/solutions/34581/die-dai-fa-by-jason-2/?envType=study-plan-v2&envId=top-100-liked

    // 好理解的颜色标记法：https://leetcode.cn/problems/binary-tree-inorder-traversal/solutions/25220/yan-se-biao-ji-fa-yi-chong-tong-yong-qie-jian-ming/?envType=study-plan-v2&envId=top-100-liked

    // 写的非常详细规整：https://blog.csdn.net/jzj_c_love/article/details/112909708

    // 中序遍历空间复杂度O(1)的算法，还没看
    //    https://leetcode.cn/problems/binary-tree-inorder-traversal/solutions/412886/er-cha-shu-de-zhong-xu-bian-li-by-leetcode-solutio/?envType=study-plan-v2&envId=top-100-liked

    // todo 莫里斯遍历法O(1):
    // https://blog.csdn.net/qq_46209845/article/details/119850599
    // https://www.cnblogs.com/gonghr/p/15387609.html

    /**
     * 官方题解中介绍了三种方法来完成树的中序遍历，包括：
     * 递归
     * 借助栈的迭代方法
     * 莫里斯遍历
     */

    public static void main(String[] args) {
        TreeNode t1 = new TreeNode(1);
        TreeNode t2 = new TreeNode(2);
        TreeNode t3 = new TreeNode(3);
        TreeNode t4 = new TreeNode(4);
        TreeNode t5 = new TreeNode(5);
        TreeNode t6 = new TreeNode(6);
        TreeNode t7 = new TreeNode(7);
        TreeNode t8 = new TreeNode(8);
        TreeNode t9 = new TreeNode(9);
        t1.left = t2;
        t1.right = t3;
        t2.left = t4;
        t2.right = t5;
        t3.left = t6;
        t3.right = t7;
        t5.left = t8;
        t5.right = t9;

//        List<Integer> list = postorderTraverse3(t1);
//        List<Integer> list = preorderTraverse4(t1);
        List<Integer> list = orderTraverse5(t1);
        System.out.println(list);

    }

    // 回溯递归
    static List<Integer> res = new ArrayList<>();
    public static void orderTraverse1(TreeNode root) {
        if (root == null) return;
        // res.add(root.val);  // 前序
        orderTraverse1(root.left);
        // res.add(root.val);  // 中序
        orderTraverse1(root.right);
        // res.add(root.val);  // 后序
    }

    // 动态规划递归
    public static List<Integer> orderTraverse2(TreeNode root) {
        if (root == null) return Collections.emptyList();

        List<Integer> res = new ArrayList<>();
        List<Integer> leftRes = orderTraverse2(root.left);  // 左子树中序遍历结果
        List<Integer> rightRes = orderTraverse2(root.right);  // 右子树中序遍历结果

        // res.add(root.val); // 加入根节点  前序
        res.addAll(leftRes); // 加入左子树中序遍历结果
        // res.add(root.val); // 加入根节点  中序
        res.addAll(rightRes); // 加入右子树中序遍历结果
        // res.add(root.val); // 加入根节点  后序

        return res;
    }

    // 迭代-前序（根->左->右）
    public static List<Integer> preorderTraverse3(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> s = new Stack<>();
        // 栈模拟递归
        while (root != null || !s.isEmpty()) {
            while (root != null) {
                res.add(root.val);  // 前序
                s.push(root);  // 左节点一路入栈
                root = root.left;  // 遍历左子树
            }
            // 遍历到左子树尽头
            TreeNode cur = s.pop();
            root = cur.right;
        }
        return res;
    }

    // 迭代-中序（左 -> 根 -> 右）
    public static List<Integer> inorderTraverse3(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> s = new Stack<>();
        // 栈模拟递归
        while (root != null || !s.isEmpty()) {
            while (root != null) {
                s.push(root);  // 左节点一路入栈
                root = root.left;  // 遍历左子树
            }
            // 遍历到左子树尽头
            TreeNode cur = s.pop();
            res.add(cur.val);  // 中序
            root = cur.right;
        }
        return res;
    }

    // 迭代-后序（左 -> 右 -> 根）方法1
    /** 思路：左右根 <-> 根右左【翻转】
     *  对照前序遍历，将前序left的地方改成right，最后翻转
     */
    public static List<Integer> postorderTraverse3_1(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> s = new Stack<>();
        // 栈模拟递归
        while (root != null || !s.isEmpty()) {
            while (root != null) {
                res.add(root.val);  // 前序
                s.push(root);  // 左节点一路入栈
                root = root.right;  // 遍历左子树
            }
            // 遍历到左子树尽头
            TreeNode cur = s.pop();
            root = cur.left;
        }
        // 后序翻转
        Collections.reverse(res);
        return res;
    }

    // 包含后序遍历的两种方法：https://leetcode.cn/problems/binary-tree-inorder-traversal/solutions/34581/die-dai-fa-by-jason-2/?envType=study-plan-v2&envId=top-100-liked
    // 迭代-后序（左 -> 右 -> 根）方法2
    /**  思路：添加标记法
     * 按照左子树-根-右子树的方式，将其转换成迭代方式。
     *
     * 思路：每到一个节点 A，因为根要最后访问，将其入栈。然后遍历左子树，遍历右子树，最后返回到 A。
     *
     * 但是出现一个问题，无法区分是从左子树返回，还是从右子树返回。
     *
     * 因此，给 A 节点附加一个标记T。在访问其右子树前，T 置为 True。之后子树返回时，当 T 为 True表示从右子树返回，否则从左子树返回。
     *
     * 当 T 为 false 时，表示 A 的左子树遍历完，还要访问右子树。
     *
     * 同时，当 T 为 True 时，表示 A 的两棵子树都遍历过了，要访问 A 了。并且在 A 访问完后，A 这棵子树都访问完成了。
     *
     * 作者：jason
     * 链接：https://leetcode.cn/problems/binary-tree-inorder-traversal/solutions/34581/die-dai-fa-by-jason-2/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    /**  伪代码
     * 栈S;
     * p= root;
     * T<节点,True/False> : 节点标记;
     * while(p || S不空){
     *     while(p){
     *         p入S;
     *         p = p的左子树;
     *     }
     *     while(S不空 且 T[S.top] = True){
     *         访问S.top;
     *         S.top出S;
     *     }
     *     if(S不空){
     *         p = S.top 的右子树;
     *         T[S.top] = True;
     *     }
     * }
     */
    /**  C++的代码
     vector<int> postorderTraversal(TreeNode* root) {
        stack<TreeNode*> S;
        unordered_map<TreeNode*,int> done;
        vector<int> v;
        TreeNode* rt = root;
        while(rt || S.size()){
            while(rt){
                S.push(rt);
                rt=rt->left;
            }
            while(S.size() && done[S.top()]){
                v.push_back(S.top()->val);
                S.pop();
            }
            if(S.size()){
                rt=S.top()->right;
                done[S.top()]=1;
            }
        }
        return v;
     }
     */
    public static List<Integer> postorderTraverse3_2(TreeNode root) {
        return null;
    }

    // ---------------------------下面为颜色标记法相关-------------------
    // 迭代-前序
    /** 中序、后序没办法用这种压栈的方式写, 改进版看下面的颜色标记法
     *  因为 中后序遍历到node时，不能直接加入到res中，需要等之后返回到node时再加入res，但是此时node已经不在栈中了，所以不能再次获取到node
     */
    public static List<Integer> preorderTraverse4(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stk = new Stack<>();
        // 初始化栈数据
        stk.push(root);
        // 模拟递归遍历
        while (!stk.isEmpty()) {
            TreeNode node = stk.pop();
            if (node == null) continue;
            res.add(node.val);  // 前序
            // --- 注意这里的压栈顺序 ---
            stk.push(node.right);  // 1.压栈right
            stk.push(node.left);   // 2.压栈left
        }
        return res;
    }
    // --------------------------------------------------------------

    /** 颜色标记法
     *  解释一下中序遍历为什么需要  右子节点 -> 自身 -> 左子节点  依次入栈
     *
     *  前序遍历：中，左，右
     *  中序遍历：左，中，右
     *  后序遍历：左，右，中
     *
     *  栈是一种 先进后出的结构
     *  先看中序遍历：
     *      出栈顺序必须为左，中，右, 才能保证添加到res中的是中序遍历
     *      所以，入栈顺序必须调整为倒序，也就是右，中，左
     *  同理，
     *      如果是前序遍历，入栈顺序为 右，左，中；
     *      后序遍历，入栈顺序中，右，左
     */
    // 颜色标记法迭代-前序-中序-后序
    public static List<Integer> orderTraverse5(TreeNode root) {
        if (root == null) return Collections.emptyList();
        class FlagNode {
            TreeNode node;
            boolean flag;  // 标识是否已经遍历过此节点（直观的讲，就是是否被栈pop过）
            FlagNode(TreeNode node, boolean flag) {
                this.node = node;
                this.flag = flag;
            }
        }

        List<Integer> res = new ArrayList<>();
        Stack<FlagNode> stk = new Stack<>();
        // 初始化栈数据
        stk.push(new FlagNode(root, false));
        // 模拟递归
        while (!stk.isEmpty()) {
            FlagNode flagNode = stk.pop();
            TreeNode node = flagNode.node;
            boolean flag = flagNode.flag;
            // base case
            if (node == null) continue;
            if (flag) { //如果当前节点已经被遍历过，则直接将值输出
                res.add(node.val);
            } else { //如果还未被遍历过，则根据前、中、后序情况将【自身、左节点、右节点的顺序】入栈
                // 前序：中、左、右；倒着压栈
//                stk.push(new FlagNode(node.right, false));
//                stk.push(new FlagNode(node.left, false));
//                stk.push(new FlagNode(node, true));
                // 中序：左、中、右；倒着压栈
//                stk.push(new FlagNode(node.right, false));
//                stk.push(new FlagNode(node, true));
//                stk.push(new FlagNode(node.left, false));
                // 后序：左、右、中；倒着压栈
                stk.push(new FlagNode(node, true));
                stk.push(new FlagNode(node.right, false));
                stk.push(new FlagNode(node.left, false));
            }
        }
        // 返回结果
        return res;
    }

    // 按层遍历（bfs 队列实现）
    public void layerTraverse(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> qu = new LinkedList<>();
        // 初始化队列数据
        qu.offer(root);
        // bfs遍历二叉树
        while (!qu.isEmpty()) {
            int size = qu.size();  // 固定这一层节点的个数
            // 将下一层节点个数全部填充进来（保证每一次queue中只存在一层的节点）
            while (size > 0) {
                TreeNode node = qu.poll();
                if (node.left != null) qu.offer(node.left);
                if (node.right != null) qu.offer(node.right);
                size--;
            }
            // 这里的队列中，存储了当前层所有的节点
        }
    }

}

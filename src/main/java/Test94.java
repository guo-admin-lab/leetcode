import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import static java.util.Collections.emptyList;


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


public class Test94 {

    // https://blog.csdn.net/jzj_c_love/article/details/112909708
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

        List<Integer> list = postorderTraverse3(t1);
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

    // 迭代-后序（左 -> 右 -> 根）
    /** 左右根 <-> 根右左【翻转】
     *  对照前序遍历，将前序left的地方改成right，最后翻转
     */
    public static List<Integer> postorderTraverse3(TreeNode root) {
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

}

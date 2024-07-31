import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Test104 {

    public static void main(String[] args) {

        Queue<Integer> qu = new LinkedList<>();

        TreeNode t1 = new TreeNode(0);
        TreeNode t2 = new TreeNode(2);
        TreeNode t3 = new TreeNode(4);
        TreeNode t4 = new TreeNode(1);
        TreeNode t5 = new TreeNode(3);
        TreeNode t6 = new TreeNode(-1);
        TreeNode t7 = new TreeNode(5);
        TreeNode t8 = new TreeNode(1);
        TreeNode t9 = new TreeNode(6);
        TreeNode t10 = new TreeNode(8);
        t1.left = t2;
        t1.right = t3;
        t2.left = t4;
        t3.left = t5;
        t3.right = t6;
        t4.left = t7;
        t4.right = t8;
        t5.right = t9;
        t6.right = t10;

        int depth = method2(t1);
        System.out.println(depth);

    }

    public static int method2(TreeNode root) {
        int max_depth = 0;
        Stack<TreeNode> stk = new Stack<>();
        int depth = 0;  // 初始深度0
        while (root != null || !stk.isEmpty()) {
            if (root == null) depth--;
            while (root != null) {
                stk.push(root);
                root = root.left;
                depth++;  // 向下遍历深度+1
            }
            // 当一路访问左子树到尽头，更新一次最大深度
            max_depth = Math.max(max_depth, depth);
            TreeNode cur = stk.pop();
            root = cur.right;
        }
        return max_depth;
    }

}

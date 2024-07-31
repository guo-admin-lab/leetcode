public class Test98 {

    public static void main(String[] args) {
    }


//    public static boolean method1(TreeNode root) {
//        // 0.base case
//        if (root == null) return true;
//        // 1.判断以当前节点为根的树，和左右子节点是否满足二叉搜索树的定义 (即有一个条件不满足，则直接返回false，不用再向下比较了)
//        if (root.left == null && root.right == null) return true;
//        else if (root.left == null && root.right.val <= root.val) return false;
//        else if (root.right == null && root.left.val >= root.val) return false;
//        else if (root.left.val >= root.val || root.right.val <= root.val) return false;
//        // 2.判断左右子树是否为 二叉搜索树
//        boolean b1 = method1(root.left);
//        boolean b2 = method1(root.right);
//        // 3.返回左右子树的比较结果
//        return b1 && b2;
//    }

}

import java.util.ArrayList;
import java.util.List;

public class Test236 {

}
// todo 下面的代码因为遍历找不到TreeNode，暂注释掉
//    public static void main(String[] args) {
//
//        ArrayList<Object> list = new ArrayList<>();
//
//        TreeNode n1 = new TreeNode(3);
//        TreeNode n2 = new TreeNode(5);
//        TreeNode n3 = new TreeNode(1);
//        TreeNode n4 = new TreeNode(6);
//        TreeNode n5 = new TreeNode(2);
//        TreeNode n6 = new TreeNode(0);
//        TreeNode n7 = new TreeNode(8);
//        TreeNode n8 = new TreeNode(7);
//        TreeNode n9 = new TreeNode(4);
//
//        n1.left = n2;
//        n1.right = n3;
//        n2.left = n4;
//        n2.right = n5;
//        n3.left = n6;
//        n3.right = n7;
//        n5.left = n8;
//        n5.right = n9;
//
////        method2(n1, 4);
////        System.out.println(fns);
//
//        Solution236 solution236 = new Solution236();
//        TreeNode treeNode = solution236.lowestCommonAncestor(n1, n2, n9);
//        System.out.println(treeNode.val);
//
//    }
//
//    static List<Integer> fns = new ArrayList<>();
//    public static void method1(TreeNode root, int target) {
//        if (root == null) return;
//
//        fns.add(root.val);
//        if (root.val == target) return;
//
//        method1(root.left, target);
//        method1(root.right, target);
//    }
//
//    public static boolean method2(TreeNode root, int target) {
//        if (root == null) return false;
//        // 先序
//        fns.add(root.val);
//        // 找到目标值直接返回
//        if (root.val == target) return true;
//        // 先去左子树中找，如果找到直接返回
//        boolean b1 = method2(root.left, target);
//        if (b1) return true;
//        // 当左子树中没有找到目标结点时，再去右子树中找
//        boolean b2 = method2(root.right, target);
//        // 返回是否找到目标结点
//        return b2;
//    }
//
//}
//
///**
// * Definition for a binary tree node.
// * public class TreeNode {
// *     int val;
// *     TreeNode left;
// *     TreeNode right;
// *     TreeNode(int x) { val = x; }
// * }
// */
//class Solution236 {
//
//    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
//        return method1(root, p, q);
//    }
//
//    // 方法一
//    /** 思路分析
//     遍历树，找p，一路记录p的父节点 [n0, n1, n2, np]
//     遍历树，找q，一路记录q的父节点 [n0, n1, nq]
//     比较两个父节点备忘录，找到最后一个相同的节点，即为解
//     */
//    List<TreeNode> fatherNodes = new ArrayList<>();
//    public TreeNode method1(TreeNode root, TreeNode p, TreeNode q) {
//        if (root == null) return null;
//
//        // 遍历树，找p，一路记录p的父节点 [n0, n1, n2, np]
//        fatherNodes.clear();
//        traverse(root, p);
//        List<TreeNode> fatherNodes1 = new ArrayList<>(fatherNodes); //浅拷贝
//        // 遍历树，找q，一路记录q的父节点 [n0, n1, nq]
//        fatherNodes.clear();
//        traverse(root, q);
//        List<TreeNode> fatherNodes2 = new ArrayList<>(fatherNodes); //浅拷贝
//        // 比较两个父节点备忘录，找到最后一个相同的节点，即为解
//        int minSize = Math.min(fatherNodes1.size(), fatherNodes2.size());
//        int i = 0;
//        while (i < minSize) {
//            // 如果两列表的节点不同了，直接跳出循环
//            if (fatherNodes1.get(i) != fatherNodes2.get(i)) {
//                break;
//            }
//            i++;
//        }
//        if (i == 0) return null;
//        else return fatherNodes1.get(i-1);
//    }
//
//    // 遍历树节点，一路记录父节点
//    public boolean traverse(TreeNode root, TreeNode target) {
//        if (root == null) return false;
//        // 先序
//        fatherNodes.add(root);
//        // 找到目标值直接返回
//        if (root == target) return true;
//
//        // 先去左子树中找，如果找到直接返回
//        boolean b1 = traverse(root.left, target);
//        if (b1) return true;
//
//        // 当左子树中没有找到目标结点时，再去右子树中找
//        boolean b2 = traverse(root.right, target);
//
//        /** 后序：如果到这里还没找到目标值，则把当前节点移除掉(当前节点为最后一个) */
//        if (!b2) fatherNodes.remove(fatherNodes.size()-1);
//
//        // 返回是否找到目标结点
//        return b2;
//    }
//}

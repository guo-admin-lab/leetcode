import java.util.*;

public class Test102 {

    public static void main(String[] args) {
        TreeNode n1 = new TreeNode(3);
        TreeNode n2 = new TreeNode(9);
        TreeNode n3 = new TreeNode(20);
        TreeNode n4 = new TreeNode(15);
        TreeNode n5 = new TreeNode(7);

        n1.left = n2;
        n1.right = n3;
        n3.left = n4;
        n3.right = n5;

        List<List<Integer>> lists = method1(n1);
        System.out.println(lists);
    }

    public static List<List<Integer>> method1(TreeNode root) {
        if (root == null) return Collections.emptyList();
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> layerNodes = new ArrayList<>();
            while (size > 0) {
                TreeNode node = queue.poll();
                /** 特别注意，如果队列中有null值，poll不会将null值弹出，而是将其永远留在队首，导致死循环了 */
                if (node == null) continue;
                layerNodes.add(node.val);
                queue.offer(node.left);
                queue.offer(node.right);
                size--;
            }
            res.add(layerNodes);
        }
        return res;
    }

}

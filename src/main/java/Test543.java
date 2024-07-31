import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class TreeNode1 {
    int val;
    TreeNode1 left;
    TreeNode1 right;
    int maxNodeCnt;
    TreeNode1() {}

    TreeNode1(int val) {
        this.val = val;
        this.maxNodeCnt = 0;
    }

    TreeNode1(int val, int maxNodeCnt) {
        this.val = val;
        this.maxNodeCnt = maxNodeCnt;
    }

    TreeNode1(int val, TreeNode1 left, TreeNode1 right, int maxNodeCnt) {
        this.val = val;
        this.left = left;
        this.right = right;
        this.maxNodeCnt = maxNodeCnt;
    }
}

public class Test543 {

    public static void main(String[] args) {
        TreeNode1 n1 = new TreeNode1(1);
        TreeNode1 n2 = new TreeNode1(2);
        TreeNode1 n3 = new TreeNode1(3);
        TreeNode1 n4 = new TreeNode1(3);
        TreeNode1 n5 = new TreeNode1(3);
        n1.left = n2;
        n1.right = n3;
        n2.left = n4;
        n2.right = n5;

        int len = method2(n1);
        System.out.println(len);

    }

    public static int method2(TreeNode1 root) {
        if (root == null) return 0;
        class FlagNode {
            TreeNode1 node;
            boolean flag;
            FlagNode(TreeNode1 node, boolean flag) {
                this.node = node;
                this.flag = flag;
            }
        }
        // List<Integer> maxNodeCnts = new ArrayList<>();  这个行不通, [1,2,3,4,5]树，依次存进来的是0，0，1，0，0，1，后两位并不是左右节点的值
        int maxLen = 0;
        Stack<FlagNode> stk = new Stack<>();
        stk.push(new FlagNode(root, false));
        while (!stk.isEmpty()) {
            FlagNode flagNode = stk.pop();
            TreeNode1 node = flagNode.node;
            boolean flag = flagNode.flag;
            if (node == null) continue;
            if (flag) {
                // 设置当前节点子节点中的最大值（包括自己）
                int maxLeftNodeCnt = node.left==null ? 0 : node.left.maxNodeCnt;
                int maxRightNodeCnt = node.right==null ? 0 : node.right.maxNodeCnt;
                node.maxNodeCnt = Math.max(maxLeftNodeCnt, maxRightNodeCnt) + 1;
                // 更新最大直径
                maxLen = Math.max(maxLen, maxLeftNodeCnt-1 + maxRightNodeCnt-1 + 2);
            } else {
                // 后序：左、右、中；倒着压栈
                stk.push(new FlagNode(node, true));
                stk.push(new FlagNode(node.right, false));
                stk.push(new FlagNode(node.left, false));
            }
        }
        return maxLen;
    }

}

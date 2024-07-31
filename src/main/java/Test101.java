import java.util.*;

public class Test101 {

    public static void main(String[] args) {
        TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(0);

        n1.left = n2;
    }

}

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
class Solution101 {

    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return method1(root.left, root.right);
    }

    // 方法一：递归——dfs【分解问题】思路
    /** 递归函数定义
     bool = fun(root1, root2)
     表示root1和root2下面的树是否是轴对称的？
     */
    public boolean method1(TreeNode leftRoot, TreeNode rightRoot) {
        // base case 比较左右根节点的值
        if (leftRoot == null && rightRoot == null) return true;
        if (leftRoot == null || rightRoot == null) return false;

        // 两个根节点需要相同
        if (leftRoot.val != rightRoot.val) return false;
        // 比较左右根节点的子节点
        boolean b1 = method1(leftRoot.left, rightRoot.right);
        boolean b2 = method1(leftRoot.right, rightRoot.left);

        // 返回两对子树是否是轴对称的结果
        return b1 && b2;
    }

    // 方法二1：迭代——dfs相反方向分别遍历两棵子树，依次比较值
    /**
     反先序遍历左子树，记录节点
     先序遍历右子树，依次和上面的节点比较

     注意：一定要把null节点也存储起来，不然遇到只有一个节点的node，其反先序和先序的结果都一样
     */
    public boolean method2_1(TreeNode root) {
        if (root == null) return false;
        // 反先序遍历左子树，按照 自身 -> 右节点 -> 左节点
        Queue<Integer> leftVals = new LinkedList<>();
        TreeNode node = root.left;
        Stack<TreeNode> stk = new Stack<>();
        while(node != null || !stk.isEmpty()) {
            while (node != null) {
                leftVals.offer(node.val);  // 先序
                stk.push(node);
                node = node.right;
            }
            // 这里node为null节点
            leftVals.offer(Integer.MIN_VALUE);
            TreeNode cur = stk.pop();
            node = cur.left;
        }
        leftVals.offer(Integer.MIN_VALUE);
        // 先序遍历右子树，依次比较值
        node = root.right;
        while(node != null || !stk.isEmpty()) {
            while (node != null) {
                Integer leftVal = leftVals.poll();
                if (!leftVal.equals(node.val)) return false;
                stk.push(node);
                node = node.left;
            }
            // 这里node为null节点
            Integer leftVal = leftVals.poll();
            if (!leftVal.equals(Integer.MIN_VALUE)) return false;
            TreeNode cur = stk.pop();
            node = cur.right;
        }
        // 这里比较最后一个为null的node
        Integer leftVal = leftVals.poll();
        if (!leftVal.equals(Integer.MIN_VALUE)) return false;
        // 到这里，不一定是对称的,必须要保证stk和leftVals都为空，才说明是比较完了
        if (stk.size() == 0 && leftVals.size() == 0) return true;
        else return true;
    }

    // 方法二2：递归-dfs，相反方向分别遍历两颗子树，然后依次比较值
    /** 方法用【递归遍历思想】就可以 */
    public boolean method2_2(TreeNode root) {
        return false;
    }

    // 方法三1：迭代——bfs比较每一层节点是否属于回文数
    /**
     每次把树的一层放入队列，比较这一层队列的节点是否是回文数
     */
    public boolean method3_1(TreeNode root) {
        if (root == null) return false;
        Queue<TreeNode> qu = new LinkedList<>();
        qu.offer(root);
        while(!qu.isEmpty()) {
            int size = qu.size();
            List<Integer> vals = new ArrayList<>();  // 放置每一层的节点值
            while (size > 0) {
                TreeNode node = qu.poll();
                if (node.left == null) vals.add(Integer.MIN_VALUE);
                else {
                    vals.add(node.left.val);
                    qu.offer(node.left);
                }
                if (node.right == null) vals.add(Integer.MIN_VALUE);
                else {
                    vals.add(node.right.val);
                    qu.offer(node.right);
                }
                size--;
            }
            // 此时，vals中存储了当前层的val，判断是否为回文数
            int li = 0, ri = vals.size()-1;
            while (li < ri) {
                if (!vals.get(li).equals(vals.get(ri))) return false;
                li++;
                ri--;
            }
        }
        return true;
    }

    // 方法三2：迭代——bfs，优化空间复杂度（不再存储每一层节点，改为直接比较）
    public boolean method3_2(TreeNode root) {
        if(root==null || (root.left==null && root.right==null)) {
            return true;
        }
        //用队列保存节点
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        //将根节点的左右孩子放到队列中
        queue.add(root.left);
        queue.add(root.right);
        while(queue.size()>0) {
            //从队列中取出两个节点，再比较这两个节点
            TreeNode left = queue.removeFirst();
            TreeNode right = queue.removeFirst();
            //如果两个节点都为空就继续循环，两者有一个为空就返回false
            if(left==null && right==null) {
                continue;
            }
            if(left==null || right==null) {
                return false;
            }
            if(left.val!=right.val) {
                return false;
            }
            //将左节点的左孩子， 右节点的右孩子放入队列
            queue.add(left.left);
            queue.add(right.right);
            //将左节点的右孩子，右节点的左孩子放入队列
            queue.add(left.right);
            queue.add(right.left);
        }

        return true;
    }

}

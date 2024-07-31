
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

    int max_depth = 0;
    int depth = 0;

    public int maxDepth(TreeNode root) {
//        method1_2(root, 0);
//        return max_depth;
//        return method1_1(root);
        return method3(root);
    }

    // 方法一：递归 dfs（动态规划思路）
    public int method1_1(TreeNode root) {
        // base case
        if (root == null) return 0;
        // 获取左子树深度
        int leftDepth = method1_1(root.left);
        // 获取右子树深度
        int rightDepth = method1_1(root.right);
        // 根据左右子树的最大深度推出原二叉树的最大深度
        int maxDepth = Math.max(leftDepth, rightDepth) + 1;
        return maxDepth;
    }

    // 方法一：递归 dfs（回溯思路）
    public void method1_2(TreeNode root, int depth) {
        /**
         * 当遍历到 null 时，更新一次树的最大深度值
         */
        if (root == null) {
            // 更新最大深度，返回
            max_depth = Math.max(max_depth, depth);
            return;
        }
        method1_2(root.left, depth+1);
        method1_2(root.right, depth+1);
    }
    void method1_2_1(TreeNode root) {
        if (root == null) return;
        // 前序遍历位置
        depth++;
        // 遍历的过程中记录最大深度
        res = Math.max(res, depth);
        traverse(root.left);
        traverse(root.right);
        // 后序遍历位置
        depth--;
    }

    // 方法三：队列 bfs
    /**
     *  第1次while，queue中只有根节点，添加第2层全部节点，ans=1
     *  第2次while，queue中第2层全部节点，添加第3层全部节点，ans=2
     *  第3次while，queue中第3层全部节点，添加第4层全部节点，ans=3
     *  第4次while，queue中第4层全部节点，添加第5层全部节点，ans=4
     */
    public int method3(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> qu = new LinkedList<>();
        // 初始化队列数据
        qu.offer(root);
        // bfs遍历二叉树
        int ans = 0;
        while (!qu.isEmpty()) {
            int size = qu.size();  // 固定这一层节点的个数
            // 将下一层节点个数全部填充进来（保证每一次queue中只存在一层的节点）
            while (size > 0) {
                TreeNode node = qu.poll();
                if (node.left != null) qu.offer(node.left);
                if (node.right != null) qu.offer(node.right);
                size--;
            }
            // queue中数据替换为下一层数据，层数+1
            ans++;
        }
        return ans;
    }

    // ! todo ---- 以下方法行不通，不知道为啥
    // 方法二：迭代（这种方法不行，因为有时候跳出栈的时候会一次性上移两层，有时候会上移一层，这个没办法区分）
    public int method_error(TreeNode root) {
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

    // 方法二：迭代（完全模拟递归的参数）仍然不通过
    // https://blog.csdn.net/weixin_42738495/article/details/100765170
    public int method2(TreeNode root) {
        class Layer {
            TreeNode node;
            int depth;
            Layer(TreeNode node, int depth) {
                this.node = node;
                this.depth = depth;
            }
        }
        int max_depth = 0;
        Stack<Layer> stk = new Stack<>();
        int depth = 0;  // 初始化栈深度为0
        while (root != null || !stk.isEmpty()) {
            while (root != null) {
                stk.push(new Layer(root, depth+1));
                root = root.left;
                depth++;  // 向下遍历深度+1
            }
            Layer cur = stk.pop();
            // 当一路访问左子树到尽头，更新一次最大深度
            max_depth = Math.max(max_depth, cur.depth);
            root = cur.node.right;
        }
        return max_depth;
    }
    /**  但是同样的方法，C++的实现就可以通过，不知道为什么

     * Definition for a binary tree node.
     * struct TreeNode {
     *     int val;
     *     TreeNode *left;
     *     TreeNode *right;
     *     TreeNode(int x) : val(x), left(NULL), right(NULL) {}
     * };

    class Solution {
        public:
        int maxDepth(TreeNode* root) {
            //迭代法，DFS,运用栈的想法；
            if(!root){
                return 0;
            }
            stack<pair<TreeNode*,int>>s;
            TreeNode*p=root;
            int maxdeep=0;
            int deep = 0;
            while(p||!s.empty()){
                //一直遍历所有的左节点；
                while(p){
                    s.push(pair<TreeNode*,int>(p,deep+1));
                    p = p->left;
                    deep++;
                }
                p = s.top().first;
                deep = s.top().second;
                maxdeep = max(maxdeep,deep);
                s.pop();
                p = p->right;
            }
            return maxdeep;
        }
    };
     */


}
//leetcode submit region end(Prohibit modification and deletion)

package tree;

public class TreeBuild {

    // 前序 + 中序 = 唯一二叉树
    // 后序 + 中序 = 唯一二叉树
    // 前序 + 后序 != 唯一二叉树
    // https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-66994/dong-ge-da-172f0/#%E9%80%9A%E8%BF%87%E5%90%8E%E5%BA%8F%E5%92%8C%E4%B8%AD%E5%BA%8F%E9%81%8D%E5%8E%86%E7%BB%93%E6%9E%9C%E6%9E%84%E9%80%A0%E4%BA%8C%E5%8F%89%E6%A0%91

    // 官方的迭代方法
    // https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/solutions/255811/cong-qian-xu-yu-zhong-xu-bian-li-xu-lie-gou-zao-9/?envType=study-plan-v2&envId=top-100-liked

    /** 递归后序遍历的思路
     * 1、首先把前序遍历结果的第一个元素或者后序遍历结果的最后一个元素确定为根节点的值。
     *
     * 2、然后把前序遍历结果的第二个元素作为左子树的根节点的值。
     *
     * 3、在后序遍历结果中寻找左子树根节点的值，从而确定了左子树的索引边界，进而确定右子树的索引边界，递归构造左右子树即可。
     */
}

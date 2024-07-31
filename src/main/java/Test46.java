import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Test46 {

    public static void main(String[] args) {
        int[] nums = {1,2,3};
        Solution46 solution46 = new Solution46();
        List<List<Integer>> lists = solution46.permute(nums);
        for (List<Integer> list : lists) {
            System.out.println(list);
        }
    }

}

class Solution46 {

    public List<List<Integer>> permute(int[] nums) {
        return method_dfs(nums);
    }

    // 方法一：递归dfs
    /** 思路：
     将nums中的每个数字都作为起点回溯一遍
     已经用过的数字不能再使用了
     */
    List<List<Integer>> res = new ArrayList<>();
    LinkedList<Integer> onPath = new LinkedList<>();
    boolean[] used;
    public List<List<Integer>> method_dfs(int[] nums) {

        // 初始化变量
        used = new boolean[nums.length];

        /** 适用于 error_dfs、dfs1、dfs2 */
        // 将nums中每个数字都作为起点，进行回溯
        for (int i=0; i<nums.length; i++) {
//            error_dfs(nums, i);
//            dfs1(nums, i);
//            dfs2(nums, i);
        }

        /** 适用于 dfs3 */
        dfs3(nums);

        return res;

    }
    // 错误方法：回溯dfs
    /** 错误原因
     *  1.函数进来之后，先【base case】，再【前序】；应该改成先【前序】再【base case】，因为只有先把元素添加到路径，才能去判断路径的完整性
     *  2.【base case】的return之前，需要把当前元素先从路径移除才行
     */
    public void error_dfs(int[] nums, int i) {
        // base case
        if (onPath.size() == nums.length) {
            res.add(new LinkedList<>(onPath));
            return;
        }

        // 前序
        used[i] = true;
        onPath.add(nums[i]);


        // dfs多叉树遍历
        for (int _i=0; _i<nums.length; _i++) {
            if (used[_i]) continue;
            error_dfs(nums, _i);
        }

        // 后序
        used[i] = false;
        onPath.removeLast();

    }

    // 修改方法一
    public void dfs1(int[] nums, int i) {

        // 前序
        used[i] = true;
        onPath.add(nums[i]);

        // base case
        if (onPath.size() == nums.length) {
            res.add(new LinkedList<>(onPath));
            // 后序
            used[i] = false;
            onPath.removeLast();
            return;
        }


        // dfs多叉树遍历
        for (int _i=0; _i<nums.length; _i++) {
            if (used[_i]) continue;
            dfs1(nums, _i);
        }

        // 后序
        used[i] = false;
        onPath.removeLast();

    }

    // 修改方法二
    public void dfs2(int[] nums, int i) {

        // 前序
        used[i] = true;
        onPath.add(nums[i]);

        // base case
        if (onPath.size() == nums.length) {
            res.add(new LinkedList<>(onPath));
        }


        // dfs多叉树遍历
        for (int _i=0; _i<nums.length; _i++) {
            if (used[_i]) continue;
            dfs1(nums, _i);
        }

        // 后序
        used[i] = false;
        onPath.removeLast();

    }

    // 修改方法三（推荐）
    /** 操作树枝的方法，简洁
     *  核心：简化书写
     *      1.让【先序】在【base case】之前执行
     *      2.让【base case】return之前，执行【后序】，将元素移除
     */
    public void dfs3(int[] nums) {
        // base case
        if (onPath.size() == nums.length) {
            res.add(new LinkedList<>(onPath));
            return;
        }


        // dfs多叉树遍历
        for (int i=0; i<nums.length; i++) {
            // base case
            if (used[i]) continue;

            // 先序
            used[i] = true;
            onPath.add(nums[i]);

            // dfs
            dfs3(nums);

            // 后序
            used[i] = false;
            onPath.removeLast();
        }

    }
}

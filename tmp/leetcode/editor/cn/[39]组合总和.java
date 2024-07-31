
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        method1(candidates, 0, target);
        return res;
    }

    // 方法一：回溯法
    List<List<Integer>> res = new ArrayList<>();
    LinkedList<Integer> onPath = new LinkedList<>();
    int sum = 0;
    public void method1(int[] candidates, int start, int target) {
        // base case
        // 如果总和 > 目标值，由于全是正数，所以直接返回，减少无效遍历
        if (sum > target) return;
        // 如果总和 == 目标值，记录当前路径，并返回
        else if (sum == target) {
            res.add(new ArrayList<>(onPath));
            return;
        }

        // dfs遍历
        for (int i=start; i<candidates.length; i++) {
            // 树枝前序
            onPath.add(candidates[i]);
            sum += candidates[i];

            method1(candidates, i, target);

            // 树枝后序
            onPath.removeLast();
            sum -= candidates[i];

        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)

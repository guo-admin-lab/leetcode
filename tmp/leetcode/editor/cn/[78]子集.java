
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public List<List<Integer>> subsets(int[] nums) {
//        return method1(nums);
        method2_dfs(nums, 0);
        return res;
    }

    // 方法一：迭代法——子集枚举
    // https://leetcode.cn/problems/subsets/solutions/420294/zi-ji-by-leetcode-solution/?envType=study-plan-v2&envId=top-100-liked
    public List<List<Integer>> method1(int[] nums) {

        List<List<Integer>> res = new ArrayList<>();
        List<Integer> tmp = new ArrayList<>();

        int nLen = nums.length;
        /** 位运算 快于 阶乘
         *  1 << 2  等同于 2^2
         *  1 << 3  等同于 2^3
         */
//        int total = (int) Math.pow(2, nLen);  // 低效率
        int total = 1 << nLen;  // 高效率
        // 共有 [0 ... total-1] 个子集
        for (int mask=0; mask<total; mask++) {
            tmp.clear();
            // 从 nums 中 按位当索引，换取子集元素
            for (int i=0; i<nLen; i++) {
                /** 假如 nums=[1,2,3]  total=8
                 * mask = 000, 001, 010, 011, 100, 101, 110, 111
                 * 索引如下：
                 * 1 << 0 = 001
                 * 1 << 1 = 010
                 * 1 << 2 = 100
                 */
                int iMask = 1 << i;
                // 如果iMask和mask中对应的位置对应上了，则加入
                if ((mask & iMask) != 0) {
                    tmp.add(nums[i]);
                }
            }
            res.add(new ArrayList<>(tmp));
        }
        return res;
    }

    // 方法二：回溯法——子集枚举
    List<List<Integer>> res = new ArrayList<>();
    LinkedList<Integer> tmp = new LinkedList<>();
    public void method2_dfs(int[] nums, int start) {
        /** start用来保证子集中的元素不会重复 */
        // base case在树枝上操作

        // 回溯路径上的每个结点都是解集
        res.add(new ArrayList<>(tmp));

        for (int i=start; i<nums.length; i++) {
            // 树枝前序
            tmp.add(nums[i]);

            // dfs遍历
            method2_dfs(nums, i+1);

            // 树枝后序
            tmp.removeLast();
        }

    }
}
//leetcode submit region end(Prohibit modification and deletion)

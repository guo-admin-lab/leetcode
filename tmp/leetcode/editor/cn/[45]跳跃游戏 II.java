
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public int jump(int[] nums) {
        return method3(nums);
    }

    // https://leetcode.cn/problems/jump-game-ii/?envType=study-plan-v2&envId=top-100-liked
    // 方法一：反向贪心算法  O(N^2)
    /**
     「贪心」地选择距离最后一个位置最远的那个位置，
     也就是对应下标最小的那个位置。
     因此，我们可以从左到右遍历数组，选择第一个满足要求的位置。
     */
    public int method1(int[] nums) {
        int pos = nums.length - 1;
        int steps = 0;
        while (pos > 0) {
            for (int i=0; i<pos; i++) {
                if (i+nums[i] >= pos) {
                    pos = i;
                    steps++;
                    break;
                }
            }
            // 由于题目保证一定可达nums[n-1]，所以不用考虑死循环的问题
            // 否则：如果找不到>=pos的位置，需要返回false
        }
        return steps;
    }

    // 方法二：正向贪心算法
    // https://leetcode.cn/problems/jump-game-ii/solutions/36035/45-by-ikaruga/?envType=study-plan-v2&envId=top-100-liked
    public int method2_1(int[] nums) {
        int steps = 0;
        int start = 0, end = 1;
        while (end < nums.length) {
            // 依次处理每一步的最远距离
            int maxpos = 0;
            for (int i=start; i<end; i++) {
                maxpos = Math.max(maxpos, i+nums[i]);
            }
            // 更新下一次跳跃的范围
            start = end;
            end = maxpos + 1;
            steps++;  // 已经跳跃的步数+1
        }
        return steps;
    }
    // 方法二：代码优化
    public int method2_2(int[] nums) {
        int steps = 0;
        int end = 0;
        int maxpos = 0;
        for (int i=0; i<nums.length; i++) {
            maxpos = Math.max(maxpos, i+nums[i]);
            if (i == end) {
                end = maxpos;
                steps++;
            }
        }
        return steps;
    }

    // 方法三：动态规划（个人感觉就是递归回溯列举所有可能，然后取最小值）
    // https://labuladong.github.io/algo/di-er-zhan-a01c6/tan-xin-le-9bedf/ru-he-yun--48a7c/
    /** 思路：
     *      定义 fun(nums, i)为从索引i跳到最后至少需要多少次
     * */
    boolean[] visited;
    int[] memo;
    public int method3(int[] nums) {
        // 备忘录优化
        visited = new boolean[nums.length];
        memo = new int[nums.length];
        Arrays.fill(memo, nums.length-1);

        return dp(nums, 0);
    }
    public int dp(int[] nums, int i) {
        // base case 写在下面

        // 当前结点已经访问
        visited[i] = true;

        // 从当前i，最多能跳的步数
        int range = nums[i];
        // 从i到i+range这个范围内，看看到底哪个索引跳到最后需要的步数最少
        int minsteps = nums.length-1;  // 初始化最少步数
        for (int nxti=i+1; nxti<=i+range; nxti++) {
            // 进行剪枝
            int subSteps;
            if (nxti >= nums.length-1) subSteps = 0;  // base case
            else if (visited[nxti]) subSteps = memo[nxti];
            else subSteps = dp(nums, nxti);  // 从nxti跳到最后需要多少步
            minsteps = Math.min(minsteps, subSteps+1);
        }

        // 设置备忘录
        memo[i] = minsteps;

        // 返回当前i跳到最后需要的最少步数
        return minsteps;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

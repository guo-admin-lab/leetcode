
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public int[] dailyTemperatures(int[] temperatures) {
        return method2(temperatures);
    }

    // https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-daeca/dan-diao-z-1bebe/

    /** 力扣官方题解：
     *  方法一：改进的暴力求解
     *  方法二：单调栈（但是正着遍历，和labuladong的解法有些不同）
     * */
    // https://leetcode.cn/problems/daily-temperatures/solutions/283196/mei-ri-wen-du-by-leetcode-solution/?envType=study-plan-v2&envId=top-100-liked

    // 方法一：双循环暴露检索  O(n^2)  超时
    public int[] method1(int[] temperatures) {
        int tLen = temperatures.length;
        int[] res = new int[tLen];

        int ri = 0;
        for (int curi=0; curi<tLen; curi++) {
            int nxti = curi+1;
            for (; nxti<tLen; nxti++) {
                if (temperatures[nxti] > temperatures[curi]) {
                    res[ri++] = nxti - curi;
                    break;
                }
            }
            // 如果找不到更高温度，设置0（找不到最高温度的标志是检索到数组末尾）
            if (nxti == tLen) res[ri++] = 0;
        }

        return res;
    }

    // 方法二：单调栈（区别于【滑动窗口最大值】的 单调队列）
    /** 思路分析
     * 核心：倒着压栈，将比自己小的数字都覆盖掉；注意：栈中放置索引
     * 73,74,75,71,69,72,76,73
     * 0, 1, 2, 3, 4, 5, 6, 7  索引
     *
     * 倒着压栈过程：（同时计算索引）
     * 73  sk空，0；将 i=7入栈    栈底【7】栈顶
     * 76  栈中元素比76小；先将栈中元素弹出；sk空，0；将 i=6 入栈    栈底【7】栈顶
     * 72  栈中元素比72大；无序弹出元素；sk.top()-i=1；将 i=5 入栈  栈底【7, 5】栈顶
     * 69  依次类推
     */
    public int[] method2(int[] temperatures) {
        int tLen = temperatures.length;
        int[] res = new int[tLen];
        Stack<Integer> sk = new Stack<>();  // 存放索引的栈
        // 倒着遍历入栈，并计算所求索引
        // int ri = 0;  // 不需要这个索引
        for (int curi=tLen-1; curi>=0; curi--) {
            // 1.先将栈中比curi小的都弹出（因为要找比自己大的值）
            while (!sk.isEmpty() && temperatures[sk.peek()] <= temperatures[curi]) {
                sk.pop();
            }
            /** 注意：下面的步骤2和步骤3的顺序不能变，因为首先要找到sk.top()的元素 */
            // 2.此时栈顶元素的值一定比curi大（除非sk为空）
            /** 注意：这里res的索引要和curi一致，不能是单独一个，否则就反了 */
            // 错误的索引：res[ri++] = sk.isEmpty() ? 0 : (sk.peek() - curi);  /** 注意：这里一定要加括号 (sk.peek() - curi) */
            res[curi] = sk.isEmpty() ? 0 : (sk.peek() - curi);
            // 3.将当前索引入栈
            sk.push(curi);
        }
        // 返回结果
        return res;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

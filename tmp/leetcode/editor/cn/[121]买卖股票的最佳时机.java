
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public int maxProfit(int[] prices) {
        return method3(prices);
    }

    // 暴力方法 | 一次遍历方法
    // https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/solutions/136684/121-mai-mai-gu-piao-de-zui-jia-shi-ji-by-leetcode-/?envType=study-plan-v2&envId=top-100-liked

    // 动态规划
    // https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/solutions/1692872/by-jyd-cu90/?envType=study-plan-v2&envId=top-100-liked

    // 通用解法
    // https://labuladong.github.io/algo/di-er-zhan-a01c6/yong-dong--63ceb/yi-ge-fang-3b01b/

    // 方法一：暴力求解【超时】
    public int method1(int[] prices) {
        int max = 0;
        for (int i=0; i<prices.length; i++) {
            for (int j=i+1; j<prices.length; j++) {
                max = Math.max(max, prices[j]-prices[i]);
            }
        }
        return max;
    }

    // 方法二：一次遍历
    public int method2(int[] prices) {
        int minprice = Integer.MAX_VALUE;
        int maxprofit = 0;
        for (int i=0; i<prices.length; i++) {
            if (prices[i] < minprice) {
                minprice = prices[i];
            } else if (prices[i] - minprice > maxprofit) {
                maxprofit = prices[i] - minprice;
            }
        }
        return maxprofit;
    }

    // 方法三：动态规划
    public int method3(int[] prices) {
        int mincost = Integer.MAX_VALUE;
        int maxprofit = 0;
        for (int price : prices) {
            mincost = Math.min(mincost, price);
            maxprofit = Math.max(maxprofit, price - mincost);
        }
        return maxprofit;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

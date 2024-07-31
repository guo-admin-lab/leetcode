
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        return method2(nums, k);
    }

    // 方法一：暴力遍历  O(n^2)  Time Limit Exceeded
    public int[] method1(int[] nums, int k) {
        if (k > nums.length) return new int[0];
        int nSize = nums.length;
        int[] res = new int[nSize-k+1];  // 结果数组大小：nSize - k + 1
        int resIndex = 0;
        // 1.设置滑动窗口首尾的坐标索引 [left, right]
        int left = 0, right = k-1;
        // 2.遍历nums， right不能超过数组长度
        while (right < nSize) {
            // 3.遍历滑动窗口，寻找最大值
            int maxNum = nums[left];
            for (int i=left+1; i<=right; i++) {
                maxNum = Math.max(maxNum, nums[i]);
            }
            res[resIndex++] = maxNum;
            left++;
            right++;
        }
        return res;
    }

//    https://leetcode.cn/studyplan/top-100-liked/
//    https://leetcode.cn/problems/sliding-window-maximum/description/?envType=study-plan-v2&envId=top-100-liked
//    https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-daeca/dan-diao-d-32cd5/#%E4%BA%8C%E3%80%81%E5%AE%9E%E7%8E%B0%E5%8D%95%E8%B0%83%E9%98%9F%E5%88%97%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84
    /** 方法一中超时原因：查找滑动窗口内最大值需要遍历，太费时间
     *  所以可以使用单调队列实现快速查找最大值
     */
    // 方法二：单调队列
    public int[] method2(int[] nums, int k) {
        if (k > nums.length) return new int[0];
        int nSize = nums.length;
        int[] res = new int[nSize-k+1];  // 结果数组大小：nSize - k + 1
        int resIndex = 0;
        MonotonousQueue window = new MonotonousQueue();
        // 1.遍历nums
        int i = 0;
        // 2.遍历nums，寻找最大值
        while (i < nSize) {
            window.push(nums[i]);  // 向滑动窗口中添加值
            if (i == k - 1 + resIndex) {  // 当窗口内数量达到k时，此时要向结果中添加当前滑动窗口内的最大值
                res[resIndex++] = window.max();  // 单调队列中的队首是最大值
                window.pop(nums[i - k + 1]);  // 需要传参的原因：队列在push的过程中，nums[i - k + 1]可能已经被覆盖掉了
            }
            i++;
            /*k=3 k-1=2
            0 1 2 3 4 5 6 7
            l   r l   r*/
        }
        return res;
    }

    class MonotonousQueue {
        Deque<Integer> maxq = new LinkedList<>();
        public void push(int n) {
            // 覆盖掉前面比n小的数，保持maxq的队首是最大值
            while (!maxq.isEmpty() && maxq.getLast() < n) {
                maxq.pollLast();
            }
            maxq.addLast(n);
        }
        public int max() {
            return maxq.getFirst();  // 单调队列中的队首是最大值
        }
        public void pop(int n) {
            if (n == maxq.getFirst()) {
                maxq.pollFirst();
            }
        }
    }

}
//leetcode submit region end(Prohibit modification and deletion)

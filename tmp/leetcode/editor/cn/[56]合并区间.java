
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[][] merge(int[][] intervals) {
        return method2(intervals);
    }

    // 方法一：排序+遍历  O(nlogn)  O(n)
    public int[][] method1(int[][] intervals) {
        // 1.将区间进行排序，根据区间的起始位置
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return o1[1] - o2[1];
                } else {
                    return o1[0] - o2[0];
                }
            }
        });

        // 2.合并区间
        int iSize = intervals.length;
        Set<Integer> rmIndexSet = new HashSet<>();
        /** 注意：定义正在处理中的索引
         * [2,6] [3,9] [5,12]
         * 正常遍历逻辑：这样会出下面的问题，丢掉一些区间
         *      [2,9] ([3,9]待删除) [5,12]
         *      [2,9] ([3,12]待删除) ([5,12]待删除)
         * 固定processIndex遍历：这是正确的
         *      [2,9] ([3,9]待删除) [5,12]
         *      [2,12] ([3,9]待删除) ([5,12]待删除)
         * */
        int processIndex = 0;
        for (int i=1; i<iSize; i++) {
            // 情况1：[2, 6] [3, 9] -> [2, 9]
            // 情况2：[2, 6] [1, 9] 排序后不会出现这种情况：i的第一个数比i-1的第一个数小
            // 情况3：[2, 6] [3, 4] -> [2, 6]
            int[] pre = intervals[processIndex];
            int[] cur = intervals[i];
            if (cur[0] <= pre[1]) {  // 两个数组有重合部分
//                if (cur[1] > pre[1]) pre[1] = cur[1];  // 与下面等价
                pre[1] = Math.max(pre[1], cur[1]);  // 取二者最大值
                rmIndexSet.add(i);  // 记录需要删除的索引
            } else {  // 两个数组没有重合部分，那么待处理索引就设置为当前索引
                /** 注意：这里不是processIndex++ */
                processIndex = i;
            }
        }

        // 3.构造结果数组
        int ri = 0;
        int[][] res = new int[iSize-rmIndexSet.size()][2];
        for (int i=0; i<iSize; i++) {
            if (!rmIndexSet.contains(i)) {
                res[ri++] = intervals[i];
            }
        }

        return res;
    }

    // 方法二：
    public int[][] method2(int[][] intervals) {
//        https://labuladong.github.io/algo/di-san-zha-24031/jing-dian--a94a0/yi-ge-fang-93124/
        return int[0][0];
    }

}
//leetcode submit region end(Prohibit modification and deletion)

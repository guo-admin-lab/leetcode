
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int subarraySum(int[] nums, int k) {
        return method4(nums, k);
    }

    // 错误方法：因为没考虑【特殊情况一：0序列】和【特殊情况二：sum>target不能直接返回，因为nums[i]有负数，sum += nums[i]后可能会导致sum<target】
    public static int method_err(int[] nums, int target) {
        int resCnt = 0;
        int nSize = nums.length;
        int start = 0;  // 控制连续序列的起始索引
        while (start < nSize) {
            int sum = 0;
            for (int i=start; i<nSize; i++) {  // 注意：这里不是i=start+1,因为要从i位置就开始算起
                if (sum < target) {
                    sum += nums[i];
                } else if (sum > target) {
                    break;
                } else {
                    if (i+1 < nSize && nums[i+1] == 0) resCnt += 2;  // 如果后面的数是0，则多算一个序列
                    else resCnt += 1;
                }
            }
            // 处理最后一个数 | for循环进不去的情况
            if (sum == target) resCnt++;
            start++;
        }
        return resCnt;
    }

    // 方法一：双重遍历枚举  O(n^2)  O(1)
    public static int method1(int[] nums, int target) {
        int resCnt = 0;
        int nSize = nums.length;
        int start = 0;  // 控制连续序列的起始索引
        while (start < nSize) {
            int sum = 0;
            for (int i=start; i<nSize; i++) {  /** 注意：这里不是i=start+1,因为要从i位置就开始算起 */
                /** if (sum > target) break;   注意：这里不能直接跳出，因为nums[i]有负数，之后再加一个负数可能会导致sum<target */
                sum += nums[i];
                // 有结果后立马判断一下
                if (sum == target) resCnt++;
            }
            start++;
        }
        return resCnt;
    }

    // 方法二：前缀 + 遍历  O(n^2)  O(n)
    public static int method2(int[] nums, int target) {
        int resCnt = 0;
        int nSize = nums.length;
        // 定义：前缀和数组     preSum[i]表示: 前i个数[0..i)的和    例如：preSum[1]: nums[0]   preSum[2]: nums[0]+nums[1]
        int[] preSum = new int[nSize + 1];
        int pSize = preSum.length;
        // 1.计算 nums 的前缀和
        preSum[0] = 0;
        for (int i=1; i<=nSize; i++) {
            preSum[i] = preSum[i-1] + nums[i-1];
        }
        /** 目标：找到所有满足【preSum[j] - preSum[i] == target】的(j,i)组合
         *  方法：双重遍历
         */
        // 2.遍历preSum，计算索引位置差
        for (int i=0; i<pSize; i++) {
            // 3.向后依次遍历，必须要遍历到每个(j,i)，然后用 preSum[j]-preSum[i] (位置j前面的数之和 - 位置i前面的数之和) = 位置j和i之间的数之和
            for (int j=i+1; j<pSize; j++) {
                if (preSum[j] - preSum[i] == target) resCnt++;
            }
        }
        return resCnt;
    }

    // 方法三：前缀 + 哈希表  O(n)  O(2n)
    public static int method3(int[] nums, int target) {
        int resCnt = 0;
        int nSize = nums.length;
        // 定义：前缀和数组    preSum[i]表示: 前i个数[0..i)的和    例如：preSum[1]: nums[0]   preSum[2]: nums[0]+nums[1]
        int[] preSum = new int[nSize + 1];
        int pSize = preSum.length;
        // 1.计算 nums 的前缀和
        preSum[0] = 0;
        for (int i=1; i<=nSize; i++) {
            preSum[i] = preSum[i-1] + nums[i-1];
        }
        /** 目标：找到所有满足【preSum[j] - preSum[i] == target】的(j,i)组合
         *  方法：
         *      1.利用哈希表记录位置i之前数的和（注意，这里‘和’可能有重复，所以需要存储相同和出现的次数）
         *      2.搜索到位置j时，利用哈希去加速查找是否有满足target-preSum[j]的值存在
         */
        // 2.遍历preSum：一边遍历，一边匹配符合条件的个数
        Map<Integer, Integer> sum2cnt = new HashMap<>();
        for (int i=0; i<pSize; i++) {
            // 如果符合条件，增加结果数量
            int need = preSum[i] - target;
            if (sum2cnt.containsKey(need)) {
                resCnt += sum2cnt.get(need);
            }
            // 记录位置i之前数的和
            sum2cnt.put(preSum[i], sum2cnt.getOrDefault(preSum[i],0)+1);
        }
        return resCnt;
    }

    // 方法四：临时前缀 + 哈希表  O(n) O(n)  这里还有问题没解决
    public static int method4(int[] nums, int target) {
        int resCnt = 0;
        int nSize = nums.length;
        Map<Integer, Integer> sum2cnt = new HashMap<>();
        // 1.记录临时前缀和（由于每个前缀和只用到一次，所以不需要用数组存储所有的前缀和）
        int preSum = 0;
        for (int i=1; i<nSize; i++) {
            // 如果符合条件，增加结果数量
            int need = preSum - target;
            if (sum2cnt.containsKey(need)) {
                resCnt += sum2cnt.get(need);
            }
            // 记录位置i-1之前数的和
            sum2cnt.put(preSum, sum2cnt.getOrDefault(preSum,0)+1);
            preSum += nums[i-1];  // preSum等同于preSum[i]
        }
        return resCnt;
    }

    // 方法五：滑动窗口(无解，原因如下)
    public static int method5(int[] nums, int target) {

        int left = 0, right = 0;
        while (right < nums.length) {
            // 扩大窗口

            // 如果当windowSum>target,就缩小窗口的话，那之后的负数也可能会将windowSum<0,
            // 所以没办法确定缩小窗口的时机

        }
    }

}
//leetcode submit region end(Prohibit modification and deletion)

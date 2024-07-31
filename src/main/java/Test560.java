import java.util.HashMap;
import java.util.Map;

public class Test560 {

    public static void main(String[] args) {
        int[] nums = {1, 1, 1};
        int res = method4(nums, 2);
        System.out.println(res);
    }

    public static int method1(int[] nums, int target) {
        int resCnt = 0;
        int nSize = nums.length;
        int start = 0;  // 控制连续序列的起始索引
        while (start < nSize) {
            int sum = 0;
            for (int i=start; i<nSize; i++) {  // 注意：这里不是i=start+1,因为要从i位置就开始算起
                // if (sum > target) break;  // 注意：这里不能直接跳出，因为nums[i]有负数，之后再加一个负数可能会导致sum<target
                sum += nums[i];
                // 有结果后立马判断一下
                if (sum == target) resCnt++;
            }
            start++;
        }
        return resCnt;
    }

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

    // 方法四：临时前缀 + 哈希表  O(n) O(1)
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

}

class Solution560 {
    public int subarraySum(int[] nums, int k) {
        int n = nums.length;
        // 前缀和数组
        int[] preSum = new int[n + 1];
        preSum[0] = 0;
        // 【前缀和】到【该前缀和出现次数】的映射，方便快速查找所需的前缀和
        HashMap<Integer, Integer> count = new HashMap<>();
        count.put(0, 1);
        // 记录和为 k 的子数组个数
        int res = 0;

        // 计算 nums 的前缀和
        for (int i = 1; i <= n; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
            // 如果之前存在值为 need 的前缀和
            // 说明存在以 nums[i-1] 结尾的子数组的和为 k
            int need = preSum[i] - k;
            if (count.containsKey(need)) {
                res += count.get(need);
            }
            // 将当前前缀和存入哈希表
            if (!count.containsKey(preSum[i])) {
                count.put(preSum[i], 1);
            } else {
                count.put(preSum[i], count.get(preSum[i]) + 1);
            }
        }
        return res;
    }
}

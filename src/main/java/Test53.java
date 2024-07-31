public class Test53 {

    public static void main(String[] args) {
        int[] nums = new int[]{1};
        int res = method4(nums);
        System.out.println(res);
    }


    // 方法四：前缀和 + 求nums[i]结尾的最大子数组和  O(n) O(n)
    public static int method4(int[] nums) {
        int nSize = nums.length;
        int[] preSum = new int[nSize+1];
        int pSize = preSum.length;
        // 1.初始化preSum数组   preSum[i]: 前i个元素的和，即nums[0..i)之和
        /**
         *  preSum[0]表示前0个元素的和，也就是零和。
         *  preSum[1]表示前1个元素的和，也就是nums[0]。
         *  preSum[2]表示前2个元素的和，也就是nums[0] + nums[1]，以此类推。
         */
        preSum[0] = 0;
        for (int i=1; i<=nSize; i++) {  // 注意：这里是i<=nSize
            preSum[i] = preSum[i-1] + nums[i-1];
        }
        /** 遍历preSum数组
         *  寻找以nums[i]结尾的最大子数组和：
         *      preSum[i+1]: nums[0..i]的总和
         *      min(preSum[0..i])中的最小值
         *  因为，preSum[i+1]的值固定，减去前面的值越小，最终得到的数越大
         *  所以，以nums[i]结尾的子数组最大值 = preSum[i+1] - min(preSum[0..i])
         */
        // 2.遍历nums数组，寻找以nums[i]结尾的最大子数组和
        int maxRes = Integer.MIN_VALUE;
        int minVal = Integer.MAX_VALUE;
        for (int i=0; i<nSize; i++) {
            // 维护 minVal 是 preSum[0..i] 的最小值
            minVal = Math.min(minVal, preSum[i]);
            // 以 nums[i] 结尾的最大子数组和就是 preSum[i+1] - min(preSum[0..i])
            maxRes = Math.max(maxRes, preSum[i+1] - minVal);
        }
        return maxRes;
    }

}

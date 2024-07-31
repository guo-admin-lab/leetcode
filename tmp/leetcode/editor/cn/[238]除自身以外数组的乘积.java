
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public int[] productExceptSelf(int[] nums) {
        return method3(nums);
    }

    /** 以下方法的空间复杂度分析，均不包括answer数组 */

    // 方法零：暴力双重遍历 O(n^2)

    // 方法一：除法 + 分类讨论  O(n) O(1) 但是除法的计算耗时非常大
    public int[] method1(int[] nums) {
        /** 特殊情况：注意：可能有多个0元素
         * 解答失败:
         * 	测试用例:[-1,1,0,-3,3]
         * 	测试结果:[-9,9,9,-3,3]
         * 	期望结果:[0,0,9,0,0]
         */
        int nSize = nums.length;
        int[] answer = new int[nSize];
        int aSize = answer.length;

        // 计算nums所有元素的乘积和（不包括0）
        // 同时记录0元素的索引
        int sum = 1;
        int zeroCnt = 0;  // 记录0元素的个数
        for (int i=0; i<nSize; i++) {
            if (nums[i] != 0) sum *= nums[i];
            else zeroCnt++;
        }

        // 计算answer的值
        if (zeroCnt == 0) {  // 1.如果nums中没有0元素
            for (int i=0; i<aSize; i++) {
                answer[i] = sum/nums[i];
            }
        } else if (zeroCnt == 1) {  // 2.如果nums中只有一个0元素
            for (int i=0; i<aSize; i++) {
                // nums[i]=0，answer=sum
                if (nums[i] == 0) answer[i] = sum;
                // nums[i]!=0，0
                else answer[i] = 0;
            }
        } else {  // 3.如果nums中有至少两个0元素
            for (int i=0; i<aSize; i++) {
                answer[i] = 0;
            }
        }
        return answer;
    }

    // 方法二：前缀积 + 后缀积  O(n) O(2n)
    public int[] method2(int[] nums) {
        int nSize = nums.length;
        // 1. 构造前缀积：从左到右，prefix[i]: nums[0..i) 前i个元素的乘积和，不包括当前nums[i]
        int[] prefix = new int[nSize+1];
        prefix[0] = 1;  // 前0个元素的乘积和为1
        for (int i=1; i<=nSize; i++) {
            prefix[i] = prefix[i-1] * nums[i-1];
        }
        // 2.构造后缀积：从右到左，suffix[i]: nums(i..n-1] 从i+1到末尾元素的乘积和，不包括当前nums[i]
        int[] suffix = new int[nSize];
        suffix[nSize-1] = 1;  // nums(n-1..n-1] = 1  不包含 nums[n-1]
        for (int i=nSize-2; i>=0; i--) {
            suffix[i] = suffix[i+1] * nums[i+1];
        }
        // 3.计算answer
        int[] answer = new int[nSize];
        for (int i=0; i<nSize; i++) {
            // nums[0..i) * nums(i..n-1]
            answer[i] = prefix[i] * suffix[i];
        }
        return answer;
    }

    // 方法三：临时前缀积 + 后缀积
    /**
     * 算法思路
     * 1. 由于题中的answer数组不算到空间复杂度中，所以可以先把后缀积存到answer中
     * 2. 然后从左到右遍历nums，更新nums[i]的临时前缀积，从而降低空间复杂度
     */
    public int[] method3(int[] nums) {
        int nSize = nums.length;
        int[] answer = new int[nSize];
        // 1.构造后缀积，存到answer中：从右到左，answer[i]: nums(i..n-1] 从i+1到末尾元素的乘积和，不包括当前nums[i]
        answer[nSize-1] = 1;  // nums(n-1..n-1] = 1  不包含 nums[n-1]
        for (int i=nSize-2; i>=0; i--) {
            answer[i] = answer[i+1] * nums[i+1];
        }
        // 2.边遍历nums，边构造临时前缀积
        int prefix = 1;  // 定义临时前缀积：prefix:  prefix[i]: nums[0..i) 前i个元素的乘积和，不包括当前nums[i]
        for (int i=0; i<nSize; i++) {
            // nums[0..i) * nums(i..n-1]
            answer[i] = prefix * answer[i];
            // 更新临时前缀积
            prefix = prefix * nums[i];
        }
        return answer;
    }

}
//leetcode submit region end(Prohibit modification and deletion)


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public void rotate(int[] nums, int k) {
        method3(nums, k);
    }

    // 方法一：创建新数组 + 数组翻倍取中间数组  O(n)  O(2n)
    public void method1(int[] nums, int k) {
        /**
         * 情况一：nums.length < k
         * 情况二：nums.length > k
         *      nums = [1,2,3,4,5,6,7]  nSize = 7
         *      doubleNums = [1,2,3,4,5,6, (7,1,2,3,4,5,6) ,7]  k = 1  [7-1, 2*7-1)
         *      doubleNums = [1,2,3,4,5, (6,7,1,2,3,4,5) ,6,7]  k = 2  [7-2, 2*7-2)
         */
        int nSize = nums.length;
        /** 由于k可能大于nSize，所以需要k%nSize取余，来更新k值，保证k<=Size */
        k = k % nSize;
        int[] doubleNums = new int[nSize*2];
        for (int i=0; i<nSize; i++) {
            doubleNums[i] = nums[i];
            doubleNums[i+nSize] = nums[i];
        }
        // 对nums重新赋值
        int nIndex = 0;
        for (int i=nSize-k; i<nSize*2-k; i++) {
            nums[nIndex++] = doubleNums[i];
        }
    }

    // 方法二：创建新数组 + 环状数组取余数 O(n) O(n)
    public void method2(int[] nums, int k) {
        /**
         * tmpNums = [1,2,3,4,5,6,7]  tSize = 7
         * 更新方法：new_i = (i+k) % tSize
         *      nums = [7,1,2,3,4,5,6]  k = 1
         *      nums = [6,7,1,2,3,4,5]  k = 2
         */
        int nSize = nums.length;
        k = k%nSize;
        int[] tmpNums = new int[nSize];
        int tSize = tmpNums.length;
        for (int i=0; i<nSize; i++) tmpNums[i] = nums[i];

        // 对nums重新赋值
        for (int i=0; i<tSize; i++) {
            // 索引更新方法：new_i = (i+k) % tSize
            nums[(i+k)%nSize] = tmpNums[i];
        }

    }

    // 方法三：翻转数组 O(n) O(1)
    public void method3(int[] nums, int k) {
        /**
         * nums = [1,2,3,4,5,6,7]  nSize = 7
         * 更新方法： k = 2
         *      1. 整个数组翻转
         *          [7,6,5,4,3,2,1]
         *      2. 从k处分割
         *          [7,6] [5,4,3,2,1]
         *      3. 各自翻转
         *          [6,7] [1,2,3,4,5]
         */
        int nSize = nums.length;
        k = k%nSize;
        reverse(nums, 0, nSize-1);
        reverse(nums, 0, k-1);
        reverse(nums, k, nSize-1);
    }

    // 翻转数组 O(n/2) O(1)
    public void reverse(int[] nums, int start, int end) {
        /** 注意：这里的start、end都表示的是索引 */
        while (start < end) {
            int tmp = nums[start];
            nums[start] = nums[end];
            nums[end] = tmp;
            start++;
            end--;
        }
    }

}
//leetcode submit region end(Prohibit modification and deletion)

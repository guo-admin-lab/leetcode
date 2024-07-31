
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int longestConsecutive(int[] nums) {
        return method3(nums);
    }

    // 方法一：nums = [1,3,4,3]  zeros = [0,1,0,2,1]
    // 缺点1：如果nums中有非常大的数，zeros的数组大小也会非常大，内存溢出
    // 缺点2：如果nums中有负数，例如nums=[0,-1]，那么【负数】无法在zeros中表示，因为数组的索引只能是正数
    public int method1(int[] nums) {
        if (nums.length == 0) return 0;
        int len = Arrays.stream(nums).max().getAsInt() + 1;
        // 1.初始化nums长度的全零数组zeros，并赋值为1
        int[] zeros = new int[len];
        // 2.遍历nums，在zeros对应位置上+1
        for (int num: nums) {
            zeros[num]++;
        }
        if (zeros.length == 1) return 1;
        // 3.遍历zeros，找到最长的连续不为零的序列长度
        int begin = -1;
        int end = -1;
        int maxLen = -1;
        int index = 0;
        // 3.1.过滤前置0
        while (zeros[index] == 0) index++;
        // 3.2.找最长子串长度
        while (index < len) {
            // 首先确定起始位置
            if (begin == -1) {
                if (zeros[index] != 0) {
                    begin = index;
                }
            } else { // 如果起始位置已经确定
                if (index == len - 1) { // 如果已经扫描到最后的位置了
                    if (zeros[index] == 0) end = index-1;
                    else end = index;
                    maxLen = Math.max(maxLen, end-begin+1);
                    begin = -1;
                    end = -1;
                } else {
                    if (zeros[index] == 0) {
                        end = index - 1;
                        maxLen = Math.max(maxLen, end-begin+1);
                        begin = -1;
                        end = -1;
                    }
                }
            }
            index++;
        }
        return maxLen;
    }

    // 方法二：排序 + 遍历 O(nlogn)
    public int method2(int[] numss) {
        if (numss.length == 0) return 0;
        // 1.排序+去重
//        Arrays.sort(nums);
        int[] nums = Arrays.stream(numss).sorted().distinct().toArray();
        if (nums.length == 1) return 1;
        // 2.遍历
        int lenNums = nums.length;
        int begin = -1;
        int maxLen = 0;
        int index = 1;
        while (index < lenNums) {
            if (begin == -1) {
                // [-8,-6,-3,-1,0,2,4,9]
                // 找到连续的数字之后，才去确定begin的值
                if (nums[index] == nums[index-1]+1) {
                    begin = index-1;
                }
                index++;
            } else {
                if (nums[index] != nums[index-1]+1) {
                    int end = index-1;
                    maxLen = Math.max(maxLen, end-begin+1);
                    begin = -1;
                }
                index++;
            }
        }
        // 如果begin=-1，代表begin并不表示索引，即说明最后的最长子序列长度为1
        if (begin == -1) return Math.max(maxLen, 1);
        // 处理end边界
        int end = index-1;
        maxLen = Math.max(maxLen, end-begin+1);
        return maxLen;
    }

    // 方法三：哈希 + 递增 O(2n)
    // 因为每个数最多遍历两次
    // {100,101,4,200,1,3,2}
    //   2   1  1  1  2 1 1
    // {1,2,3,4,5,6,7,8,9}
    //  2 2 2 2 2 2 2 2 2
    public int method3(int[] nums) {

        // 1.转化成哈希set，方便快速查找是否存在某个元素，顺便还能去重
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }

        // 2.寻找最大子序列长度
        int maxLen = 0;
        for (int num : numSet) {
            // 2.1.寻找连续子序列的第一个数字
            if (numSet.contains(num-1)){ // num=99时，如果set中还有98，则99不是连续子序列的起始数字
                continue;
            }

            // 2.2.找到连续子序列的起始数字，开始向上计算子序列的长度
            int curNum = num; // 编程习惯，为了防止改变源对象内容
            int subLen = 1;
            while (numSet.contains(curNum+1)) {
                curNum++;
                subLen++;
            }
            // 2.3.更新最长连续子序列长度
            maxLen = Math.max(maxLen, subLen);
        }

        // 3.返回结果
        return maxLen;

    }
}
//leetcode submit region end(Prohibit modification and deletion)


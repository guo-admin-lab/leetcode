
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public int firstMissingPositive(int[] nums) {
        return method3(nums);
    }

    // 方法一：排序 + 去重 + 遍历  O(nlogn)  O(1)
    public int method1(int[] nums) {
        // 1.排序 + 去重
        Arrays.sort(nums);
        int nSize = removeReNum(nums);
        // 2.寻找未出现的最小正整数
        int flagNum = 1;  // 设置最小正整数
        int index = 0;  // nums索引
        // 2.1.过滤非正整数
        while (index < nSize && nums[index] <= 0) {
            index++;
        }
        /**
         * 匹配原理
         * 1,2,3, 4  flagNum
         * 1,2,3, 5 ,6,7,9,10  nums[i]
         * */
        // 2.2.匹配最小正整数
        while (index < nSize) {
            if (nums[index++] != flagNum) break;
            flagNum++;
        }
        return flagNum;
    }

    // 去重函数（快慢指针），返回新数组的长度
    public int removeReNum(int[] nums) {
        // nums必须是有序数组
        if (nums.length <= 1) return nums.length;
        int slow = 0, fast = 0;
        while (fast < nums.length) {
            if (nums[fast] != nums[slow]) {
                slow++;
                nums[slow] = nums[fast];
            }
            fast++;
        }
        return slow+1;
    }

    // 方法二：哈希Set + 遍历  O(n) O(n)
    public int method2(int[] nums) {
        /**
         * 算法思路
         * 题目要求规定时间复杂度为O(n)，则不能排序；所以，考虑用哈希存储来加快查找速度
         * 1.通过set存储nums
         * 2.从flagNum=1开始递增遍历，看nums中是否存在flagNum
         * 3.当不存在num=flagNum时，则找到了缺失的最小正整数
         * */
        int nSize = nums.length;
        // 1.构造set集合
        Set<Integer> numSet = new HashSet<>();
        for (int n : nums) {
            if (n > 0) {
                numSet.add(n);
            }
        }
        // 2.从1开始遍历，找缺失的最小正整数
        int flagNum = 1;
        int maxNum = Integer.MAX_VALUE;
        for (int i=flagNum; i<=maxNum; i++) {
            if (!numSet.contains(i)) {
                return i;
            }
        }
        return maxNum;
    }

    // 方法三：数组视为哈希表  |  置换  O(n) O(1)
    public int method3() {
//        https://leetcode.cn/problems/first-missing-positive/solutions/304743/que-shi-de-di-yi-ge-zheng-shu-by-leetcode-solution/?envType=study-plan-v2&envId=top-100-liked
//        https://leetcode.cn/problems/first-missing-positive/solutions/7703/tong-pai-xu-python-dai-ma-by-liweiwei1419/?envType=study-plan-v2&envId=top-100-liked
        return 0;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

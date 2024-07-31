import java.util.*;

public class Test128 {

    public static void main(String[] args) {
        int[] nums = new int[]{0,3,7,2,5,8,4,6,0,1};
//        int[] nums = new int[]{100,4,200,1,3,2};
//        int[] nums = new int[]{0,-1};
//        int[] nums = new int[]{0,0};
//        int[] nums = new int[]{0,0,-1};
//        int[] nums = new int[]{-2,-3,-3,7,-3,0,5,0,-8,-4,-1,2};
//        int[] nums = new int[]{-6,-1,-1,9,-8,-6,-6,4,4,-3,-8,-1};

        Solution solution = new Solution();
        int i = solution.longestConsecutive(nums);
        System.out.println(i);
//        System.out.println(solution.res);
    }

}

class Solution {
    public int longestConsecutive(int[] numss) {
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
        // 如果begin=-1，代表begin并不是一个有效的索引，说明最后的最长子序列长度为1
        if (begin == -1) return Math.max(maxLen, 1);
        // 处理end边界
        int end = index-1;
        maxLen = Math.max(maxLen, end-begin+1);
        return maxLen;
    }
}

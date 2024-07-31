package Exam;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * 小美拿到了一个数组。她希望你求出所有区间众数之和。你能帮帮她吗？
 * 定义区间的众数为出现次数最多的那个数。如果有多个数出现次数最多，那么众数是其中最小的那个数。
 * 第一行输入一个正整数n，代表数组的大小。
 * 第二行输入n个正整数a_i，代表数组的元素。
 * 1\leq n \leq 200000
 * 1\leq a_i \leq 2
 *
 * 输出描述
 * 一个正整数，代表所有区间的众数之和。
 *
 * 示例 1
 * 收起
 *
 * 输入
 * 复制
 * 3
 * 2 1 2
 * 输出
 * 复制
 * 9
 * 说明
 * [2],[2,1,2],[2]的众数是 2。
 * [2,1],[1],[1,2]的众数是 1。
 * 因此答案是 9。
 */

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Test13 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n = in.nextInt();
        int[] arr = new int[n];
        for (int i=0; i<n; i++) {
            arr[i] = in.nextInt();
        }
        dfs(arr, 0);
        System.out.print(res);
    }

    static List<List<Integer>> res = new LinkedList<>();
    static LinkedList<Integer> track = new LinkedList<>();
    // 回溯遍历
    static void dfs(int[] nums, int start) {
        // 前序位置，每个节点的值都是一个子集
        res.add(new LinkedList<>(track));
        // 回溯
        /**
         2
         1   2
         2   2
         */
        for (int i=start; i<nums.length; i++) {  // 1
            // 剪枝
            if (start == 0 && i == nums.length-1) continue;
            track.addLast(nums[i]);  // 2
            dfs(nums, i+1);  // [2,1,2]
            track.removeLast();  // [2,1]
        }
    }
}

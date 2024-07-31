package Exam.HW;

import java.util.*;

// 构成指定长度字符串的个数

public class Test2 {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String[] inputStr = in.nextLine().split(" ");
        char[] chars = inputStr[0].toCharArray();
        targetCnt = Integer.parseInt(inputStr[1]);

        // 1.先给chars排序
        Arrays.sort(chars);

        // 2.回溯找到组合
        traceback(chars, 0);

        // 3.计算有多少种组合
        int sum = 1;
        for (int i=1; i<=targetCnt; i++) {
            sum *= i;
        }
        System.out.println(cnt*sum);

    }

    static List<List<Character>> res = new LinkedList<>();
    static LinkedList<Character> track = new LinkedList<>();
    static int cnt = 0;
    static int targetCnt;

    // 组合：有重复，不可复选（指定数量）
    static void traceback(char[] chars, int start) {

        // base case
        if (track.size() == targetCnt) {
            cnt++;
            return;
        }

        // 回溯选择
        for (int i=start; i<chars.length; i++) {
            // 剪枝逻辑
            if (i > 0 && chars[i] == chars[i-1]) continue;
            // 做选择
            track.addLast(chars[i]);
            // 回溯
            traceback(chars, i+1);
            // 撤销选择
            track.removeLast();
        }

    }

}

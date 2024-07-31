package Exam.HW;

import java.util.Scanner;

public class Test5 {

//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int targetNum = in.nextInt();
//        in.nextLine();
//        String inputStr = in.nextLine();
//        Test5 test5 = new Test5();
//        /**
//         * asdbuiodevauufgh
//         *
//         * 扩大窗口：
//         *      如果遇到第一个元音字母，
//         *          左指针 = start
//         *          continue寻找
//         *      如果遇到非元音字母，计数++
//         *          如果targetNum超限
//         *              如果当前窗口内包含元音字母（endNum != -1）
//         *                  while (当前字母不是元音字母) {
//         *                      移动左指针
//         *                  }
//         *                  // 执行到这里，当前字符一定是元音字母
//         *                  更新 左指针 = start = 当前位置
//         *              否则，
//         *                  start重置（目前还没有找到过元音字母）
//         *          否则，
//         *              continue寻找
//         *      如果遇到第二个元音字母（start != -1），
//         *          如果targetNum符合条件
//         *              更新最新的结果长度
//         *              更新start标志为当前位置
//         *              endNum = 0
//         *              continue寻找
//         *          如果targetNum未达标准
//         *              continue寻找
//         *
//         */
//        int left = -1, right = 0;
//        int errCnt = 0;
//        int endNum = 0;
//        int maxRes = 0;
//        char[] chars = inputStr.toCharArray();
//        while (right < chars.length) {
//            char cur = chars[right];
//            right++;  // 扩大窗口
//            // 如果遇到队首元音字母
//            if (left == -1 && test5.is(cur)) {
//                left = right;
//                continue;
//            }
//            // 如果遇到队尾元音字母候选人
//            if (left != -1 && test5.is(cur)) {
//                // 如果targetNum未达标准
//                if (errCnt < targetNum) {
//                    endNum++;
//                    continue;
//                }
//                // 如果targetNum符合标准数量
//                if (errCnt == targetNum) {
//                    maxRes = Math.max(maxRes, right-left);
//                    // 寻找新的队首元音字母
//                    if ()
//
//                }
//            }
//            // 如果遇到非元音字母
//            // 如果targetNum未超限，继续寻找
//            if (errCnt < targetNum) continue;
//            // 如果targetNum已经超限
//            // 如果当前窗口中包含结尾元音字母候选人
//            if (endNum != 0) {
//                // 找到第一个候选人
//                left++;  // 此时，left一定是队首元音字母
//                while (!test5.is(chars[left])) {
//                    left++;
//                }
//                // 执行到这里，left位置一定是元音字母
//                endNum--;
//            } else {
//                // 如果当前窗口中没有结尾元音字母候选人
//                left = -1; // 队首元音位置重置
//            }
//        }
//
//
//    }
//
//    public boolean is(char c) {
//        // todo 逻辑待实现
//        return true;
//    }

}

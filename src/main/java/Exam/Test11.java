package Exam;

import java.util.Scanner;


/**
 * 小美定义以下三种单词是合法的：
 * 1. 所有字母都是小写。例如：good
 * 2. 所有字母都是大写。例如：APP
 * 3. 第一个字母大写，后面所有字母都是小写。例如：Alice
 *
 * 现在小美拿到了一个单词，她每次操作可以修改任意一个字符的大小写。小美想知道最少操作几次可以使得单词变成合法的？
 */

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Test11 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str1 = in.nextLine();
        char[] chars = str1.toCharArray();
        // 1.判断第一个字母是否为大写
        boolean flag1 = false;
        int upperCnt = 0;
        int lowerCnt = 0;
        if (Character.isUpperCase(chars[0])) flag1 = true;

        if (flag1) {
            for (int i = 0; i < chars.length; i++) {
                char cur = chars[i];
                if (Character.isUpperCase(cur)) {
                    upperCnt++;
                } else {
                    lowerCnt++;
                }
            }
            // 当前首字母大写，如果lowerCnt=0，说明不需要修改
            if (upperCnt == 1) {
                System.out.print(0);
                return;
            } else {
                // 这里首字母是大写，并且其余的有大写，有小写
                // AcBBbcd
                int res = Math.min(upperCnt-1, lowerCnt);
                System.out.print(res);
                return;
            }
        } else {
            for (int i = 0; i < chars.length; i++) {
                char cur = chars[i];
                if (Character.isUpperCase(cur)) {
                    upperCnt++;
                } else {
                    lowerCnt++;
                }
            }
            // 这里的首字母不是大写
            // 如果全部是小写，那么只需要修改一次
            if (upperCnt == 0) {
                System.out.print(1);
                return;
            }
            // 否则
            int res = Math.min(upperCnt, lowerCnt);
            System.out.print(res);
            return;
        }

        /**
         for (int i=0; i<chars.length; i++) {
         char cur = chars[i];
         if (Character.isUpperCase(cur)) {
         upperCnt++;
         } else {
         lowerCnt++;
         }
         }
         int change1 = Math.min(upperCnt, lowerCnt);
         int firstFlag = chars.length - lowerCnt;
         if (!Character.isUpperCase(chars[0])) {
         firstFlag++;
         }
         System.out.print(Math.min(change1, firstFlag));
         */
    }
}

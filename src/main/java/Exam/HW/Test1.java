package Exam.HW;

import java.util.*;

// 字符串序列判定

public class Test1 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        char[] s = in.nextLine().toCharArray();
        char[] l = in.nextLine().toCharArray();
        // s是否为l的有效子串
        /**
         * l  a b c d e
         *        j
         * s  a c e
         *      i
         */
        // 1.从s串开始扫描，定位l串中的首字母
        int lastIndex = -1;
        int si = 0, li = 0;
        while (si < s.length && li < l.length) {
            boolean flag = false;
            while (li < l.length) {
                if (s[si] == l[li]) {
                    flag = true;
                    break;
                }
                li++;
            }
            // 如果li中找到了当前目标字符，则开始找下一个目标字符
            if (flag && li > lastIndex) {
                lastIndex = li;
                si++;
                li++;
            } else {
                // 如果当前l中没有找到目标字符，说明之后没有目标字符了
                System.out.println(lastIndex);
                return;
            }
        }
        System.out.println(lastIndex);
    }

}

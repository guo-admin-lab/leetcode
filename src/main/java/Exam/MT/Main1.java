package Exam.MT;
/**小美拿到了一个由正整数组成的数组，但其中有一些元素是未知的（用 0 来表示）。
        现在小美想知道，如果那些未知的元素在区间[l,r]范围内随机取值的话，数组所有元素之和的最小值和最大值分别是多少？
        共有q次询问。

        第一行输入两个正整数n,q，代表数组大小和询问次数。
        第二行输入n个整数a_i，其中如果输入的a_i为 0，那么说明a_i是未知的。
        接下来的q行，每行输入两个正整数 l,r，代表一次询问。
        1<=n,q<=10^5
        0<=ai<=10^9
        1<=l<=r<=10^9

        输出q行，每行输出两个正整数，代表所有元素之和的最小值和最大值。

注意：最终的sum较大，必须用long类型来存储

 */



import java.util.Scanner;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别

        // 正整数数组   未知元素=0
        // 未知元素在【l,r】区间随机取值，元素数组所有元素之和的最小值和最大值
        // 共有 q 次询问
        int arrSize = in.nextInt();
        int queryCnt = in.nextInt();
        // in.nextLine();
        long sum = 0;
        long zeroCnt = 0;
        for (int i = 0; i < arrSize; i++) {
            long tempNum = in.nextInt();
            if (tempNum == 0) zeroCnt++;
            else sum += tempNum;
        }
        // in.nextLine();
        // int[][] qarr = new int[queryCnt][2];
        for (int i = 0; i < queryCnt; i++) {
            // qarr[i][0] = in.nextInt();
            // qarr[i][1] = in.nextInt();
            long minNum = in.nextInt();
            long maxNum = in.nextInt();
            // 1.输出最小和
            long minSum = sum + zeroCnt * minNum;
            // 2.输出最大值
            long maxSum = sum + zeroCnt * maxNum;
            System.out.println(minSum + " " + maxSum);
            // in.nextLine();
        }

    }
}

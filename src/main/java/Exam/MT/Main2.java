package Exam.MT; /**
小美拿到了一个n*n的矩阵，其中每个元素是 0 或者 1。
        小美认为一个矩形区域是完美的，当且仅当该区域内 0 的数量恰好等于 1 的数量。
        现在，小美希望你回答有多少个i*i的完美矩形区域。你需要回答1<=i<=n的所有答案。

        第一行输入一个正整数n，代表矩阵大小。
        接下来的n行，每行输入一个长度为n的 01 串，用来表示矩阵。
        1<=n<= 200

        输出n行，第i行输出i*i的完美矩形区域的数量。

4
1010
0101
1100
0011

 注意：最终要用  前缀和   来优化时间复杂度


*/
import java.util.Scanner;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int arrSize = in.nextInt();
        in.nextLine();
        char[][] arr = new char[arrSize][arrSize];
        for (int i=0; i<arrSize; i++) {
            String temp = in.nextLine();
            arr[i] = temp.toCharArray();
        }

        // 提前记录(0,0) -> (x,y)矩阵中有多少个0，多少个1(前缀和)
        int[][][] pre = new int[arrSize+1][arrSize+1][2];
        /**
         * {0, 1, 1, 0},
         * {1, 0, 0, 1},
         * {1, 0, 0, 1},
         * {0, 1, 1, 0}
         */
        for (int i=1; i<=arrSize; i++) {
            for (int j=1; j<=arrSize; j++) {
                pre[i][j][0] = pre[i-1][j][0] + pre[i][j-1][0] - pre[i-1][j-1][0] + ((arr[i-1][j-1]=='0')?1:0);
                pre[i][j][1] = pre[i-1][j][1] + pre[i][j-1][1] - pre[i-1][j-1][1] + ((arr[i-1][j-1]=='1')?1:0);
            }
        }


        for (int size=1; size<=arrSize; size++) {
            int res = 0;
            for (int i=0; i<arrSize; i++) {
                for (int j=0; j<arrSize; j++) {
                    if (i+size>arrSize || j+size>arrSize) continue;
                    if (fun(pre, i, j, size)) res++;
                }
            }
            System.out.println(res);
        }

    }

    /**
     * {0, 1, 1, 0},
     * {1, 0, 0, 1},
     * {1, 0, 0, 1},
     * {0, 1, 1, 0}
     *
     * rowStart=1, colStart=1, size = 2
     * rowstart1 = 3, colstart1 = 3
     *
     *
     */
    public static boolean fun(int[][][] pre, int rowStart, int colStart, int size) {
        int oneCnt = pre[rowStart+size][colStart+size][1] - pre[rowStart+size][colStart][1] - pre[rowStart][colStart+size][1] + pre[rowStart][colStart][1];
        int zeroCnt = pre[rowStart+size][colStart+size][0] - pre[rowStart+size][colStart][0] - pre[rowStart][colStart+size][0] + pre[rowStart][colStart][0];
        if (oneCnt == 0 && zeroCnt == 0) return false;
        else return oneCnt == zeroCnt;
    }

    // 超时
    public static boolean funBak(char[][] arr, int rowStart, int colStart, int size) {
        if (rowStart+size > arr.length || colStart+size > arr.length) return false;
        int oneCnt = 0, zeroCnt = 0;

        for (int i=rowStart; i<rowStart+size; i++) {
            for (int j=colStart; j<colStart+size; j++) {
                if (arr[i][j] == '1') oneCnt++;
                else if (arr[i][j] == '0') zeroCnt++;
            }
        }

        if (oneCnt == 0 || zeroCnt == 0) return false;
        else return oneCnt == zeroCnt;
    }



}

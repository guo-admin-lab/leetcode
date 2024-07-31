package Exam.HW;

import java.util.*;

public class 寿司转盘 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] inputs = in.nextLine().split(" ");
        int[] price = new int[inputs.length];
        for (int i=0; i<inputs.length; i++) {
            price[i] = Integer.parseInt(inputs[i]);
        }

        int[] res = new int[inputs.length];
        for (int i=0; i<inputs.length; i++) {
            int sum = price[i];
            // 寻找下一个小于price[i]的值
            for (int j=i+1; j<2 * inputs.length; j++) {
                if (price[j%4] < price[i]) {
                    sum += price[j%4];
                    break;
                }
            }
            res[i] = sum;
        }
        for (int i=0; i<res.length; i++) {
            System.out.print(res[i] + " ");
        }
    }

}

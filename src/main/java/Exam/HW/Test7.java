package Exam.HW;

import java.util.*;
public class Test7 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        String s = sc.next();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == 'R') {
                list.add(arr[i]);
            }
        }
        Collections.sort(list);
        int j = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == 'R') {
                if (arr[i] != list.get(j)) {
                    System.out.println(-1);
                    return;
                }
                j++;
            }
        }
        System.out.println(list.size() - j);
    }
}

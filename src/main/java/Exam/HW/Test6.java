package Exam.HW;

import java.util.*;

public class Test6 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        in.nextLine();
        int[] arr = new int[n];
        for (int i=0; i<n; i++) {
            String s = in.nextLine();
            String[] s1 = s.split(" ");
            arr[i] = Integer.parseInt(s1[1]);
        }

        // 找分差
        int minNum = Integer.MAX_VALUE; // 记录最小分差
        LinkedHashMap<Integer, List<int[]>> res = new LinkedHashMap<>();
        for (int i=0; i<n; i++) {
            for (int j=i+1; j<n; j++) {
                // 计算分差
                int chaNum = Math.abs(arr[j] - arr[i]);
                // 更新最小分差
                minNum = Math.min(minNum, chaNum);
                // 加入map
                List<int[]> info = res.get(chaNum);
                if (info == null) {
                    LinkedList<int[]> list = new LinkedList<>();
                    list.addLast(new int[]{i+1, j+1});
                    res.put(chaNum, list);
                } else {
                    info.add(new int[]{i+1, j+1});
                }
            }
        }

        // 按顺序输出最小分差
        List<int[]> list = res.get(minNum);
        Collections.sort(list, (a1, a2) -> {
            return a1[0] - a2[0];
        });
        for (int i=0; i<list.size(); i++) {
            int[] ints = list.get(i);
            System.out.println(ints[0] + " " + ints[1]);
        }
    }

}

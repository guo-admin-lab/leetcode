package Exam.HW;

import java.util.*;

public class Test4 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        String[] qian2hou = input.split("@");
        LinkedHashMap<Character, Integer> qianMap = new LinkedHashMap<>();
        LinkedHashMap<Character, Integer> houMap = new LinkedHashMap<>();

        // 设置前map
        String[] qianTmp = qian2hou[0].split(",");
        for (String item : qianTmp) {
            String[] c2n = item.split(":");
            char c = c2n[0].charAt(0);
            int n = Integer.parseInt(c2n[1]);
            qianMap.put(c, n);
        }

        // 设置后map
        String[] houTmp = qian2hou[1].split(",");
        for (String item : houTmp) {
            String[] c2n = item.split(":");
            char c = c2n[0].charAt(0);
            int n = Integer.parseInt(c2n[1]);
            houMap.put(c, n);
        }

        // 计算差值
        for(Map.Entry<Character, Integer> entry : qianMap.entrySet()) {
            char c = entry.getKey();
            int n = entry.getValue();
            // 到后map中找是否已经使用过
            Integer usedNum = houMap.get(c);
            if (usedNum == null) continue;
            qianMap.put(c, n - usedNum);
        }

        // 输出结果
        StringBuilder res = new StringBuilder();
        for(Map.Entry<Character, Integer> entry : qianMap.entrySet()) {
            res.append(entry.getKey() + ":" + entry.getValue() + ",");
        }
        res.deleteCharAt(res.length()-1);
        System.out.println(res);
    }

}

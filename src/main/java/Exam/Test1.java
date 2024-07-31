package Exam;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import java.util.Map;
import java.util.HashMap;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Test1 {
    public static void main(String[] args) {
        String adshfa = "adshfa";
        String substring = adshfa.substring(5);
        System.out.println(substring);
        Scanner in = new Scanner(System.in);

        Map<String, List<String>> last162Filename = new HashMap<>();  // 每个后16位的名称，可能对应了多个源文件名
        Map<String, Integer> last162Num = new LinkedHashMap<>();

        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextLine()) { // 注意 while 处理多个 case

            String line = in.nextLine();
            String[] temp = line.split(" ");
            String path = temp[0];
            String rowNum = temp[1];
            String[] pathArr = path.split("\\\\");
            String filename = pathArr[pathArr.length-1];
            int filenameLen = filename.length();
            String filenameLast16;
            if (filenameLen > 16) {
                filenameLast16 = filename.substring(filenameLen-16, filenameLen) + " " + rowNum;
            } else {
                filenameLast16 = filename + " " + rowNum;
            }
            // 查看这16位文件名的完整文件名都有哪些
            List<String> origins = last162Filename.getOrDefault(filenameLast16, null);
            // 如果文件名不存在
            if (origins == null) {
                last162Filename.put(filenameLast16, new LinkedList<>(Arrays.asList(filename)));
                String key = "0-" + filenameLast16;
                last162Num.put(key, 1);
            } else {
                // 如果文件名已经存在，需要找到原文件名和当前文件名一样的索引
                int targetIndex = -1;
                for (int i=0; i<origins.size(); i++) {
                    if (origins.get(i).equals(filename)) {
                        targetIndex = i;
                        break;
                    }
                }
                // 如果从已有源文件名中没有找到，说明这是新的原文件名
                if (targetIndex == -1) {
                    origins.add(filename);
                    String key = origins.size() + "-" + filenameLast16;
                    last162Num.put(key, 1);
                } else {
                    // 如果从已有源文件名中找到了，说明是原来有，直接累加计数
                    String key = targetIndex + "-" + filenameLast16;
                    last162Num.put(key, last162Num.get(key)+1);
                }
            }
        }

        // 从map中解析最终的结果
        String[] res = new String[last162Num.size()];
        int index = 0;
        for (Map.Entry<String, Integer> entry : last162Num.entrySet()) {
            String key = entry.getKey(); // 序号_后16位_代码行数
            Integer value = entry.getValue(); // 出现次数
            // 将待排序的结果放入res中(去掉序号)
            String newKey = key.split("-")[key.split("-").length-1];
            res[index++] = newKey + " " + value;
        }
        // 数组排序
        Arrays.sort(res, (a,b) -> {
            int anum = Integer.parseInt(a.split(" ")[a.split(" ").length-1]);
            int bnum = Integer.parseInt(b.split(" ")[b.split(" ").length-1]);
            return bnum - anum;
        });
        // 输出结果
        if (res.length <= 8) {
            for (int i=0; i<res.length; i++) {
                System.out.println(res[i]);
            }
        } else {
            for (int i=0; i<8; i++) {
                System.out.println(res[i]);
            }
        }
    }
}

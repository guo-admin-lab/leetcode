import java.util.ArrayList;
import java.util.List;

public class Test118 {
}

class Solution118 {

    public List<List<Integer>> generate(int numRows) {
        return method2(numRows);
    }

    // 方法一：借用数组   O(n^2)  O(n^2)
    /** 思路
     1
     1 1
     1 2 1
     1 3 3 1
     1 4 6 4 1
     首先初始化边界为1，然后再依次填中间的值
     */
    public List<List<Integer>> method1(int numRows) {
        List<List<Integer>> res = new ArrayList<>();
        int[][] arr = new int[numRows][numRows];
        // 初始化矩阵边界
        for (int ri=0; ri<numRows; ri++) {
            arr[ri][0] = 1;  // 每行的起始元素
            arr[ri][ri] = 1;  // 每行的末尾元素
        }
        // 填充矩阵中间值（从第3行开始，ri=2）
        for (int ri=2; ri<numRows; ri++) {
            for (int ci=1; ci<ri; ci++) {
                arr[ri][ci] = arr[ri-1][ci-1] + arr[ri-1][ci];
            }
        }
        // 将数组变成list返回
        for (int ri=0; ri<numRows; ri++) {
            List<Integer> temp = new ArrayList<>();
            for (int ci=0; ci<=ri; ci++) temp.add(arr[ri][ci]);
            res.add(temp);
        }
        return res;
    }

    // 方法二：直接构造  O(n^2)  O(1)
    public List<List<Integer>> method2(int numRows) {
        List<List<Integer>> res = new ArrayList<>();
        for (int ri=0; ri<numRows; ri++) {
            List<Integer> temp = new ArrayList<>();
            for (int ci=0; ci<=ri; ci++) {
                if (ci == 0 || ri == ci) temp.add(1);
                else temp.add(res.get(ri-1).get(ci-1) + res.get(ri-1).get(ci));
            }
            res.add(temp);
        }
        return res;
    }
}

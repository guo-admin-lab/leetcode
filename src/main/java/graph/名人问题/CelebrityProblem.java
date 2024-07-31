package graph.名人问题;

import java.util.LinkedList;

/** 名人问题
 * https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-03a72/zhong-li-x-ec564/
 * */
public class CelebrityProblem {

    public static void main(String[] args) {
        int celebrity1 = findCelebrity3(4);
        System.out.println(celebrity1);
    }

    /** 由于需要频繁判断两个人是否认识，所以用【邻接矩阵】表示人与人的【认识关系】
     *  现在有 0,1,2,3 共4个人，其邻接矩阵表示如下:
     *      graph[i][j]==1：表示 i 认识 j
     * */
    static int[][] graph = new int[][]{
            {1,1,1,0},
            {1,1,1,1},
            {0,0,1,0},
            {0,0,1,1}
    };

    /** API:返回i是否认识j */
    static boolean knows(int i, int j) {
        return graph[i][j] == 1;
    }

    /** API:寻找名人，返回名人编号 */
    // 方法一：暴力寻找 O(n^2) O(1)
    /** 思路
     *  遍历每个人，看其是否符合名人条件
     *      1.不认识任何人
     *      2.其他人都认识自己
     */
    static int findCelebrity1(int n) {
        // 1.遍历每个候选人
        for (int cand=0; cand<n; cand++) {
            int other;
            for (other=0; other<n; other++) {
                if (cand == other) continue;  // 排除自己
                // 2.如果cand认识other 或 other不认识cand，那么cand就不可能是名人
                if (knows(cand, other) || !knows(other, cand)) break;
            }
            // 3.如果找遍了other，都符合条件，那么当前cand就是名人
            if (other == n) {
                return cand;
            }
        }
        // 4.没有一个人符合名人特性
        return -1;
    }

    // 方法二：排除法(排除不是名人) + 维护候选人队列(只存储候选人，从而缩小范围)  O(n) O(n)
    /** 优化思路：
     *  方法一中耗时的地方是，每个候选人cand都要用内层for循环判断这个cand是否符合名人条件
     *  名人的定义保证了：人群中最多只有一个名人
     *
     *  两个人的关系只有下面4种情况：
     *  cand1 认识 cand2； cand2 不认识 cand1   排除cand1，cand2可能是名人
     *  cand1 不认识 cand2； cand2 认识 cand1   cand1可能是名人，排除cand2
     *  cand1 认识 cand2； cand2 认识 cand1    排除cand1，排除cand2
     *  cand1 不认识 cand2； cand2 不认识 cand1  排除cand1，排除cand2
     *
     */
    static int findCelebrity2(int n) {
        if (n == 1) return 0;
        // 1.将所有候选人装进队列
        LinkedList<Integer> q = new LinkedList<>();
        for (int i=0; i<n; i++) q.addLast(i);
        // 2.一直排除 不可能是名人 的候选人, 只保留 可能是名人 的候选人
        while (q.size() > 2) {
            // 2.1.每次取出两个候选人
            int cand1 = q.removeFirst();
            int cand2 = q.removeFirst();
            // 2.2.根据条件，只保留可能是名人的候选人
            if (knows(cand1, cand2) && !knows(cand2, cand1)) {
                // cand2可能是名人
                q.addLast(cand2);
            } else if (knows(cand2, cand1) && !knows(cand1, cand2)) {
                // cand1可能是名人
                q.addLast(cand1);
            } else {
                // cand1和cand2都不可能是名人
            }
        }
        // 3.如果队列中没有候选人，说明没有名人
        if (q.size() == 0) return -1;
        // 4.如果队列中还剩1人，需要判断此人是否为名人
        int cand = q.removeFirst();
        for (int other=0; other<n; other++) {
            if (other == cand) continue;
            if (knows(cand, other) || !knows(other, cand)) return -1;
        }
        // 5.cand是名人
        return cand;
    }

    // 方法三：排除法 + 维护临时候选人变量 O(n) O(1)
    /** 优化思路：
     *  由于上面的队列中最终只需要维护一个元素，所以可以用更新变量来替代队列维护
     *  方法二中，每次比较只涉及到两个元素的比较，所以可以优化
     */
    static int findCelebrity3(int n) {
        if (n == 1) return 0;
        // 1.设置候选人变量
        int cand = 0;
        // 2.遍历其它人，不断更新候选人变量
        for (int other=1; other<n; other++) {
            // 2.1.根据条件，将可能是名人的候选人，赋值给cand
            if (knows(cand, other) && !knows(other, cand)) {
                // other 可能是名人
                cand = other;
            } else if (knows(other, cand) && !knows(cand, other)) {
                // cand 仍然可能是名人，不需要更新
            } else {
                // cand 和 other都不可能是名人
                // 但为了之后还能比较，这里可以cand让其保留不是名人的候选人
            }
        }
        // 3.此时cand不能保证一定是名人，需要判断
        for (int other=0; other<n; other++) {
            if (other == cand) continue;
            if (knows(cand, other) || !knows(other, cand)) return -1;
        }
        // 4.cand是名人
        return cand;
    }
}

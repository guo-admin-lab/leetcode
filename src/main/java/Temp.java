import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Temp {

    static class Node {
        int x;
        Node (int x) {
            this.x = x;
        }
    }

    public static void main(String[] args) {


        PriorityQueue<Node> que = new PriorityQueue<>((a,b)->{
            return a.x - b.x;
        });
        Node node1 = new Node(4);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        que.offer(node1);
        que.offer(node2);
        que.offer(node3);
        que.remove(node1);
        node1.x = 1;
        que.offer(node1);
        for (Node node : que) {
            System.out.println(node.x);
        }
    }

}


//https://leetcode.cn/problems/two-sum/?envType=study-plan-v2&envId=top-100-liked
//https://labuladong.github.io/algo/di-ling-zh-bfe1b/yi-ge-fang-894da/
class Solution1 {
    public int[] twoSum(int[] nums, int target) {
        return method2(nums, target);
    }

    // 方法一：双重遍历暴露求解 O(n^2)
    public int[] method1(int[] nums, int target) {
        for (int i=0; i<nums.length; i++) {
            for (int j=i+1; j<nums.length; j++) {
                if (nums[i] + nums[j] == target){
                    return new int[]{i,j};
                }
            }
        }
        return new int[0];
    }

    /** 分析方法一的缺点：
     * 查找target-i的时间复杂度=O(n)，如何降低查找的时间复杂度呢？
     * 可以使用哈希set或者哈希map，由于题中要求返回索引值，所以需要使用map来存储 <值，索引>
     */
    // 方法二：哈希表 O(n)
    // https://leetcode.cn/problems/two-sum/solutions/434597/liang-shu-zhi-he-by-leetcode-solution/?envType=study-plan-v2&envId=top-100-liked
    public int[] method2(int[] nums, int target) {
        // 注意：因为nums中可能有相同的num，所以不能提前构造，因为map的key是唯一的。需要【一边遍历】【一边构造】
        Map<Integer, Integer> num2index = new HashMap<>();
        for (int i=0; i<nums.length; i++) {
            if (num2index.containsKey(target-nums[i])) {
                return new int[]{num2index.get(target-nums[i]), i};
            }
            num2index.put(nums[i], i);
        }
        return new int[0];
    }
}

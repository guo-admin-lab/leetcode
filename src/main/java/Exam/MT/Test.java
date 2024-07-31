//package Exam.MT;
//import java.util.Deque;
//import java.util.LinkedList;
//import java.util.Scanner;
//
//public class Test {
//    public static void main(String[] args) {
//
//        Deque<String> que = new LinkedList<>();
//        boolean a1 = que.remove('a');
//        String s = que.removeFirst();
//
//
//        Scanner scanner = new Scanner(System.in);
//
//        // 输入 n 和 k
//        int n = scanner.nextInt();
//        int k = scanner.nextInt();
//
//        // 初始化数组
//        int[] a2 = new int[n];
//        int[] a5 = new int[n];
//
//        // 输入数组 a
//        int[] a = new int[n];
//        for (int i = 0; i < n; i++) {
//            a[i] = scanner.nextInt();
//
//            // 计算 a2 和 a5
//            while (a[i] % 2 == 0) {
//                a[i] /= 2;
//                a2[i]++;
//            }
//            while (a[i] % 5 == 0) {
//                a[i] /= 5;
//                a5[i]++;
//            }
//        }
//
//        // 计算 cnt2 和 cnt5
//        int cnt2 = 0, cnt5 = 0;
//        for (int i = 0; i < n; i++) {
//            cnt2 += a2[i];
//            cnt5 += a5[i];
//        }
//
//        int left = 0;
//        int ans = 0;
//
//        for (int right = 0; right < n; right++) {
//            cnt2 -= a2[right];
//            cnt5 -= a5[right];
//
//            while (left <= right && Math.min(cnt2, cnt5) < k) {
//                cnt2 += a2[left];
//                cnt5 += a5[left];
//                left++;
//            }
//
//            ans += right - left + 1;
//        }
//
//        System.out.println(ans);
//    }
//}
//
//class LRUCache {
//
//    Map<Integer, Node> map;
//    Deque<Node> que;
//    int cap;
//
//    public LRUCache(int capacity) {
//        map = new HashMap<>();
//        que = new LinkedList<>();
//        cap = capacity;
//    }
//
//    // 这一步需要快速找到key所在位置，需要使用HashMap
//    public int get(int key) {
//        if (!map.containsKey(key)) return -1;
//        // 将key数据提升为最近使用的
//        makeRecently(key);
//        return map.get(key).value;
//    }
//
//    // 将元素插入队列最后
//    public void put(int key, int value) {
//        // 1.如果key已经存在了，替换value，并将节点提升为最近使用的
//        if (map.containsKey(key)) {
//            // 将数据提升为最近使用的
//            makeRecently(key);
//        }
//        // 2.
//    }
//
//    // 将key提升为最近使用的
//    // 方法一：<最近使用> 用【时间】标识，这样需要去遍历链表比较时间，找出最新值  O(n)
//    // 方法二：<最近使用> 用【链表天然插入顺序】标识，先删除，再插入，最新值就是链表队尾节点  O(1)
//    void makeRecently(int key) {
//        if (!map.containsKey(key)) return;
//        // 根据key找到节点
//        Node node = map.get(key);
//        // 删除节点
//        que.removeLast(node);
//        // 插入节点到队尾
//        que.addLast(node);
//        // 添加key和node的映射关系
//        map.put(key, node);
//    }
//}
//
//class Node {
//    int key;
//    int value;
//    Node(int key, int value) {
//        this.key = key;
//        this.value = value;
//    }
//}
//
///**
// * Your LRUCache object will be instantiated and called as such:
// * LRUCache obj = new LRUCache(capacity);
// * int param_1 = obj.get(key);
// * obj.put(key,value);
// */
//

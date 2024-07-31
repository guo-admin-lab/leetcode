public class Test2 {
}

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution2 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        return method1(l1, l2);
    }

    // 方法一：O(max(m,n)) O(1)
    /** 思路：
         2 -> 4 -> 3
         5 -> 6 -> 7 -> 2
         初始化进位 = 0
         初始化虚拟节点dummy, p = dummy
         while (l1和l2都不等于null，因为要对两个节点的数求和) {
             第一步：
                 进位 = (l1.val + l2.val + 进位) / 10
                 当前位 = (l1.val + l2.val + 进位) % 10
             第二步：
                 创建新节点加入新链表，p指针后移
                 p.next = new ListNode(当前位)
                 p = p.next
             第三步：
                 l1前进
                 l2前进
         }
         if (l1 == null) {
             while (l2 != null) {
                 进位 = (l2.val + 进位) / 10
                 当前位 = (l2.val + 进位) % 10

                 p.next = new ListNode(当前位)
                 p = p.next

                 l2 = l2.next
             }
         }
         if (l2 == null) {
            // 同上
         }
     */
    public ListNode method1(ListNode l1, ListNode l2) {
        int c = 0; // 进位
        int n = 0; // 当前位
        ListNode dummy = new ListNode(-1), p = dummy;
        ListNode p1 = l1, p2 = l2;
        while (p1 != null && p2 != null) {
            n = (p1.val + p2.val + c) % 10;
            c = (p1.val + p2.val + c) / 10;
            p.next = new ListNode(n);
            p = p.next;
            p1 = p1.next;
            p2 = p2.next;
        }
        if (p1 == null) {
            while (p2 != null) {
                n = (p2.val + c) % 10;
                c = (p2.val + c) / 10;
                p.next = new ListNode(n);
                p = p.next;
                p2 = p2.next;
            }
        }
        if (p2 == null) {
            while (p1 != null) {
                n = (p1.val + c) % 10;
                c = (p1.val + c) / 10;
                p.next = new ListNode(n);
                p = p.next;
                p1 = p1.next;
            }
        }
        // 如果最后进位是1，则需要再多添加一个节点
        /**
             输入
             [9,9,9,9,9,9,9]
             [9,9,9,9]
             输出
             [8,9,9,9,0,0,0,1]
         */
        if (c == 1) p.next = new ListNode(1);
        return dummy.next;
    }
}

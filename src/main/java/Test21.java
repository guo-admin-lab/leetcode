public class Test21 {
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
class Solution21 {

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        return method1(list1, list2);
    }

    // 方法一：迭代   O(n|m) O(1)
    /** 思路
     *  同时遍历 list1，list2
     如果 list1 == null 或 list2 == null 直接跳出
     */
    public ListNode method1(ListNode list1, ListNode list2) {
        ListNode p1 = list1, p2 = list2;
        ListNode dummy = new ListNode(-1);
        ListNode p = dummy;
        while (p1 != null && p2 != null) {
            if (p1.val <= p2.val) {
                p.next = p1;
                p1 = p1.next;
            } else {
                p.next = p2;
                p2 = p2.next;
            }
            p = p.next;
        }
        if (p1 == null) {
            p.next = p2;
        }
        if (p2 == null) {
            p.next = p1;
        }
        return dummy.next;
    }


}

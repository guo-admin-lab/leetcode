
//leetcode submit region begin(Prohibit modification and deletion)
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
class Solution {

    public ListNode oddEvenList(ListNode head) {
        return method1(head);
    }

    // 用两个虚拟链表将原有链表分别串到自己链条上，不需要额外空间
    public ListNode method1(ListNode head) {
        ListNode dummy1 = new ListNode(-1);
        ListNode dummy2 = new ListNode(-1);
        int cnt = 1;
        ListNode p = head, p1 = dummy1, p2 = dummy2;
        while (p != null) {
            if (cnt % 2 == 1) {
                p1.next = p;
                p1 = p1.next;
            } else {
                p2.next = p;
                p2 = p2.next;
            }
            p = p.next;
            cnt++;
        }
        p1.next = dummy2.next;
        p2.next = null;  // 如果结点是奇数的话，最后p2.next会指向p1，必须得断开，不然会形成圈
        return dummy1.next;
    }

    // 力扣官方更简洁的写法
    public ListNode oddEvenList(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode evenHead = head.next;
        ListNode odd = head, even = evenHead;
        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

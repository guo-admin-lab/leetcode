
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

    public ListNode swapPairs(ListNode head) {
        return method2(head);
    }

    // 方法一：迭代法 + 虚拟头节点 + pre/cur/nxt   O(n)  O(1)
    /** 思路
     * 前置条件：head至少有两个节点
     *  s = head, f = head.next;
     *  1 -> 2 -> 3 -> 4
     *  s    f
     *
     * 第一次循环：
     *  第1步：s.next = f.next;
     *  null -> 1 -> [3] -> 4
     *   p      s
     *          2 -> [3] -> 4
     *          f
     *  第2步：f.next = s;
     *  null -> 1 -> 3 -> 4
     *   p      s
     *     2 -> 1
     *     f    s
     *  第3步：p.next = f
     *  null -> 2 -> 1 -> 3 -> 4
     *   p      f    s
     *  第4步：f和s交换指针位置
     *  null -> 2 -> 1 -> 3 -> 4
     *   p      s    f
     *  第5步：p\s\f各向前走两步
     *  null -> 2 -> 1 -> 3 -> 4
     *               p    s    f
     * 第二次循环后：
     *  第1步：s.next = f.next
     *  2 -> 1 -> 3 -> [null]
     *       p    s
     *            4 -> [null]
     *            f
     *  第2步：f.next = s
     *  2 -> 1 -> [3] -> null
     *       p    s
     *       4 -> [3]
     *       f    s
     *  第3步：p.next = f
     *  2 -> 1 -> 4 -> 3 -> null
     *       p    f    s
     *  第4步：f和s交换位置
     *  2 -> 1 -> 4 -> 3 -> null
     *       p    s    f
     *  第5步：p\s\f各向前两步
     *  2 -> 1 -> 4 -> 3 -> null
     *                 p    s    f   这里f前进两步时应该注意空指针异常，当s==null时，
     *
     *  循环条件：
     *  偶数情况：s != null  （注意这里f前进两步时应该注意空指针异常，当s==null时，f不在前进两步了）
     *  2 -> 1 -> 4 -> 3 -> null
     *                 p    s    f
     *  奇数情况：s.next != null
     *  2 -> 1 -> 4 -> 3 -> 5
     *                 p    s    f
     * */
    public ListNode method1(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode pre = dummy, cur = head, nxt = head.next;
        while (cur != null && cur.next != null) {
            cur.next = nxt.next;  // 1
            nxt.next = cur;  // 2
            pre.next = nxt; // 3
            // 4
            ListNode tmp = cur;
            cur = nxt;
            nxt = tmp;
            // 5
            pre = pre.next.next;
            cur = cur.next.next;
            if (cur != null) nxt = nxt.next.next;
        }
        return dummy.next;
    }

    // 方法二：递归  O(n)  O(n)
    /** 思路
         递归核心：类似于压栈，先进后处理，后进先处理
         递归定义：后面的链表节点已经实现了两两交换  后面链表的头节点=fun(当前节点指针，交换长度)
         最外层：
             1->2->3->4->5->6
             p1 p2 nk
             nxtk = head.next.next;
             new_head = fun(nxtk, 2)
             4->[3]->6->5
             nh  nk
             1->2->[3]
             p1 p2  nk

             第1步：p1.next = nh;
             第2步：p2.next = p1;
             return p2;
         负二层-偶数情况：
            5->6   null
            p1 p2  nk
            fun(nk, 2)
                最里层：if (head == null) return head;
         负二层-奇数情况：等价于最里层
            p1 = head, p2 = head.next;
            5   null
            p1   p2    if(head.next == null) return head;
     */
    public ListNode method2(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode p1 = head, p2 = head.next;
        ListNode nxtk = head.next.next;  //! 这里可以根据反转的长度来找到第k个节点
        ListNode newHead = method2(nxtk);
        p1.next = newHead;
        p2.next = p1;
        return p2;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

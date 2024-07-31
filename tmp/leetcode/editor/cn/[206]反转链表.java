
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

    public ListNode reverseList(ListNode head) {
        return method2(head);
    }

    // 方法一：迭代法  O(n)  O(1)
    public ListNode method1(ListNode head) {
        /** 思路
         *  null     1   ->   2 -> 3 -> 4 -> 5
         *  pre  cur|nxt
         *  第1步：nxt后移
         *  null     1   ->   2 -> 3 -> 4 -> 5
         *  pre     cur      nxt
         *  第2步：cur.next 指向 pre
         *  null <-  1        2 -> 3 -> 4 -> 5
         *  pre     cur      nxt
         *  第3步：pre 指向 cur
         *  null <-  1        2 -> 3 -> 4 -> 5
         *        pre|cur    nxt
         *  第4步：cur后移， 指向 nxt
         *  null <-  1        2 -> 3 -> 4 -> 5
         *          pre    cur|nxt
         *  循环条件：nxt != null
         * */
        ListNode pre = null, cur = head, nxt = head;
        while (nxt != null) {
            nxt = nxt.next;
            cur.next = pre;
            pre = cur;
            cur = nxt;
        }
        return pre;
    }

    // 方法二：递归法  O(n)  O(n)主要是递归的层数
    public ListNode method2(ListNode head) {
        /** 思路
         *  1 -> 2 -> 3 -> 4 -> 5
         *  定义递归含义：
         *      last = fun(head.next)
         *      将head及其之后的链表全部反转，然后返回新链表的头节点(即反转链表之后的节点)
         *  递归最外层：
         *      last(5) = fun(head.next(1.next<->2))
         *      1 -> 2 <- 3 <- 4 <- 5
         *      第一步：1.next.next = 1 等价于 2.next = 1
         *      第二步：1.next = null
         *      1 <- 2 <- 3 <- 4 <- 5
         *      最终返回：last(5)
         * */
        /** 递归分析
         *  1 -> 2 -> 3
         *  head = 1
         *  3 = method2(2)  1 -> 2 <- 3
         *  1.next: 2
         *  1.next.next <-> 2.next
         *  1.next.next = 1  <-> 2.next = 1    1 <-> 2 <- 3
         *  1.next = null   1 <- 2 <- 3
         *  return 3
         */
        // base case
        if (head == null || head.next == null) return head;
        ListNode last = method2(head.next);
        head.next.next = head;
        head.next = null;
        return last;
    }


}
//leetcode submit region end(Prohibit modification and deletion)

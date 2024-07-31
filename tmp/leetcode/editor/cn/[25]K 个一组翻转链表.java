
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

    public ListNode reverseKGroup(ListNode head, int k) {
        return method2(head, k);
    }

    // 方法一：迭代  O(n) O(1)
    /** 思路
     * 初始定义 ph代表上一个反转后链表的尾节点，用于之后连接断开的链表
     *  dm      1 -> 2 -> 3 -> 4 -> 5 -> 6  k = 2
     *   pl     c         nx (需要事先定位到n指针的位置，不然反转后就断开无法定位了，顺便可以看一下链表长度是否小于k)
     *
     * 第1次反转后链表
     *  dm      1 <- 2    3 -> 4 -> 5 -> 6
     *   pl     c    nh   nx
     * 连接链表：pl.next = nh
     *  dm ->   2 -> 1    3 -> 4 -> 5 -> 6
     *   pl     nh   c    nx
     * 更新pl，指向c；更新c, 指向nx；更新nx，往后移动k步；
     *          2 -> 1    3 -> 4 -> 5 -> 6
     *          nh   pl   c         nx
     *
     * 第2次反转后链表
     *  dm ->   2 -> 1    3 <- 4    5 -> 6
     *               pl   c    nh   nx
     * 连接链表：pl.next = nh
     *  dm ->   2 -> 1 -> 4 -> 3    5 -> 6
     *               pl   nh   c    nx
     * 更新pl，指向c；更新c, 指向nx；更新nx，往后移动k步；
     *  dm ->   2 -> 1 -> 4 -> 3    5 -> 6 -> null
     *                         pl   c         nx
     *
     * 循环结束条件：
     *  当 剩余链表长度 > k时，按照上述的步骤进行反转，循环结束后得到：
     *  dm ->   2 -> 1 -> 4 -> 3 -> 6 -> 5    null
     *                                   pl    c
     *
     *  当 剩余链表长度 < k时，跳出循环，得到如下图所示的链表结构，循环结束后得到：
     *  dm ->   [2] -> 1 -> 4 -> 3    [5] -> null
     *                           pl   c          nx  注意：这里nx的更新需要进行判空处理
     *  此时需要再次连接链表：pl.next = c;
     *  return dm.next;
     * */
    public ListNode method1(ListNode head, int k) {
        // 1.初始定义
        ListNode dummy = new ListNode(-1);
        ListNode preLast = dummy, cur = head, nxt = head;
        int tk = k;
        while (tk > 0) {
            if (nxt == null) break;
            nxt = nxt.next;
            tk--;
        }
        if (tk > 0) return head;  // 如果tk没有减到0，说明链表长度 < k
        // 2.反转链表
        boolean flag = true;  // true: 剩余链表长度>k
        while (flag) {
            // 反转链表
            ListNode newHead = reverse(cur, k);
            // 链接前面的链表
            preLast.next = newHead;
            // 更新pl
            preLast = cur;
            // 更新cur，直接指向nxt
            cur = nxt;
            // nxt，往后移动k步
            tk = k;
            while (tk > 0) {
                if (nxt == null) {
                    flag = false;
                    break;
                }
                nxt = nxt.next;
                tk--;
            }
        }
        // 此时 flag == false
        // 3.如果最后一段链表长度<k了，表现为cur!=null需要特殊处理
        if (cur != null) preLast.next = cur;
        return dummy.next;
    }

    // 迭代反转k个节点，返回反转后的节点
    public ListNode reverse(ListNode head, int k) {
        //! 前置条件：链表长度 < k
        /**
         * null    1 -> 2 -> 3   k=2
         *  p      c|n
         * null <- 1    2 -> 3   k=1
         *         p    c|n
         * null <- 1 <- 2    3   k=0
         *              p    c|n
         * */
        ListNode pre = null, cur = head, nxt = head;
        while (k > 0) {
            nxt = nxt.next;
            cur.next = pre;
            pre = cur;
            cur = nxt;
            k--;
        }
        return pre;
    }

    // 方法二：递归  O(n)  O(n)
    /** 思路
         递归核心：类似于压栈，先进后处理，后进先处理
         递归定义：后面的链表节点已经实现了两两交换  后面链表的头节点=fun(当前节点指针，交换长度)
         最外层：
            1 初始化定义
             1->2->3->4->5->6
             c     nk
             nxtk = 向后移动k步 (!注意：如果剩余链表长度 < k，直接返回head，不需要反转)
            2 递归获取后面处理好的新头节点
             new_head = fun(nxtk, k)
             4->[3]->6->5
             nh  nk
            3 反转当前的k个节点
             reverse_nh = reverse(c, k)
             [2]    -> 1
       [reverse_nh]    c
            4 连接链表
             c.next = nh
             return reverse_nh;
     */
    public ListNode method2(ListNode head, int k) {
        if (head == null || head.next == null) return head;
        // 1.初始化定义
        ListNode cur = head, nxtk = head;
        // 2.nxtk向后移动k步
        int tk = k;
        while (tk > 0) {
            if (nxtk == null) break;
            nxtk = nxtk.next;
            tk--;
        }
        if (tk > 0) return head;  // 如果tk没有减到0，说明链表长度 < k，不需要反转
        // 3.递归返回之后已经全部处理好的新的头节点
        ListNode newHead = method2(nxtk, k);
        // 4.迭代反转当前的k个节点
        ListNode reverseHead = reverse(cur, k);
        // 5.连接链表
        cur.next = newHead;
        return reverseHead;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

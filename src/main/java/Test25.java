

public class Test25 {

    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;

        Solution25 solution25 = new Solution25();
        ListNode node = solution25.reverseKGroup(n1, 3);
        System.out.println(node.val);

    }

}

class Solution25 {

    public ListNode reverseKGroup(ListNode head, int k) {
        return method2(head, k);
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


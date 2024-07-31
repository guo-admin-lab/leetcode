public class Test19 {
}
class ListNode {
    int val;
    ListNode next;
     ListNode() {}
    ListNode(int val) { this.val = val; }
     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Solution19 {

    public ListNode removeNthFromEnd(ListNode head, int n) {
        return method2(head, n);
    }

    // 方法一: 遍历两次
    /** 思路
         1.遍历链表，计算链表长度len
         2.根据len-n，找到要删除节点的位置
         3.删除节点，返回头结点
         时间复杂度：O(2n)
         空间复杂度：O(1)
     */
    public ListNode method1(ListNode head, int n) {
        return null;
    }

    // 方法二：快慢指针 + 遍历一次  O(n) O(1)
    /** 思路
         核心：设置d0虚拟头结点，因为要考虑到删除head节点时的空指针异常
         第一步：f指针先走n步
             d0 -> 1 -> 2 -> 3    n = 2
             s          f
         第二步：s和f指针每次都前进1步
             d0 -> 1 -> 2 -> 3    n = 2
                   s         f
         第三步：当f==null时，s指向倒数第n个节点
             d0 -> 1 -> 2 -> 3   null   n = 2
                        s         f
         总结：当f.next == null时，开始删除倒数第n个节点
             d0 -> 1 -> 2 -> 3   null   n = 2
                   s         f
     */
    public ListNode method2(ListNode head, int n) {
        // 边界情况前置处理(题干中说n>=1，也就是说至少删除1个节点)
        if (head == null || head.next == null) return null;

        /**核心：设置dummy虚拟头结点，因为要考虑到删除head节点时的空指针异常 */
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode slow = dummy, fast = dummy;
        // 1.f指针前进n步
        while (n-- > 0) {
            fast = fast.next;
        }
        // 2.s和f指针同时前进
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }
        // 3.当fast.next == null时，slow指向倒数第n+1个节点，可删除倒数第n个节点
        ListNode tmp = slow.next;
        slow.next = slow.next.next;
        tmp.next = null;
        return dummy.next;
    }
}

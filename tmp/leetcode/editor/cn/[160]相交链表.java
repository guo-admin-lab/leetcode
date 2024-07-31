
//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        return method2(headA, headB);
    }

    // 方法一：尾部对齐 + 依次比较  O(m+n) O(1)
    public ListNode method1(ListNode headA, ListNode headB) {
        // 1.计算两条链表的长度
        int aLen = getLengthOfListNode(headA);
        int bLen = getLengthOfListNode(headB);
        // 2.尾部对齐（较长的链表向前移动）
        ListNode pa = headA, pb = headB;
        if (aLen > bLen) {
            int moveCnt = aLen - bLen;
            while (moveCnt > 0) {
                pa = pa.next;
                moveCnt--;
            }
        } else if (aLen < bLen) {
            int moveCnt = bLen - aLen;
            while (moveCnt > 0) {
                pb = pb.next;
                moveCnt--;
            }
        }
        // 3.依次比较
        while (pa != null && pb != null) {
            if (pa == pb) return pa;
            pa = pa.next;
            pb = pb.next;
        }
        // 4.没有相交节点返回null
        return null;
    }

    public int getLengthOfListNode(ListNode head) {
        int len = 0;
        while (head != null) {
            head = head.next;
            len++;
        }
        return len;
    }

    // 方法二：哈希表 + 匹配地址值  O(m+n) O(n)
    public ListNode method2(ListNode headA, ListNode headB) {
        // 1.记录list_a中的所有节点
        Set<ListNode> set = new HashSet<>();
        ListNode pa = headA;
        while (pa != null) {
            set.add(pa);
            pa = pa.next;
        }
        // 2.遍历list_b，检查list_a中是否存在相同节点
        ListNode pb = headB;
        while (pb != null) {
            if (set.contains(pb)) return pb;
            pb = pb.next;
        }
        // 3.无相交节点
        return null;
    }

    // 方法三：方法一的高级版，没看懂代码
    // https://leetcode.cn/problems/intersection-of-two-linked-lists/solutions/10774/tu-jie-xiang-jiao-lian-biao-by-user7208t/?envType=study-plan-v2&envId=top-100-liked


}
//leetcode submit region end(Prohibit modification and deletion)

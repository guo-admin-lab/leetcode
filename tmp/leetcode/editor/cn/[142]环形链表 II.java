
//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {

    public ListNode detectCycle(ListNode head) {
        return method2(head);
    }

    // https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-8f30d/shuang-zhi-0f7cc/#%E5%88%A4%E6%96%AD%E9%93%BE%E8%A1%A8%E6%98%AF%E5%90%A6%E5%8C%85%E5%90%AB%E7%8E%AF
    // https://leetcode.cn/problems/linked-list-cycle-ii/solutions/441131/huan-xing-lian-biao-ii-by-leetcode-solution/?envType=study-plan-v2&envId=top-100-liked

    // 方法一：哈希记录  O(n) O(n)
    public ListNode method1(ListNode head) {
        if (head == null || head.next == null) return null;
        // 存储不需要保证顺序，因为遇到的第一个冲突节点就是相遇的起始节点
        Set<ListNode> set = new HashSet<>();
        ListNode p = head;
        while (p != null) {
            if (set.contains(p)) return p;
            set.add(p);
            p = p.next;
        }
        return null;
    }

    // 方法二：快慢指针
    /** 思路；
     * 1.环的起始点: a
     * 2.快慢指针的相遇点: b（慢指针走了k步，快指针走了2k步）
     * 3.相遇点 与 起始点 相差的距离: m = b - a
     * 4.head节点位置 到 起始点 距离：k - m
     * 5.相遇点 到 起始点 距离：k - m
     * */
    public ListNode method2(ListNode head) {
        if (head == null || head.next == null) return null;
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            // 如果快慢指针相遇了（不一定是在起始节点）
            // 相遇点：b
            if (slow == fast) break;
        }
        // fast走到尽头，说明没有相交节点
        if (fast == null || fast.next == null) return null;
        // 让满指针重新指向head位置
        slow = head;
        // head位置 和 相遇点位置 同时出发，再次相遇点即为起始环位置
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

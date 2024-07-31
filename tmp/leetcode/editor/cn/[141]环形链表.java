
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
    public boolean hasCycle(ListNode head) {
        return method2(head);
    }

    // 双指针秒杀7道链表题目：https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-8f30d/shuang-zhi-0f7cc/
    // 力扣：https://leetcode.cn/problems/linked-list-cycle/solutions/440042/huan-xing-lian-biao-by-leetcode-solution/?envType=study-plan-v2&envId=top-100-liked

    // 方法一：哈希存储节点 + 键相同匹配  O(n)  O(n)
    public boolean method1(ListNode head) {
        if (head == null || head.next == null) return false;
        Set<ListNode> set = new HashSet<>();
        ListNode p = head;
        while (p != null) {
            // 如果遍历过程中已经被添加过了，说明有环
            if (set.contains(p)) return true;
            set.add(p);
            p = p.next;
        }
        return false;
    }

    // 方法二：快慢指针  O(n) O(1)
    public boolean method2(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            // 如果快慢指针相遇，说明有环
            if (slow == fast) return true;
        }
        return false;
    }

}
//leetcode submit region end(Prohibit modification and deletion)


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

    public boolean isPalindrome(ListNode head) {
        return method3(head);
    }

    /**
     * 判断回文链表：https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-8f30d/ru-he-pan--f9d3c/
     * 反转单链表：https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-8f30d/di-gui-mo--10b77/
     * 双指针秒杀7道链表题：https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-8f30d/shuang-zhi-0f7cc/
     */

    // 方法一：分别存储前后两段链表，依次判断是否相等  O(n)  O(n/2)  只遍历一次
    public boolean method1(ListNode head) {
        if (head == null) return false;
        List<ListNode> beforeNodes = new ArrayList<>();

        // 1.找到链表的中点节点（顺便存储前半段链表节点） —— 快慢指针
        ListNode slow = head, fast = head;
        /**
         * 情况一：奇数
         *  1  ->  2  ->  3
         * s|f
         *  1  ->  2  ->  3
         *         s      f   当f.next=null时，s即为中点
         *
         * 情况二：偶数
         *  1  ->  2  ->  3  ->  4
         * s|f
         *  1  ->  2  ->  3  ->  4
         *         s      f   当f.next.next=null时，s即为中点
         *  1  ->  2  ->  3  ->  4    null
         *                s           f
         * */
        while (fast.next != null && fast.next.next != null) {
            beforeNodes.add(slow);
            slow = slow.next;
            fast = fast.next.next;
        }
        // 判断 奇数|偶数，来决定是否将中点添加到集合中
        if (fast.next != null) { // 说明是偶数情况，需要将中点添加进来
            beforeNodes.add(slow);
        }
        slow = slow.next;  // slow往后移动一个
        int index = beforeNodes.size() - 1;
        while (slow != null) {
            if (slow.val != beforeNodes.get(index).val) return false;
            index--;
            slow = slow.next;
        }
        return true;
    }

    // 方法二：递归 + 前序后序遍历  O(n) O(n)  递归n层，空间复杂度O(n)
    /** 思路：
     *
     * 当使用递归方法压栈到最后时，即后序遍历到末尾节点right时，
     *      开始出栈并依次和left比较
     * l
     * 1 2 3 4
     *       r
     * 递归函数定义：
     *      ?不太会写?
     * */
    ListNode left;
    public boolean method2(ListNode head) {
        left = head;
        return method2_traverse(head);
    }

    public boolean method2_traverse(ListNode right) {
        if (right == null) return true;
        boolean res = method2_traverse(right.next);
        // 后序，获取到末尾节点right
        res = res && (right.val == left.val);
        left = left.next;
        return res;  // 返回结果
    }

    // 方法三：左右指针 + 反转后半段链表  O(n) O(1)
    // 缺点：会修改原链表的结构
    // 解决方法：（所以更好的解决方法是在return之前恢复一下链表的顺序结构）
    /**! 由于是单向链表，不能从right向左访问，所以需要事先将后半段链表反转*/
    public boolean method3(ListNode head) {
        // 1.找到中点，并从下一个节点开始反转链表
        /**
         * 1 2 3
         *   s f  f.next=null
         * 1 2 3 4
         *   s f  f.next.next=null
         * */
        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // 从slow的后一个节点开始反转链表，此时slow指向中点处
        int flag = 1;  // 1：奇数  0：偶数
        ListNode pre = slow, cur = slow.next, nxt = slow.next;  // 默认奇数时的情况
        if (fast.next != null) { // 偶数时，需要断开slow后的节点，并将pre指向null，因为左右指针判断时需要
            flag = 0;
            slow.next = null;
            pre = null;
        }

        /**
         * null    1 -> 2 -> 3
         *  pre   cur  nxt     nxt = nxt.next
         * null <- 1    2 -> 3
         *  pre   cur  nxt     cur.next = pre
         *                     pre = cur
         * null <- 1      2 -> 3
         *        pre  cur|nxt   cur = nxt
         * null <- 1 <- 2      3
         *             pre  cur|nxt
         * null <- 1 <- 2      3
         *
         * */
        while (nxt != null) {
            nxt = nxt.next;
            cur.next = pre;
            pre = cur;
            cur = nxt;
        }
        // 分奇偶两种情况，用左右指针判断回文链表
        // 此时pre指向原链表最后的节点(反转后的头节点)
        /**
         * 1 -> 2 <- 3    while(left != right)
         *
         * 1 -> 2 <- 3 <- 4    while()
         * */
        ListNode left = head, right = pre;
        if (flag == 1) {  // 奇数
            while (left != right) {
                if (left.val != right.val) return false;
                left = left.next;
                right = right.next;
            }
        } else {  // 偶数
            while (left != null && right != null) {
                if (left.val != right.val) return false;
                left = left.next;
                right = right.next;
            }
        }
        //! 如果要求不破坏链表的结构，需要在这里恢复一下链表的顺序，再将后半段反转回去
        return true;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

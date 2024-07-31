import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Test148 {

    public static void main(String[] args) {

        ListNode n1 = new ListNode(4);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(1);
        ListNode n4 = new ListNode(3);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        Solution148 solution148 = new Solution148();
        ListNode h = solution148.sortList(n1);
        while (h != null) {
            System.out.print(h.val);
            System.out.print(' ');
            h = h.next;
        }
    }

}

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
class Solution148 {

    public ListNode sortList(ListNode head) {
        return method3_1(head);
    }

    // https://leetcode.cn/problems/sort-list/solutions/492301/pai-xu-lian-biao-by-leetcode-solution/?envType=study-plan-v2&envId=top-100-liked

    // 方法一：冒泡排序 O(n^2) O(1)  超时
    public ListNode method1(ListNode head) {
        if (head == null || head.next == null) return head;
        // 1.找到链表的长度
        int len = 0;
        ListNode p = head;
        while (p != null) {
            len++;
            p = p.next;
        }
        // 2.冒泡排序
        /**
         9 8 7 5 6 1
         8 9 7 5 6 1
         核心：依次把最小|最大的数送到最后
         第1层循环：表示这是第i次循环，i=1; i<=len-1; i++
         第2层循环：每次都从j=1出发，比较交换[j] [j-1]
         第1次，需要访问到最后1个元素，到<=len-1结束，确定最后1个元素
         第2次，需要访问倒数第2个元素，到<=len-2结束，确定倒数第2个元素
         ...
         第n次，需要访问倒数第n个元素(即正数第2个元素)，到<=1，等价于<=len-(len-1)
         第i次，需要访问倒数第i个元素，到<=len-i
         总结：需要循环len-1次，确定后len-1个数后，第一个数自然就确定了。
         */
        ListNode dummy = new ListNode(-1);  // 设置虚拟节点
        dummy.next = head;
        int cnt = 1;  // 循环次数
        while (cnt <= len-1) {
            /** 注意：这里必须是这样写，因为head节点每次可能会发生变化 */
            ListNode pre = dummy, cur = pre.next, nxt = cur.next;
            boolean flag = true; // 是否已经完成排序的标志
            for (int i=1; i<=len-cnt; i++) {
                /**  交换时的过程，需要设置三个指针才能完成交换
                 dm  4   2  1  3
                 p   c   n
                 dm  2   4  1  3
                 p   n   c
                 ---------------------------
                 2   4  1  3
                 p   c  n
                 注意中间这一步交换的时候会有问题：
                 c.next = n.next  【第一步】
                 2   4  3
                 p   c
                 1  3
                 n
                 n.next = c   【第二步】
                 2   4  3
                 p   c
                 1   4
                 n   c
                 做完上述两步后，没有完全交换，需要再设置一个前置指针
                 p.next = n   【第三步】
                 2   1   4   3
                 p   n   c
                 ---------------------------
                 【第四步】 指针移动
                 情况一：发生了交换   p = p.next  n = c.next
                 2   1   4   3
                 p   n   c
                 情况二：没有发生交换  p = p.next c = c.next n = n.next
                 1   2   4   3
                 p   c   n
                 */
                if (cur.val > nxt.val) {
                    cur.next = nxt.next;
                    nxt.next = cur;
                    pre.next = nxt;
                    // 指针后移
                    nxt = cur.next;
                    pre = pre.next;
                    // 如果这次循环交换仍然进到了这个里面，说明此次排序仍然有错位
                    flag = false;
                } else {
                    pre = pre.next;
                    cur = cur.next;
                    nxt = nxt.next;
                }
            }
            // 当本次排序完成后，如果没有进行交换了，说明已经排序完成了，不需要再遍历
            if (flag) break;
            cnt++;
        }
        return dummy.next;
    }

    // 归并排序
    public ListNode method3(ListNode head) {
        // base case
        if (head == null || head.next == null) return head;
        // 快慢指针找中点
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        /** 找到中点后，必须断开中点与后一节点的联系，不然原链表不断，每次递归找的中点都一样，死循环 */
        ListNode middle = slow.next;
        slow.next = null;
        // 递归对两部分进行排序
        ListNode h1 = method3(head);
        ListNode h2 = method3(middle);
        // 两部分进行归并排序
        ListNode dm = new ListNode(-1), p = dm;
        while (h1 != null && h2 != null) {
            if (h1.val <= h2.val) {
                p.next = h1;
                h1 = h1.next;
            } else {
                p.next = h2;
                h2 = h2.next;
            }
            p = p.next;
        }
        // 此时应该只相差一个节点没加了
        if (h1 != null) p.next = h1;
        else if (h2 != null) p.next = h2;
        // 返回新头节点
        return dm.next;
    }

    public ListNode method3_1(ListNode head) {
        if (head == null || head.next == null) return head;
        // 初始化，链表中的每个节点都作为一个组
        List<ListNode> group = new ArrayList<>();
        ListNode p = head;
        while (p.next != null) {
            group.add(p);
            ListNode tmp = p;
            p = p.next;
            tmp.next = null;  // 注意：这里必须断开连接
        }
        group.add(p);  // 添加最后一个元素
        // 遍历group，两两进行归并合并排序
        while (group.size() > 1) {
            List<ListNode> tmpGroup = new ArrayList<>();
            // 每次合并相邻的两个链表
            for (int i=1; i<group.size(); i+=2) {
                ListNode dm = new ListNode(-1), h3 = dm;
                ListNode h1 = group.get(i-1);
                ListNode h2 = group.get(i);
                while (h1 != null && h2 != null) {
                    if (h1.val <= h2.val) {
                        h3.next = h1;
                        h1 = h1.next;
                    } else {
                        h3.next = h2;
                        h2 = h2.next;
                    }
                    h3 = h3.next;
                }
                while (h1 != null) {
                    h3.next = h1;
                    h1 = h1.next;
                    h3 = h3.next;
                }
                while (h2 != null) {
                    h3.next = h2;
                    h2 = h2.next;
                    h3 = h3.next;
                }
                // dm.next为两两合并后的链表头指针
                tmpGroup.add(dm.next);
                dm.next = null;
            }
            // 如果group中元素个数为奇数，需要把最后一个链表再添加到group中
            if (group.size() % 2 != 0) tmpGroup.add(group.get(group.size()-1));
            // 更新group的引用指向
            group = tmpGroup;
        }
        // 返回整个链表
        return group.get(0);
    }


}

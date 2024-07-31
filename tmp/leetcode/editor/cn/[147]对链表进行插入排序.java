
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
    public ListNode insertionSortList(ListNode head) {
        return method2(head);
    }

    // 方法一：冒泡排序
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

    // 方法二：插入排序
    /** 情况分析
     * 第一轮
     * 移动前
     *  dm -> 4 -> 2 -> 1 -> 3
     *  pr    py   pw
     *        pwt
     * 移动后
     *  dm -> 2 -> 4 -> 1 -> 3
     *  pr    pw   py
     *             pwt
     * 更新指针位置, 分两种情况：
     *      1) 如果没有发生位置交换
     *          pw = pw.next;
     *          pwt = pwt.next;
     *      2) 如果发生位置交换
     *          pw = pwt.next;
     *  dm -> 2 -> 4 -> 1 -> 3
     *  pr    py   pwt  pw
     *
     * 第二轮
     * 移动前
     *  dm -> 2 -> 4 -> 1 -> 3
     *  pr    py   pwt  pw
     * 移动后
     *  pwt.next = pw.next;
     *  pw.next = py;
     *  pr.next = pw;
     *  dm -> 1 -> 2 -> 4 -> 3
     *  pr    pw   py   pwt
     * 更新指针位置：发生交换情况: pw = pwt.next;
     *  dm -> 1 -> 2 -> 4 -> 3
     *  pr         py   pwt  pw
     *
     * 伪代码书写：
     *  前置条件：
     *  if (head == null || head.next == null) return head;
     *  dm.next = head;
     *  初始化：
     *  pw = dm.next.next;  // 未排序链表的起始节点(用于移动)
     *  pwt = dm.next;  // 未排序指针的起始节点(用于记录位置、恢复位置、交换位置)
     *  while (pw != null) {  // 依次遍历每一个未排序链表的起始节点
     *      // 从链表头遍历已排序链表，找到比pw大的数字，插入其前面
     *      从 py = dm.next 开始遍历, 直到未排序的起始索引
     *      pr = dm; pr指针用于交换节点时用
     *      for (py != pw) {
     *          // 从前向后遍历，如果发现比pw大的数字，则插入前面
     *          if (py.val > pw.val) {
     *              // 将pw插入p的前面
     *              py.next = pw.next;
     *              pw.next = py;
     *              pr.next = pw;
     *              ------
     *              dm -> 2 -> 4 -> 1 -> 3
     *              pr    pw   py
     *              ------
     *              // 跳出循环，结束本轮排序
     *              break;
     *          }
     *          // 已排序指针、辅助指针前移
     *          py = py.next;
     *          pr = pr.next;
     *      }
     *      // 未排序指针前移
     *      pw = pwt.next.next;
     *      pwt = pwt.next;
     *  }
     */
    public ListNode method2(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode dm = new ListNode(-1);
        dm.next = head;

        ListNode pw = dm.next.next, pwt = dm.next;
        while (pw != null) {
            boolean flag = false;  // 判断有没有发生交换
            ListNode py = dm.next;
            ListNode pr = dm;
            while (py != pw) {
                if (py.val > pw.val) {
                    pwt.next = pw.next;
                    pw.next = py;
                    pr.next = pw;
                    flag = true;
                    break;
                }
                py = py.next;
                pr = pr.next;
            }
            // 分情况进行指针前移
            if (flag) {  // 发生交换
                pw = pwt.next;
            } else {  // 未发生交换
                pw = pw.next;
                pwt = pwt.next;
            }
        }

        return dm.next;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

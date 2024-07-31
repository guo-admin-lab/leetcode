
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
    public ListNode sortList(ListNode head) {
        return method3_1(head);
    }

    // https://leetcode.cn/problems/sort-list/solutions/492301/pai-xu-lian-biao-by-leetcode-solution/?envType=study-plan-v2&envId=top-100-liked

    // 方法三：归并排序(递归)  o(nlogn) o(logn)和递归层数相关
    /** 递归思路
     *      1.链表的前后两部分已经排好序
     *      2.两部分进行归并排序
     *  伪代码：
     *  -1.前置条件
     *  if (head == null || head.next == null) return head;
     *  0.快慢指针找到中点
     *  ---------------------
     *  偶数情况: f.next=null时，s.next为中点
     *  4->2->1->3
     *  s  f
     *  4->2->1->3
     *     s     f
     *  奇数情况：f=null时，s.next为中点
     *  4->2->1
     *  s  f
     *  4->2->1 nl
     *     s    f
     *  ---------------------
     *  slow = head; fast = head.next;
     *  while (fast != null && fast.next != null) {
     *      slow = slow.next;
     *      fast = fast.next.next;
     *  }
     *  中点：slow.next
     *  !注意：找到中点后，必须断开中点与后一节点的联系，不然原链表不断，每次递归找的中点都一样，死循环
     *  ListNode middle = slow.next;
     *  slow.next = null;
     *  1.对链表的前后两部分进行排序
     *      head1 = fun(首节点);
     *      head2 = fun(中节点);
     *  dm -> 4 -> 2 -> 1 -> 3
     *        h1        h2
     *  2.对前后两部分进行归并排序
     *      dm;
     *      p = dm;
     *      while (h1 != null && h2 != null) {
     *          if (h1 <= h2) {
     *              p.next = h1;
     *              h1 = h1.next;
     *          } else {
     *              p.next = h2;
     *              h2 = h2.next;
     *          }
     *          p = p.next;
     *      }
     *      // 此时应该只相差一个节点没加了
     *      if (h1 != null) p.next = h1;
     *      else if (h2 != null) p.next = h2;
     *      // 返回结果
     *      return dm.next;
     *
     */
    public ListNode method3(ListNode head) {
        // base case
        if (head == null || head.next == null) return head;
        // 快慢指针找中点
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        /** !注意：找到中点后，必须断开中点与后一节点的联系，不然原链表不断，每次递归找的中点都一样，死循环 */
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

    // 方法三：归并排序(迭代)  o(nlogn) o(n)
    /** 迭代思路
     *  参考：sort/MergeSort.java
     *
     */
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
        group.add(p);
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

    // 方法二：插入排序  O(n^2)  超时
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
class Solution {
    // o(nlogn) o(1)
    public ListNode sortList(ListNode head) {
        if (head == null) {
            return head;
        }
        int length = 0;
        ListNode node = head;
        while (node != null) {
            length++;
            node = node.next;
        }
        ListNode dummyHead = new ListNode(0, head);
        for (int subLength = 1; subLength < length; subLength <<= 1) {  // 控制每个子串的长度
            // 每次从开头开始遍历
            ListNode prev = dummyHead, curr = dummyHead.next;
            // 遍历一次链表（每次处理两段）
            while (curr != null) {
                // 找第一个链表头部
                ListNode head1 = curr;
                for (int i = 1; i < subLength && curr.next != null; i++) {
                    curr = curr.next;
                }
                // 找第二个链表头部
                ListNode head2 = curr.next;
                curr.next = null;
                curr = head2;
                for (int i = 1; i < subLength && curr != null && curr.next != null; i++) {
                    curr = curr.next;
                }
                ListNode next = null;
                if (curr != null) {
                    next = curr.next;
                    curr.next = null;
                }
                // 对这两段链表进行归并排序
                ListNode merged = merge(head1, head2);
                // 指针后移，准备处理下两段子链表
                prev.next = merged;
                while (prev.next != null) {
                    prev = prev.next;
                }
                curr = next;
            }
        }
        return dummyHead.next;
    }

    public ListNode merge(ListNode head1, ListNode head2) {
        ListNode dummyHead = new ListNode(0);
        ListNode temp = dummyHead, temp1 = head1, temp2 = head2;
        while (temp1 != null && temp2 != null) {
            if (temp1.val <= temp2.val) {
                temp.next = temp1;
                temp1 = temp1.next;
            } else {
                temp.next = temp2;
                temp2 = temp2.next;
            }
            temp = temp.next;
        }
        if (temp1 != null) {
            temp.next = temp1;
        } else if (temp2 != null) {
            temp.next = temp2;
        }
        return dummyHead.next;
    }
}


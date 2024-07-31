
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

    public ListNode mergeKLists(ListNode[] lists) {
//        return method1_1(lists,0);
//        return method1_2(lists);
//        return method2_1(lists, 0, lists.length-1);
//        return method2_2(lists);
        return method3_2(lists);
    }

    // 方法一: 顺序合并（递归） O(k^2 * n) O(k)
    /** 递归思路
     *  当前链表头ch
     *  排好序的链表头nh = fun(剩余链表)
     *  对ch和nh进行升序合并
     */
    public ListNode method1_1(ListNode[] lists, int i) {
        if (lists.length == 0) return null;
        // base case
        if (i == lists.length-1) return lists[i];
        // 获取当前链表头
        ListNode cur_head = lists[i];
        // 递归获取合并后面所有升序链表后的头节点
        ListNode new_head = method1_1(lists, i+1);
        // 合并两链表
        ListNode res_head = merge(cur_head, new_head);
        // 返回头节点
        return res_head;
    }

    // 方法一：顺序合并（迭代） O(k^2 * n) O(1)   k: 数组个数  n:每个链表最长的长度
    public ListNode method1_2(ListNode[] lists) {
        if (lists.length == 0) return null;
        // 1. 依次合并两个链表
        ListNode mergeHead = lists[0];
        for (int i=1; i<lists.length; i++) {  // O(n)
            /** merge时间复杂度分析
             *  第1次：mergeHead链表长度=n
             *  第2次：mergeHead链表长度=2n
             *  第k次：mergeHead链表长度=kn
             *
             *  总时间复杂度：
             *  n + 2n + ... + kn = [（1+k）*k/2] * n = n*k^2
             */
            mergeHead = merge(mergeHead, lists[i]);
        }
        return mergeHead;
    }

    // 方法二：分治合并（递归）  o(logk * k * n)  o(logk)
    /** 递归思路
     *  数组前半段链表合并后的头节点before_head = fun()
     *  数组后半段链表合并后的头节点after_head = fun()
     *  合并 before_head 和 after_head
     *
     *  时间复杂度分析：共有k个链表，每个链表最长长度为n
     *  最外层：2段链表长度均为：(k/2)*n，合并1次，1*(k/2)*n
     *  负2层：4段链表长度均为：(k/4)*n，合并2次，2*(k/4)*n
     *  负3层：8段链表长度均为：(k/8)*n，合并4次，4*(k/8)*n
     *  最里层：k段链表长度均为：(k/k)*n，合并k/2次，(k/2)*(k/k)*n
     *  总时间复杂度：
     *      1*(k/2)*n + 2*(k/4)*n + 4*(k/8)*n +...+ (k/2)*(k/k)*n
     *  =   (2/2)*(k/2)*n + (4/2)*(k/4)*n + (8/2)*(k/8)*n +...+ (k/2)*[k/k]*n
     *  =   [k/2]*n + _ + _ +...+ [k/2]*n  (共有logk个)
     *  =   logk * (k/2) * n
     *  =   logk * k * n
     *
     *  k = 16
     *  1
     *  2
     *  4
     *  8
     */
    public ListNode method2_1(ListNode[] lists, int begin, int end) {
        if (lists.length == 0) return null;
        if (begin == end) return lists[begin];
        // 1.进行前半段和后半段排序
        int middle = (end - begin) / 2 + begin;
        ListNode h1 = method2_1(lists, begin, middle);
        ListNode h2 = method2_1(lists, middle+1, end);
        // 2.合并两段链表
        ListNode res_head = merge(h1, h2);
        return res_head;
    }

    // 方法二：分治合并（迭代）
    public ListNode method2_2(ListNode[] lists) {
        if (lists.length == 0) return null;
        List<ListNode> group = new ArrayList<>();
        // 初始将每个链表都作为1组
        for (ListNode lHead : lists) {
            group.add(lHead);
        }
        while (group.size() > 1) {
            List<ListNode> newGroup = new ArrayList<>();
            for (int i = 1; i < group.size(); i += 2) {
                ListNode l1 = group.get(i - 1);
                ListNode l2 = group.get(i);
                ListNode merge = merge(l1, l2);
                newGroup.add(merge);
            }
            if (group.size() % 2 != 0) newGroup.add(group.get(group.size()-1));
            group = newGroup;
        }
        return group.get(0);
    }

    // 合并两个升序链表的代码
    public ListNode merge(ListNode l1, ListNode l2) {
        ListNode dm = new ListNode(-1), p = dm;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                p.next = l1;
                l1 = l1.next;
            } else {
                p.next = l2;
                l2 = l2.next;
            }
            p = p.next;
        }
        /** 注意：由于链表后面都是连着的，所以这里连接后面的头节点即可 */
        p.next = l1 != null ? l1 : l2;
        return dm.next;
    }

    // https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-8f30d/shuang-zhi-0f7cc/
    // 方法三：优先队列（不好的实现）
    /** 全部入队 + 依次找到最小值
     *  缺点：全部入队后，优先队列内的数据会变的非常庞大，每一次offer和poll的时间复杂度都会很高
     */
    public ListNode method3_1(ListNode[] lists) {
        if (lists.length == 0) return null;
        // // 定义最小堆优先队列
        PriorityQueue<ListNode> pq = new PriorityQueue<>(lists.length, (a, b)->(a.val - b.val));
        // 将元素插入优先队列 O( nk * nk * log(nk) )
        for (ListNode p : lists) {  // O(k)
            while (p != null) {  // O(n)
                ListNode next = p.next;  // 保存下一个节点
                p.next = null;  // 将当前节点与下一个节点断开连接
                /** 时间复杂度分析
                 * 共有nk个节点  nk = 2^h -1  h = log(nk+1)
                 * 1*0 + 2*1 + 4*2 + 8*3 + 最后一层节点个数*层数 2^(log(nk+1) - 1) * log(nk+1)
                 * 如果每一层节点都为nk，每一次都需要比较log(nk+1)次
                 * 渐进时间复杂度为 nk*log(nk)
                 */
                pq.offer(p);  // O( nk*log(nk) )
                p = next;  // 移动到下一个节点
                // pq.offer(p);  // O()
                // p = p.next;
            }
        }

        // 从队列中依次取最小值串起来
        ListNode dm = new ListNode(-1), p = dm;
        while (!pq.isEmpty()) { // O(nk)
            ListNode node = pq.poll();  // O( log(nk) )
            p.next = node;
            p = p.next;
        }
        return dm.next;
    }

    // 方法三：优先队列（好的实现） O(logk * k * n)  O(k)
    /** 边入队 + 边出队
     * 优点：
     *      首先把所有链表的头结点放入队列
     *      然后边弹出最小值，边加入后一个节点
     *      这样能让队列数据一直保持较小值，从而降低offer和poll的时间复杂度
     */
    public ListNode method3_2(ListNode[] lists) {
        if (lists.length == 0) return null;
        // 虚拟头结点
        ListNode dummy = new ListNode(-1);
        ListNode p = dummy;
        // 优先级队列，最小堆
        PriorityQueue<ListNode> pq = new PriorityQueue<>(lists.length, (a, b)->(a.val - b.val));
        // 将 k 个链表的头结点加入最小堆
        for (ListNode head : lists) {  // O( k )
            if (head != null)
                pq.add(head);  // O ( log(k) )
        }

        while (!pq.isEmpty()) {
            // 获取最小节点，接到结果链表中
            ListNode node = pq.poll();
            p.next = node;
            if (node.next != null) {
                pq.add(node.next);
            }
            // p 指针不断前进
            p = p.next;
        }
        return dummy.next;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

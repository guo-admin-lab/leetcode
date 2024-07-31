import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Test23 {

    public static void main(String[] args) {
        ListNode n11 = new ListNode(1);
        ListNode n12 = new ListNode(4);
        ListNode n13 = new ListNode(5);
        n11.next = n12;
        n12.next = n13;

        ListNode n21 = new ListNode(1);
        ListNode n22 = new ListNode(3);
        ListNode n23 = new ListNode(4);
        n21.next = n22;
        n22.next = n23;

        ListNode n31 = new ListNode(2);
        ListNode n32 = new ListNode(6);
        n31.next = n32;

        ListNode[] lists = {n11, n21, n31};
        ListNode listNode = method2_2(lists);
        while (listNode != null) {
            System.out.print(listNode.val);
            System.out.print(' ');
            listNode = listNode.next;
        }
    }


    public static ListNode method2_2(ListNode[] lists) {
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

    public static ListNode merge(ListNode l1, ListNode l2) {
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
    // 优先队列方法
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

    ListNode method3_2(ListNode[] lists) {
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

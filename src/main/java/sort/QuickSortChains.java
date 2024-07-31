package sort;


// https://www.bilibili.com/video/BV1m84y1n7xb/?spm_id_from=333.337.search-card.all.click&vd_source=7b2276f29cc5fd65128f4c4c85074775
// https://www.bilibili.com/video/BV1at411T75o/?spm_id_from=333.337.search-card.all.click&vd_source=7b2276f29cc5fd65128f4c4c85074775

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class QuickSortChains {

    public static void main(String[] args) {

        Node n1 = new Node(-1);
        Node n2 = new Node(2);
        Node n3 = new Node(-3);
        Node n4 = new Node(100);
        Node n5 = new Node(-20);
        n1.next = n2; n1.pre = null;
        n2.next = n3; n2.pre = n1;
        n3.next = n4; n3.pre = n2;
        n4.next = n5; n4.pre = n3;
        n5.next = null; n5.pre = n4;

        Node node = sort(n1);
        while (node != null) {
            System.out.println(node.val);
            node = node.next;
        }

    }

    static public Node sort(Node head) {
        // base case
        if (head == null) return null;

        Node piv = partition(head);
        // 断开中间连接
        Node second = piv.next;
        piv.pre.next = null;
        sort(head);
        sort(second);
        return head;
    }

    static public Node partition(Node head) {
        // 1.选取基准点
        int piv = head.val;
        Node right = head;
        while (right.next != null) right = right.next;
        Node left = head;
        // 2.划分区间
        /**
         * d 4 3 2 1
         *   p l   r
         */
        while (left != right) {
            while (left != right && right.val >= piv) right = right.pre;
            left.val = right.val;
            while (left != right && left.val < piv) left = left.next;
            right.val = left.val;
        }
        right.val = piv;
        return right;
    }



}

class Node {
    int val;
    Node pre;
    Node next;
    Node (int val) {
        this.val = val;
    }
    Node(int val, Node pre, Node next) {
        this.val = val;
        this.pre = pre;
        this.next = next;
    }
}

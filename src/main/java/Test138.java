import java.util.ArrayList;
import java.util.List;

public class Test138 {
}



class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}


class Solution138 {

    public Node copyRandomList(Node head) {
        return method1(head);
    }

    // 方法一：
    public Node method1(Node head) {
        /** 思路
         *  1.遍历链表
         *  2.依次创建新节点，赋值next、random
         *  3.但是之后的节点可能还没有创建，不能指向
         *  4.所有需要把所有节点都创建完成后，再遍历赋值next、random
         *  1 -> 2 -> 3
         * */
        // 1.创建新节点，并暂存
        List<Node> newNodes = new ArrayList<>();
        List<Integer> randomPos = new ArrayList<>();  // 如果random指向null，则设置-1
        Node p = head;
        while (p != null) {
            // 1.1.暂存节点
            Node node = new Node(p.val);
            newNodes.add(node);
            // 1.2.next指向位置不需要记录，因为就是下一个节点
            // 1.3.记录当前节点的random指向位置
            Node randomNode = p.random;
            if (randomNode == null) {
                randomPos.add(-1);
            } else {
                // 需要找到指向节点的位置
                Node tp = head;
                int pos = 0;  // 从head节点的位置算起，起始pos=0
                while (tp != null) {
                    if (tp == randomNode) {
                        randomPos.add(pos);
                        break;
                    }
                    tp = tp.next;
                    pos++;
                }
                //! 如果没找到的话，应该是指向null的情况，前面已经判断过
            }
            // 链表指针向后移动
            p = p.next;
        }
        // 2.赋值next、random
        int i;
        for (i=0; i<newNodes.size()-1; i++) {
            // 赋值next
            Node cur = newNodes.get(i);
            Node nxt = newNodes.get(i+1);
            cur.next = nxt;
            // 赋值random
            int pos = randomPos.get(i);
            if (pos == -1) {
                cur.random = null;
            } else {
                Node rand = newNodes.get(pos);
                cur.random = rand;
            }
        }
        // 2.1.处理最后一个节点的random（next不需要管，因为指向null）
        Node last = newNodes.get(i);
        int pos = randomPos.get(i);
        if (pos == -1) {
            last.random = null;
        } else {
            Node rand = newNodes.get(i);
            last.random = rand;
        }
        // 3.返回新链表的头节点
        return newNodes.get(0);
    }

}
//leetcode submit region end(Prohibit modification and deletion)


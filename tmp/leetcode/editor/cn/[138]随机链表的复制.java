
//leetcode submit region begin(Prohibit modification and deletion)
/*
// Definition for a Node.
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
*/

class Solution {

    public Node copyRandomList(Node head) {
        return method4(head);
    }

    // 方法一：暂存新链表节点 + 暂存random指向节点位置 + 先存后赋值  O(n^2 + n)  O(2n)
    /** 思路
     *  1.遍历链表
     *  2.依次创建新节点，赋值next、random
     *  3.但是之后的节点可能还没有创建，不能指向
     *  4.所有需要把所有节点都创建完成后，再遍历赋值next、random
     *      1 对于next，直接指向List存储的后面的节点即可
     *      2 对于random，由于原链表中的random指向的节点不能用于新链表，所以需要提前记录原链表中random指向节点的索引
     * */
    public Node method1(Node head) {
        if (head == null) return head;
        // 1.创建新节点，并暂存random指向节点的位置
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
            Node rand = newNodes.get(pos);
            last.random = rand;
        }
        // 3.返回新链表的头节点
        return newNodes.get(0);
    }

    // 方法二：提前暂存【节点<->位置】+ 暂存新链表节点  O(3n)  O(3n)
    /**
     * 目的：优化方法一的时间复杂度，方法一主要是寻找random指向节点时遍历了整个链表
     * 解法：使用备忘录思路将random指向的位置提前记录下来
     */
    public Node method2(Node head) {
        if (head == null) return head;
        // 1.遍历链表，存储节点在链表中对应的位置
        Map<Node, Integer> node2pos = new HashMap<>();
        Node p = head;
        int pos = 0;
        while (p != null) {
            node2pos.put(p, pos);
            pos++;
            p = p.next;
        }
        // 2.创建新节点，并暂存random指向节点的位置
        List<Node> newNodes = new ArrayList<>();
        List<Integer> randomPos = new ArrayList<>();  // 如果random指向null，则设置-1
        p = head;
        while (p != null) {
            // 2.1.暂存节点
            Node node = new Node(p.val);
            newNodes.add(node);
            // 2.2.next指向位置不需要记录，因为就是下一个节点
            // 2.3.记录当前节点的random指向位置
            Node randomNode = p.random;
            randomPos.add(node2pos.getOrDefault(randomNode, -1));
            // 2.4.链表指针向后移动
            p = p.next;
        }
        // 3.赋值next、random
        int i;
        for (i=0; i<newNodes.size()-1; i++) {
            // 赋值next
            Node cur = newNodes.get(i);
            Node nxt = newNodes.get(i+1);
            cur.next = nxt;
            // 赋值random
            pos = randomPos.get(i);
            if (pos == -1) {
                cur.random = null;
            } else {
                Node rand = newNodes.get(pos);
                cur.random = rand;
            }
        }
        // 3.1.处理最后一个节点的random（next不需要管，因为指向null）
        Node last = newNodes.get(i);
        pos = randomPos.get(i);
        if (pos == -1) {
            last.random = null;
        } else {
            Node rand = newNodes.get(pos);
            last.random = rand;
        }
        // 3.返回新链表的头节点
        return newNodes.get(0);
    }

    // 方法三：回溯递归 + 哈希表（本题类似于Spring的循环引用设计，先把对象创建好，之后可以再赋值）  O(n)  O(n)
    // https://leetcode.cn/problems/copy-list-with-random-pointer/solutions/889166/fu-zhi-dai-sui-ji-zhi-zhen-de-lian-biao-rblsf/?envType=study-plan-v2&envId=top-100-liked
    /** 思路
     *  本题的难点：当前节点的next、random无法被赋值（因为next、random指向的节点可能还没有创建）
     *  顺着这个思路，既然next、random指向的节点没有被创建，
     *              那么就先去创建好这个节点（包括创建这个节点需要的一系列连接其他连接等），
     *              然后再处理当前节点
     *  由于当前节点next指向节点的next指向节点仍然没有被创建，所以就是一个递归的思路了。
     *  递归定义：
     *      创建后新的节点 = fun(当前需要创建的next或random的源节点)
     *      核心：类似于栈结构，先入后处理，后入先处理
     *      特殊：本题类似于Spring的循环引用设计，先把对象创建好，之后可以再赋值
     */
    Map<Node, Node> old2new = new HashMap<>();
    public Node method3(Node head) {
        if (head == null) return head;
        // 1.如果节点之前没有被创建，则新建节点并递归设置next和random
        if (!old2new.containsKey(head)) {
            // 1.1.根据当前节点复制新节点
            Node newNode = new Node(head.val);
            // 1.2.建立新节点和纠结点的映射，防止递归重复创建新节点对象
            old2new.put(head, newNode);
            // 1.3.递归设置next和random
            newNode.next = method3(head.next);
            newNode.random = method3(head.random);
        }
        // 2.返回创建好的新节点
        return old2new.get(head);
    }

    // 方法四：迭代 + 节点拆分成两个  O(3n)  O(1)
    /** 思路过程
     *  1 -> 2 -> 3 -> nl
     * [3]  [1]  [nl]
     *
     * 一、节点拆分过程：
     * 1 -> 2 -> 3 -> nl
     * p = 1  // p从head开始遍历
     * nw1 = new Node(p.val)  // 新建1节点
     * nw1.next = p.next
     * p.next = nw1   1->1->2->3->nl
     * p = p.next.next
     * 循环条件：p!=null
     *
     * 节点拆分后：节点个数一定为偶数
     *  1 -> 1 -> 2 -> 2 -> 3 -> 3 -> nl
     * [3]  [nl] [1]  [nl] [nl] [nl]
     *
     * 二、赋值random
     * 初始节点：p1 = head, p2 = head.next
     * 需要注意：p1.random可能为null
     * if p1.random == null: p2.random = null
     * else: p2.random = p1.random.next
     * p1 = p1.next.next
     * 需要注意：当p1==null时，p2.next==null，此时已经到末尾了不需要再赋值了
     * if p1!=null: p2 = p2.next.next
     * 循环条件：p1 != null
     *
     * 三、将新链表从原有链表上分解出来（包括了赋值next）
     *  1 -> 1 -> 2 -> 2 -> 3 -> 3 -> nl
     * [3]  [3]  [1]  [1]  [nl] [nl]
     * 初始化：p1 = head, p2 = head.next
     * p1.next = p2.next;
     * p2.next = p2.next.next;
     * p1 = p1.next
     * p2 = p2.next
     * 循环条件：p2.next != null
     */
    public Node method4(Node head) {
        if (head == null) return head;
        // 1.进行节点拆分
        Node p = head;
        while (p != null) {
            Node newNode = new Node(p.val);
            newNode.next = p.next;
            p.next = newNode;
            p = p.next.next;
        }
        // 2.赋值random（拆分后节点个数一定为偶数）
        Node p1 = head, p2 = head.next;
        while (p1 != null) {
            if (p1.random == null) p2.random = null;
            else p2.random = p1.random.next;
            p1 = p1.next.next;
            if (p1 != null) p2 = p2.next.next;
        }
        // 3.将新链表从原链表上分解出来（包括了赋值next）
        Node newHead = head.next; // 记录新链表的头结点，用于返回结果
        p1 = head; p2 = head.next;
        while (p2 != null) {
            // 分解链表
            p1.next = p2.next;
            if (p2.next != null) p2.next = p2.next.next;
            // 指针后移
            p1 = p1.next;
            p2 = p2.next;
        }
        // 4.返回新链表头结点
        return newHead;
    }

}
//leetcode submit region end(Prohibit modification and deletion)



// 力扣官方题解：
// https://leetcode.cn/problems/lru-cache/solutions/259678/lruhuan-cun-ji-zhi-by-leetcode-solution/?envType=study-plan-v2&envId=top-100-liked
// https://leetcode.cn/problems/lru-cache/solutions/81045/yuan-yu-linkedhashmapyuan-ma-by-jeromememory/?envType=study-plan-v2&envId=top-100-liked

// 自己写的简化版的算法（）
class LRUCache {

    class Node {
        int key;
        int value;
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    Map<Integer, Node> map;
    Deque<Node> que;
    int cap;

    public LRUCache(int capacity) {
        map = new HashMap<>();
        que = new LinkedList<>();
        cap = capacity;
    }

    // 这一步需要快速找到key所在位置，需要使用HashMap
    public int get(int key) {
        Node node = map.get(key);
        if (node == null) return -1;
        // 将数据提升为最近使用的
        que.remove(node);
        que.addLast(node);
        return node.value;
    }

    // 将元素插入队列最后
    public void put(int key, int value) {
        Node node = map.get(key);
        // 1.如果key已经存在了
        if (node != null) {
            // 修改node值
            node.value = value;
            // 将数据提升为最近使用的
            que.remove(node);
            que.addLast(node);
            return;
        }

        // 2.如果node不存在，需要新添加
        // 2.1.如果缓存容量超限，逐出最久未使用的节点（队首元素）
        if (que.size() == cap) {
            // 移除队首节点（包括map映射关系）
            Node deleteNode = que.removeFirst();
            map.remove(deleteNode.key);
        }
        // 2.2.添加新节点（包括新的map映射关系）
        node = new Node(key, value);
        que.addLast(node);
        map.put(key, node);
    }
}


// labuladong算法
// LRU算法：https://labuladong.online/algo/data-structure/lru-cache/#%E4%B8%89%E3%80%81%E4%BB%A3%E7%A0%81%E5%AE%9E%E7%8E%B0
// LFU算法：https://labuladong.online/algo/frequency-interview/lfu/
class Node {
    int key;
    int value;
    Node(int key, int value) {
        this.key = key;
        this.value = value;
    }
}
class DoubleList {
    // 头尾虚节点
    private Node head, tail;
    // 链表元素数
    private int size;

    public DoubleList() {
        // 初始化双向链表的数据
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    // 在链表尾部添加节点 x，时间 O(1)
    public void addLast(Node x) {
        x.prev = tail.prev;
        x.next = tail;
        tail.prev.next = x;
        tail.prev = x;
        size++;
    }

    // 删除链表中的 x 节点（x 一定存在）
    // 由于是双链表且给的是目标 Node 节点，时间 O(1)
    public void remove(Node x) {
        x.prev.next = x.next;
        x.next.prev = x.prev;
        size--;
    }

    // 删除链表中第一个节点，并返回该节点，时间 O(1)
    public Node removeFirst() {
        if (head.next == tail)
            return null;
        Node first = head.next;
        remove(first);
        return first;
    }

    // 返回链表长度，时间 O(1)
    public int size() { return size; }

}
class LRUCache {
    // key -> Node(key, val)
    private HashMap<Integer, Node> map;
    // Node(k1, v1) <-> Node(k2, v2)...
    private DoubleList cache;
    // 最大容量
    private int cap;

    public LRUCache(int capacity) {
        this.cap = capacity;
        map = new HashMap<>();
        cache = new DoubleList();
    }

    /* 将某个 key 提升为最近使用的 */
    private void makeRecently(int key) {
        Node x = map.get(key);
        // 先从链表中删除这个节点
        cache.remove(x);
        // 重新插到队尾
        cache.addLast(x);
    }

    /* 添加最近使用的元素 */
    private void addRecently(int key, int val) {
        Node x = new Node(key, val);
        // 链表尾部就是最近使用的元素
        cache.addLast(x);
        // 别忘了在 map 中添加 key 的映射
        map.put(key, x);
    }

    /* 删除某一个 key */
    private void deleteKey(int key) {
        Node x = map.get(key);
        // 从链表中删除
        cache.remove(x);
        // 从 map 中删除
        map.remove(key);
    }

    /* 删除最久未使用的元素 */
    private void removeLeastRecently() {
        // 链表头部的第一个元素就是最久未使用的
        Node deletedNode = cache.removeFirst();
        // 同时别忘了从 map 中删除它的 key
        int deletedKey = deletedNode.key;
        map.remove(deletedKey);
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        // 将该数据提升为最近使用的
        makeRecently(key);
        return map.get(key).val;
    }

    public void put(int key, int val) {
        if (map.containsKey(key)) {
            // 删除旧的数据
            deleteKey(key);
            // 新插入的数据为最近使用的数据
            addRecently(key, val);
            return;
        }

        if (cap == cache.size()) {
            // 删除最久未使用的元素
            removeLeastRecently();
        }
        // 添加为最近使用的元素
        addRecently(key, val);
    }

}



// 自己写的废弃版本
//leetcode submit region begin(Prohibit modification and deletion)
class LRUCache {
    /** 分析
     *  put(1,1) {1=[1,0]}
     *  put(2,2) {1=[1,1], 2=[2,0]}
     *  get(1) {1=[1,0], 2=[2,1]}
     *  put(3,3) {1=[1,1], 3=[3,0]}
     *  get(2) {1=[1,2], 3=[3,1]}
     *
     *  总结：
     *      put(k1,v1) :
     *          如果k1存在: if map.containsKey(k1):
     *              valueAndCacheTime = map.get(k1);
     *              valueAndCacheTime[0] = v1;
     *              valueAndCacheTime[1] = 0;
     *          如果k1不存在：else:
     *              // 判断容量是否已经满了
     *              if map.size() == cap:  // 如果容量已经满了
     *                  maxUnusedTime = 0;
     *                  maxKey = 0;
     *                  for key in map.keySet():  // 找到最久未使用的key
     *                      valueAndCacheTime = map.get(key);
     *                      value = valueAndCacheTime[0];
     *                      unusedTime = valueAndCacheTime[1];
     *                      if unusedTime > maxUnusedTime:
     *                          maxUnusedTime = unusedTime;
     *                          maxKey = key;
     *                  // 删除最久未使用的entry
     *                  map.remove(maxKey);
     *              // 添加新元素
     *              map.put(new int[]{v1, 0})
     *              // map中的其他key的缓存时间需要增加
     *              for key in map.keySet():
     *                 if !key.equals(k1):
     *                      valueAndCacheTime = map.get(k1);
     *                      valueAndCacheTime[1] += 1
     *
     *       get(k1):
     *          如果k1存在：
     *              k1的未使用时间清0
     *              其他key的未使用时间增加1
     *              返回v1
     *          如果k1不存在：
     *              其他key的未使用时间加1
     *              返回-1
     *
     */
    /** capacity=1
     * put(2,1)  {2=[1,0]}
     * get(2)  1
     * put(3,2)  {3={2,0}}
     * get(2)  -1
     * get(3)  2
     */

    private int capactiy;

    private Map<Integer, int[]> key2ValueAndUnusedTime = new HashMap<>();

    public LRUCache(int capacity) {
        this.capactiy = capacity;
    }

    // O(n)
    public int get(int key) {
        // 如果key存在
        if (key2ValueAndUnusedTime.containsKey(key)) {
            // key未使用时间清0
            int[] valueAndUnusedTime = key2ValueAndUnusedTime.get(key);
            valueAndUnusedTime[1] = 0;
            // 其他key的未使用时间加1
            timeGrow(key);
            // 返回value
            return valueAndUnusedTime[0];
        } else {  // 如果key不存在
            // 其他key的未使用时间加1
            timeGrow(key);
            // 返回-1
            return -1;
        }

    }

    // O(n)
    public void put(int key, int value) {
        // 如果k1存在
        if (key2ValueAndUnusedTime.containsKey(key)) {
            int[] valueAndUnusedTime = key2ValueAndUnusedTime.get(key);
            // 更新v1的值
            valueAndUnusedTime[0] = value;
            // k1的未使用时间清0
            valueAndUnusedTime[1] = 0;
        } else {  // 如果k1不存在
            // 判断容量是否已经满了
            // 如果已满
            if (key2ValueAndUnusedTime.size() == capactiy) {
                int maxUnusedTime = 0;
                int rmKey = 0;
                // 找到最久未使用的key
                for (Map.Entry<Integer, int[]> entry : key2ValueAndUnusedTime.entrySet()) {
                    int _key = entry.getKey();
                    int[] valueAndUnusedTime = entry.getValue();
                    if (valueAndUnusedTime[1] >= maxUnusedTime) {
                        maxUnusedTime = valueAndUnusedTime[1];
                        rmKey = _key;
                    }
                }
                // 删除最久未使用的entry
                key2ValueAndUnusedTime.remove(rmKey);
            }
            // 添加新元素
            key2ValueAndUnusedTime.put(key, new int[]{value, 0});
        }
        // 其他key的未使用时间加1
        timeGrow(key);
    }

    // map中其他key的未使用时间加1
    private void timeGrow(Integer targetKey) {
        for (Map.Entry<Integer, int[]> entry : key2ValueAndUnusedTime.entrySet()) {
            Integer key = entry.getKey();
            // todo 这里需要注意包装类的空值情况
            if (!key.equals(targetKey)) {
                int[] valueAndUnusedTime = entry.getValue();
                valueAndUnusedTime[1] += 1;
            }
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
//leetcode submit region end(Prohibit modification and deletion)


//leetcode submit region begin(Prohibit modification and deletion)

/**  思路分析：
 *      https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-daeca/yi-dao-qiu-b3109/
 *
 *   思路一：数组
 *      数组记录addNum添加的数字，通过插入排序的逻辑保证数组中的元素有序，
 *      调用 findMedian 方法时，可以通过数组索引直接计算中位数。
 *      缺点：
 *          数组作为底层容器的问题也很明显，
 *          addNum 搜索插入位置的时候可以用二分搜索算法，
 *          但是插入操作需要搬移数据，所以最坏时间复杂度为 O(N)。
 *
 *   思路二：链表
 *      缺点：
 *          链表插入元素很快，
 *          但是查找插入位置的时候只能线性遍历，最坏时间复杂度还是 O(N)，
 *          而且 findMedian 方法也需要遍历寻找中间索引，最坏时间复杂度也是 O(N)。
 *
 *   思路三：平衡二叉树
 *      优点：
 *          增删查改复杂度都是 O(logN)
 *      比如：
 *          用 Java 提供的 TreeSet 容器，底层是红黑树，
 *          addNum 直接插入，findMedian 可以通过当前元素的个数推出计算中位数的元素的排名。
 *      缺点：
 *          第一，TreeSet 是一种 Set，其中不存在重复元素的元素，但是我们的数据流可能输入重复数据的，而且计算中位数也是需要算上重复元素的。
 *          第二，TreeSet 并没有实现一个通过排名快速计算元素的 API。假设我想找到 TreeSet 中第 5 大的元素，并没有一个现成可用的方法实现这个需求。
 *
 *   思路四：优先级队列（二叉堆）
 *      也不行，
 *      因为优先级队列是一种受限的数据结构，只能从堆顶添加/删除元素，
 *      我们的 addNum 方法可以从堆顶插入元素，
 *      但是 findMedian 函数需要从数据中间取，这个功能优先级队列是没办法提供的。
 *
 *   最终思路：使用两个优先级队列
 *
 *   额外思路：使用 TreeMap 存储，使用双指针查找中位数
 *   https://leetcode.cn/problems/find-median-from-data-stream/solutions/961062/shu-ju-liu-de-zhong-wei-shu-by-leetcode-ktkst/?envType=study-plan-v2&envId=top-100-liked
 */

// （不要看）效率很低的实现
class MedianFinder1 {

    List<Double> nums;

    public MedianFinder1() {
        nums = new ArrayList<>();
    }

    public void addNum(int num) {
        if (nums.size() == 0) {
            nums.add((double) num);
            return;
        }
        for(int i=0; i<nums.size(); i++) {
            if (num <= nums.get(i)) {
                nums.add(i, (double) num);
                return;
            }
        }
        nums.add((double) num);
    }

    public double findMedian() {
        int size = nums.size();
        if (size % 2 == 0) {
            return (nums.get(size/2) + nums.get(size/2-1)) / 2;
        } else {
            return nums.get(size/2);
        }
    }
}

// 两个优先级队列实现
class MedianFinder {

    PriorityQueue<Integer> small;
    PriorityQueue<Integer> large;

    public MedianFinder() {
        small = new PriorityQueue<>((a, b) -> (b - a));  // 大顶堆
        large = new PriorityQueue<>(); // 小顶堆
    }

    public void addNum(int num) {
        // 为保持两个堆中元素的平衡
        if (small.size() <= large.size()) {
            large.offer(num);
            small.offer(large.poll());
        } else {
            small.offer(num);
            large.offer(small.poll());
        }
    }

    public double findMedian() {
        if (large.size() > small.size()) return large.peek();
        else if (large.size() < small.size()) return small.peek();
        else return (large.peek() + small.peek()) / 2.0;
    }
}

// 方法二：有序集合 + 双指针（还没看）
class MedianFinder {
    TreeMap<Integer, Integer> nums;
    int n;
    int[] left;
    int[] right;

    public MedianFinder() {
        nums = new TreeMap<Integer, Integer>();
        n = 0;
        left = new int[2];
        right = new int[2];
    }

    public void addNum(int num) {
        nums.put(num, nums.getOrDefault(num, 0) + 1);
        if (n == 0) {
            left[0] = right[0] = num;
            left[1] = right[1] = 1;
        } else if ((n & 1) != 0) {
            if (num < left[0]) {
                decrease(left);
            } else {
                increase(right);
            }
        } else {
            if (num > left[0] && num < right[0]) {
                increase(left);
                decrease(right);
            } else if (num >= right[0]) {
                increase(left);
            } else {
                decrease(right);
                System.arraycopy(right, 0, left, 0, 2);
            }
        }
        n++;
    }

    public double findMedian() {
        return (left[0] + right[0]) / 2.0;
    }

    private void increase(int[] iterator) {
        iterator[1]++;
        if (iterator[1] > nums.get(iterator[0])) {
            iterator[0] = nums.ceilingKey(iterator[0] + 1);
            iterator[1] = 1;
        }
    }

    private void decrease(int[] iterator) {
        iterator[1]--;
        if (iterator[1] == 0) {
            iterator[0] = nums.floorKey(iterator[0] - 1);
            iterator[1] = nums.get(iterator[0]);
        }
    }
}


/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
//leetcode submit region end(Prohibit modification and deletion)

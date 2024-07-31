
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public int findKthLargest(int[] nums, int k) {
        return method4(nums, k);
    }

    // 方法一：快速排序  O(nlogn) -------------------------------------
    public int method1(int[] nums, int k) {
        Arrays.sort(nums);  // 升序排序
        return nums[nums.length - k];  // 倒数第k个元素
    }

    // 方法二：优先队列（堆排序） O(nlogn)  -------------------------------------
    public int method2(int[] nums, int k) {
        /** 思路1：最小堆，保持堆中元素为k，时间复杂的O(nlogk) */
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int num : nums) {
            pq.offer(num);
            if (pq.size() > k) {
                pq.poll();  // 当 堆中 有 k+1个元素时，那么堆顶的最小值一定不是第k大元素，因为除去堆顶元素，堆中还剩下k个元素，第k大元素在这剩下的k个元素中
            }
        }
        // 当所有的元素全部入队一次后，现在堆顶的元素就是第k大元素
        return pq.peek();

        /** 思路2：最大堆，让所有的元素入堆后，依次弹出k个元素，时间复杂度度O(nlogn * k) */
        /**PriorityQueue<Integer> pq = new PriorityQueue<>((a,b)->(b-a));
        for (int num : nums) pq.offer(num);
        for (int i=0; i<k-1; i++) pq.poll();
        return pq.peek();*/
    }

    // 方法三：堆排序  O(nlogn)  -------------------------------------
    public int method3(int[] nums, int k) {
        // 1.建堆
        // 1.1.找到最后一个非叶子结点的索引
        int nonLeafIndex = (int) Math.floor(nums.length/2)-1;
        for (int i=nonLeafIndex; i>=0; i--) {
            heapify(nums, i, nums.length);
        }

        // 2.进行堆排序
        int size = nums.length;
        for (int endi=size-1; endi>=0; endi--) {
            swap(nums, 0, endi);
            size--;
            heapify(nums, 0, size);
        }

        // 3.取正数第k个元素（由于是最小堆，排序的时候，每次都是把最小值放到nums的最后，所以是降序排序）
        return nums[k-1];
    }
    // 构建最小堆（从底向上）
    public void heapify(int[] nums, int i, int size) {
        int father = i;
        int leftSon = 2*i+1;
        int rightSon = 2*i+2;
        while (leftSon < size || rightSon < size) {
            // 1.找到两孩子中值较小的索引
            int mini;
            if (leftSon >= size) mini = rightSon;
            else if (rightSon >= size) mini = leftSon;
            else mini = nums[leftSon]<nums[rightSon] ? leftSon : rightSon;

            // 2.判断father是否需要下沉
            if (nums[father] < nums[mini]) return;

            // 3.下沉父元素，并继续向下交换
            swap(nums, father, mini);
            father = mini;
            leftSon = 2*father+1;
            rightSon = 2*father+2;
        }

    }
    public void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    // 方法四：基于选择的快速排序  -------------------------------------
    public int method4(int[] nums, int k) {
        // 洗牌操作乱序
        shuffle(nums);
        // 快速选择算法
        int start = 0, end = nums.length-1;
        while (start <= end) {
            // 1.进行分区排序
            int p = partition(nums, start, end);  // 从大到小排序
            // 2.判断第k大的元素存在于左右哪个分区中
            if ((k-1) < p) {  // 第k大元素存在于左分区中
                end = p - 1;
            } else if ((k-1) > p) {  // 第k大元素存在于右分区中
                start = p + 1;
            } else {  // p就是第k大元素
                 return nums[p];
            }
        }
        return -1;
    }
    // 快排的分区函数（左右指针交替移动赋值的方式）
    public int partition(int[] nums, int start, int end) {
        int pivot = nums[start];  // 默认选择第一个为支点元素
        int left = start;
        int right = end;

        while (left < right) {
            // 寻找 < pivot的数
            while (left < right && nums[right] <= pivot) right--;
            nums[left] = nums[right];
            // 寻找 >= pivot的数
            while (left < right && nums[left] > pivot) left++;
            nums[right] = nums[left];
        }

        // left == right 的情况
        nums[right] = pivot;

        return right;
    }
    // 洗牌算法，将输入的数组随机打乱
    private void shuffle(int[] nums) {
        Random rand = new Random();
        int n = nums.length;
        for (int i = 0 ; i < n; i++) {
            // 生成 [i, n - 1] 的随机数
            int r = i + rand.nextInt(n - i);
            swap(nums, i, r);
        }
    }

    /** 自己写的快速选择算法执行很慢，不知道为什么，力扣官方的就很快 */
    // https://leetcode.cn/problems/kth-largest-element-in-an-array/solutions/307351/shu-zu-zhong-de-di-kge-zui-da-yuan-su-by-leetcode-/?envType=study-plan-v2&envId=top-100-liked

}
//leetcode submit region end(Prohibit modification and deletion)

package sort;


// https://www.bilibili.com/video/BV1m84y1n7xb/?spm_id_from=333.337.search-card.all.click&vd_source=7b2276f29cc5fd65128f4c4c85074775
// https://www.bilibili.com/video/BV1at411T75o/?spm_id_from=333.337.search-card.all.click&vd_source=7b2276f29cc5fd65128f4c4c85074775

import java.util.*;

public class QuickSort {

    public static void main(String[] args) {

        int[] arr = new int[]{-1,-6,3,2,3,100,4,9,10,8,7,99,7,4,1,0,-1,51,-3,0,88,-9,9,6,59,-55};
//        arr = new int[]{20,5,13,33,2,99,14,100,99,57,-1};
        int[] sort = sort1(arr, 0, arr.length-1);  // 递归法
//        int[] sort = sort2_1(arr);  // 迭代法  栈实现
//        int[] sort = sort2_2(arr);  // 迭代法  队列实现
        for (int n : sort) {
            System.out.print(n);
            System.out.print(' ');
        }

//        Arrays.sort();  // 这个快排的实现使用的递归

    }

    /** 方法一：递归
     *  总结：递归思想
     *      递归定义：
     *      0 base
     *          如果left >= right: 直接返回
     *      1 按照上述步骤计算出临界索引：partitionIndex
     *          1) 设置pivot = left; left = pivot+1;
     *          2) while (left <= right) {
     *                  // 移动 l++, 找到 > 基准的数
     *                  while (arr[left] <= arr[pivot]) left++;
     *                  // 移动 r--，找到 <= 基准的数
     *                  while (arr[right] > arr[pivot]) right--;
     *                  // 交换l和r的值
     *                  if left <= right:
     *                      // 交换l和r的值
     *                      swap(arr, left, right);
     *                  else:
     *                      // 交换r和p的值
     *                      swap(arr, pivot, right);
     *                      break;
     *                  // l、r各收缩一位
     *                  left++; right--;
     *             }
     *         3) partitionIndex = right;
     *      2 递归将临界左右分区中的数进行排序
     *      fun(arr, left, partitionIndex-1)
     *      fun(arr, partitionIndex+1, right)
     *      3 返回arr，即为排好序的数组
     *
     */
    public static int[] sort1(int[] arr, int left, int right) {
        // 0.base case
        if (left >= right) return arr;
        // 1.设置分区临界索引
        int partitionIndex = partition2(arr, left, right);
        // 2.递归将临界左右分区中的数进行排序
        sort1(arr, left, partitionIndex-1);
        sort1(arr, partitionIndex+1, right);
        // 3.返回arr，即为排好序的数组
        return arr;
    }

    /** 方法二：迭代
     *  思路：
     *      1 所谓递归，就是让每一层函数按照递归的顺序依次进行排序，让这些函数依次执行即可
     *          最外层划分界线后，让左右两区依次进行重复的操作即可
     *      2 队列和栈都能实现快排的迭代方法
     *  4  2  3  1  5  6  7  8    len=8
     *  l                    r
     *  第一次需要排序范围：[l=0,r=7]
     *      找出分界线
     *  1  2  3  4  5  6  7  8    len=8
     *           p
     *  第二次需要排序范围：[l=0,r=2] [l=4,r=7]
     *  第三次需要排序范围：[0] [2] [4] [6,7]
     *
     *  如果用队列实现，依次需要进行分区排序的顺序为(即添加至队列的顺序)：
     *      初始化：[l=0,r=7] 入队     q = (队首 [l=0,r=7] 队尾)
     *      while(!q.isEmpty()) {
     *          第1次：
     *              [l=0,r=7] 出队    q = ()
     *              进行分区排序
     *              [l=0,r=2] 入队
     *              [l=4,r=7] 入队    q = ( [l=0,r=2], [l=4,r=7] )
     *          第2次：
     *              [l=0,r=2] 出队    q = ( [l=4,r=7] )
     *              进行分区排序
     *              [0] 入队
     *              [2] 入队          q = ( [l=4,r=7], [0], [2] )
     *          第3次：
     *              [l=4,r=7] 出队    q = ( [0], [2] )
     *              进行分区排序
     *              [4] 入队
     *              [6,7] 入队        q = ( [0], [2], [4], [6,7] )
     *          第x次：
     *              依次类推，依次排序的顺序是对的，但是和递归执行的顺序不一致
     *      }
     *
     *  如果用栈实现，依次需要进行分区排序的顺序为(即添加到栈的顺序)；
     *      初始化：[l=0,r=7] 入栈     s = (栈底 [l=0,r=7] 栈顶)
     *      while(!s.isEmpty()) {
     *          第1次：
     *              [l=0,r=7]  出栈   s = ()
     *              进行分区排序
     *              [l=0,r=2] 入栈
     *              [l=4,r=7] 入栈    s = ( [l=0,r=2], [l=4,r=7] )
     *          第2次：
     *              [l=4,r=7] 出栈    s = ( [l=0,r=2] )
     *              进行分区排序
     *              [4] 入栈
     *              [6,7] 入栈        s = ( [l=0,r=2], [4], [6,7] )
     *          第3次：
     *              [6,7] 出栈        s = ( [l=0,r=2], [4] )
     *              进行分区排序
     *          第4次：
     *              [4]  出栈         s = ( [l=0,r=2] )
     *              进行分区排序
     *          第5次：
     *              [l=0,r=2] 出栈    s = ()
     *              进行分区排序
     *              [0] 入栈
     *              [2] 入栈          s = ( [0], [2] )
     *          第x次：
     *              依次类推，这样依次排序的顺序也是对的，而且和递归执行的顺序一致
     *      }
     */
    // 栈迭代（分区排序的执行顺序和递归完全一致）相当于深度遍历dfs
    public static int[] sort2_1(int[] arr) {
        // 定义模拟栈帧
        class Range {
            int left;
            int right;
            Range(int left, int right){
                this.left = left;
                this.right = right;
            }
        }
        Stack<Range> stack = new Stack<>();
        // 初始化栈数据
        stack.push(new Range(0, arr.length-1));
        // 开始按照栈顺序执行分区排序
        while (!stack.isEmpty()) {
            // 当前需要分区排序的界限出栈
            Range range = stack.pop();
            // 1.base case
            if (range.left >= range.right) continue;
            // 2.设置分区临界索引
            int partitionIndex = partition2(arr, range.left, range.right);
            // 3.将下一组分区界限入栈
            stack.push(new Range(range.left, partitionIndex-1));
            stack.push(new Range(partitionIndex+1, range.right));
        }
        // 返回排好序的数组
        return arr;
    }
    // 队列迭代（分区排序的执行顺序和递归不完全一致，但也符合正确的执行顺序） 相当于广度遍历bfs
    public static int[] sort2_2(int[] arr) {
        // 定义模拟栈帧
        class Range {
            int left;
            int right;
            Range(int left, int right){
                this.left = left;
                this.right = right;
            }
        }
        Queue<Range> queue = new LinkedList<>();
        // 初始化队列数据
        queue.offer(new Range(0, arr.length-1));
        // 开始按照栈顺序执行分区排序
        while (!queue.isEmpty()) {
            // 当前需要分区排序的界限出栈
            Range range = queue.poll();
            // 1.base case
            if (range.left >= range.right) continue;
            // 2.设置分区临界索引
            int partitionIndex = partition2(arr, range.left, range.right);
            // 3.将下一组分区界限入栈
            queue.offer(new Range(range.left, partitionIndex-1));
            queue.offer(new Range(partitionIndex+1, range.right));
        }
        // 返回排好序的数组
        return arr;
    }

    /** 思路一：最直观的思考方法，需要占用额外的存储空间  out-place
     *  20  5  13  57  99  14  2  33
     *  找到第1个数为基准pivot：20
     *  将<20的数放到20左边，>=20的数放到20的右边：
     *      5  13  14  2  20  57  99  33
     *  但是代码要怎么写呢？
     *  数组情况：
     *      new_arr = [20]
     *      遍历数组:
     *          if arr[i] < 20:
     *              将arr[i]插入首位置，需要第1个数及后面的数全部后移，时间复杂度O(n)
     *          else:
     *              将arr[i]插入末尾位置
     *   链表情况；
     *      new_list = [20]
     *      遍历链表：
     *          if node.val < 20:
     *              将node插入头节点位置
     *          else:
     *              将node插入尾节点位置
     *  总结：
     *      上述两种情况，均需要开辟额外的存储空间，不太好
     */

    /** 思路二：左右指针 + 交替移动赋值寻找分界点的思路
     *  20  5  13  57  99  14  2  33    len=8
     *  第1次排序：
     *  (-1) 设置pivot=0, li = 0, ri = len-1
     *  (0)  暂存pivot数：temp = arr[pivot] = arr[0] = 20    下面的[n]表示可被覆盖的数字，等于没有数
     *  [20]  5  13  57  99  14  2  33
     *  l                           r
     *  (1) 移动 r--, 找到 < 20的数, 将其赋值到 l位置
     *  2   5  13  57  99  14  [2]  33
     *  l                      r
     *  (2) 移动 l++, 找到 >= 20的数，将其赋值到r位置
     *  2   5  13  [57]  99  14  57  33
     *             l             r
     *  (3) 移动 r--, 找到 < 20的数，将其赋值到l位置
     *  2   5  13  14  99  [14]  57  33
     *             l       r
     *  (4) 移动 l++, 找到 >= 20的数，将其赋值到r位置
     *  2   5  13  14  [99]  99  57  33
     *                 l     r
     *  (5) 移动 r--, 找到 < 20的数，将其赋值到l位置
     *     （如果在移动过程中，li == ri，将temp赋值到l|r处）
     *      2   5  13  14  20  99  57  33
     *                     l|r
     *
     *  第2次排序：
     *
     */
    /** 思路二的临界分析
     * 方式一：不正确，因为等于tmp=20的数，不知道按照<20处理，还是>20处理，最终结果的左右两边都会有20出现
     *      r--，遇到 < 20的数，arr[l] = arr[r]
     *      l++，遇到 > 20的数，arr[r] = arr[l]
     * tmp = 20
     * [20]  5  20  57  99  14  20  2  33
     * l                               r
     * 2  5  20  57  99  14  20  [2]  33
     * l                          r
     * 2  5  20  [57]  99  14  20  57  33
     *           l                 r
     * 2  5  20  14  99  [14]  20  57  33
     *           l        r
     * 2  5  20  14  [99] 99  20  57  33
     *               l    r
     * 2  5  20  14  20   99  20  57  33
     *               l|r
     *
     * 方式二：等于20的数，按照>20的数处理
     *      r--，遇到 < 20的数，arr[l] = arr[r]
     *      l++，遇到 >=20的数，arr[r] = arr[l]
     * tmp = 20
     * [20]  5  20  57  99  14  20  2  33
     * l                               r
     * 2  5  20  57  99  14  20  [2]  33
     * l                          r
     * 2  5  [20] 57  99  14  20  20  33
     *        l                   r
     * 2  5  14 57  99  [14]  20  20  33
     *       l           r
     * 2  5  14 [57]  99  57  20  20  33
     *           l        r
     * 2  5  14  99  [99]  57  20  20  33
     *           l    r
     * 2  5  14  99  20  57  20  20  33
     *               r|l
     *
     * 方式三：等于20的数，按照<20的数处理
     *      r--，遇到 <= 20的数，arr[l] = arr[r]
     *      l++，遇到 > 20的数，arr[r] = arr[l]
     * 分析同上
     *
     * 方式四：不正确，因为等于tmp=20的数，不知道按照<20处理，还是>20处理，最终结果的左右两边都会有20出现
     *      r--，遇到 <= 20的数，arr[l] = arr[r]
     *      l++，遇到 >= 20的数，arr[r] = arr[l]
     */
    public static int partition2(int[] arr, int left, int right) {
        // 1.初始化基准元素、左右指针位置
        int pivotNum = arr[left];  // 暂存基准元素
        // 2.进行分区排序
        while (left < right) {
            // 以下代码即可实现左右指针交替移动，并交替赋值
            // 2.1.先移动 r--，找到 < pivot的数，将其赋值到 l位置
            while (left < right && arr[right] >= pivotNum) right--;
            arr[left] = arr[right];
            // 2.2.再移动 l++, 找到 >= pivot的数，将其赋值到 r位置
            while (left < right && arr[left] < pivotNum) left++;
            arr[right] = arr[left];
        }
        // 跳出循环时，这里一定是因为left==right
        // 此时将pivot赋值到left|right即可
        arr[right] = pivotNum;
        // 3.返回临界点索引（此时left|right即为临界点）
        return right;
    }

    /** 思路三：左右指针 + 交换位置寻找分界点的思路
     *  20  5  13  57  99  14  2  33    len=8
     *  第1次排序：
     *  (-1) 设置pivot=0, li = pivot+1, ri = len-1
     *  20  5  13  57  99  14  2  33
     *  p   l                     r
     *  (1) 移动 l++, 找到 > 20的数
     *      20  5  13  57  99  14  2  33
     *      p          l              r
     *      再移动 r--，找到 <= 20的数，swap(arr[l], arr[r])
     *      将l++, r--，各收缩一位
     *      20  5  13  33  99  14  2  57
     *      p              l       r
     *  (2) 移动l++, 找到 > 20的数
     *      20  5  13  33  99  14  2  57
     *      p              l       r
     *      再移动 r--，找到 <= 20的数，swap(arr[l], arr[r])
     *      将l++, r--，各收缩一位
     *      20  5  13  33  2  14  99  57
     *      p                 l|r
     *  (3) 移动l++, 找到 > 20的数
     *      20  5  13  33  2  14  99  57
     *      p                 r   l
     *      此时，循环条件结束，因为r<l了，执行swap(arr[p], arr[r])
     *      14  5  13  33  2  20  99  57
     *      p                 r   l
     */
    public static int partition3(int[] arr, int left, int right) {
        // 1.计算出临界索引
        // 1.1.初始化基准元素
        /** 废弃注意：_left和_right是一定要设置的，因为下面的操作会改变left和right的值，导致递归传参传入改变后的left和right的值 */
        int pivot = left;
        int _left = pivot+1;
        int _right = right;
        // 1.2.进行初始排序
        while (_left < _right) {
            /** 注意：下面两个while循环中的_left<_right一定要加上，不然会导致索引越出的错误 */
            // 移动 l++, 找到 > 基准的数
            while (_left < _right && arr[_left] <= arr[pivot]) _left++;
            // 移动 r--，找到 <= 基准的数
            while (_left < _right && arr[_right] > arr[pivot]) _right--;
            /** 注意：这里会因为 _left == _right，造成跳出循环，此时_right指向的数 不知道 和 pivot数的大小关系 */
            // 交换l和r的值
            if (_left < _right) {
                // 交换l和r的值
                swap(arr, _left, _right);
                // l、r各收缩一位
                _left++; _right--;
                /** 注意：这里会造成两种情况
                 *  1 这里会因为 _left > _right，跳出循环，此时_right指向的数 < pivot数
                 *  2 这里会因为 _left == _right，造成跳出循环，此时_right指向的数 不知道 和 pivot数的大小关系
                 *  */
            }
        }
        // 根据情况交换right和pivot
        if (_left > _right) {
            // 当_left > _right，那么直接交换即可：（因为_right此时的位置处于之前_left的位置，之前所有的_left位置数一定<=pivot的）
            // 例如：14  5  13  33  2  20  99  57
            //      p              r  l
            // 交换pivot和right
            swap(arr, _right, pivot);
        } else if (_left == _right) {
            // 当_left == _right，需要判断：（因为_right和_left此时刚碰到一块，不确定）
            // 例1：20  5  13  33  2  14  99  57
            //      p                l|r
            // 此时交换p和r，然后以r作为分界线即可
            if (arr[_right] < arr[pivot]) {
                // 交换pivot和right
                swap(arr, _right, pivot);
            }
            // 例2：20  5  13  33  2  99  99  57
            //      p                l|r
            // 此时不能交换p和r，并且不能以r作为分界线，因为r右面的数只保证全部比20小，不能保证全部比99小
            else if (arr[_right] > arr[pivot]) {
                // r左移一位
                _right--;  // 这里right--一定不会<0，因为_right最小也等于_left，而_left是基准元素的下一个
                // 交换r和p
                swap(arr, _right, pivot);
            } else {
                // 相等情况不需要任何操作
            }
        }
        // 此时的_right即为临界线
        return _right;
    }
    /** 思路三优化写法：左右指针l、r交换位置寻找分界点
     * */
    public static int partition3_1(int[] arr, int left, int right) {
        // 1.计算出临界索引
        // 1.1.初始化基准元素
        /** 废弃注意：_left和_right是一定要设置的，因为下面的操作会改变left和right的值，导致递归传参传入改变后的left和right的值 */
        int pivot = left;
        int _left = pivot+1;
        int _right = right;
        // 1.2.进行初始排序
        while (_left < _right) {
            /** 注意：下面两个while循环中的_left<_right一定要加上，不然会导致索引越出的错误 */
            // 移动 l++, 找到 > 基准的数
            while (_left < _right && arr[_left] <= arr[pivot]) _left++;
            // 移动 r--，找到 <= 基准的数
            while (_left < _right && arr[_right] > arr[pivot]) _right--;
            // 交换 l、r的值
            swap(arr, _left, _right);
            /** 注意：这里会因为 _left == _right，造成跳出循环，此时_right指向的数 不知道 和 pivot数的大小关系 */
        }
        // 此时一定是_left == _right的情况
        // 根据情况交换right和pivot
        if (arr[_right] > arr[pivot]) {
            // 例2：20  5  13  33  2  99  99  57
            //      p                l|r
            // 此时不能交换p和r，并且不能以r作为分界线，因为r左面的数只保证全部比20小，不能保证全部比99小
            // 需要r左移一位
            _right--;  // 这里right--一定不会<0，因为_right最小也等于_left，而_left是基准元素的下一个
        }
        // 交换r和p
        swap(arr, _right, pivot);
        // 此时的_right即为临界线
        return _right;
    }

    // 交换值
    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

}

package sort;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 堆排序是完全二叉树，但不是排序二叉树
 * 排序二叉树：兄弟节点之间需要有大小关系，例如：左孩子节点 < 父节点 < 右孩子节点
 * 堆排序：兄弟节点之间没有大小关系，只有父节点和孩子节点之间有大小关系。
 */

public class HeapSort {

    public static void main(String[] args) {
        int[] arr = new int[]{-1,-6,3,2,3,100,4,9,10,8,7,99,7,4,1,0,-1,51,-3,0,88,-9,9,6,59,-55};
        arr = new int[]{20,5,13,33,2,99,14,100,99,57,-1,3,45,0,-1,-1,-6,3,2,3,100};
        int[] sort = sort1(arr);  // 递归法
        for (int n : sort) {
            System.out.print(n);
            System.out.print(' ');
        }
    }

    // 堆排序在java中的应用总结的很全：https://blog.csdn.net/qq_42449106/article/details/130857179
    // 图解过程简单明了：https://blog.csdn.net/d875708765/article/details/108531476

    // 分析过程在语雀上：https://www.yuque.com/u1589209/za9w05/qdzixcfa92r0il1g

    /** 堆排序的两个步骤：
     *  0 前置知识：最初的arr数组结构相当于无序堆
     *  1 由arr无序堆构建最大堆|最小堆
     *  2 进行堆排序
     */
    public static int[] sort1(int[] arr) {

        if (arr.length < 2) return arr;
        int aSize = arr.length;

        // 进行堆构建
        int nonLeafLastIndex = (int) Math.floor(aSize/2) - 1;
        // 倒着遍历非叶子节点  这两个操作细化后时间复杂度约等于O(n)
        for (int i=nonLeafLastIndex; i>=0; i--) {  // O(n)
            // 依次进行堆化
            // heapify1(arr, i, aSize);  // O(logn)  栈迭代
            heapify2(arr, i, aSize);  // O(logn)  普通迭代
            // heapify(arr, i, aSize);  // O(logn)  递归

        }

        // 进行堆排序
        // 倒着遍历每个节点（因为每次末尾元素要和i=0的元素交换）
        for (int i=aSize-1; i>=0; i--) {
            // 当前末尾元素与堆顶元素(最大值)进行交换
            swap(arr, 0, i);
            // 断开最后的元素（因为最大值已经确定了）
            aSize--;
            // 从堆顶元素重新构建堆
            // heapify1(arr, 0, aSize);  // 栈迭代法
            heapify2(arr, 0, aSize);  // 普通迭代法
            // heapify(arr, 0, aSize);  // 递归法
        }

        // 返回排序的数组
        return arr;
    }

    // 堆化过程（递归）
    public static void heapify(int[] arr, int i, int len) {
        int father = i;
        int leftSon = 2*i+1;
        int rightSon = 2*i+2;
        // 如果当前节点没有子节点了，不需要进行堆化，直接返回
        if (leftSon >= len && rightSon >= len) return;
        // 找两孩子中较大数的索引
        int maxi;
        if (leftSon >= len) {
            maxi = rightSon;
        } else if (rightSon >= len) {
            maxi = leftSon;
        } else {
            maxi = arr[leftSon] > arr[rightSon] ? leftSon : rightSon;
        }
        // 比较父亲节点和孩子节点的大小关系
        if (arr[father] < arr[maxi]) {
            // 交换数字
            swap(arr, father, maxi);
            // 递归找子孙节点进行堆构建
            heapify(arr, maxi, len);
        }
    }

    // 堆化过程（栈迭代）
    public static void heapify1(int[] arr, int i, int len) {
        // 定义栈
        class Info {
            int i;
            int len;
            Info(int i, int len) {
                this.i = i;
                this.len = len;
            }
        }
        Stack<Info> stack = new Stack<>();
        // 初始化栈数据
        stack.push(new Info(i,len));
        // 模拟递归
        while (!stack.isEmpty()) {
            // 取栈帧数据
            Info info = stack.pop();
            int _i = info.i;
            int _len = info.len;
            // 执行函数逻辑
            int father = _i;
            int leftSon = 2 * _i + 1;
            int rightSon = 2 * _i + 2;
            // 如果当前节点没有子节点了，不需要进行堆化，直接返回
            if (leftSon >= _len && rightSon >= _len) return;
            // 找两孩子中较大数的索引
            int maxi;
            if (leftSon >= _len) {
                maxi = rightSon;
            } else if (rightSon >= _len) {
                maxi = leftSon;
            } else {
                maxi = arr[leftSon] > arr[rightSon] ? leftSon : rightSon;
            }
            // 比较父亲节点和孩子节点的大小关系
            if (arr[father] < arr[maxi]) {
                // 交换数字
                swap(arr, father, maxi);
                // 入栈找子孙节点进行堆构建
                stack.push(new Info(maxi, len));
            }
        }
    }

    // 堆化过程（普通迭代）又称为【下沉】【AdjustDown】
    public static void heapify2(int[] arr, int i, int len) {
        int father = i;
        int leftSon = 2*i+1;
        int rightSon = 2*i+2;
        // 1.以当前节点为根节点，一直往下找进行堆化
        while (leftSon < len || rightSon < len) {
            // 2.找两孩子中较大数的索引
            int maxi;
            if (leftSon >= len) {
                maxi = rightSon;
            } else if (rightSon >= len) {
                maxi = leftSon;
            } else {
                maxi = arr[leftSon] > arr[rightSon] ? leftSon : rightSon;
            }
            // 3.比较父亲节点和孩子节点的大小关系
            // 3.1.如果父亲节点已经比孩子节点都大，则不需要再往深处搜索了
            if (arr[father] >= arr[maxi]) return;
            // 3.2.否则需要继续向深处搜索进行堆化
            // 3.2.1.交换数字
            swap(arr, father, maxi);
            // 3.2.2.以下一层变化后的孩子节点为根节点进行堆构建
            father = maxi;
            leftSon = 2*father + 1;
            rightSon = 2*father + 2;
        }
    }

    // 上浮操作【AdjustUp】
    public static void adjustUp(int[] arr, int soni) {
        // 1.找到当前i的父节点
        int father = (soni-1)/2;
        // 2.开始上浮
        while (father >= 0) {
            // 判断当前孩子结点是否需要上浮（最大堆）
            if (arr[soni] <= arr[father]) return;  // 已满足条件，不需要上浮
            // 孩子上浮
            swap(arr, father, soni);
            // 继续向上比较
            father = (father-1)/2;
        }
    }

    // 插入操作
    public static void insert(int[] arr, int aSize, int num) {
        /**
         * aSize：当前堆的容量大小
         * num: 要插入的元素值
         */
        // 如果容量超限，则增长一倍
        if (aSize == arr.length) Arrays.copyOf(arr, aSize*2);

        // 将新元素插入堆底
        arr[aSize] = num;

        // 上浮元素
        adjustUp(arr, aSize);
    }

    // 删除操作
    public static void delete(int[] arr, int aSize) {
        /**
         * aSize: 当前堆的容量大小
         */
        // 将堆顶元素断开（和最后一个元素交换）
        swap(arr, 0, aSize-1);

        // 容量减一
        aSize--;

        // 下沉元素
        heapify2(arr, 0, aSize);
    }

    // 交换元素
    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

}



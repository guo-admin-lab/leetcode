package sort;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeSort {

    public static void main(String[] args) {

        int[] arr = new int[]{-1,-6,3,2,3,100,4,9,10,-22,8,7,99,7,4,1,0,-1,-3,0,-23,21,101,-34};
        int[] sort = sort1(arr);
        for (int n : sort) {
            System.out.print(n);
            System.out.print(' ');
        }

    }

    // 自上而下（由大到小）：递归（所有递归的方法都可以用迭代重写，所以就有了第 2 种方法）
    /**
     * 递归思路：
     *      核心：类似于栈，先入后处理完，后入先处理完
     *      递归函数定义：
     *          排序后数组 = fun(原始数组)
     *          2 1 6 4
     *          最后一层：参数(arr)
     *              int len = arr.length
     *              int middle = len/2
     *              _before = arr[0:middle]
     *              _after = arr[middle:len]
     *              before = [1,2] = fun([2,1]) = fun(_before)
     *              after = [4,6] = fun([6,4]) = fun(_after)
     *              比较两部分数组的头位置，进行排序
     *              int[] res = new int[arr.length]
     *              初始化两部分数组的指针：bi=0; ai=0
     *              while (bi < before_len && ai < after_len) {
     *                  if before[bi] < after[ai]:
     *                      res.add(before[bi]);
     *                      bi++;
     *                  else:
     *                      res.add(after[ai]);
     *                      ai++;
     *              }
     *              如果before数组遍历完了，就把after数组剩余的数添加到结果中
     *              if bi == before_len:
     *                  while (ai < after_len):
     *                      res.add(after[ai++]);
     *              if ai == after_len:
     *                  while (bi < before_len):
     *                      res.add(before[bi++]);
     *              返回res(已经排好序)
     *          最里层：
     *              if arr.length == 1: return arr;
     *
     *
     */
    public static int[] sort(int[] sourceArray) {
        // 1.base case
        if (sourceArray.length == 1) return sourceArray;
        // 2.划分数组
        int middleIndex = sourceArray.length/2;
        int[] _beforeArr = Arrays.copyOfRange(sourceArray, 0, middleIndex);
        int[] _afterArr = Arrays.copyOfRange(sourceArray, middleIndex, sourceArray.length);
        // 3.递归将前后两部分数组排序
        int[] beforeArr = sort(_beforeArr);
        int[] afterArr = sort(_afterArr);
        // 4.合并排好序的前后两部分
        /** 这里定义新数组来存储合并后的数据，以空间换时间。
         *  如果不定义新数组，直接将before按照顺序插入到after中，每次需要遍历数组将数据后移，增加了时间复杂度
         * */
        // -----------------这里可以单独写个合并函数-------------------
        int[] res = new int[beforeArr.length+afterArr.length];
        int bi = 0, ai = 0, ri = 0;
        while (bi < beforeArr.length && ai < afterArr.length) {
            if (beforeArr[bi] < afterArr[ai]) {
                res[ri++] = beforeArr[bi++];
            } else {
                res[ri++] = afterArr[ai++];
            }
        }
        // 4.1.如果before数组遍历完了，就把after数组剩余数据全部添加到结果中
        if (bi == beforeArr.length) {
            while (ai < afterArr.length) {
                res[ri++] = afterArr[ai++];
            }
        }
        if (ai == afterArr.length) {
            while (bi < beforeArr.length) {
                res[ri++] = beforeArr[bi++];
            }
        }
        // 5.返回结果
        return res;
        // ---------------------------------------------------------
    }

    // 自下而上（有小到大）：迭代
    /**
     * 迭代思路：
     *      与递归的区别：递归从最外层开始考虑，迭代从最里层开始考虑
     *      2 1 6 4 0 3
     *      第1次：
     *          groupArr = [[2],[1],[6],[4],[0],[3]]  glen = 6
     *          两两排序，排序合并后的分组数目glen=6/2=3
     *          [0,1] [2,3] [4,5]  <->  [0,]// 表示索引
     *          [1,2] [4,6] [0,3] // 表示数字
     *      第2次：
     *          groupArr = [[1,2], [4,6], [0,3]]  glen = 3
     *          两两排序，排序合并后的分组数目glen=3/2=|2| 向上取整
     *          [0,1,2,3] [4,5]  <->  [0,] // 表示索引
     *          [1,2,4,6] [0,3]  // 表示数字
     *      第3次：
     *          groupArr = [[1,2,4,6], [0,3]]  glen=2
     *          两两排序，排序合并后的分组数目glen=2/2=1
     *          [0,1,2,3,4,5]  <->  [0,len-1]// 表示索引
     *          [0,1,2,3,4,6]  // 表示数字
     *
     *      总结：
     *          1 初始化groupArr
     *          2 遍历groupArr，将其元素两两合并
     *       while (groupArr.length > 1) {  // 当存在至少两个数组时，才需要去进行排序合并
     *          2.1 计算两两合并后的数组大小，并创建新数组
     *              glen = groupArr.length
     *              merger_glen = | glen/2 | 向上取整，使其偏大
     *              merger_group = new int[merger_glen]
     *          2.2 遍历groupArr，开始两两合并
     *              a) i=1; i<glen; i=i+2
     *                  1] groupArr[i-1]和groupArr[i]中的数两两排序合并，得到res
     *                      1) 初始化两数组和结果数组
     *                          li = 0, ri = 0;
     *                          leftArr = groupArr[i-1]
     *                          rightArr = groupArr[i]
     *                          res = new int[leftArr.len + rightArr.len]
     *                      2) 开始比较合并
     *                          while (li < leftArr.length && ri < rightArr.length) {
     *                              if leftArr[li] < rightArr[ri]:
     *                                  res.add(leftArr[li])
     *                                  li++;
     *                              else:
     *                                  res.add(rightArr[ri])
     *                                  ri++;
     *                          }
     *                          if (li == leftArr.length) {
     *                              while(ri < rightArr.length) {
     *                                  res.add(rightArr[ri])
     *                                  ri++;
     *                              }
     *                          }
     *                          if (ri == rightArr.length) {
     *                              同上
     *                          }
     *                  2] 合并后的数组添加到merger_group中
     *                      merger_group.add(res)
     *          2.3.由于i一次性加两个，如果groupArr是奇数个元素的话，那么最后一个元素在循环过程中不会添加到merger_group中，所以这里需要判断添加一下
     *               if groupArr.len % 2 != 0:
     *                   merger_group.add(groupArr.get(groupArr.length-1))
     *          2.4 更新groupArr数组
     *              groupArr = merger_group
     *       }
     */
    public static int[] sort1(int[] sourceArray) {
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
        int len = arr.length;
        // 1.初始化groupArr, 将每个元素作为一个组
        List<int[]> groupArr = new ArrayList<>();
        for (int a: arr) {
            groupArr.add(new int[]{a});
        }
        // 当存在至少两个数组时，才需要去进行排序合并
        while (groupArr.size() > 1) {
            // 2.遍历groupArr，将其元素两两合并
            // 2.1.创建新数组存储结果
            List<int[]> mergerGroup = new ArrayList<>();
            // 2.2.遍历groupArr，开始两两合并
            for (int i=1; i<groupArr.size(); i+=2) {
                // -----------------这里可以单独写个合并函数-------------------
                // 初始化两数组和结果数组
                int li = 0, ri = 0, resIndex = 0;
                int[] leftArr = groupArr.get(i-1);
                int[] rightArr = groupArr.get(i);
                int[] resArr = new int[leftArr.length + rightArr.length];
                // 开始两两比较合并
                while (li < leftArr.length && ri < rightArr.length) {
                    if (leftArr[li] < rightArr[ri]) {
                        resArr[resIndex++] = leftArr[li++];
                    } else {
                        resArr[resIndex++] = rightArr[ri++];
                    }
                }
                if (li == leftArr.length) {
                    while (ri < rightArr.length) {
                        resArr[resIndex++] = rightArr[ri++];
                    }
                }
                if (ri == rightArr.length) {
                    while (li < leftArr.length) {
                        resArr[resIndex++] = leftArr[li++];
                    }
                }
                // ---------------------------------------------------------
                // 合并后的数组添加到mergerGroup中
                mergerGroup.add(resArr);
            }
            // 2.3.由于i一次性加两个，如果groupArr是奇数个元素的话，那么最后一个元素在循环过程中不会添加到merger_group中，所以这里需要判断添加一下
            if (groupArr.size() % 2 != 0) {
                mergerGroup.add(groupArr.get(groupArr.size()-1));
            }
            // 2.4.更新groupArr数组
            groupArr = mergerGroup;
        }
        // 此时，groupArr只剩下排好序的一个数组，直接返回
        return groupArr.get(0);
    }
}

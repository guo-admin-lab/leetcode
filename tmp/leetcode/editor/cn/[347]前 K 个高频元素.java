
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public int[] topKFrequent(int[] nums, int k) {
        return method1(nums, k);
    }

    // 方法一：先统计数字的频率，然后排序   O(nlogn)
    public int[] method1(int[] nums, int k) {
        int nLen = nums.length;

        // 1.遍历数组，统计各个数字出现的次数
        Map<Integer, Integer> num2cnt = new HashMap<>();
        for (int n : nums) {
            num2cnt.put(n, num2cnt.getOrDefault(n,0) + 1);
        }
        // 转换成数组，方便排序
        int[][] arr = new int[nLen][2];
        int arri = 0;
        for (Map.Entry<Integer, Integer> entry : num2cnt.entrySet()) {
            int num = entry.getKey();
            int cnt = entry.getValue();
            arr[arri++] = new int[]{num, cnt};
        }

        // 2.根据出现次数进行排序，取前k个元素
        Arrays.sort(arr, (a1,a2)->(a2[1]-a1[1]));  // 降序排序

        // 3.返回结果
        int[] res = new int[k];
        for (int i=0; i<k; i++) {
            res[i] = arr[i][0];
        }
        return res;
    }

    // 方法二：用优先级队列解决这道题
    public int[] method2(int[] nums, int k) {
        // nums 中的元素 -> 该元素出现的频率
        HashMap<Integer, Integer> valToFreq = new HashMap<>();
        for (int v : nums) {
            valToFreq.put(v, valToFreq.getOrDefault(v, 0) + 1);
        }

        PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>((entry1, entry2) -> {
            // 队列按照键值对中的值（元素出现频率）从小到大排序
            return entry1.getValue().compareTo(entry2.getValue());
        });

        for (Map.Entry<Integer, Integer> entry : valToFreq.entrySet()) {
            pq.offer(entry);
            if (pq.size() > k) {
                // 弹出最小元素，维护队列内是 k 个频率最大的元素
                pq.poll();
            }
        }

        int[] res = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            // res 数组中存储前 k 个最大元素
            res[i] = pq.poll().getKey();
        }

        return res;
    }

    // 方法三：使用快速选择算法

    // 方法四：使用计数排序算法
    public int[] method4(int[] nums, int k) {
        // nums 中的元素 -> 该元素出现的频率
        HashMap<Integer, Integer> valToFreq = new HashMap<>();
        for (int v : nums) {
            valToFreq.put(v, valToFreq.getOrDefault(v, 0) + 1);
        }

        // 频率 -> 这个频率有哪些元素
        ArrayList<Integer>[] freqToVals = new ArrayList[nums.length + 1];
        for (int val : valToFreq.keySet()) {
            int freq = valToFreq.get(val);
            if (freqToVals[freq] == null) {
                freqToVals[freq] = new ArrayList<>();
            }
            freqToVals[freq].add(val);
        }

        int[] res = new int[k];
        int p = 0;
        // freqToVals 从后往前存储着出现最多的元素
        for (int i = freqToVals.length - 1; i > 0; i--) {
            ArrayList<Integer> valList = freqToVals[i];
            if (valList == null) continue;
            for (int j = 0; j < valList.size(); j++) {
                // 将出现次数最多的 k 个元素装入 res
                res[p] = valList.get(j);
                p++;
                if (p == k) {
                    return res;
                }
            }
        }

        return null;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

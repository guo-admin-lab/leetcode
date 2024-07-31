public class Test42 {

    public static void main(String[] args) {
        int[] nums = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int res = method2(nums);
        System.out.println(res);
    }

    public static int method2(int[] height) {
        int hSize = height.length;
        int sum = 0;
        // 1.记录第i柱子的左边最大柱子高度和右边最大高度  lMaxMemos[i]: i柱子左边的最大高度
        int[] lMaxMemos = new int[hSize], rMaxMemos = new int[hSize];
        lMaxMemos[0] = height[0];
        for (int i=1; i<hSize; i++) {
            lMaxMemos[i] = Math.max(lMaxMemos[i-1], height[i]);
        }
        rMaxMemos[hSize-1] = height[hSize-1];
        for (int i=hSize-2; i>=0; i--) {
            rMaxMemos[i] = Math.max(rMaxMemos[i+1], height[i]);
        }
        // 2.遍历计算
        for (int i=0; i<hSize; i++) {
            sum += 1 * ( Math.min(lMaxMemos[i], rMaxMemos[i]) - height[i] );
        }
        return sum;
    }

}

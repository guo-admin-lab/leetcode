
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public int[] asteroidCollision(int[] asteroids) {

        Stack<Integer> stk = new Stack<>();

        for (int i=0; i<asteroids.length; i++) {
            /** 思路：
             元素不断出栈：inNum
             即将入栈的元素：outNum
             inNum*outNum同向，跳出
             inNum*outNum反向发生碰撞，最终留下outNum
             outNum入栈
             */
            int outNum = asteroids[i];  // 初始化栈外元素
            boolean alive = true;  // 标识outNum还活着，如果两个星球同归于尽了，就不活着了
            while (!stk.isEmpty()) {
                int inNum = stk.peek();
                // 注意：如果栈外元素已经死了，不需要再碰撞了
                if (!alive || (inNum<0 && outNum>0) || outNum * inNum > 0) {
                    // 同向而行
                    break;
                }
                // 反向而行
                inNum = stk.pop();
                // 进行碰撞
                if (Math.abs(outNum) > Math.abs(inNum)) {
                    outNum = outNum;
                } else if (Math.abs(outNum) < Math.abs(inNum)) {
                    outNum = inNum;
                } else {
                    // 同归于尽
                    alive = false;
                }
            }

            if (alive) {
                // outNum入栈
                stk.push(outNum);
            }
            // System.out.println(outNum);
        }

        // 组织结果
        int size = stk.size();
        int[] res = new int[size];
        for (int i=size-1; i>=0; i--) {
            res[i] = stk.pop();
        }

        return res;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

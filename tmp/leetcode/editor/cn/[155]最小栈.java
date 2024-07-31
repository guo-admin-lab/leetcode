
//leetcode submit region begin(Prohibit modification and deletion)
// 用min对象实现
class MinStack {

    /** 实现思路1（有错误）：
     *  用队列模拟栈，并且用一个变量记录最小值
     *  错误原因：
     *      当向队列中添加元素时，可以更新最小值。
     *      但是从队列中弹出元素恰好等于最小值的时候，此时需要找到次小值，但是没有办法找到。
     * */

    /** 实现思路2：
     *  用另一个辅助栈记录当前的最小值
     *
     *  栈顶
     *  元素栈    最小值栈
     *  1        1
     *  3        2
     *  2        2
     *  3        3
     *  栈底
     *
     */

    class Info {
        int val;
        int min;
        Info(int val, int min) {
            this.val = val;
            this.min = min;
        }
    }

    Stack<Info> sk;

    public MinStack() {
        sk = new Stack<>();
    }

    public void push(int val) {
        if (sk.isEmpty()) {
            sk.push(new Info(val, val));
            return;
        }
        Info preInfo = sk.peek();
        int preMin = preInfo.min;
        int curMin = val < preMin ? val : preMin;
        sk.push(new Info(val, curMin));
    }

    public void pop() {
        sk.pop();
    }

    public int top() {
        Info info = sk.peek();
        return info.val;
    }

    public int getMin() {
        Info info = sk.peek();
        return info.min;
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
//leetcode submit region end(Prohibit modification and deletion)

// 用双栈实现
class MinStack1 {
    // 记录栈中的所有元素
    Stack<Integer> stk = new Stack<>();
    // 阶段性记录栈中的最小元素
    Stack<Integer> minStk = new Stack<>();

    public void push(int val) {
        stk.push(val);
        // 维护 minStk 栈顶为全栈最小元素
        if (minStk.isEmpty() || val <= minStk.peek()) {
            // 新插入的这个元素就是全栈最小的
            minStk.push(val);
        } else {
            // 插入的这个元素比较大
            minStk.push(minStk.peek());
        }
    }

    public void pop() {
        stk.pop();
        minStk.pop();
    }

    public int top() {
        return stk.peek();
    }

    public int getMin() {
        // minStk 栈顶为全栈最小元素
        return minStk.peek();
    }
}



// 用双栈实现——空间复杂度优化版
/**
 * 栈顶
 * 元素栈    最小值栈（只维护栈顶为最小值即可）
 * 3
 * 1
 * 3       1
 * 2       2
 * 栈底
 *
 * 当 stk.top() == minStk.top()时，minStk的栈顶元素才弹出
 */
class MinStack {
    // 记录栈中的所有元素
    Stack<Integer> stk = new Stack<>();
    // 阶段性记录栈中的最小元素
    Stack<Integer> minStk = new Stack<>();

    public void push(int val) {
        stk.push(val);
        // 维护 minStk 栈顶为全栈最小元素
        if (minStk.isEmpty() || val <= minStk.peek()) {
            // 新插入的这个元素就是全栈最小的
            minStk.push(val);
        }
    }

    public void pop() {
        // 注意 Java 的语言特性，比较 Integer 相等要用 equals 方法
        if (stk.peek().equals(minStk.peek())) {
            // 弹出的元素是全栈最小的
            minStk.pop();
        }
        stk.pop();
    }

    public int top() {
        return stk.peek();
    }

    public int getMin() {
        // minStk 栈顶为全栈最小元素
        return minStk.peek();
    }
}

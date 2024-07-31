import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Test155 {

    public static void main(String[] args) {

        MinStack155 minsk = new MinStack155();
        minsk.push(-2);
        minsk.push(0);
        minsk.push(-3);
        System.out.println(minsk.getMin());
        minsk.pop();
        System.out.println(minsk.top());
        System.out.println(minsk.getMin());
    }

}



//leetcode submit region begin(Prohibit modification and deletion)
class MinStack155 {

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

    MinStack155() {
        sk = new Stack<>();
    }

    public void push(int val) {
        if (sk.isEmpty()) {
            sk.push(new Info(val, val));
            return;
        }
        Info preInfo = sk.peek();
        int preMin = preInfo.min;
        int curMin = Math.min(val, preMin);
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


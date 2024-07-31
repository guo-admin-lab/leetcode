import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class Test226 {

    public static void main(String[] args) {
        Deque<Integer> queue = new LinkedList<>();
        Stack<Integer> stack = new Stack<>();
        Integer push = stack.push(null);
        Integer pop = stack.pop();
        System.out.println(pop);
    }

}

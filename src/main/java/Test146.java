import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Test146 {

    private int a;

    private void test() {
        int a = 0;
        this.a = 1;
    }

    public static void main(String[] args) {
        Map<Integer, int[]> map = new HashMap<>();
        map.put(1, new int[]{1,1});
        map.remove(1);
        System.out.println(map);
//        map.put(1,1);
//        map.put(2,2);
//        map.put(null, 3);
//        System.out.println(map);
//        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
//            Integer value = entry.getValue();
//            Integer key = entry.getKey();
//            System.out.println(value);
//            System.out.println(key);
//        }
//        Integer a = 1;
//        int b = 1;
//        System.out.println(a.equals(b));

    }

}

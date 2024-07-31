import java.util.*;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {
        String xxx = "a;b;";
        String[] split = xxx.split(";");
        for (String s : split) {
            System.out.println(s);
        }
    }

}

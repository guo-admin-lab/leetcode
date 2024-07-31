import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Test20 {

    public static void main(String[] args) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        Set<Object> objects = objectObjectHashMap.keySet();
        Collection<Object> values = objectObjectHashMap.values();
        HashSet<Object> objects1 = new HashSet<>(values);


    }

}

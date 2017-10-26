import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SomeSet {
    public static void main(String[] args) {
        String o1 = "1,2,3,4";
        String o2 = "2,3,4,5,6";

        String[] r1 = o1.split(",");
        String[] r2 = o2.split(",");

        /* 交集 */
        Set<String> intersection = new HashSet<String>();
        intersection.addAll(Arrays.asList(r1));
        intersection.retainAll(Arrays.asList(r2)); //取交集
        intersection.forEach(System.out::print);
        System.out.println();
        /* 聯集 */
        Set<String> union = new HashSet<String>();
        union.addAll(Arrays.asList(r1));
        union.addAll(Arrays.asList(r2));
        System.out.println(String.join(",",union));
        /* 差集 */
        Set<String> complement = new HashSet<String>();
        complement.addAll(union);
        complement.removeAll(intersection);
        complement.forEach(x -> System.out.print(x));
    }
}
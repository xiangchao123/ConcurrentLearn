import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xiangchao on 2020/4/22.
 */
public class TEst1 {
    private static final AtomicInteger i = new AtomicInteger(1);
    private static final int j=1;
    public static void main(String[] args) {
        System.out.println(i.incrementAndGet());
    }
}

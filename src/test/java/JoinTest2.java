import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;

/**
 * Created by xiangchao on 2020/4/15.
 */
@Slf4j(topic = "c.JoinTest2")
public class JoinTest2 {
    static int r1 = 0;
    static int r2 = 0;
    public static void main(String[] args) throws InterruptedException {
        test2();
    }
    private static void test2() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r1 = 10;
        });
        Thread t2 = new Thread(() -> {
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r2 = 20;
        });
        t1.start();
        t2.start();
        long start = System.currentTimeMillis();
        log.debug("join start");
        t1.join();
        log.debug("t1 join end");
        t2.join();
        log.debug("t2 join end");
        long end = System.currentTimeMillis();
        log.debug("r1: {} r2: {} cost: {}", r1, r2, end - start);
    }
}

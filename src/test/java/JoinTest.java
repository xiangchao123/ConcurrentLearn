import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;

/**
 * Created by xiangchao on 2020/4/15.
 */
@Slf4j(topic = "c.JoinTest")
public class JoinTest {
    static int r = 0;
    public static void main(String[] args) throws InterruptedException {
        test1();
    }
    private static void test1() throws InterruptedException {
        log.debug("开始");
        Thread t1 = new Thread(() -> {
            log.debug("开始");
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("结束");
            r = 10;
        });
        t1.start();
        t1.join();//等待t1线程运行结束，所以此时r的值为10
        log.debug("结果为:{}", r);
        log.debug("结束");
    }
}

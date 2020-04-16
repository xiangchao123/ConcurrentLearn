import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by xiangchao on 2020/4/15.
 */
@Slf4j(topic = "c.ParkTest")
public class ParkTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("park...");
            LockSupport.park();//让当前线程停下来
            log.debug("unpark...");
//            log.debug("打断状态：{}", Thread.currentThread().isInterrupted());
            log.debug("打断状态：{}", Thread.interrupted());//改变打断标记为false，后面继续停止
            LockSupport.park();
            log.debug("unpark...");
        }, "t1");
        t1.start();
        Thread.sleep(1000);
        t1.interrupt();
    }
}

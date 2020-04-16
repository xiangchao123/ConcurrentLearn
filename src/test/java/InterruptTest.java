import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;

/**
 * Created by xiangchao on 2020/4/15.
 */
@Slf4j(topic = "c.InterruptTest")
public class InterruptTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                sleep(1);//打断 sleep 的线程, 会清空打断状态，wait,join同理
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");
        t1.start();
        Thread.sleep(500);
        t1.interrupt();
        log.debug(" 打断状态: {}", t1.isInterrupted());
    }
    private static void test2() throws InterruptedException {
        Thread t2 = new Thread(()->{
            while(true) {
                Thread current = Thread.currentThread();
                boolean interrupted = current.isInterrupted();
                if(interrupted) {
                    log.debug(" 打断状态: {}", interrupted);
                    break;
                }
            }
        }, "t2");
        t2.start();
        Thread.sleep(500);
        t2.interrupt();
    }
}

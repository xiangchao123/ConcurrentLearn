import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xiangchao on 2020/4/17.
 * 测试可打断
 */
@Slf4j(topic = "c.TestReentrantLock2")
public class TestReentrantLock2 {
   static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("启动...");
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("等锁的过程中被打断");
                return;
            }
            try {
                log.debug("获得了锁");
            } finally {
                lock.unlock();
            }
        }, "t1");
        lock.lock();
        log.debug("获得了锁");
        t1.start();
        try {
            Thread.sleep(1);
            t1.interrupt();
            log.debug("执行打断");
        } finally {
            lock.unlock();
        }
    }
}

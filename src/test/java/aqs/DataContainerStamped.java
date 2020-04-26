package aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.StampedLock;

import static java.lang.Thread.sleep;

/**
 * Created by xiangchao on 2020/4/26.
 */
@Slf4j(topic = "c.DataContainerStamped")
public class DataContainerStamped {
    public static void main(String[] args) throws InterruptedException {
        DataContainerStamped dataContainer = new DataContainerStamped(1);
        new Thread(() -> {
            try {
                dataContainer.read(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();
        sleep(500);
        new Thread(() -> {
            try {
                dataContainer.read(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();
//        DataContainerStamped dataContainer = new DataContainerStamped(1);
//        new Thread(() -> {
//            dataContainer.read(1);
//        }, "t1").start();
//        sleep(0.5);
//        new Thread(() -> {
//            dataContainer.write(100);
//        }, "t2").start();
    }
    private int data;
    private final StampedLock lock = new StampedLock();
    public DataContainerStamped(int data) {
        this.data = data;
    }
    public int read(int readTime) throws InterruptedException {
        long stamp = lock.tryOptimisticRead();
        log.debug("optimistic read locking...{}", stamp);
        sleep(readTime);
        if (lock.validate(stamp)) {
            log.debug("read finish...{}, data:{}", stamp, data);
            return data;
        }
        // 锁升级 - 读锁
        log.debug("updating to read lock... {}", stamp);
        try {
            stamp = lock.readLock();
            log.debug("read lock {}", stamp);
            sleep(readTime);
            log.debug("read finish...{}, data:{}", stamp, data);
            return data;
        } finally {
            log.debug("read unlock {}", stamp);
            lock.unlockRead(stamp);
        }
    }
    public void write(int newData) throws InterruptedException {
        long stamp = lock.writeLock();
        log.debug("write lock {}", stamp);
        try {
            sleep(2);
            this.data = newData;
        } finally {
            log.debug("write unlock {}", stamp);
            lock.unlockWrite(stamp);
        }
    }
}

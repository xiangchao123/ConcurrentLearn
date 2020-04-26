package aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Thread.sleep;

/**
 * Created by xiangchao on 2020/4/26.
 */
@Slf4j(topic = "c.DataContainer")
public class DataContainer {
    public static void main(String[] args) {
        DataContainer dataContainer = new DataContainer();
        new Thread(() -> {
            try {
                dataContainer.read();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();
        new Thread(() -> {
            try {
                dataContainer.read();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();
//        DataContainer dataContainer = new DataContainer();
//        new Thread(() -> {
//            dataContainer.read();
//        }, "t1").start();
//        Thread.sleep(100);
//        new Thread(() -> {
//            dataContainer.write();
//        }, "t2").start();
    }
    private Object data;
    private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock r = rw.readLock();
    private ReentrantReadWriteLock.WriteLock w = rw.writeLock();
    public Object read() throws InterruptedException {
        log.debug("获取读锁...");
        r.lock();
        try {
            log.debug("读取");
            sleep(1);
            return data;
        }  finally {
            log.debug("释放读锁...");
            r.unlock();
        }
    }
    public void write() throws InterruptedException {
        log.debug("获取写锁...");
        w.lock();
        try {
            log.debug("写入");
            sleep(1);
        }  finally {
            log.debug("释放写锁...");
            w.unlock();
        }
    }
}

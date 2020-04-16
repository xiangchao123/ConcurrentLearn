import lombok.extern.slf4j.Slf4j;

/**
 * Created by xiangchao on 2020/4/16.
 */
@Slf4j(topic = "c.SynchronizedTest")
public class SynchronizedTest {
    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();
        Thread t1 = new Thread(() -> {
            for (int j = 0; j < 5000; j++) {
                room.increment();
            }
            }, "t1");
            Thread t2 = new Thread(() -> {
                for (int j = 0; j < 5000; j++) {
                    room.decrement();
                }
            }, "t2");
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            log.debug("count: {}" , room.get());
        }
}

class Room {
    int value = 0;
    //等价于下面的方法
    public synchronized void increment() {
            value++;
    }
    public void decrement() {
        synchronized (this) {
            value--;
        }
    }
    public  int get() {
        synchronized (this) {
            return value;
        }
    }
}
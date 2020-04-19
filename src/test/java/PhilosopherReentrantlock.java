import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * Created by xiangchao on 2020/4/17.
 */
@Slf4j(topic = "c.PhilosopherReentrantlock")
public class PhilosopherReentrantlock {
    public static void main(String[] args) {
        Chopstick1 c1 = new Chopstick1("1");
        Chopstick1 c2 = new Chopstick1("2");
        Chopstick1 c3 = new Chopstick1("3");
        Chopstick1 c4 = new Chopstick1("4");
        Chopstick1 c5 = new Chopstick1("5");
        new Philosopher1("苏格拉底", c1, c2).start();
        new Philosopher1("柏拉图", c2, c3).start();
        new Philosopher1("亚里士多德", c3, c4).start();
        new Philosopher1("赫拉克利特", c4, c5).start();
        new Philosopher1("阿基米德", c5, c1).start();
    }
}
class Chopstick1 extends ReentrantLock {
    String name;
    public Chopstick1(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "筷子{" + name + '}';
    }
}
@Slf4j(topic = "c.Philosopher")
class Philosopher1 extends Thread {
    Chopstick1 left;
    Chopstick1 right;
    public Philosopher1(String name, Chopstick1 left, Chopstick1 right) {
        super(name);
        this.left = left;
        this.right = right;
    }
    @Override
    public void run() {
        while (true) {
            // 尝试获得左手筷子
            if (left.tryLock()) {
                try {
                    // 尝试获得右手筷子
                    if (right.tryLock()) {
                        try {
                            eat();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            right.unlock();
                        }
                    }
                } finally {
                    left.unlock();
                }
            }
        }
    }
    private void eat() throws InterruptedException {
        log.debug("eating...");
        Thread.sleep(1);
    }
}
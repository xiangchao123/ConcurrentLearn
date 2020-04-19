import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * Created by xiangchao on 2020/4/17.
 */
@Slf4j(topic="c.GuardedObject")
public class GuardedObject {
    // 标识 Guarded Object
    private int id;
    public GuardedObject(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    private Object response;
//    private final Object lock = new Object();
    //获取结果
    public Object get(long millis) {
        synchronized (this) {
// 1) 记录最初时间
            long begin = System.currentTimeMillis();
// 2) 已经经历的时间
            long timePassed = 0;
            while (response == null) {
// 4) 假设 millis 是 1000，结果在 400 时唤醒了，那么还有 600 要等
                long waitTime = millis - timePassed;
                log.debug("waitTime: {}", waitTime);
                if (waitTime <= 0) {
                    log.debug("break...");
                    break; }
                try {
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
// 3) 如果提前被唤醒，这时已经经历的时间假设为 400
                timePassed = System.currentTimeMillis() - begin;
                log.debug("timePassed: {}, object is null {}",
                        timePassed, response == null);
            }
            return response; }
    }
    //产生结果
    public void complete(Object response) {
        synchronized (this) {
// 条件满足，通知等待线程
            this.response = response;
            this.notifyAll();
        }
    }
//    public static void main(String[] args) {
//        GuardedObject guardedObject = new GuardedObject();
//        new Thread(() -> {
//            log.debug("等待结果");
//            List<String> list = (List<String>) guardedObject.get(5000);
//            log.debug("大小：{}", list.size());
//        }, "t1").start();
//        new Thread(() -> {
//            try {
//// 子线程执行下载
//                log.debug("执行下载");
//                List<String> response = Downloader.download();
//                log.debug("download complete...");
//                guardedObject.complete(response);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }, "t2").start();
////        log.debug("waiting...");
////        // 主线程阻塞等待
////        Object response = guardedObject.get(5000);
////        log.debug("get response: [{}] lines", ((List<String>) response).size());
//    }
public static void main(String[] args) throws InterruptedException {
    for (int i = 0; i < 3; i++) {
        new People().start();
    }
    Thread.sleep(1);
    for (Integer id : Mailboxes.getIds()) {
        new Postman(id, "内容" + id).start();
    }
}
}
class Mailboxes {
    private static Map<Integer, GuardedObject> boxes = new Hashtable<>();
    private static int id = 1;
    // 产生唯一 id
    private static synchronized int generateId() {
        return id++;
    }
    public static GuardedObject getGuardedObject(int id) {
        return boxes.remove(id);
    }
    public static GuardedObject createGuardedObject() {
        GuardedObject go = new GuardedObject(generateId());
        boxes.put(go.getId(), go);
        return go;
    }
    public static Set<Integer> getIds() {
        return boxes.keySet();
    }
}
@Slf4j(topic = "c.People")
class People extends Thread{
    @Override
    public void run() {
        // 收信
        GuardedObject guardedObject = Mailboxes.createGuardedObject();
        log.debug("开始收信 id:{}", guardedObject.getId());
        Object mail = guardedObject.get(5000);
        log.debug("收到信 id:{}, 内容:{}", guardedObject.getId(), mail);
    }
}
@Slf4j(topic = "c.Postman")
class Postman extends Thread {
    private int id;
    private String mail;
    public Postman(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }
    @Override
    public void run() {
        GuardedObject guardedObject = Mailboxes.getGuardedObject(id);
        log.debug("送信 id:{}, 内容:{}", id, mail);
        guardedObject.complete(mail);
    }
}

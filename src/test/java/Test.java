import lombok.extern.slf4j.Slf4j;

/**
 * Created by xiangchao on 2020/4/15.
 */
@Slf4j(topic = "c.Test")
public class Test {
    static int counter = 0;
    static Object lock = new Object();

//    public static void main(String[] args) throws InterruptedException {
//        Thread t1 = new Thread(() -> {
//            for (int i = 0; i < 5000; i++) {
//                synchronized (lock) {
//                    counter++;
//                }
//            }
//        }, "t1");
//        Thread t2 = new Thread(() -> {
//            for (int i = 0; i < 5000; i++) {
//                synchronized (lock) {
//                    counter--;
//                }
//            }
//        }, "t2");
//        t1.start();
//        t2.start();
//        t1.join();
//        t2.join();
//        log.debug("{}", counter);
//    }
public static void main(String[] args) {
    int a=5;
    if(a>10){
        System.out.println("1");
    }
    else if(a>2){
        System.out.println("2");
    }
    else if(a>3){
        System.out.println("3");
    }
}
}

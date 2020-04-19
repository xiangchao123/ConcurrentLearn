/**
 * Created by xiangchao on 2020/4/18.
 * double-checked locking问题，因为INSTANCE没有完全被synchronized包住，会导致INSTANCE = new BalkingSingleton2();指令重排，
 * t1线程，指令重排后先执行赋值，在调用构造器，从字节码层面来分析的。因此t2线程那么拿到的Instance可能没有初始化完全
 * 解决，给INstance加volatile
 * DCL模式：double-checked locking
 */
public class BalkingSingleton2 {
    private BalkingSingleton2() {
    }

    private volatile static BalkingSingleton2 INSTANCE = null;

    public static synchronized BalkingSingleton2 getInstance() {
        if (INSTANCE == null) {
            synchronized (BalkingSingleton2.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BalkingSingleton2();
                }
            }
        }
        return INSTANCE;
    }
}

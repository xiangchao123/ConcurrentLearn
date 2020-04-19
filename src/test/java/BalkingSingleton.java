/**
 * Created by xiangchao on 2020/4/18.
 * Balking （犹豫）模式用在一个线程发现另一个线程或本线程已经做了某一件相同的事，那么本线程就无需再做
 了，直接结束返回,
 缺点：每个线程都要去获得锁，进入monitor，即使已经创建好了单例
 */
public class BalkingSingleton {
    private BalkingSingleton() {
    }
    private static BalkingSingleton INSTANCE = null;
    public static synchronized BalkingSingleton getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }

        INSTANCE = new BalkingSingleton();
        return INSTANCE;
    }
}

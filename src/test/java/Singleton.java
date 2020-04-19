/**
 * Created by xiangchao on 2020/4/18.
 */
public class Singleton {
    private Singleton() { }
    // 问题1：属于懒汉式还是饿汉式：懒汉式
    private static class LazyHolder {
        static final Singleton INSTANCE = new Singleton();
    }
    // 问题2：在创建时是否有并发问题：没有，类加载时，它对静态变量的赋值操作，有JVM保证线程安全性
    public static Singleton getInstance() {
        return LazyHolder.INSTANCE;
    }
}

import static java.lang.Thread.sleep;

/**
 * Created by xiangchao on 2020/4/18.
 * 可见性解决之volatile，可见性即保证读取到的值为最新的
 * 有序行解决指令重排序
 */
public class VolatileTest {
    //它可以用来修饰成员变量和静态成员变量，他可以避免线程从自己的工作缓存中查找变量的值，必须到主存中获取
   //它的值，线程操作 volatile 变量都是直接操作主存
   //synchronized枷锁也可以，但锁太重，应该用volatile比较轻，适合可见性
    //synchronized可以保证原子性、可见性，volatile只能保证可见性
    volatile static boolean run = true;
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(()->{
            while(run){
                // ....
            }
        });
        System.out.println();
        t.start();
        sleep(1);
        run = false; // 线程t不会如预想的停下来,加volatile
    }
}

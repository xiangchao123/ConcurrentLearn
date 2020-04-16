import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * Created by xiangchao on 2020/4/15.
 */
@Slf4j(topic = "c.ThreadTest")
public class ThreadTest {
    public void test1(){
        Thread t = new Thread(){
            @Override
            public void run() {
                log.debug("running");
            }
        };
        t.setName("t1");
        t.start();
    }
    public void test2() {
        Runnable runnable = () -> log.debug("running");
        Thread thread = new Thread(runnable,"t2");
        thread.start();
        log.debug("running");
    }

    public void test3() throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("running");
                Thread.sleep(2000);
                return 123;
            }
        });
        Thread t3 = new Thread(task,"t3");
        t3.start();
        log.debug("{}",task.get());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //sleep时间单位的修改,JDK1.5后添加的TimeUnit
        log.debug("before");
        TimeUnit.SECONDS.sleep(1);
        log.debug("after");
    }
}

package atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * Created by xiangchao on 2020/4/19.
 * 利用字段更新器，可以针对对象的某个域（Field）进行原子操作，只能配合 volatile 修饰的字段使用，否则会出现
 异常
 */
public class AtomicFiledUpdaterTest {
    private volatile int field;
    public static void main(String[] args) {
        AtomicIntegerFieldUpdater fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(AtomicFiledUpdaterTest.class, "field");
        AtomicFiledUpdaterTest test5 = new AtomicFiledUpdaterTest();
        fieldUpdater.compareAndSet(test5, 0, 10);
        // 修改成功 field = 10
        System.out.println(test5.field);
        // 修改成功 field = 20
        fieldUpdater.compareAndSet(test5, 10, 20);
        System.out.println(test5.field);
        // 修改失败 field = 20
        fieldUpdater.compareAndSet(test5, 10, 30);
        System.out.println(test5.field);
    }
}

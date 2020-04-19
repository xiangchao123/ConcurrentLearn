package unsafe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xiangchao on 2020/4/19.
 */
class TestAccountCas{
    public static void main(String[] args) {
        AccountCas.demo(new AccountSafe(10000));
    }
}

class AccountSafe implements AccountCas {
    private AtomicInteger balance;
    public AccountSafe(Integer balance) {
        this.balance = new AtomicInteger(balance);
    }
    @Override
    public Integer getBalance() {
        return balance.get();
    }
    @Override
    public void withdraw(Integer amount) {
        while (true) {
            int prev = balance.get();
            int next = prev - amount;
            //比较并设置值
            /*
 compareAndSet 正是做这个检查，在 set 前，先比较 prev 与当前值
 - 不一致了，next 作废，返回 false 表示失败
 比如，别的线程已经做了减法，当前值已经被减成了 990
 那么本线程的这次 990 就作废了，进入 while 下次循环重试
 - 一致，以 next 设置为新值，返回 true 表示成功
 */
            if (balance.compareAndSet(prev, next)) {
                break;
            }
        }
        // 可以简化为下面的方法
        // balance.addAndGet(-1 * amount);
    }
}

public interface AccountCas {
    // 获取余额
    Integer getBalance();
    // 取款
    void withdraw(Integer amount);
    /**
     * 方法内会启动 1000 个线程，每个线程做 -10 元 的操作
     * 如果初始余额为 10000 那么正确的结果应当是 0
     */
    static void demo(AccountCas accountCas) {
        List<Thread> ts = new ArrayList<>();
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                accountCas.withdraw(10);
            }));
        }
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();
        System.out.println(accountCas.getBalance()
                + " cost: " + (end-start)/1000_000 + " ms");
    }
}

class AccountUnsafe implements AccountCas {
    private Integer balance;
    public AccountUnsafe(Integer balance) {
        this.balance = balance;
    }
    @Override
    public synchronized Integer getBalance() {
        return balance;
    }
    @Override
    public synchronized void withdraw(Integer amount) {
        balance -= amount;
    }
}
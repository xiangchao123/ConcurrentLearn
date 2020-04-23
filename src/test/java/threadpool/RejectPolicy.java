package threadpool;

/**
 * Created by xiangchao on 2020/4/22.
 */
@FunctionalInterface // 拒绝策略
 interface RejectPolicy<T> {
    void reject(BlockingQueue<T> queue, T task);
}

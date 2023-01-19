package cn.ken.deadlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 * ReentrantLock的条件变量测试
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/19 12:07
 */
@Slf4j
public class ReentrantLockTest4 {
    
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Condition condition = lock.newCondition();
        lock.lock();
        try {
            condition.await();
            condition.signal();
            // 唤醒这个条件变量对应队列中的所有线程
            condition.signalAll();
            // 不可被打断的等待
            condition.awaitUninterruptibly();
        } finally {
            lock.unlock();
        }
    }
}

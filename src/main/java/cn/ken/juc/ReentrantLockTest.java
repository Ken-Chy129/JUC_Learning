package cn.ken.juc;

import java.util.concurrent.locks.*;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/2/1 12:11
 */
public class ReentrantLockTest {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock(true);
        Condition condition = reentrantLock.newCondition();
        condition.await();
        condition.signal();
        reentrantLock.wait();
        try {
            
        } finally {
            reentrantLock.unlock();
        }
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        // 会抛出异常，因为读锁不支持条件变量
        Condition condition1 = readLock.newCondition();
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
    }
}

package cn.ken.deadlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 * ReentrantLock的可中断性
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/17 22:48
 */
@Slf4j
public class ReentrantLockTest2 {
    
    private static ReentrantLock lock = new ReentrantLock();
    
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                // 获取可中断锁，lock.lock()获取的是不可中断锁
                // 如果有竞争就就进入阻塞队列，可以被其他线程用interrupt方法打断阻塞等待锁的状态
                log.debug("尝试获取锁");
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("没有获取锁，返回");
                return;
            }
            try {
                log.debug("获取到锁");
            } finally {
                lock.unlock();
            }
        }, "t1");

        lock.lock();
        thread.start();
        
        TimeUnit.SECONDS.sleep(1);
        log.debug("打断t1");
        thread.interrupt();
    }
}

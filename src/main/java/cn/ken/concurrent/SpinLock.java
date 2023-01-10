package cn.ken.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

/**
 * <pre>
 * 自旋锁实现
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2023/1/7 20:49
 */
@Slf4j
public class SpinLock {
    
    // 原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();
    
    public void lock() throws InterruptedException {
        Thread thread = Thread.currentThread();
        log.debug("线程{}正在获取锁，尝试使用cas交换MarkWord", thread.getName());

        // 执行cas操作，期望值为null，更新值是当前线程
        while (atomicReference.compareAndSet(null, thread)) {
            Thread.sleep(1000);
            // 锁已被其它线程持有，目标值不为期望值null，cas失败
            log.debug("cas失败，正在自旋...");
        }
        
        log.debug("自旋成功");
    }
    
    public void unlock() {
        Thread thread = Thread.currentThread();
        // 释放锁，将MarkWord的指针清空
        atomicReference.compareAndSet(thread, null);
        log.info("把指针变为null");
    }
}

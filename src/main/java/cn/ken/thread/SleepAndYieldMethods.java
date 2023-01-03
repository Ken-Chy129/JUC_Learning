package cn.ken.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * <p>Thread类方法：sleep和yield</p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2023/1/2 22:23
 */
@Slf4j
public class SleepAndYieldMethods {
    
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("running");
            try {
                Thread.sleep(2000);
                log.debug("cannot reach due to interrupt");
            } catch (InterruptedException e) {
                log.debug("interrupted");
                e.printStackTrace();
            }
            log.debug("go on"); // 线程继续执行
        }, "t1");
        t1.start();
        
        Thread.sleep(500); // 先让主线程睡眠一段时间，防止调用interrupt方发时t1线程还位睡眠
        log.debug("sleep时的状态:{}", t1.getState()); // TIMED_WAITING
        log.debug("interrupt...");
        t1.interrupt(); // TIME_WAITING
        TimeUnit.MILLISECONDS.sleep(500); // 睡眠1秒，可读性更高，底层还是调用的Thread.sleep
        log.debug("interrupt后的状态:{}", t1.getState()); // TERMINATED(线程已经执行完了)
        
        // yield
        
        Thread t2 = new Thread(() -> {
            log.debug("before");
            Thread.yield();
            log.debug("after"); // 基本上迅速就执行了，因为很快就抢占到了时间片
        }, "t2");
        t2.start();
    }
}

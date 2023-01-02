package cn.ken.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * <p>Thread类方法：sleep和yield</p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2023/1/2 22:23
 */
@Slf4j
public class SleepAndYieldMethod {
    
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
        log.debug("interrupt...");
        t1.interrupt();
    }
}

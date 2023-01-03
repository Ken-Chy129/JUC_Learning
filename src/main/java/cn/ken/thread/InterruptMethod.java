package cn.ken.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * <pre>
 * <p>Thread类方法：interrupt</p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2023/1/3 17:00
 */
@Slf4j
public class InterruptMethod {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                log.debug("sleep...");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                log.debug("异常处理时t1的打断标记:{}", Thread.currentThread().isInterrupted()); // false
                e.printStackTrace();
            }
            log.debug("被打断了");
        }, "t1");
        t1.start();
        
        t1.interrupt(); // 如果在睡眠前（正常执行时）打断，则打断标记为真
        // 即如果是打断正常运行的程序而不是睡眠中的程序，打断标记才为真
        // 这个标记是用来做一些结束工作的，被打断不会结束程序执行，而是给你这个标志告诉你被打断了，让你自己结束
        log.debug("打断标记:{}", t1.isInterrupted());
        
        Thread.sleep(500);
        t1.interrupt(); // 如果在睡眠时(sleep, wait, join)打断，则打断标记为false
        // 因为打断的是睡眠，后面的程序还会继续运行，即正常运行流程没有被打断，所以打断标记为假
        log.debug("打断标记:{}", t1.isInterrupted());
    }
}

@Slf4j
class TwoPhaseTermination {
    
    private Thread monitor;
    
    // 开启监控
    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                if (current.isInterrupted()) {
                    log.debug("料理后事");
                    break; // 被打断则执行一些操作后结束线程
                }
                try {
                    Thread.sleep(1000); // 情况1：睡眠时被打断，打断标记为假，进入异常处理
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 重新设置打断标记为真，以使得循环结束
                    current.interrupt();
                }
                log.debug("执行监控..."); // 情况2：正常执行时被打断，打断标记为真
            }
        });
        monitor.start();
    }
    
    // 停止监控
    public void stop() {
        monitor.interrupt();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("park");
            LockSupport.park();
            log.debug("unpark");
            log.debug("打断标记:{}", Thread.currentThread().isInterrupted()); // true
            
            LockSupport.park(); // 如果此处再次调用park程序不会停下，因为当前打断标记为true
            // 如果需要该park能起作用，则上面应该调用interrupted方法而不是isInterrupted方法
            // 因为前者获取标记后会重置为false
            log.debug("正常执行");
        }, "t1");
        t1.start();
        
        Thread.sleep(1000);
        log.debug("park时t1线程的状态:{}", t1.getState()); // WAITING
        log.debug("打断park");
        t1.interrupt();
    }
    
}

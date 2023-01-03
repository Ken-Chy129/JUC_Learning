package cn.ken.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * <p>Java线程的六种状态演示</p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2023/1/3 21:59
 */
@Slf4j
public class ThreadStatus {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("running"); 
        }, "t1");
        
        Thread t2 = new Thread(() -> {
            while (true) {
                
            }
        }, "t2");
        t2.start();
        
        Thread t3 = new Thread(() -> {
            log.debug("running");
        }, "t3");
        t3.start();
        
        Thread t4 = new Thread(() -> {
            synchronized (ThreadStatus.class) {
                try {
                    Thread.sleep(10000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t4");
        t4.start();
        
        Thread t5 = new Thread(() -> {
            try {
                t4.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t5");
        t5.start();
        
        Thread t6 = new Thread(() -> {
            synchronized (ThreadStatus.class) {
                try {
                    Thread.sleep(1000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t6");
        t6.start();
        
        log.debug("t1的状态:{}", t1.getState()); // NEW
        log.debug("t2的状态:{}", t2.getState()); // RUNNABLE
        log.debug("t3的状态:{}", t3.getState()); // TERMINATED
        log.debug("t4的状态:{}", t4.getState()); // TIME_WAITING
        log.debug("t5的状态:{}", t5.getState()); // WAITING
        log.debug("t6的状态:{}", t6.getState()); // BLOCK(等待t4释放锁)
    }
}

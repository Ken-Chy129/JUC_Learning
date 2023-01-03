package cn.ken.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * <p>Thread类方法：join</p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2023/1/3 0:07
 */
@Slf4j
public class JoinMethod {
    
    static int r = 0;
    
    public static void main(String[] args) throws InterruptedException {
        /**
        Thread t1 = new Thread(() -> {
            log.debug("启动");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r = 10;
            log.debug("结束");
        }, "t1");
        t1.join(); // t1线程加入主线程，主线程必须阻塞至其执行完
        t1.start();
//        t1.join(500); // 可以设置最大等待时间，只等待500ms，则r仍然为0
        // 可以同时等待多个线程
        log.debug("r的值为:{}", r); // 调用了join则为10，没调用则为0
         **/
        main2(args);
    }

    public static void main2(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            Thread t2 = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            log.debug("t2的状态:{}", t2.getState()); // NEW
            t2.start();
            log.debug("t2的状态:{}", t2.getState()); // RUNNABLE
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("t2的状态:{}", t2.getState()); // TERMINATED
        }, "t1");
        t1.start();
        Thread.sleep(500);
        log.debug("t1的状态:{}", t1.getState()); // WAITING
    }
}

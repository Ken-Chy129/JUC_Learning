package cn.ken.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/11 22:23
 */
@Slf4j
public class WaitNotifyTest {
    
    static Object object = new Object();

    public static void main(String[] args) throws InterruptedException {
        
//        try {
//            object.wait(); // 会报错，因为线程没有获得对象的锁
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        
        new Thread(() -> {
            synchronized (object) {
                log.debug("线程wait");
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("线程{}被notify", Thread.currentThread().getName());
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (object) {
                log.debug("线程wait");
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("线程{}被notify", Thread.currentThread().getName());
            }
        }, "t2").start();
        
        Thread.sleep(1000);
        synchronized (object) {
//            object.notify(); // 随即唤醒一个线程
            object.notifyAll(); // 全部唤醒
        }
    }
}

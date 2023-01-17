package cn.ken.deadlock;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/17 19:37
 */
@Slf4j
public class DeadlockTest {
    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();
        new Thread(() -> {
            synchronized (lock1) {
                log.debug("获取锁1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("开始获取锁2");
                synchronized (lock2) {
                    log.debug("成功获取锁2");
                }
            }
        }, "t1").start();
        new Thread(() -> {
            synchronized (lock2) {
                log.debug("获取锁2");
                log.debug("开始获取锁1");
                synchronized (lock1) {
                    log.debug("成功获取锁1");
                }
            }
        }, "t2").start();
    }
}

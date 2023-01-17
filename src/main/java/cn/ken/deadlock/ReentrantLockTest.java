package cn.ken.deadlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 * reentrantLock的可重入性
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/17 22:26
 */
@Slf4j
public class ReentrantLockTest {
    
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        lock.lock();
        try {
            log.debug("main");
            m1();
        } finally {
            lock.unlock();
        }
    }
    
    public static void m1() {
        lock.lock();
        try {
            log.debug("m1");
            m2();
        } finally {
            lock.unlock();
        }
    }

    public static void m2() {
        lock.lock();
        try {
            log.debug("m2");
        } finally {
            lock.unlock();
        }
    }
}

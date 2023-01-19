package cn.ken.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 * 三个线程交替输出a,b,c五次:使用await和signal
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/19 22:20
 */
public class AlternateOutputWay2 {
    
    private static final ReentrantLock lock = new ReentrantLock();
    
    public static void main(String[] args) throws InterruptedException {
        Condition c1 = lock.newCondition();
        Condition c2 = lock.newCondition();
        Condition c3 = lock.newCondition();
        new Thread(() -> print("a", c1, c2)).start();
        new Thread(() -> print("b", c2, c3)).start();
        new Thread(() -> print("c", c3, c1)).start();
        
        Thread.sleep(1000);
        lock.lock();
        try {
            c1.signal();
        } finally {
            lock.unlock();
        }
    }
    
    public static void print(String strToPrint, Condition condition, Condition nextCondition) {
        for (int i=1; i<5; i++) {
            lock.lock();
            try {
                condition.await();
                System.out.print(strToPrint);
                nextCondition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}

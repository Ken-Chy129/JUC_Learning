package cn.ken.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 * 三个线程交替输出a,b,c五次:使用park和unpark
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/19 22:20
 */
public class AlternateOutputWay3 {
    
    private static Thread t1, t2, t3; 

    public static void main(String[] args) {
        t1 = new Thread(() -> print("a", t2));
        t2 = new Thread(() -> print("b", t3));
        t3 = new Thread(() -> print("c", t1));
        t1.start();
        t2.start();
        t3.start();
        LockSupport.unpark(t1);
    }
    
    public static void print(String strToPrint, Thread next) {
        for (int i=0; i<5;  i++) {
            LockSupport.park();
            System.out.print(strToPrint);
            LockSupport.unpark(next);
        }
    } 
}

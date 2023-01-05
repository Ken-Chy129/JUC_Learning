package cn.ken.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * <pre>
 * <p>转账场景体会锁的使用</p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2023/1/5 23:19
 */
@Slf4j
public class TransferMoneyExample {

    public static void main(String[] args) throws InterruptedException {
        Customer a = new Customer(1000);
        Customer b = new Customer(1000);
        Random random = new Random();
        List<Thread> threads = new ArrayList<>();
        for (int i=1; i<=1000; i++) {
            Thread thread1 = new Thread(() -> {
                a.transfer(b, random.nextInt(100));
            });
            threads.add(thread1);
            thread1.start();
            Thread thread2 = new Thread(() -> {
                b.transfer(a, random.nextInt(100));
            });
            threads.add(thread2);
            thread2.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        log.debug("a用户当前金额:{}", a.getMoney());
        log.debug("b用户当前金额:{}", b.getMoney());
    }
}

class Customer {
    
    private Integer money;

    public Customer(Integer money) {
        this.money = money;
    }

    public Integer getMoney() {
        return this.money;
    }
    
    public Integer transfer(Customer other, Integer money) {
        // 如果锁的是对象仍然会出现线程安全问题，锁a只能避免a向b转账时的线程安全网问题
        // 即如果只是单项转账则没有问题，因为都是a对象操作，有一个线程操作其他就会阻塞
        // 但是同时b也在向a转账，和a是两个不同的对象，a转账时b不会被阻塞，就仍然存在线程安全问题
        synchronized (this) {
            if (this.money < money) {
                return 0;
            }
            other.money += money;
            this.money -= money;
            return money;
        }
    }
}

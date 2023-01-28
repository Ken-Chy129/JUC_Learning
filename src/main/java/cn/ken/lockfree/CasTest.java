package cn.ken.lockfree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/25 22:28
 */
public class CasTest {

    private AtomicInteger num;

    public CasTest(int num) {
        this.num = new AtomicInteger(num);
    }

    public Integer getNum() {
        return num.get();
    }
    
    public void decrease(int dis) {
        while (true) {
            int now = num.get();
            int next = now - dis;
            if (num.compareAndSet(now, next)) { // 操作系统层面的原子操作
                break;
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        List<Thread> list = new ArrayList<>();
        CasTest ticket = new CasTest(100000);
        for (int i=0; i<100000; i++) {
            Thread thread = new Thread(() -> {
                ticket.decrease(1);
            });
            list.add(thread);
            thread.start();
        }
        for (Thread thread : list) {
            thread.join();
        }
        System.out.println(ticket.getNum());
    }
}

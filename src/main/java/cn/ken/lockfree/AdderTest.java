package cn.ken.lockfree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/28 0:30
 */
public class AdderTest {
    
    public static void main(String[] args) throws InterruptedException {
        LongAccumulator accumulator = new LongAccumulator((x, y) -> x*y, 1);
        accumulator.accumulate(10);
        System.out.println(accumulator.get());
        accumulator.accumulate(10);
        System.out.println(accumulator.get());
        
        LongAdder longAdder = new LongAdder();
        List<Thread> threads1 = new ArrayList<>();
        for (int i=1; i<=10; i++) {
            threads1.add(new Thread(() -> {
                for (int j=0; j<500000; j++) {
                    longAdder.increment();
                }
            }));
        }
        long time1 = System.nanoTime();
        threads1.forEach(Thread::start);
        for (Thread thread : threads1) {
            thread.join();
        }
        System.out.println(System.nanoTime() - time1);

        AtomicLong atomicLong = new AtomicLong(0);
        List<Thread> threads2 = new ArrayList<>();
        for (int i=1; i<=10; i++) {
            threads2.add(new Thread(() -> {
                for (int j=0; j<500000; j++) {
                    atomicLong.incrementAndGet();
                }
            }));
        }
        long time2 = System.nanoTime();
        threads2.forEach(Thread::start);
        for (Thread thread : threads2) {
            thread.join();
        }
        System.out.println(System.nanoTime() - time2);
    }
}

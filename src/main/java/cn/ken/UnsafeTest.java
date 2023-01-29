package cn.ken;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/28 22:33
 */
public class UnsafeTest {
    
    private static int num = 10000;
    
    public static void main(String[] args) throws InterruptedException {
        MyAtomicInteger myAtomicInteger = new MyAtomicInteger(10000);
        List<Thread> threads = new ArrayList<>();
        for (int i=1; i<=5; i++) {
            threads.add(new Thread(() -> {
                for (int j=1; j<=2000; j++) {
                    myAtomicInteger.decrement(1);
                    num -= 1;
                }
            }));
        }
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println(myAtomicInteger.getValue());
        System.out.println(num);
    }
}

class MyAtomicInteger {
    
    private volatile int value;
    private static final long valueOffset;
    private static final Unsafe unsafe;
    
    static {
        try {
            // Unsafe.getUnsafe()方法会报错，需要反射获取
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null); // theUnsafe是静态变量，不属于某个对象
            valueOffset = unsafe.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public MyAtomicInteger(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void decrement(int x) {
        int prev, next;
        do {
            prev = getValue();
            next = prev - x;
        } while (!compareAndSwap(prev, next));
    }
    
    public boolean compareAndSwap(int prev, int next) {
        return unsafe.compareAndSwapInt(this, valueOffset, prev, next);
    } 
}

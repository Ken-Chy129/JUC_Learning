package cn.ken.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * <pre>
 * <p>线程不安全的情况分析</p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2023/1/4 19:55
 */
@Slf4j
public class ThreadSafety {

    public static void main(String[] args) {
        ThreadSafety threadSafety = new ThreadSafety();
        threadSafety.method1();
    }
    
    public void method1() {
        for (int i=1; i<=2; i++) {
            new Thread(() -> {
                ArrayList<Integer> list = new ArrayList<>();
                for (int j = 1; j <=2000; j++) { // 因为list是局部变量，所以两个线程使用的是不同的list，没有共享，不会出现线程安全问题
                    method2(list);
                    method3(list);
                    log.debug("{}", list.size());
                }
            }, "Thread-"+i).start();
        }
    }
    
    public void method2(ArrayList<Integer> list) {
        list.add(1);
    }

    // 因为此处设置的是public，所以子类可以重写该方法
    // 如果应该设置为private，避免了子类重写该方法开了新线程操作list而导致method1方法线程不安全
    public void method3(ArrayList<Integer> list) {
        log.debug("111");
        list.remove(0);
    }
}

class ThreadNotSafety extends ThreadSafety {

    // 重写了父类的method3，在其中创建了新的线程使用list
    // 当调用method1方法时，将list传递给method3，两个线程共享同一个变量进行读写操作
    // 就导致了线程不安全的问题
    @Override
    public void method3(ArrayList<Integer> list) {
        new Thread(() -> {
            list.remove(0);
        }).start();
    }

    public static void main(String[] args) {
        ThreadSafety safety = new ThreadSafety();
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        safety.method3(list);
//        ThreadNotSafety threadNotSafety = new ThreadNotSafety();
//        threadNotSafety.method1(); // 最终的结果不是0，显然线程不安全
        // 有可能大于0或者报错（数组长度为0后仍去remove）
        // 报错的原因：arrayList的remove方法中执行了--size的方法，可能size减完还未赋值的时候时间片被抢走了
        // 其他线程执行增加之后，回到该线程的时候把之前减完的数赋值回去，覆盖掉了增加，所以导致增加的不够减
        // add同样有++size的问题，同理可能出现减的值被覆盖了，导致最后加的比减的多
        // 即arraylist时线程不安全的，操作没有保证原子性
    }
}

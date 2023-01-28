package cn.ken.lockfree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/27 22:30
 */
public class AtomicArrayTest {

    public static void main(String[] args) {
        demo(
                () -> new int[10],
                array -> array.length,
                (array, index) -> array[index]++,
                array -> System.out.println(Arrays.toString(array))
        );
        
        demo(
                () -> new AtomicIntegerArray(10),
                AtomicIntegerArray::length,
                AtomicIntegerArray::getAndIncrement,
                System.out::println
        );
    }

    /**
     * 多个线程对数组执行每个元素执行累加操作，检测不同类型数组的线程安全性
     * @param arraySupplier 提供数组
     * @param arrayLength 计算数组长度
     * @param operator 执行的操作
     * @param printOperator 打印操作
     */
    public static <T> void demo(
        Supplier<T>  arraySupplier, // 生产者，无参数 -> 返回T
        Function<T, Integer> arrayLength, // 函数，参数T -> 返回Integer， BiFunction接受两参数
        BiConsumer<T, Integer> operator, // 消费者，参数T,Integer，执行操作不返回
        Consumer<T> printOperator // 消费者，参数T，执行操作不反悔
    ) {
        List<Thread> threads = new ArrayList<>();
        T array = arraySupplier.get();
        int len = arrayLength.apply(array);
        for (int i=0; i<len; i++) {
            threads.add(new Thread(() -> {
                for (int j=0; j<10000; j++) {
                    operator.accept(array, j%len);
                }
            }));
        }
        threads.forEach(Thread::start);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        printOperator.accept(array);
    }
}

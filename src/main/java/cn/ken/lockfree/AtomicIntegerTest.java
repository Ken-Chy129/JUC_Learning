package cn.ken.lockfree;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/26 23:23
 */
public class AtomicIntegerTest {
    public static void main(String[] args) {
        // 初始化一个默认值为 0 的原子型 Integer
        AtomicInteger atomicInteger = new AtomicInteger();
        // 初始化一个指定值的原子型 Integer
        AtomicInteger integer = new AtomicInteger(5);

        System.out.println(integer.getAndSet(20)); // 如果调用过程中没有其他线程修改了value，则将value置为20
        System.out.println(integer.compareAndSet(20, 10)); // 如果value=20则置为10并返回true,否则返回false

        System.out.println(integer.incrementAndGet()); // ++i,输出11
        System.out.println(integer.getAndIncrement()); // i++,输出11

        System.out.println(integer.getAndDecrement()); // i--,输出12
        System.out.println(integer.decrementAndGet()); // --i,输出10

        System.out.println(integer.addAndGet(10)); // i+10后输出
        System.out.println(integer.getAndAdd(10)); // 输出后i+10

        System.out.println(integer.getAndDecrement()); // 默认为减1，即输出后i-1
        System.out.println(integer.decrementAndGet()); // 默认为减1，即i-1后输出

        // 接受一个IntUnaryOperator类型的参数，是一个函数式接口
        // 声明了applyAsInt方法，接受一个整型参数并返回一个整型参数
        System.out.println(integer.updateAndGet(x -> x*-1));

        // 接受一个整数和一个IntBinaryOperator类型的参数，是一个函数式接口
        // 声明了applyAsInt方法，接受两个整型参数，第一个参数是对象本身的value值，第二个参数是原函数的第一个参数
        System.out.println(integer.accumulateAndGet(10, (x, y) -> x - y));

    }
}

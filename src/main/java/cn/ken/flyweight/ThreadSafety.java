package cn.ken.flyweight;

import java.math.BigDecimal;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/29 0:10
 */
public class ThreadSafety {
    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal(10000);
        bigDecimal.subtract(BigDecimal.valueOf(1)); // 该类是不可变类，所以该方法是线程安全的，因为其创建的是新的对象，没有改动旧的对象
        System.out.println(bigDecimal); // 还是10000
        // 单个方法的线程安全组合起来并不一定是线程安全的，如下
        BigDecimal next = bigDecimal.subtract(BigDecimal.valueOf(1)); // 该操作并不是原子的，所以会出现线程安全问题
        // 所以才需要AtomicReference<BigDecimal>的存在
    }
}

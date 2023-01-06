package cn.ken.concurrent;

/**
 * <pre>
 * 轻量级锁
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2023/1/6 23:01
 */
public class LightWeightLock {
    static final Object obj = new Object();
    public static void method1() {
        synchronized (obj) {
            method2();
        }
    }
    public static void method2() {
        synchronized (obj) {
            // 同步块2
        }
    }
}

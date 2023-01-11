package cn.ken.concurrent;

/**
 * <pre>
 * 锁粗化和锁消除
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/11 18:15
 */
public class LockCoarsenAndEliminate {
    
    public void method1() {
        for (int i=0; i<1000; i++) {
            synchronized (LockCoarsenAndEliminate.class) {
                i++;
            }
        }
    }
    
    // method1锁粗化成method2
    public void method2() {
        synchronized (LockCoarsenAndEliminate.class) {
            for (int i = 0; i < 1000; i++) {
                i++;
            }
        }
    }
    
    public void method3() {
        Object object = new Object();
        synchronized (object) {
            System.out.println("hello");
        }
    }
    
    // method3经过锁消除变成method4
    public void method4() {
        Object object = new Object();
        System.out.println("eliminate");
    }
}

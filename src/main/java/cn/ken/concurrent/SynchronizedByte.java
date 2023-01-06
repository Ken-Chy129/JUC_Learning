package cn.ken.concurrent;

/**
 * <pre>
 * Synchronized字节码分析
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2023/1/6 22:16
 */
public class SynchronizedByte {
    
    static final Object lock = new Object();
    static int cnt = 0;
    
    public static void main(String[] args) {
        synchronized (lock) {
            cnt++;
        }
    }
}

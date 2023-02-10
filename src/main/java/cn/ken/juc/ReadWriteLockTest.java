package cn.ken.juc;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/2/2 13:28
 */
public class ReadWriteLockTest {
    
    static boolean cacheValid = false;
    
    static Object data;
    
    public static void main(String[] args) {
        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = rwl.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = rwl.writeLock();
        readLock.lock();
        if (!cacheValid) {
            readLock.unlock();
            // 获取写锁前必须释放读锁
            writeLock.lock();
            try {
                // double checked，有可能其他线程已经获取了写锁更新了缓存，避免重复更新
                if (!cacheValid) {
                    data = 1;
                    cacheValid = true;
                }
                // 持有写锁的线程允许重入读锁
                readLock.lock();
            } finally {
                // 一定要在释放写锁前先获取读锁，防止释放写锁后还没加上读锁数据就被改了
                writeLock.lock();
            }
        }
        try {
            // 读锁保证了这里使用的数据是没有被修改的
            use(data);
        } finally {
            readLock.unlock();
        }
    }
    
    private static void use(Object x) {}
}

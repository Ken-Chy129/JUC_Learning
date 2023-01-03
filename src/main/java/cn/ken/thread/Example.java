package cn.ken.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * <p>线程使用示例</p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2023/1/3 22:38
 */
@Slf4j
public class Example {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                log.debug("洗水壶");
                TimeUnit.SECONDS.sleep(1);
                log.debug("烧开水");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "甲");
        
        Thread t2 = new Thread(() -> {
            try {
                log.debug("洗茶壶");
                TimeUnit.SECONDS.sleep(1);
                log.debug("洗茶杯");
                TimeUnit.SECONDS.sleep(2);
                log.debug("放茶叶");
                TimeUnit.SECONDS.sleep(1);
                t1.join(); // 等待水烧开
                log.debug("泡茶");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "乙");
        
        t1.start();
        t2.start();
    }
}

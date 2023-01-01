package cn.ken.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * <p>线程执行顺序：交替执行，执行顺序不受我们控制</p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2023/1/1 22:17
 */
@Slf4j
public class Sequence {

    public static void main(String[] args) {
        Thread thread1 = new Thread("t1") {
            @Override
            public void run() {
                while (true) {
                    log.debug("running");
                }
            }
        };
        thread1.start();

        Thread thread2 = new Thread("t2") {
            @Override
            public void run() {
                while (true) {
                    log.debug("running");
                }
            }
        };
        thread2.start();
    }
}

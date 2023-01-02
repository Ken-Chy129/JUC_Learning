package cn.ken.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * <p>Thread类方法：run和start</p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2023/1/2 21:32
 */
@Slf4j
public class RunAndStartMethods {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("running...");
        }, "t1");
        t1.run(); // 不会创建新线程，仍然在main线程中运行，和调用普通方法相似
        log.debug("t1线程状态:{}", t1.getState()); // NEW
        t1.start(); // 创建t1线程
        log.debug("t1线程状态:{}", t1.getState()); // RUNNABLE
        t1.run(); // 正常运行
        t1.start(); // 运行报错，抛出IllegalThreadStateException异常
    }
}

package cn.ken.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * <p>线程优先级</p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2023/1/2 23:50
 */
@Slf4j
public class ThreadPriority {
    
    public static void main(String[] args) {
        
        // 正常情况
        
        Thread t1 = new Thread(() -> {
            int count = 0;
            while (true) {
                log.debug("         t1:{}", count++);
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            int count = 0;
            while (true) {
                log.debug("t2:{}", count++);
            }
        }, "t2");

        t1.start();
        t2.start(); // 此时可以看到t1和t2的count值相差不大，因为两者的优先级相同，有均等的机会抢占cpu时间片

        // way1
        
//        Thread t1 = new Thread(() -> {
//            int count = 0;
//            while (true) {
//                log.debug("         t1:{}", count++);
//            }
//        }, "t1");
//
//        Thread t2 = new Thread(() -> {
//            int count = 0;
//            while (true) {
//                Thread.yield();
//                log.debug("t2:{}", count++);
//            }
//        }, "t2");
//
//        t1.start();
//        t2.start(); // 此时可以明显看到t2的count值要相对比t1少很多，因为t2调用了yield方法让放弃当前cpu资源

        // way2
        
//        Thread t1 = new Thread(() -> {
//            int count = 0;
//            while (true) {
//                log.debug("         t1:{}", count++);
//            }
//        }, "t1");
//
//        Thread t2 = new Thread(() -> {
//            int count = 0;
//            while (true) {
//                log.debug("t2:{}", count++);
//            }
//        }, "t2");
//
//        // 默认线程的优先级时5
//        t1.setPriority(Thread.MIN_PRIORITY); // 设置为1
//        t2.setPriority(Thread.MAX_PRIORITY); // 设置为10
//        t1.start();
//        t2.start(); // 此时可以看到t2的count值要明显比t1的count值大，因为t2设置了更高的优先级
    }
}

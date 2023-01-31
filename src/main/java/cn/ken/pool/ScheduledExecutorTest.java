package cn.ken.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/31 11:59
 */
@Slf4j
public class ScheduledExecutorTest {

    public static void main(String[] args) {
        // 虽然最大线程数量是Integer.MAX，但是救急线程存活时间为0，所以仍然只能使用核心线程数量的线程执行任务
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
//        scheduledExecutorService.execute(() -> {
//            log.debug("task1");
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        scheduledExecutorService.execute(() -> {
//            log.debug("task2");
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        log.debug("start");
//        scheduledExecutorService.schedule(() -> {
//            log.debug("task1");
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, 1, TimeUnit.SECONDS);
//        scheduledExecutorService.schedule(() -> {
//            log.debug("task2");
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, 1, TimeUnit.SECONDS);

        scheduledExecutorService.schedule(() -> {
            log.debug("task1");
            int i = 1/0; // 不会影响下面的任务执行，线程出异常会重新创建一个新的线程执行下面的任务
        }, 1, TimeUnit.SECONDS);
        scheduledExecutorService.schedule(() -> {
            log.debug("task2");
            
        }, 1, TimeUnit.SECONDS);
        
//        scheduledExecutorService.scheduleAtFixedRate(() -> {
//            log.debug("start1");
//            try {
//                Thread.sleep(500); // 会导致下面的延时任务延后半秒才执行，因为当前线程池只有一个核心线程
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
////            int i = 1/0; // 中断执行，后续任务不会继续执行
//        }, 1, 1, TimeUnit.SECONDS);
//
//        scheduledExecutorService.scheduleAtFixedRate(() -> {
//            log.debug("start2");
////            int i = 1/0; // 中断执行，后续任务不会继续执行
//        }, 1, 2, TimeUnit.SECONDS);
    }
}

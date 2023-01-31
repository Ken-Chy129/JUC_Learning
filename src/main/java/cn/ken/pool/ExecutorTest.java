package cn.ken.pool;

import jdk.jfr.internal.tool.PrettyWriter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/30 20:45
 */
@Slf4j
public class ExecutorTest {
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
//        Future<String> submit = executorService.submit(() -> {
//            log.debug("submitted");
//            Thread.sleep(1000);
//            return "test";
//        });
//        log.debug("done"); // 主线程提交完任务便继续运行，不需要阻塞至任务执行结束
//        log.debug(submit.get()); // 阻塞至任务执行结束获得结果
        
        
//        List<Future<String>> futures = executorService.invokeAll(Arrays.asList(
//                () -> {
//                    log.debug("begin");
//                    Thread.sleep(1000);
//                    return "1";
//                },
//                () -> {
//                    log.debug("begin");
//                    Thread.sleep(500);
//                    return "2";
//                },
//                () -> {
//                    log.debug("begin");
//                    Thread.sleep(2000);
//                    return "3";
//                }
//        ));
//        log.debug("invoked"); // 主线程会阻塞至任务执行结束
//        for (Future<String> future : futures) {
//            log.debug(future.get());
//        }

//        System.out.println(executorService.submit(() -> "ok").get()); // 传入callable，get方法等待执行结束后的返回值
//        System.out.println(executorService.submit(() -> log.debug("test")).get()); // 传入runnable，get方法执行结束后返回null
//        System.out.println(executorService.submit(() -> log.debug("test"), "ok").get()); // 传入runnable并指定返回结果，get方法执行结束后返回传入的指定值
//        
        executorService.execute(() -> {
            log.debug("execute");
            try {
                Thread.sleep(1000);
                log.debug("done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        log.debug("ok");
        executorService.execute(() -> {
            while (true) {
//                log.debug("1");
            }
        }); // 不会停止
        executorService.shutdownNow();
    }
}

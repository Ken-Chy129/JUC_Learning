package cn.ken.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * <pre>
 * <p>线程的创建方式2:FutureTask和Thread</p>
 *
 * Thread对象可以通过FutureTask对象构造，因为其也实现了Runnable接口。
 * 
 * FutureTask是一个可取消的异步任务，实现了RunnableFuture接口（该接口继承了Runnable接口和Future接口），可以调用方法去开始和取消一个计算，可以查询计算是否完成并且获取计算结果。
 * 只有当计算完成时才能获取到计算结果，一旦计算完成，计算将不能被重启或者被取消，除非调用runAndReset方法。
 * 创建FutureTask对象的时候通过传入Callable接口的实现类，重写call方法，定义一个有返回值的方法。
 * 执行FutureTask对象的run时会查找callable对象是否定义，有的话即执行。
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2023/1/1 20:46
 */
@Slf4j
public class CreateWay2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 泛型定义的是返回的数据类型
        FutureTask<Integer> futureTask = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("running");
                Thread.sleep(1000);
                log.debug("end");
                return new Random().nextInt(10);
            }
        });
        // futureTask.run();可以直接调用，即不开辟一个新的线程，直接在当前线程执行
        Thread thread = new Thread(futureTask, "t1");
        thread.start();
        
        log.debug("main");
        log.debug("线程返回的值是:{}", futureTask.get()); // get方法会一直阻塞到线程执行结束返回，即t1线程执行结束后才能执行该语句
        // get即Future接口中定义的方法
        log.debug("{}", futureTask.get()); // 上一语句执行结束后直接执行，结果相同
        
        Thread thread1 = new Thread(futureTask, "t2");
        thread1.start(); // 不会执行，因为futureTask对象只能使用一次，一旦计算完成便不能重启
        log.debug("{}", futureTask.get());
    }
}

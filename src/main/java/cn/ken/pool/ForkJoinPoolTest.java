package cn.ken.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/31 15:17
 */
public class ForkJoinPoolTest {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        System.out.println(pool.invoke(new MyTask(1, 100)));
    }
}

@Slf4j
class MyTask extends RecursiveTask<Long> {
    
    long begin;
    
    long end;

    public MyTask(long begin, long end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (begin == end) {
            return begin;
        }
        if (end - begin == 1) {
            return begin + end;
        }
        long mid = (end - begin) / 2 + begin;
        log.debug("{}", mid);
        MyTask myTask1 = new MyTask(begin, mid);
        myTask1.fork();
        MyTask myTask2 = new MyTask(mid+1, end);
        myTask2.fork();
        return myTask1.join() + myTask2.join();
    }
} 

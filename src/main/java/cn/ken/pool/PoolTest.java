package cn.ken.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 * 自定义线程池实现
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/29 20:30
 */
@Slf4j
public class PoolTest {
    public static void main(String[] args) {

        ThreadPool pool = new ThreadPool(2,
                1, TimeUnit.SECONDS, 3,
//                BlockingQueue::put // 1.入队死等
                (queue, task) -> {
                    queue.offer(task, 1, TimeUnit.SECONDS); // 2.带超时的入队
//                    log.debug("放弃{}", task); // 3.放弃执行
//                    throw new RuntimeException("任务执行失败"); // 4.抛出异常
//                    task.run(); // 5.调用者自己执行任务
                }
        );
        for (int i = 0; i < 10; i++) {
            int j = i;
            pool.execute(() -> {
                log.debug("{}", j);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}

@Slf4j
class ThreadPool {

    // 核心线程数
    private int coreSize;

    // 线程集合
    private HashSet<Worker> workers;

    // 任务队列
    private BlockingQueue<Runnable> taskQueue;

    // 超时时间
    private long timeout;

    private TimeUnit timeUnit;

    // 拒绝策略
    private RejectPolicy<Runnable> rejectPolicy;

    public ThreadPool(int coreSize, long timeout, TimeUnit unit, int queueCapacity, RejectPolicy<Runnable> rejectPolicy) {
        this.coreSize = coreSize;
        this.workers = new HashSet<>(coreSize);
        this.timeout = timeout;
        this.timeUnit = unit;
        this.taskQueue = new BlockingQueue<>(queueCapacity);
        this.rejectPolicy = rejectPolicy;
    }

    public void execute(Runnable task) {
        synchronized (workers) {
            if (workers.size() < coreSize) {
                Worker worker = new Worker(task);
                workers.add(worker);
                worker.start();
            } else {
//                taskQueue.put(task);
//                taskQueue.offer(task, timeout, timeUnit);
                // 线程已满时的策略
                rejectPolicy.reject(taskQueue, task);
            }
        }
    }

    class Worker extends Thread {

        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            while (task != null || (task = taskQueue.poll(timeout, timeUnit)) != null) {
                try {
                    log.debug("开始执行任务{}", task);
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    log.debug("任务{}执行结束", task);
                    task = null;
                }
            }
            synchronized (workers) {
                log.debug("移除线程");
                workers.remove(this);
            }

        }
    }
}

@FunctionalInterface
interface RejectPolicy<T> {
    void reject(BlockingQueue<T> queue, Runnable task);
}

@Slf4j
class BlockingQueue<T> {

    // 双向队列
    private Deque<T> queue;

    // 队列容量
    private int capacity;

    // 锁
    private ReentrantLock lock = new ReentrantLock();

    // 队列满时生产者等待的条件变量
    private Condition fullWaitSet = lock.newCondition();

    // 队列为空时消费者等待的条件变量
    private Condition emptyWaitSet = lock.newCondition();

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
        queue = new ArrayDeque<>(capacity);
    }

    // 带超时时间的阻塞添加
    public boolean offer(T element, long timeout, TimeUnit unit) {
        lock.lock();
        try {
            long nanos = unit.toNanos(timeout);
            while (queue.size() == capacity) {
                try {
                    if (nanos <= 0) {
                        log.debug("等待超时，任务{}添加失败", element);
                        return false;
                    }
                    log.debug("任务队列已满，任务{}进入等待", element);
                    nanos = fullWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("任务{}成功加入任务队列", element);
            queue.addLast(element);
            emptyWaitSet.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

    // 阻塞添加
    public void put(T element) {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                try {
                    log.debug("任务队列已满，任务{}进入等待", element);
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("任务{}添加成功", element);
            queue.addLast(element);
            emptyWaitSet.signal();
        } finally {
            lock.unlock();
        }
    }

    // 带超时时间的阻塞获取
    public T poll(long timeout, TimeUnit unit) {
        lock.lock();
        try {
            long nanos = unit.toNanos(timeout);
            while (queue.isEmpty()) {
                try {
                    if (nanos <= 0) {
                        log.debug("等待超时，任务获取失败");
                        return null;
                    }
                    log.debug("等待获取任务");
                    nanos = emptyWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            log.debug("成功获取任务{}", t);
            fullWaitSet.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    // 阻塞获取
    public T take() {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                try {
                    log.debug("等待获取任务");
                    emptyWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            log.debug("成功获取任务{}", t);
            fullWaitSet.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }
}

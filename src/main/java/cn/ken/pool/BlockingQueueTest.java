package cn.ken.pool;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/30 19:24
 */
public class BlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>();
        Queue<Integer> queue = new LinkedList<>();
        blockingQueue.add(1); // 队列满则抛出异常
        blockingQueue.put(1); // 队列满则阻塞至可以添加元素
        blockingQueue.offer(1); // 如果队列满则直接直接返回false
        blockingQueue.offer(1, 1, TimeUnit.SECONDS); // 如果队列满则等待一段时间，超时后仍未插入则返回false
    }
}

package cn.ken.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 使用wait，notify（保护性暂停 ）模拟join方法的实现
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/13 19:17
 */
@Slf4j
public class WaitNotifyToSimulateJoin {

    public static void main(String[] args) {
        GuardedObject guardedObject = new GuardedObject();
        new Thread(() -> {
            try {
                log.debug("等待结果");
                Object o = guardedObject.get(1500);
                if (o == null) {
                    log.debug("超时，未得到结果");
                } else {
                    log.debug("结果:{}", o.toString());
                } 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();
        
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("计算出结果");
            guardedObject.complete("hello");
        }, "t2").start();
    }
}

class GuardedObject {
    Object response;
    
    public Object get() throws InterruptedException {
        return get(0);
    }
    
    synchronized public Object get(long timeout) throws InterruptedException {
        long begin =  System.currentTimeMillis();
        long passedTime = 0;
        if (timeout < 0) {
            throw new IllegalArgumentException("参数异常");
        }
        if (timeout == 0) {
            while (response == null) {
                this.wait();
            }
        } else {
            while (response == null) {
                long waitTime = timeout - passedTime; // 本轮需要等待的时间
                if (waitTime <= 0) {
                    break;
                }
                wait(waitTime);
                passedTime = System.currentTimeMillis() - begin; // 已经等待了的时间
            }
        }
        return response;
    }
    
    synchronized public void complete(Object response) {
        this.response = response;
        this.notifyAll();
    } 
}

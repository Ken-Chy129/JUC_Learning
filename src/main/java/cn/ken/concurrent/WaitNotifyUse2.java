package cn.ken.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 保护性暂停
 * 较之于join的好处是，join需要等待现成的运行结束，而使用以下方法只要结果计算出来释放锁，另一个线程就可以使用，不需要等待线程结束
 * join的实现方式其实也是类似，只不过结束条件是isAlive为false而不是结果是否计算出来(无传递等待时间的情况下)
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/13 19:17
 */
@Slf4j
public class WaitNotifyUse2 {

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

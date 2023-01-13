package cn.ken.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/13 17:38
 */
@Slf4j
public class WaitNotifyUse {

    static final Object room = new Object();

    static boolean coffeeReady = false;
    static boolean teaReady = false;
    
    public static void main(String[] args) throws InterruptedException {
       
        // 甲需要咖啡续命
        new Thread(() -> {
            synchronized (room) {
                log.debug("开始工作");
                log.debug("累了需要咖啡提神才能继续工作");
                try {
                    room.wait(); // 开始休息
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (!coffeeReady) {
                    try {
                        log.debug("咖啡未到，继续休息");
                        room.wait(); // 咖啡未到,继续休息
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("咖啡来了，开始工作");
            }
        }, "甲").start();
        
        // 乙需要茶续命
        new Thread(() -> {
            synchronized (room) {
                log.debug("开始工作");
                log.debug("累了需要茶提神才能继续工作");
                try {
                    room.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (!teaReady) {
                    try {
                        log.debug("茶未到，继续休息");
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("茶来了，开始工作");
            }
        }, "乙").start();
        
        Thread.sleep(1000);
        
        new Thread(() -> {
            synchronized (room) {
                log.debug("茶到了");
                teaReady = true;
                room.notifyAll();
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                log.debug("咖啡到了");
//                coffeeReady = true;
//                room.notifyAll();
            }
        }).start();
        
    }
}


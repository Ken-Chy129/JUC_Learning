package cn.ken.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * <p>线程的创建方式1:Thread</p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2023/1/1 19:41
 */
@Slf4j
public class CreateWay1 {

    public static void main(String[] args) {
        
        // 通过匿名子类创建Thread类的子类，重写run方法（Thread类实现了Runnable接口）
        Thread thread = new Thread("way1") {
            @Override
            public void run() {
                log.debug("running");
            }
        };
        thread.start(); // 启动线程
        log.debug("running"); // 主线程执行
        
        
        
        // 先创建Runnable对象，再传入Thread类的构造器
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log.debug("runnable");
            }
        };
        Thread thread1 = new Thread(runnable, "thread1");
        // runnable创建可使用lambda表达式
        Runnable runnable1 = () -> log.debug("runnable");
        Thread thread2 = new Thread(runnable1);
        // 或
        Thread thread3 = new Thread(() -> log.debug("runnable"));
        thread1.setName("thread3");
    }
    
}

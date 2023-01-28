package cn.ken.jmm;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/20 23:19
 */
@Slf4j
public class JMMTest {
    
    private static boolean run = true;
    
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            log.debug("开始");
            while (run) {
                System.out.println(); // 调用该方法则可以结束
            }
            log.debug("结束"); // 不会执行
        }).start();
        
        Thread.sleep(1000);
        run = false;
    }
}

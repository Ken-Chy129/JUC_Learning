package cn.ken.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * <p>临界区</p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2023/1/3 23:23
 */
@Slf4j
public class CriticalRezone {
    
    static int i = 0; // 临界资源
    
    // 临界区
    static void increment() {
        i++;
    }

    // 临界区
    static void decrement() {
        i--;
    }
}

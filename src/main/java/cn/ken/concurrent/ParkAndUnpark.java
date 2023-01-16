package cn.ken.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/14 12:20
 */
@Slf4j
public class ParkAndUnpark {
    public static void main(String[] args) {
        new Thread(() -> {
            log.debug("开始park");
            LockSupport.park(); // 内部调用的是Unsafe类的park方法，底层调用的是c++方法
            log.debug("结束park");
        }).start();
    }
}

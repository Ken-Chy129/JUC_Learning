package cn.ken.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * <pre>
 * 测试偏向锁
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/9 17:20
 */
@Slf4j
public class TestBiased {

    public static void main(String[] args) throws InterruptedException {
//        Dog dog = new Dog();
//        log.debug(ClassLayout.parseInstance(dog).toPrintable()); // 最后是00000001（无锁）
//        // 程序刚启动时偏向锁不会立即生效
//        Thread.sleep(4000);
//        Dog dog2 = new Dog();
//        log.debug(ClassLayout.parseInstance(dog2).toPrintable()); // 最后是00000101（偏向锁）
//        // 偏向锁和无锁最后8位都是unused(1),age(4),biased(1),state(2)，最开始age都是0
//        // 无锁前面是25位的unused和31位的hashcode，而偏向锁时54位的thread和2位的epoch
//        // 调用hashcode之后才会给age赋值
//        log.debug(Integer.toBinaryString(dog.hashCode()));
//        log.debug(ClassLayout.parseInstance(dog).toPrintable()); // 最后是001
//        log.debug(Integer.toBinaryString(dog2.hashCode())); // 会禁用掉对象的偏向锁
//        log.debug(ClassLayout.parseInstance(dog2).toPrintable()); // 最后是001，取消了偏向状态
//        log.debug("==============================================\n");
//        Dog dog3 = new Dog();
//        log.debug(ClassLayout.parseInstance(dog3).toPrintable()); // 最后是101
//        synchronized (dog3) {
//            log.debug(ClassLayout.parseInstance(dog3).toPrintable()); // 最后是101,前面加上了主线程的线程id
//        }
//        log.debug(ClassLayout.parseInstance(dog3).toPrintable()); // 最后是101，前面主线程线程id仍然存在
//        
//        new Thread(() -> {
//            synchronized (dog3) { // 出现其他线程想要获得dog3的锁
//                log.debug(ClassLayout.parseInstance(dog3).toPrintable()); // 最后变为000，轻量级锁，前面是锁记录地址
//            }
//            log.debug(ClassLayout.parseInstance(dog3).toPrintable()); // 最后变为001，禁止偏向，前面全0
//        }).start();
        
        Dog dog4 = new Dog();
        log.debug(ClassLayout.parseInstance(dog4).toPrintable()); // 如果最开始是不可偏向则后面不会变成可偏向
        Thread.sleep(4000);
        synchronized (dog4) {
            log.debug(ClassLayout.parseInstance(dog4).toPrintable());
        }
        log.debug(ClassLayout.parseInstance(dog4).toPrintable());
    }
}
class Dog {}

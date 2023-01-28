package cn.ken.jmm;

import java.io.Serializable;

/**
 * <pre>
 * 双重检查锁定问题
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/23 23:28
 */
public class DoubleCheckLocking {
    
}

// 单例模式
final class Singleton {
    private Singleton() {}
    private static Singleton INSTANCE = null; // 懒惰实例化，直到首次使用时才创建对象
    public static Singleton getSingleton() {
        if (INSTANCE == null) {
            synchronized (Singleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton();
                }
            }
        }
        return INSTANCE;
    }
}

// 问题1: 为什么加final ----- 防止子类继承重写破坏单例
// 问题2: 如果实现了序列化接口，如何防止反序列化破坏单例 ----- 提供readResolve方法(反序列通过该方法来获取反序列化后的对象)
final class Singleton1 implements Serializable {
    // 问题3: 这样初始化是否能保证单例对象创建时的安全 ----- 可以，静态成员在类加载时创建，由jvm保证其线程安全
    private static final Singleton1 INSTANCE = new Singleton1();
    
    // 问题4: 为什么提供静态方法而不是直接将INSTANCE置为public ----- 提供了更好的封装性，可以在返回前执行某些操作，方便改为懒惰式的初始化，可以支持泛型
    public static Singleton1 getInstance() {
        return INSTANCE;
    }
}

// 问题1: 枚举是如何限制实例个数的 ----- 枚举中的每一个值其实对应的就是类的静态成员变量
// 问题2: 枚举单例在创建时是否由并发问题 ----- 没有，其是静态成员变量，安全性由类加载器完成
// 问题3: 枚举单例能否被反射破坏 ----- 不能，反射进行newInstance时进行了判断，如果是enum类型则抛出异常
// 问题4: 枚举单例能否被反序列化破坏单例 ----- 不能
// 问题5: 枚举单例属于懒汉式还是饿汉式 ----- 饿汉式
// 问题6: 枚举单例如果希望加入一些单例创建时的初始化逻辑应该怎么做 ----- 定义构造方法
enum Singleton2 {
    INSTANCE;
}

class Singleton3 {
    private Singleton3() {}
    
    // 问题1: 属于懒汉式还是饿汉式 ----- 懒汉式，因为类加载是懒惰的，只有第一次使用时才会触发类加载操作，所以只有调用getInstance方法时才会触发类加载，才会进行Singleton的创建
    private static class LazyHolder {
        static final Singleton3 INSTANCE = new Singleton3();
    }
    
    // 问题2: 在创建时是否并发问题 ----- 没有，因为本质上还是静态成员变量
    public static Singleton3 getInstance() {
        return LazyHolder.INSTANCE;
    }
}

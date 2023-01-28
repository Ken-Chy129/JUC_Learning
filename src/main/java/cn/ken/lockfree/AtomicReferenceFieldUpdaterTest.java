package cn.ken.lockfree;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/28 0:12
 */
public class AtomicReferenceFieldUpdaterTest {
    public static void main(String[] args) {
        AtomicReferenceFieldUpdater<Test, String> atomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(Test.class, String.class, "name");
        Test test = new Test();
        System.out.println(test);
        System.out.println(atomicReferenceFieldUpdater.compareAndSet(test, null, "cyh"));
        AtomicIntegerFieldUpdater<Test> atomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(Test.class, "age");
        System.out.println(atomicIntegerFieldUpdater.compareAndSet(test, 0, 11));
        System.out.println(test);
    }
}
class Test {
    volatile String name; 
    
    volatile int age; // 使用AtomicIntegerFieldUpdater应该是int型而不能是integer型

    @Override
    public String toString() {
        return "Test{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

package cn.ken.lockfree;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/27 23:39
 */
public class ABATest {
    public static void main(String[] args) {
        AtomicStampedReference<String> atomicStampedReference = new AtomicStampedReference<>("a", 0);
        String prevObj = atomicStampedReference.getReference();
        // 若在此处有其他线程发生了修改，则获取的已经是新的版本号，后面cas也是基于该版本加一，并不会出现问题
        int preStamp = atomicStampedReference.getStamp();
        // 若在此处有其他线程将a更改成其他后又改成a，则版本号发生了变化，不满足preStamp，所以cas失败，避免了aba问题
        System.out.println(atomicStampedReference.compareAndSet(prevObj, "b", preStamp, preStamp + 1));
        System.out.println(atomicStampedReference.getReference());
        System.out.println(atomicStampedReference.getStamp());

        AtomicMarkableReference<String> atomicMarkableReference = new AtomicMarkableReference<>("a", false);
        String prevObj2 = atomicMarkableReference.getReference();
        boolean prevMark = atomicMarkableReference.isMarked();
        System.out.println(atomicMarkableReference.compareAndSet(prevObj2, "b", prevMark, !prevMark));
    }
}

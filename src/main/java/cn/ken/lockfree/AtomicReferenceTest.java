package cn.ken.lockfree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/27 0:38
 */
public class AtomicReferenceTest {
    private AtomicReference<Student> studentAtomicReference;
    
    public AtomicReferenceTest(int num) {
        studentAtomicReference = new AtomicReference<>(new Student("cyh", num));
    }

    public Integer getNum() {
        return studentAtomicReference.get().num;
    }

    public void decrease(int dis) {
        while (true) {
            Student now = studentAtomicReference.get();
            Student prev = new Student("cyh", now.getNum() - dis);
            if (studentAtomicReference.compareAndSet(now, prev)) { // 操作系统层面的原子操作
                break;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<Thread> list = new ArrayList<>();
        AtomicReferenceTest atomicReferenceTest = new AtomicReferenceTest(100000);
        for (int i=0; i<100000; i++) {
            Thread thread = new Thread(() -> {
                atomicReferenceTest.decrease(1);
            });
            list.add(thread);
            thread.start();
        }
        for (Thread thread : list) {
            thread.join();
        }
        System.out.println(atomicReferenceTest.getNum());
    }
}

class Student {
    String name;
    int num;

    public Student(String name, int num) {
        this.name = name;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
//
//    @Override
//    public boolean equals(Object obj) {
//        return this.num == ((Student)obj).num;
//    }
}

package cn.ken.flyweight;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/28 23:39
 */
public class ImmutableTest {
    public static void main(String[] args) {
        A a = new A(new char[]{'1', '2'});
        System.out.println(a);
        char[] t = new char[]{'2', '3'};
        A modify = a.modify(t);
        System.out.println(modify); // 两次输出的对象不是同一个
        System.out.println(a.value); // 原对象的值仍然是1，2，体现了不可变性
        
        A b = new B(new char[]{'1', '2'});
        System.out.println(b);
        A modify1 = b.modify(t);
        System.out.println(modify1); // 两次输出的是同一个对象
        System.out.println(modify.value); // 原先对象的value已经被改变了
        // 说明继承后重写方法可能会破坏不可变性，所以想要实现不可变类需要对类加上final不允许类被继承
    }
}

class A {
    final char[] value; // 只是引用不可修改，值可以修改，想要值不可修改只能通过不提供修改的方法且让类不可被继承而重写方法
    public A(char[] value) {
        this.value = value;
    }
    
    public A modify(char[] value) {
        char[] newValue = new char[value.length];
        System.arraycopy(value, 0, newValue, 0, value.length);
        return new A(newValue);
    }
}

class B extends A {
    public B(char[] value) {
        super(value);
    }

    @Override
    public A modify(char[] value) {
        System.arraycopy(value, 0, this.value, 0, value.length);
        return this;
    }
}

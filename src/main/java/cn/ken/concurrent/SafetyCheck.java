package cn.ken.concurrent;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * <p>线程安全判断</p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2023/1/4 21:20
 */
public class SafetyCheck {
    
    // 不安全，hashtable才是线程安全的
    Map<String, Object> map = new HashMap<>();
    
    // String类线程安全，对象不可变
    String s1 = "...";

    // 还是不安全的
    // final如果修饰基本变量，那么不能被重新赋值，其值也不能修改
    // 而如果修饰的是引用类型变量，它保存的只是一个引用，final只能保证引用的对象不可变(一直x指向同一个地址)
    // 但是对象中的属性是可以修改的，所以线程不安全
    final Date date = new Date();
    
    // 数组是引用数据类型
    final char[] values;

    public SafetyCheck(char[] values) {
        this.values = values;
    }

    public static void main(String[] args) {
        char[] value = {22};
    }
}

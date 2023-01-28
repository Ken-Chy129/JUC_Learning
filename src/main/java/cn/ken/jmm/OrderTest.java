package cn.ken.jmm;

/**
 * <pre>
 * 多线程下指令重排序导致的有序性问题
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/22 16:52
 */
public class OrderTest {
    
    int num = 0;
    
    boolean ready = false;
    
    public int method1() {
        if (ready) {
            return num + num; // 进入此处是ready为true，那么正常来说num肯定已经为2，所以返回4
            // 但是由于指令重排的原因，ready=true可能比num的赋值语句先执行，那么就有可能存在最终返回0的情况
            // 这显然不是我们希望看到的结果
        } else {
            return 1; // ready为false时返回1
        } 
    }
    
    public void method2() {
        num = 2;
        ready = true;
    }
}

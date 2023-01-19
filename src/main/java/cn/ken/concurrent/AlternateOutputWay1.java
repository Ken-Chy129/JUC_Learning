package cn.ken.concurrent;

/**
 * <pre>
 * 三个线程交替输出a,b,c五次:使用wait和notify
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/19 22:20
 */
public class AlternateOutputWay1 {
    public static void main(String[] args) {
        WaitNotify wn = new WaitNotify(1, 5);
        new Thread(() -> wn.print("a", 1, 2)).start();
        new Thread(() -> wn.print("b", 2, 3)).start();
        new Thread(() -> wn.print("c", 3, 1)).start();
    }
}

class WaitNotify {
    private int flag;
    private int loopNumber;

    public WaitNotify(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }
    
    public void print(String strToPrint, int flag, int nextFlag) {
        for (int i=0; i<loopNumber; i++) {
            synchronized (this) {
                while (flag != this.flag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(strToPrint);
                this.flag = nextFlag;
                this.notifyAll();
            }
        }
    }
}

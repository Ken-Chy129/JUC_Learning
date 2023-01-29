package cn.ken.flyweight;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @date 2023/1/29 15:01
 */
public class FlyWeightTest {
    public static void main(String[] args) {
        Long aLong = Long.valueOf("1");
        Long bLong = Long.valueOf("1");
        Long cLong = Long.valueOf("128");
        Long dLong = Long.valueOf("128");
        System.out.println(aLong == bLong); // true
        System.out.println(cLong == dLong); // false
    }
}

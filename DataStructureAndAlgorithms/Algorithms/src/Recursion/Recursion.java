package Recursion;

/**
 * 递归阶乘
 */
public class Recursion {

    public static void main(String[] args) {
        Factorial f = new Factorial();
        System.out.println("3的阶乘：" + f.fact(3));
        System.out.println("4的阶乘：" + f.fact(4));
        System.out.println("5的阶乘：" + f.fact(5));
    }

}

class Factorial {

    /**
     * 数字 N 的阶乘是 1 到 N 之间所有整数的乘积
     *
     * @param n
     * @return 所有整数的乘积
     */
    int fact(int n) {
        int result;
        if (n == 1) {
            return 1;
        }
        result = fact(n - 1) * n;
        return result;
    }
}

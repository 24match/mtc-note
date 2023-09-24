/**
 * @author Match
 * @date 2022-07-23 22:59
 */
public class FibonacciSolutions {

    /**
     * 迭代法
     *
     * @param number
     * @return
     */
    int Fibonacci(int number) {
        if (number <= 0) {
            return 0;
        }
        if (number == 1 || number == 2) {
            return 1;
        }
        int first = 1, second = 1, third = 0;
        for (int i = 0; i <= number; i++) {
            third = first + second;
            first = second;
            second = third;
        }
        return third;
    }

    public static void main(String[] args) {
        FibonacciSolutions solutions = new FibonacciSolutions();
        System.out.println(solutions.Fibonacci(3));
    }


}

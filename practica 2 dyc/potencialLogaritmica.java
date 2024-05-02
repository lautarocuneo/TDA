public class potencialLogaritmica {
    public static double power(double a, int b) {
        if (b == 0) {
            return 1;
        }
        double temp = power(a, b / 2);
        if (b % 2 == 0) {
            return temp * temp;
        } else {
            return a * temp * temp;
        }
    }

    public static void main(String[] args) {
        double a = 2;
        int b = 10;
        System.out.println(power(a, b));
    }



}

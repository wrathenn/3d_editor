package libs;

public class SharedFunctions {
    public static final double mistake = 1e-6;
    public static int doubleCompare(double thisVal, double anotherVal) {
        if (Math.abs(thisVal - anotherVal) < mistake) {
            return 0;
        }

        if (thisVal < anotherVal) {
            return -1;
        }

        return 1;
    }
}

import Jama.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        Matrix m = new Matrix(2, 2, 3);
        Matrix m1 = new Matrix(2, 1, 4);

        m.print(0, 0);
        m1.print(0, 0);

        Matrix m2 = m1.times(m);

        m2.print(0, 0);
    }
}

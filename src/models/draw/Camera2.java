package models.draw;

import Jama.Matrix;

public class Camera2 {
    public final Matrix position = new Matrix(new double[][]{{0, 0, 3}});
    public final Matrix target = new Matrix(new double[][]{{0, 0, 0}});
    public final Matrix direction = position.minus(target);
}

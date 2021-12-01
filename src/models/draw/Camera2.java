package models.draw;

import Jama.Matrix;

public class Camera2 {
    public final Matrix position = new Matrix(1, 3);
    public final Matrix direction = new Matrix(1, 3);
}

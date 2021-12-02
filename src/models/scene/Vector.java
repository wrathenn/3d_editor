package models.scene;

import Jama.Matrix;

public class Vector {
    private final Matrix m = new Matrix(1, 3);

    // ----- Конструкторы ----- //

    public Vector() {

    }

    // ----- Геттеры и Сеттеры ----- //

    public double getX() {
        return m.get(0, 0);
    }

    public void setX(double x) {
        m.set(0, 0, x);
    }

    public double getY() {
        return m.get(0, 1);
    }

    public void setY(double y) {
        m.set(0, 1, y);
    }

    public double getZ() {
        return m.get(0, 2);
    }

    public void setZ(double z) {
        m.set(0, 2, z);
    }

    // ----- Методы ----- //
    public void normalize() {
        double x = getX();
        double y = getY();
        double z = getZ();
        double len = Math.sqrt(x * x + y * y + z * z);

        setX(x / len);
        setY(y / len);
        setZ(z / len);
    }
}

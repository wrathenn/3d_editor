package models.scene;

import Jama.Matrix;
import libs.SharedFunctions;

public class Vector {
    private Matrix m = new Matrix(4, 1);

    // ----- Конструкторы ----- //

    public Vector() {
        m.set(3, 0, 1);
    }

    public Vector(double x, double y, double z) {
        this();
        setX(x);
        setY(y);
        setZ(z);
    }

    public Vector(Vector v) {
        this();
        setX(v.getX());
        setY(v.getY());
        setZ(v.getZ());
    }

    // ----- Геттеры и Сеттеры ----- //

    public double getX() {
        return m.get(0, 0);
    }

    public void setX(double x) {
        m.set(0, 0, x);
    }

    public double getY() {
        return m.get(1, 0);
    }

    public void setY(double y) {
        m.set(1, 0, y);
    }

    public double getZ() {
        return m.get(2, 0);
    }

    public void setZ(double z) {
        m.set(2, 0, z);
    }

    // ----- Методы ----- //
    public double len() {
        double x = getX();
        double y = getY();
        double z = getZ();

        return Math.sqrt(x * x + y * y + z * z);
    }

    public Vector normalizeEquals() {
        double x = getX();
        double y = getY();
        double z = getZ();
        double len = Math.sqrt(x * x + y * y + z * z);

        setX(x / len);
        setY(y / len);
        setZ(z / len);

        return this;
    }

    public Vector plus(Vector v) {
        return new Vector(
                getX() + v.getX(),
                getY() + v.getY(),
                getZ() + v.getZ()
        );
    }

    public Vector plusEquals(Vector v) {
        setX(getX() + v.getX());
        setY(getY() + v.getY());
        setZ(getZ() + v.getZ());
        return this;
    }

    public Vector minus(Vector v) {
        return new Vector(
                getX() - v.getX(),
                getY() - v.getY(),
                getZ() - v.getZ()
        );
    }

    public Vector minusEquals(Vector v) {
        setX(getX() - v.getX());
        setY(getY() - v.getY());
        setZ(getZ() - v.getZ());
        return this;
    }

    public Vector times(double a) {
        return new Vector(
                getX() * a,
                getY() * a,
                getZ() * a
        );
    }

    public Vector divideEquals(double a) {
        setX(getX() / a);
        setY(getY() / a);
        setZ(getZ() / a);
        return this;
    }

    public Vector invert() {
        return new Vector(
                -getX(),
                -getY(),
                -getZ()
        );
    }

    public Vector invertEquals() {
        setX(-getX());
        setY(-getY());
        setZ(-getZ());
        return this;
    }

    public Vector cross(Vector v) {
        return new Vector(
                getY() * v.getZ() - getZ() * v.getY(),
                getZ() * v.getX() - getX() * v.getZ(),
                getX() * v.getY() - getY() * v.getX()
        );
    }

    public Vector timesRightEquals(Matrix m) {
        this.m = m.times(this.m);
        return this;
    }


    public static double scalarProduct(Vector v1, Vector v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY() + v1.getZ() * v2.getZ();
    }

    public double scalarProduct(Vector v) {
        return scalarProduct(this, v);
    }

    // ----- Object ----- //
    public static class VectorWrapper {
        public Vector v;

        public VectorWrapper(Vector v) {
            this.v = v;
        }
    }

    public double getAngle(Vector v) {
        double a = scalarProduct(v);
        double b = this.len() * v.len();
        if (SharedFunctions.doubleCompare(b, 0) == 0) {
            return 0;
        }

        return a / b;
    }

    public String toString() {
        return String.format("Vector{x=%.6f, y=%.6f, z=%.6f}", getX(), getY(), getZ());
    }
}

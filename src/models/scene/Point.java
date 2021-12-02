package models.scene;

import Jama.Matrix;

public class Point {
    private String nameID;
    private final Vector v = new Vector();

    // ----- Конструкторы ----- //

    public Point() {
    }

    public Point(String nameID, double x, double y, double z) {
        this.nameID = nameID;
        setX(x);
        setY(y);
        setZ(z);
    }

    public Point(String nameId) {
        this.nameID = nameId;
    }

    public Point(double x, double y, double z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    // Конструктор копирования
    public Point(Point p) {
        this(p.nameID, p.getX(), p.getY(), p.getZ());
    }

    // ----- Геттеры и Сеттеры ----- //

    public double getX() {
        return m.get(0, 0);
    }

    public void setX(double x) {
        m.set(0,0, x);
    }

    public double getY() {
        return m.get(0, 1);
    }

    public void setY(double y) {
        m.set(0,1, y);
    }

    public double getZ() {
        return m.get(0, 2);
    }

    public void setZ(double z) {
        m.set(0,2, z);;
    }

    public String getNameID() {
        return nameID;
    }

    public void setNameID(String nameID) {
        this.nameID = nameID;
    }

    public Matrix getM() {
        return m;
    }

    // ----- Нормальные методы ----- //

    public double vectorLen() {
        double x = getX();
        double y = getY();
        double z = getZ();

        return Math.sqrt(x * x + y * y + z * z);
    }

    public Point makeUnitVector() {
        double len = vectorLen();
        setX(getX() / len);
        setY(getY() / len);
        setZ(getZ() / len);

        return this;
    }

    public Point minus(Point p) {
        setX(getX() - p.getX());
        setY(getY() - p.getY());
        setZ(getZ() - p.getZ());

        return this;
    }

    public static double scalarProduct(Point p1, Point p2) {
        return p1.getX() * p2.getX() + p1.getY() * p2.getY() + p1.getZ() * p2.getZ();
    }

    // ----- Object ----- //

    static class PointWrapper {
        Point p;
        PointWrapper(Point point) {
            p = point;
        }
    }

    public static void swap(PointWrapper p1, PointWrapper p2) {
        Point temp = p1.p;
        p1.p = p2.p;
        p2.p = temp;
    }

    @Override
    public String toString() {
        return String.format("Point{Name=%s, x=%.6f, y=%.6f, z=%.6f}", nameID, getX(), getY(), getZ());
    }
}

package models;

public class Point {
    public String nameID;
    public double x;
    public double y;
    public double z;

    // ----- Конструкторы ----- //

    public Point() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Point(String nameID, double x, double y, double z) {
        this.nameID = nameID;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(String nameId) {
        this.nameID = nameId;
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Конструктор копирования
    public Point(Point p) {
        this(p.nameID, p.x, p.y, p.z);
    }

    // ----- Геттеры и Сеттеры ----- //

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public String getNameID() {
        return nameID;
    }

    public void setNameID(String nameID) {
        this.nameID = nameID;
    }

    public double vectorLen() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public Point makeUnitVector() {
        double len = vectorLen();
        x /= len;
        y /= len;
        z /= len;
        return this;
    }

    public Point minus(Point p) {
        x -= p.x;
        y -= p.y;
        z -= p.z;
        return this;
    }

    public static Point multiplyOneByOne(Point p1, Point p2) {
        return new Point(p1.x * p2.x, p1.y * p2.y, p1.z * p2.z);
    }

    public static void swap(Point p1, Point p2) {
        Point temp = p1;
        p1 = p2;
        p2 = temp;
    }

    @Override
    public String toString() {
        return "Point " + this.nameID + ", x: " + this.x + ", y: " + this.y + ", z: " + this.z;
    }
}

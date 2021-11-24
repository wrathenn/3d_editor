package models;

import repositories.Visitor;

public class Point implements Shape {
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

    public void setX(float x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public String getNameID() {
        return nameID;
    }

    public void setNameID(String nameID) {
        this.nameID = nameID;
    }


    public Point deepCopy() {
        return new Point(this);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public static void swap(Point p1, Point p2) {
        Point temp = p1;
        p1 = p2;
        p2 = temp;
    }

    @Override
    public String toString() {
        return "Point " + this.nameID + ", x: " + this.x + ", y: " + this.y +", z: " + this.z;
    }
}

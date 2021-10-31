package models;

import shared.Visitor;

public class Point implements Shape {
    String nameID;
    private float x;
    private float y;
    private float z;

    // ----- Конструкторы ----- //

    public Point() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Point(String nameID, float x, float y, float z) {
        this.nameID = nameID;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // ----- Геттеры и Сеттеры ----- //

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
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

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

package models.draw;


import Jama.Matrix;
import models.scene.Point;

public class Camera {
    private double x;
    private double y;
    private double z;

    private double screenDistance;

    private int screenWidth;
    private int screenHeight;

    private Matrix rotateMatrix;
    private Matrix scaleMatrix;
    private Matrix moveMatrix;

    private final int DEFAULT_SCREEN_DISTANCE = 150;
    private final int DEFAULT_X = 0;
    private final int DEFAULT_Y = 0;
    private final int DEFAULT_Z = 0;

    // ----- Конструкторы ----- //

    public Camera() {
        this.x = DEFAULT_X;
        this.y = DEFAULT_Y;
        this.z = DEFAULT_Z;

        this.screenDistance = DEFAULT_SCREEN_DISTANCE;

        rotateMatrix = Matrix.identity(3, 3);
        scaleMatrix = Matrix.identity(3, 3);
        moveMatrix = Matrix.identity(3, 3);
    }

    public Camera(double x, double y, double z, double screenDistance) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.screenDistance = screenDistance;

        rotateMatrix = Matrix.identity(3, 3);
        scaleMatrix = Matrix.identity(3, 3);
        moveMatrix = Matrix.identity(3, 3);
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

    public double getScreenDistance() {
        return screenDistance;
    }

    public void setScreenDistance(double screenDistance) {
        this.screenDistance = screenDistance;
    }

    public void setDimensions(int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    // ----- Нормальные методы ----- //

    public void findViewerVector(PointDraw point) {
        Point viewVector = new Point(point.getPoint()).makeUnitVector();
        point.setViewerVector(viewVector);
    }

    public void transformPointToCameraCoordinates(PointDraw point) {
        Point p = point.getPoint();

        p.setX(p.getX() - this.x);
        p.setY(p.getY() - this.y);
        p.setZ(p.getZ() - this.z);
    }

    public void transformPointToCameraScreen(PointDraw point) {
        Point p = point.getPoint();

        p.setY(p.getY() * this.screenDistance / p.getZ());
        p.setX(p.getX() * this.screenDistance / p.getZ());
//        p.setZ(this.screenDistance);

        p.setX((int) (p.getX() + (this.screenWidth / 2)));
        p.setY((int) (this.screenHeight / 2 - p.getY()));
    }
}

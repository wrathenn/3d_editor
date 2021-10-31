package models;


import Jama.Matrix;

public class Camera {
    private double x;
    private double y;
    private double z;

    private double screenDistance;
    private int fov;

    private Matrix rotateMatrix;
    private Matrix scaleMatrix;
    private Matrix moveMatrix;

    private final int DEFAULT_FOV = 120;
    private final int DEFAULT_SCREEN_DISTANCE = 10;
    private final int DEFAULT_X = 0;
    private final int DEFAULT_Y = 0;
    private final int DEFAULT_Z = -20;

    public Camera() {
        this.x = DEFAULT_X;
        this.y = DEFAULT_Y;
        this.z = DEFAULT_Z;

        this.fov = DEFAULT_FOV;
        this.screenDistance = DEFAULT_SCREEN_DISTANCE;

        rotateMatrix = Matrix.identity(3, 3);
        scaleMatrix = Matrix.identity(3, 3);
        moveMatrix = Matrix.identity(3, 3);
    }

    public Camera(double x, double y, double z, double screenDistance, int fov) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.screenDistance = screenDistance;
        this.fov = fov;

        rotateMatrix = Matrix.identity(3, 3);
        scaleMatrix = Matrix.identity(3, 3);
        moveMatrix = Matrix.identity(3, 3);
    }

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

    public int getFov() {
        return fov;
    }

    public void setFov(int fov) {
        this.fov = fov;
    }
}

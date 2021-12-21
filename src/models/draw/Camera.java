package models.draw;

import Jama.Matrix;
import models.scene.Point;
import models.scene.Vector;

public class Camera {
    private static Vector generalUp = new Vector(0, 1, 0);

    // ----- Переменные - позиция камеры ----- //

    public Vector position = new Vector(0, 0, 0);
    public Vector target = new Vector(0, 0, -1);

    public Vector direction;
    public Vector right;
    public Vector up;

    public Matrix lookAt;

    private double pitch;
    private double yaw = 90;

    // ----- Переменные - переспективная проекция ----- //

    private int screenWidth;
    private int screenHeight;
    private double screenDistance = 150;

    // ----- Конструктор ----- //

    public Camera() {
        createLookAt();
    }

    public Camera(Vector position, Vector target, double yaw, double pitch, int screenDistance) {
        this.position = position;
        this.target = target;
        this.yaw = yaw;
        this.pitch = pitch;
        this.screenDistance = screenDistance;
    }

    // ----- Методы ----- //

    private void findDirections() {
        direction = position.minus(target);
        direction.setX(Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        direction.setY(Math.sin(Math.toRadians(pitch)));
        direction.setZ(Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        direction.normalizeEquals();

        right = generalUp.cross(direction).normalizeEquals();
        up = direction.cross(right);
    }

    public void createLookAt() {
        findDirections();

        Matrix rotation = new Matrix(new double[][]{
                {right.getX(), right.getY(), right.getZ(), 0},
                {up.getX(), up.getY(), up.getZ(), 0},
                {direction.getX(), direction.getY(), direction.getZ(), 0},
                {0, 0, 0, 1}
        });

        Matrix translation = new Matrix(new double[][]{
                {1, 0, 0, -position.getX()},
                {0, 1, 0, -position.getY()},
                {0, 0, 1, -position.getZ()},
                {0, 0, 0, 1}
        });

        lookAt = rotation.times(translation);
        System.out.println(lookAt);
    }

    public void moveX(double mov) {
        position.setX(position.getX() + mov);
        target.setX(target.getX() + mov);
    }

    public void moveY(double mov) {
        position.setY(position.getY() + mov);
        target.setY(target.getY() + mov);
    }

    public void moveZ(double mov) {
        position.setZ(position.getZ() + mov);
        target.setZ(target.getZ() + mov);
    }

    public void rotateX(double a) {
        yaw += a;
    }

    public void rotateY(double a) {
        pitch += a;
        if (pitch >= 90) {
            pitch = 90;
        }
        if (pitch <= -90) {
            pitch = -90;
        }
    }

    public Vector findViewerVector(PointDraw point) {
        Vector viewVector = new Vector(point.getVector()).normalizeEquals();
        return viewVector;
    }

    public void transformPointToCameraCoordinates(PointDraw point) {
        Point p = point.getPoint();
        p.timesRightEquals(lookAt);
    }

    public void transformPointToCameraScreen(PointDraw point) {
        Point p = point.getPoint();

        p.setY(p.getY() * screenDistance / p.getZ());
        p.setX(p.getX() * screenDistance / p.getZ());

    }

    public void transformPointToCanvas(PointDraw point) {
        Point p = point.getPoint();

        p.setX((int) (p.getX() + (screenWidth / 2)));
        p.setY((int) (screenHeight / 2 - p.getY()));
    }

    // ----- Геттеры и Сеттеры ----- //


    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public double getScreenDistance() {
        return screenDistance;
    }

    public void setScreenDistance(double screenDistance) {
        this.screenDistance = screenDistance;
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public double getZ() {
        return position.getZ();
    }
}

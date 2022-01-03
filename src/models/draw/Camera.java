package models.draw;

import Jama.Matrix;
import models.scene.Point;
import models.scene.Vector;

public class Camera {
    private Vector generalUp = new Vector(0, 1, 0);

    // ----- Переменные - позиция камеры ----- //

    public Vector position = new Vector(0, 0, 0);
    public Vector target = new Vector(0, 0, -1);

    public Vector forward;
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

    public Camera(Vector position, Vector target, Vector up, double yaw, double pitch, int screenDistance) {
        this.position = position;
        this.target = target;
        this.generalUp = up;
        this.yaw = yaw;
        this.pitch = pitch;
        this.screenDistance = screenDistance;
    }

    // ----- Методы ----- //

    private void findDirections() {
        forward = position.minus(target).normalizeEquals();
        forward.setX(Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        forward.setY(Math.sin(Math.toRadians(pitch)));
        forward.setZ(Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        forward.normalizeEquals();

        right = generalUp.cross(forward).normalizeEquals();
        up = forward.cross(right);
    }

    public void createLookAt() {
        findDirections();

        Matrix rotation = new Matrix(new double[][]{
                {right.getX(), right.getY(), right.getZ(), 0},
                {up.getX(), up.getY(), up.getZ(), 0},
                {forward.getX(), forward.getY(), forward.getZ(), 0},
                {0, 0, 0, 1}
        });

        Matrix translation = new Matrix(new double[][]{
                {1, 0, 0, -position.getX()},
                {0, 1, 0, -position.getY()},
                {0, 0, 1, -position.getZ()},
                {0, 0, 0, 1}
        });

        lookAt = rotation.times(translation);
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

    public void pointViewerVector(PointDraw p) {
        p.setViewerVector(new Vector(
                p.getVector()
        ).normalizeEquals());
    }

    public void pointToCameraCoordinates(PointDraw p) {
        p.getPoint().timesRightEquals(lookAt);
    }

    public void pointToCameraScreen(PointDraw point) {
        Point p = point.getPoint();

        p.setY(p.getY() * screenDistance / p.getZ());
        p.setX(p.getX() * screenDistance / p.getZ());
    }

    public void pointToCanvas(PointDraw point) {
        Point p = point.getPoint();

        p.setX(Math.round(p.getX() + (double) screenWidth / 2));
        p.setY(Math.round((double) screenHeight / 2 - p.getY()));
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

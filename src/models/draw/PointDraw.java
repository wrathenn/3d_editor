package models.draw;

import models.scene.Point;

public class PointDraw {
    private final Point point;
    private Point viewerVector;
    private Intensity intensity;

    // ----- Конструкторы ----- //

    public PointDraw(Point p) {
        this.point = new Point(p);
    }

    public PointDraw(PointDraw p) {
        this.point = new Point(p.point);
        this.viewerVector = p.viewerVector;
        this.intensity = p.intensity;
    }

    // ----- Геттеры и Сеттеры ----- //

    public Point getPoint() {
        return point;
    }

    public double getX() {
        return point.getX();
    }

    public void setX(double x) {
        point.setX(x);
    }

    public double getY() {
        return point.getY();
    }

    public void setY(double y) {
        point.setY(y);
    }

    public double getZ() {
        return point.getZ();
    }

    public void setZ(double z) {
        point.setZ(z);
    }

    public void setNameID(String nameID) {
        point.setNameID(nameID);
    }

    public String getNameID() {
        return point.getNameID();
    }

    public void setViewerVector(Point viewerVector) {
        this.viewerVector = viewerVector;
    }

    public Point getViewerVector() {
        return viewerVector;
    }

    public Intensity getIntensity() {
        return intensity;
    }

    public void setIntensity(Intensity intensity) {
        this.intensity = intensity;
    }

    // ----- Object ----- //

    static class PointDrawWrapper {
        PointDraw p;

        public PointDrawWrapper(PointDraw p) {
            this.p = p;
        }
    }

    public static void swap(PointDrawWrapper p1, PointDrawWrapper p2) {
        PointDraw pt = p1.p;
        p1.p = p2.p;
        p2.p = pt;
    }

    @Override
    public String toString() {
        return "PointDraw {" +
                "point = " + point +
                ", viewerVector = " + viewerVector +
                ", intensity = " + intensity +
                "}";
    }
}

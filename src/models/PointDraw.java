package models;

public class PointDraw {
    public Point point;
    public Point viewerVector;
    public Intensity intensity;

    public PointDraw(Point p) {
        this.point = new Point(p);
    }

    // ----- Геттеры и Сеттеры ----- //

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
}

package models;

public class PointDraw {
    public Point point;
    public Point normalLine;

    public PointDraw(Point p) {
        this.point = new Point(p);
    }

    public String getNameID() {
        return point.getNameID();
    }

    public void setNormalLine(Point normalLine) {
        this.normalLine = normalLine;
    }
}

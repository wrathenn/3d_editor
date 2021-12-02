package models.scene;

public class Point extends Vector {
    private String nameID;

    // ----- Конструкторы ----- //

    public Point() {
        super();
    }

    public Point(String nameID, double x, double y, double z) {
        super(x, y, z);
        this.nameID = nameID;
    }

    public Point(String nameId) {
        super();
        this.nameID = nameId;
    }

    public Point(double x, double y, double z) {
        super(x, y, z);
    }

    // Конструктор копирования
    public Point(Point p) {
        this(p.nameID, p.getX(), p.getY(), p.getZ());
    }

    // ----- Геттеры и Сеттеры ----- //

    public String getNameID() {
        return nameID;
    }

    public void setNameID(String nameID) {
        this.nameID = nameID;
    }

    // ----- Object ----- //

    static class PointWrapper {
        Point p;
        PointWrapper(Point point) {
            p = point;
        }
    }

    public static void swap(PointWrapper p1, PointWrapper p2) {
        Point temp = p1.p;
        p1.p = p2.p;
        p2.p = temp;
    }

    @Override
    public String toString() {
        return String.format("Point{Name=%s, x=%.6f, y=%.6f, z=%.6f}", nameID, getX(), getY(), getZ());
    }
}

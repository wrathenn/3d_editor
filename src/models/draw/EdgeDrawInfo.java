package models.draw;

public class EdgeDrawInfo {
    public double lenY;
    public double x;
    public double z;
    public double yBegin;
    public Intensity currentI;
    public Intensity dI;
    public double dx;
    public double dz;

    public EdgeDrawInfo(EdgeDraw edge, Camera camera) {
        PointDraw pBegin = new PointDraw(edge.getBegin());
        PointDraw pEnd = new PointDraw(edge.getEnd());
        camera.transformPointToCameraScreen(pBegin);
        camera.transformPointToCameraScreen(pEnd);

        if (pBegin.getY() < pEnd.getY()) {
            PointDraw temp = pBegin;
            pBegin = pEnd;
            pEnd = temp;
        }

        lenY = pBegin.getY() - pEnd.getY();
        x = pBegin.getX();
        yBegin = pBegin.getY();
        z = pBegin.getZ();
        currentI = new Intensity(pBegin.getIntensity());
        dI = new Intensity(pEnd.getIntensity()).minus(currentI).divide(lenY);
        dx = lenY != 0 ? (pEnd.getX() - pBegin.getX()) / lenY : 0;
        dz = lenY != 0 ? (pEnd.getZ() - pBegin.getZ()) / lenY : 0;
    }

    @Override
    public String toString() {
        return "EdgeDrawInfo{" +
                "lenY = " + lenY +
                ", x = " + x +
                ", z = " + z +
                ", yBegin = " + yBegin +
                ", currentI = " + currentI +
                ", dI = " + dI +
                ", dx = " + dx +
                ", dz = " + dz +
                '}';
    }
}

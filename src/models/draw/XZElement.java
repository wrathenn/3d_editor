package models.draw;

public class XZElement {
    public double x;
    public double z;
    public Intensity intensity;

    public XZElement(EdgeDrawInfo edgeInfo) {
        x = edgeInfo.x;
        z = edgeInfo.z;
        intensity = new Intensity(edgeInfo.currentI);
    }

    @Override
    public String toString() {
        return String.format("XZElem{x=%.4f, z=%.4f, " + intensity + "}", x, z);
    }
}

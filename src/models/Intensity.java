package models;

public class Intensity {
    public double redI;
    public double greenI;
    public double blueI;

    public Intensity(double r, double g, double b) {
        redI = r;
        greenI = g;
        blueI = b;
    }

    public void add(Intensity i) {
        redI += i.redI;
        greenI += i.greenI;
        blueI += i.blueI;
    }

    public static Intensity multiplyVector(Intensity i, Point p) {
        return new Intensity(
                i.redI *= p.x,
                i.greenI *= p.y,
                i.blueI *= p.z
        );
    }
}

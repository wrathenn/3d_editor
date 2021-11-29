package models.draw;

import models.scene.Point;

import java.awt.*;

public class Intensity {
    public double redI;
    public double greenI;
    public double blueI;

    public Intensity(double r, double g, double b) {
        redI = r;
        greenI = g;
        blueI = b;
    }

    public Intensity(Intensity i) {
        redI = i.redI;
        greenI = i.greenI;
        blueI = i.blueI;
    }

    public Intensity add(Intensity i) {
        redI += i.redI;
        greenI += i.greenI;
        blueI += i.blueI;
        return this;
    }

    public Intensity minus(Intensity i) {
        redI -= i.redI;
        greenI -= i.greenI;
        blueI -= i.blueI;
        return this;
    }

    public Intensity divide(double n) {
        redI /= n;
        greenI /= n;
        blueI /= n;
        return this;
    }

    public static Intensity multiplyVector(Intensity i, Point p) {
        return new Intensity(
                i.redI *= p.x,
                i.greenI *= p.y,
                i.blueI *= p.z
        );
    }

    public Color applyColor(Color c) {
        return new Color(
                (int) (c.getRed() * redI),
                (int) (c.getGreen() * greenI),
                (int) (c.getBlue() * blueI)
        );
    }

    @Override
    public String toString() {
        return "Intensity: " + redI + "|"+ greenI + "|"+ blueI;
    }
}

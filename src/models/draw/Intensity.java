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

    public static Intensity multiply(Intensity i, double n) {
        return new Intensity(
                Math.abs(i.redI * n),
                Math.abs(i.greenI * n),
                Math.abs(i.blueI * n)
        );
    }

    public Color applyColor(Color c) {
        return new Color(
                (int) (c.getRed() * redI),
                (int) (c.getGreen() * greenI),
                (int) (c.getBlue() * blueI)
        );
    }

    public void applyColorDBG(Color c) {
        System.out.printf("DBG color: %d, %d, %d%n\n",
                (int) (c.getRed() * redI),
                (int) (c.getGreen() * greenI),
                (int) (c.getBlue() * blueI));
    }

    @Override
    public String toString() {
        return String.format("Intensity{redI=%.4f, greenI=%.4f, blueI=%.4f}", redI, greenI, blueI);
    }
}

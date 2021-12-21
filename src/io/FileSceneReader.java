package io;

import models.scene.Point;
import models.scene.Vector;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class FileSceneReader {
    private BufferedReader file;
    private int lineIndex;

    public FileSceneReader(String filepath) throws FileNotFoundException {
        file = new BufferedReader(new java.io.FileReader(filepath));
        lineIndex = 0;
    }

    public int getLineIndex() {
        return lineIndex;
    }

    private String readLine() throws IOException {
        lineIndex++;
        String s;
        try {
            s = file.readLine();
        } catch (IOException e) {
            throw new IOException(String.format(
                    "Ошибка при чтении - строка #%d.", lineIndex
            ));
        }
        return s;
    }

    public String readHeader(String expectedWord) throws IOException {
        String header = readLine();

        if (!Objects.equals(header, expectedWord)) {
            throw new IOException(String.format(
                    "Некорректный заголовок - строка #%d. (Необходимый - %s)",
                    lineIndex, expectedWord
            ));
        }

        return header;
    }

    public int readInt() throws IOException {
        int res;
        String sInt = readLine();

        try {
            res = Integer.parseInt(sInt);
        } catch (NumberFormatException e) {
            throw new IOException(String.format(
                    "Ошибка при чтении целого числа из файла - строка #%d.", lineIndex
            ));
        }

        return res;
    }

    public double readDouble() throws IOException {
        double res;
        String sInt = readLine();

        try {
            res = Double.parseDouble(sInt);
        } catch (NumberFormatException e) {
            throw new IOException(String.format(
                    "Ошибка при чтении вещественного числа из файла - строка #%d.", lineIndex
            ));
        }

        return res;
    }

    public Point readPoint() throws IOException {
        String s = readLine();
        String[] pointValues = s.split(" ");
        if (pointValues.length != 4) {
            throw new IOException(String.format(
                    "Ошибка при чтении Point из файла. Некорректный формат. Строка #%d.", lineIndex
            ));
        }

        String sName = pointValues[0];
        double x, y, z;
        try {
            x = Double.parseDouble(pointValues[1]);
            y = Double.parseDouble(pointValues[2]);
            z = Double.parseDouble(pointValues[3]);
        } catch (NumberFormatException e) {
            throw new IOException(String.format(
                    "Ошибка при чтении координат Point из файла. Некорректный формат. Строка #%d.", lineIndex
            ));
        }

        return new Point(sName, x, y, z);
    }

    public String[] readPolygonPoints() throws IOException {
        String s = readLine();
        String[] pointNames = s.split(" ");
        if (pointNames.length <= 2) {
            throw new IOException(String.format(
                    "Ошибка при чтении Polygon из файла. Некорректный формат. Строка #%d.", lineIndex
            ));
        }

        return pointNames;
    }

    public Color readPolygonColor() throws IOException {
        int value = readInt();

        return new Color(value, true);
    }

    public Vector readVector() throws IOException {
        String s = readLine();
        String[] vectorValues = s.split(" ");
        if (vectorValues.length != 3) {
            throw new IOException(String.format(
                    "Ошибка при чтении Vector. Некорректный формат. Строка #%d.", lineIndex
            ));
        }

        double x, y, z;
        try {
            x = Double.parseDouble(vectorValues[0]);
            y = Double.parseDouble(vectorValues[1]);
            z = Double.parseDouble(vectorValues[2]);
        } catch (NumberFormatException e) {
            throw new IOException(String.format(
                    "Ошибка при чтении координат Vector. Некорректный формат. Строка #%d.", lineIndex
            ));
        }

        return new Vector(x, y, z);
    }
}

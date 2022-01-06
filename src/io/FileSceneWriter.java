package io;

import models.draw.Camera;
import models.scene.Point;
import models.scene.Polygon;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Collection;

public class FileSceneWriter {
    private BufferedWriter file;

    public FileSceneWriter(String filepath) throws IOException {
        file = new BufferedWriter(new java.io.FileWriter(filepath));
    }

    public void writePoints(Collection<Point> points) throws IOException {
        file.write("POINTS:\n");
        file.write(points.size() + "\n");

        for (Point p : points) {
            file.write(p.getNameID() + " ");
            file.write(p.getX() + " " + p.getY() + " " + p.getZ() + "\n");
        }
    }

    public void writePolygons(Collection<Polygon> polys) throws IOException {
        file.write("POLYGONS:\n");
        file.write(polys.size() + "\n");

        for (Polygon poly : polys) {
            Point[] points = poly.getPoints();
            for (int i = 0; i < points.length; i++) {
                file.write(points[i].getNameID() +
                        (i != points.length - 1 ? " " : ""));
            }
            file.write("\n");
            file.write(poly.getColor().getRGB() + "\n");
        }
    }

    public void writeCamera(Camera camera) throws IOException {
        file.write("CAMERA:\n");
        file.write(camera.position.getX() + " " + camera.position.getY() + " " + camera.position.getZ() + "\n");
        file.write(camera.target.getX() + " " + camera.target.getY() + " " + camera.target.getZ() + "\n");
        file.write(camera.up.getX() + " " + camera.up.getY() + " " + camera.up.getZ() + "\n");
        file.write(camera.getYaw() + "\n");
        file.write(camera.getPitch() + "\n");
        file.write(camera.getScreenDistance() + "\n");
    }

    public void finishWrite() throws IOException {
        file.close();
    }
}

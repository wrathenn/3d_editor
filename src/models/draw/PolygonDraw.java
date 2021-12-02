package models.draw;

import libs.SharedFunctions;
import models.scene.Point;
import models.scene.Vector;

import java.awt.*;
import java.util.ArrayList;

public class PolygonDraw {
    private Vector normalLine;
    private ArrayList<EdgeDraw> edges;
    private Color color;

    // ----- Конструкторы ----- //

    public PolygonDraw(ArrayList<EdgeDraw> edges, Color color) {
        this.edges = edges;
        this.color = color;
    }

    // ----- Геттеры и Сеттеры ----- //

    public ArrayList<EdgeDraw> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<EdgeDraw> edges) {
        this.edges = edges;
    }

    public Vector getNormalLine() {
        return normalLine;
    }

    public void setNormalLine(Vector normalLine) {
        this.normalLine = normalLine;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    // ----- Остальные методы ----- //

    public void findNormalLine() {
        Point p1, p2, p3;
        for (int i = 0; i < edges.size(); i++) {
            p1 = edges.get(i).getBegin().getPoint();
            p2 = edges.get(i).getEnd().getPoint();
            p3 = edges.get(i == edges.size() - 1 ? 0 : i + 1).getEnd().getPoint();

            double vx1 = p1.getX() - p2.getX();
            double vx2 = p2.getX() - p3.getX();
            double vy1 = p1.getY() - p2.getY();
            double vy2 = p2.getY() - p3.getY();
            double vz1 = p1.getZ() - p2.getZ();
            double vz2 = p2.getZ() - p3.getZ();

            double nx = vy1 * vz2 - vz1 * vy2;
            double ny = vz1 * vx2 - vx1 * vz2;
            double nz = vx1 * vy2 - vy1 * vx2;

            // Точки на одной прямой
            if (SharedFunctions.doubleCompare(nx, 0) == 0
                    && SharedFunctions.doubleCompare(ny, 0) == 0
                    && SharedFunctions.doubleCompare(nz, 0) == 0) {
                continue;
            }

            double len = Math.sqrt(nx * nx + ny * ny + nz * nz);
            nx /= len;
            ny /= len;
            nz /= len;

            normalLine = new Vector(nx, ny, nz);
            return;
        }
    }
}

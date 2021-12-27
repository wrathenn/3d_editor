package views.editor;

import Jama.Matrix;
import models.scene.Edge;
import models.scene.Polygon;
import models.scene.Vector;
import models.scene.Point;

import javax.swing.*;
import java.awt.*;

public class PolygonEditor {
    Polygon sourcePoly;
    Polygon finalPoly;

    JPanel canvas;

    public PolygonEditor(Polygon sourcePoly) {
        this.sourcePoly = sourcePoly;
    }

    public void setCanvas(JPanel canvas) {
        this.canvas = canvas;
    }

    public void countFinalPoly() {
        Point[] points = sourcePoly.getPoints();
        Point[] newPoints = new Point[points.length];

        Point center = new Point(0, 0, 0);
        for (Point point : points) {
            center.plusEquals(point);
        }
        center.divideEquals(points.length);

        for (int i = 0; i < points.length; i++) {
            Point newP = new Point(points[i]);
            newP.minusEquals(center); // Перенос к центру
            newPoints[i] = newP;
        }

        Vector normalLine = sourcePoly.findNormalLine();
        Vector p1 = new Point(newPoints[0]).normalizeEquals();
        Vector p2 = p1.cross(normalLine).normalizeEquals();

        Matrix m = new Matrix(new double[][]{
                {p1.getX(), p1.getY(), p1.getZ(), 0},
                {p2.getX(), p2.getY(), p2.getZ(), 0},
                {normalLine.getX(), normalLine.getY(), normalLine.getZ(), 0},
                {0, 0, 0, 1}
        });

        for (Point p : newPoints) {
            p.timesRightEquals(m);
        }

        finalPoly = new Polygon(newPoints, Color.black);
    }

    private static int toScreenX(double x, int w) {
        return (int) Math.round(x + (double) w / 2);
    }

    private static int toScreenY(double y, int h) {
        return (int) Math.round((double) h / 2 - y);
    }

    public void drawPoly(Graphics g) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        for (Edge e : finalPoly.getEdges()) {
            Point begin = e.getBegin();
            Point end = e.getEnd();
            int x1 = toScreenX(begin.getX(), width);
            int y1 = toScreenY(begin.getY(), height);
            int x2 = toScreenX(end.getX(), width);
            int y2 = toScreenY(end.getY(), height);

            g.drawLine(x1, y1, x2, y2);
        }
    }
}

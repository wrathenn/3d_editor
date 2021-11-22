package repositories;

import models.*;
import models.Point;
import models.Polygon;

import java.awt.*;

public class DrawerVisitor implements Visitor {
    protected Graphics canvas;
    protected Camera camera;

    private double zBuffer[][];

    private double width;
    private double height;

    public DrawerVisitor(int width, int height) {
        super();
        this.setSize(width, height);
    }

    public DrawerVisitor(Dimension dim) {
        super();
        this.setSize(dim);
    }

    public void setCanvas(Graphics canvas) {
        this.canvas = canvas;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setSize(Dimension dim) {
        this.setSize(dim.width, dim.height);
    }

    public void setSize(int width, int height) {
        if (this.width < width || this.height < height) {
            zBuffer = new double[width][height];
        }
        this.width = width;
        this.height = height;
    }

    public void clearBuffer() {
        for (int i = 0; i < width; i++) {
            for (int k = 0; k < height; k++) {
                zBuffer[i][k] = Integer.MIN_VALUE;
            }
        }
    }

    private double[] interpolate(double i0, double d0, double i1, double d1) {
        if (i0 == i1) {
            return new double[]{d0};
        }

        int size = (int) (i1 - i0) + 1;
        double[] values = new double[size];
        double a = (d1 - d0) / (i1 - i0);
        double d = d0;

        for (int i = 0; i < size; i++) {
            values[i] = d;
            d += a;
        }

        System.out.println("len---" + values.length);

        return values;
    }

    private void drawLine(Point p1, Point p2) {
        canvas.setColor(Color.ORANGE);
//        canvas.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
//        /*
        canvas.setColor(Color.GREEN);
        if (Math.abs(p2.x - p1.x) > Math.abs(p2.y - p1.y)) {
            // Прямая ближе к горизонтальной
            if (p1.x > p2.x) {
                Point temp = p1;
                p1 = p2;
                p2 = temp;
            }

            double[] valuesY = interpolate(p1.x, p1.y, p2.x, p2.y);
            double[] valuesZ = interpolate(p1.x, p1.z, p2.x, p2.z);
            int i = 0;
            for (double x = p1.x; x <= p2.x; x++, i++) {
                int xt = (int) x;
                int y = (int) valuesY[i];
                int z = (int) valuesZ[i];
//                canvas.drawLine(xt, y, xt, y);
                drawPixel(xt, y, z);
            }
        } else {
            if (p1.y > p2.y) {
                Point temp = p1;
                p1 = p2;
                p2 = temp;
            }

            double[] valuesY = interpolate(p1.y, p1.x, p2.y, p2.x);
            double[] valuesZ = interpolate(p1.y, p1.z, p2.y, p2.z);
            int i = 0;
            for (double y = p1.y; y <= p2.y; y++, i++) {
                int yt = (int) y;
                int x = (int) valuesY[i];
                int z = (int) valuesZ[i];
//                canvas.drawLine(x, yt, x, yt);
                drawPixel(x, yt, z);
            }
        }
//         */
    }

    private void drawPixel(int x, int y, int z) {
        if (x >= width || x < 0 || y >= height || y < 0) {
            return;
        }
        if (zBuffer[x][y] < z) {
            zBuffer[x][y] = z;
            canvas.drawLine(x, y, x, y);
        }
    }

    private void transformPointCamera(Point p) {
        p.x -= camera.getX();
        p.y -= camera.getY();
        p.z -= camera.getZ();

        p.y = p.y * camera.getScreenDistance() / p.z;
        p.x = p.x * camera.getScreenDistance() / p.z;
        p.z = camera.getScreenDistance();

        p.x += (int) (camera.getScreenWidth() / 2);
        p.y = (int) (camera.getScreenHeight() / 2) - p.y;
    }

    @Override
    public void visit(Point p) {
        transformPointCamera(p);

        canvas.drawOval((int) p.x, (int) p.y, 2, 2);
        canvas.drawString(p.getNameID(), (int) p.x - 2, (int) p.y - 2);

        System.out.println("Drawing point");
    }

    @Override
    public void visit(Edge e) {
        Point begin = new Point(e.getBegin());
        Point end = new Point(e.getEnd());

        visit(begin);
        visit(end);

//        transformPointCamera(begin);
//        transformPointCamera(end);

        drawLine(begin, end);

        System.out.println("Drawing edge");
    }

    @Override
    public void visit(Polygon p) {
        for (Edge e : p.getEdges()) {
            this.visit(e);
        }

        System.out.println("Drawing Polygon");
    }
}

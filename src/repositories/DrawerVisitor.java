package repositories;

import models.*;
import models.Point;
import models.Polygon;

import java.awt.*;
import java.util.ArrayList;

public class DrawerVisitor implements Visitor {
    protected Graphics canvas;
    protected Camera camera;

    private double zBuffer[][];

    private final Intensity diffusionI = new Intensity(0.3, 0.3, 0.3);
    private final Intensity backGroundI = new Intensity(0.2, 0.2, 0.2);

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
        if (Math.abs(p2.getX() - p1.getX()) > Math.abs(p2.getY() - p1.getY())) {
            // Прямая ближе к горизонтальной
            if (p1.getX() > p2.getX()) {
                Point temp = p1;
                p1 = p2;
                p2 = temp;
            }

            double[] valuesY = interpolate(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            double[] valuesZ = interpolate(p1.getX(), p1.getZ(), p2.getX(), p2.getZ());
            int i = 0;
            for (double x = p1.getX(); x <= p2.getX(); x++, i++) {
                int xt = (int) x;
                int y = (int) valuesY[i];
                int z = (int) valuesZ[i];
//                canvas.drawLine(xt, y, xt, y);
                drawPixel(xt, y, z);
            }
        } else {
            if (p1.getY() > p2.getY()) {
                Point temp = p1;
                p1 = p2;
                p2 = temp;
            }

            double[] valuesY = interpolate(p1.getY(), p1.getX(), p2.getY(), p2.getX());
            double[] valuesZ = interpolate(p1.getY(), p1.getZ(), p2.getY(), p2.getZ());
            int i = 0;
            for (double y = p1.getY(); y <= p2.getY(); y++, i++) {
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

    public void transformPointCamera(PointDraw point) {
        Point p = point.point;
        p.setX(p.getX() - camera.getX());
        p.setY(p.getY() - camera.getY());
        p.setZ(p.getZ() - camera.getZ());

        p.setY(p.getY() * camera.getScreenDistance() / p.getZ());
        p.setX(p.getX() * camera.getScreenDistance() / p.getZ());
        p.setZ(camera.getScreenDistance());

        p.setX((int) (p.getX() + (camera.getScreenWidth() / 2)));
        p.setY((int) (camera.getScreenHeight() / 2 - p.getY()));
    }

    public void findViewerVector(PointDraw point) {
        Point viewVector = new Point(
                point.getX() - camera.getX(),
                point.getY() - camera.getY(),
                point.getZ() - camera.getZ()
        ).makeUnitVector();
        point.setViewerVector(viewVector);
    }

    public void findPointColor(PointDraw point, Point polyNormal) {
        Point diffusionVector = Point.multiplyOneByOne(point.viewerVector, polyNormal);
        Intensity curI = Intensity.multiplyVector(diffusionI, diffusionVector);
        curI.add(backGroundI);
    }

    @Override
    public void visit(Point p) {
//        transformPointCamera(p);

        canvas.drawOval((int) p.getX(), (int) p.getY(), 2, 2);
        canvas.drawString(p.getNameID(), (int) p.getX() - 2, (int) p.getY() - 2);

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
        // Преобразование по камере
        // В каждом ребре точка начала будет выше по Y
        for (Edge e : p.getEdges()) {
            Point pBegin = e.getBegin();
            Point pEnd = e.getEnd();

//            transformPointCamera(pBegin);
//            transformPointCamera(pEnd);

            if (pBegin.getY() < pEnd.getY()) {
                Point.swap(pBegin, pEnd);
            }
        }

        // Отсортировать ребра по убыванию Y начала ребра
        ArrayList<Edge> edges = new ArrayList<>(p.getEdges());
        edges.sort((a, b) -> {
            if (a.getBegin().getY() > b.getBegin().getY()) {
                return -1;
            } else if (a.getBegin().getY() < b.getBegin().getY()) {
                return 1;
            }
            return 0;
        });

        // Найти нормаль в каждой точке по той формуле

        System.out.println("Drawing Polygon");
    }
}

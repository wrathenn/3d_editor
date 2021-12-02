package repositories;

import libs.SharedFunctions;
import libs.SortedLinkedList;
import models.draw.*;
import models.scene.Point;
import models.scene.Vector;

import java.awt.*;
import java.util.*;
import java.util.List;

public class DrawerZBuffer {
    protected Graphics canvas;
    protected Camera camera;

    private double[][] zBuffer;

    private final Intensity diffusionI = new Intensity(0.5, 0.5, 0.5);
    private final Intensity backGroundI = new Intensity(0.35, 0.35, 0.35);

    private int width;
    private int height;
    private Object IllegalArgumentException;

    public DrawerZBuffer(int width, int height) {
        super();
        this.setSize(width, height);
    }

    public DrawerZBuffer(Dimension dim) {
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
                zBuffer[i][k] = Integer.MAX_VALUE;
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

        return values;
    }

    private void drawLine(Point p1, Point p2, Color c) {
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
                double z = valuesZ[i];
//                canvas.drawLine(xt, y, xt, y);
                drawPixelCheck(xt, y, z, c);
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
                double z = valuesZ[i];
//                canvas.drawLine(x, yt, x, yt);
                drawPixelCheck(x, yt, z, c);
            }
        }
//         */
    }

    private void drawPixelCheck(int x, int y, double z, Color c) {
        if (x >= width || x < 0 || y >= height || y < 0) {
            return;
        }
        drawPixel(x, y, z, c);
    }

    private void drawPixelCheckX(int x, int y, double z, Color c) {
        if (x >= width || x < 0) {
            return;
        }
        drawPixel(x, y, z, c);
    }

    private void drawPixel(int x, int y, double z, Color c) {
        canvas.setColor(c);

        if (zBuffer[x][y] > z) {
            zBuffer[x][y] = z;
            canvas.drawLine(x, y, x, y);
        }
//        canvas.drawLine(x, y, x, y);
    }

    public void transformPointToCameraCoordinates(PointDraw point) {
        camera.transformPointToCameraCoordinates(point);
    }

    public void transformPointToCameraScreen(PointDraw point) {
        camera.transformPointToCameraScreen(point);
    }

    public void findViewerVector(PointDraw point) {
        Vector viewerVector = camera.findViewerVector(point);
        point.setViewerVector(viewerVector);
    }

    public void findPointColor(PointDraw point, Vector polyNormal) {
        // Почленное умножение нормали точки на вектор к наблюдателю
        double diffusionLN = Point.scalarProduct(point.getViewerVector(), polyNormal);
        // Нахождение диффузной составляющей интенсивности
        Intensity intensity = Intensity.multiply(diffusionI, diffusionLN);
        // Прибавить фоновую составляющую
        intensity.add(backGroundI);

        point.setIntensity(intensity);
    }

    private void renderSortEdges(List<EdgeDraw> edges, SortedLinkedList<EdgeDrawInfo> infoList) {
        for (EdgeDraw e : edges) {
            EdgeDrawInfo newInfo = new EdgeDrawInfo(e, camera);
            infoList.add(newInfo);
        }
    }


    private void drawXZList(SortedLinkedList<XZElement> xzList, int currentY, Color polyColor) {
        if (currentY >= height || currentY < 0) {
            return;
        }

        while (xzList.size() != 0) {
            XZElement xzBegin = xzList.pop();
            XZElement xzEnd;
            if (xzList.size() == 0) {
                xzEnd = xzBegin;
            } else {
                xzEnd = xzList.pop();
            }

            double dz = (xzEnd.z - xzBegin.z) / (xzEnd.x - xzBegin.x);
            Intensity dI = xzEnd.intensity.minus(xzBegin.intensity).divide(xzEnd.x - xzBegin.x);

            while (xzBegin.x < xzEnd.x) {
                Color c1 = xzBegin.intensity.applyColor(polyColor);

                drawPixelCheckX((int) xzBegin.x, currentY, xzBegin.z, c1);

                xzBegin.x++;
                xzBegin.z += dz;
                xzBegin.intensity.add(dI);
            }
        }
    }

    private void activeListStep(List<EdgeDrawInfo> activeList) {
        for (int i = 0; i < activeList.size(); i++) {
            EdgeDrawInfo edgeInfo = activeList.get(i);
            edgeInfo.x += edgeInfo.dx;
            edgeInfo.z += edgeInfo.dz;

            edgeInfo.yBegin--;
            edgeInfo.lenY--;
            edgeInfo.currentI.add(edgeInfo.dI);

            if (edgeInfo.lenY <= 0) {
                activeList.remove(i);
                i--;
            }
        }
    }

    public void drawPolygon(PolygonDraw poly) {
        // В каждом ребре начало будет выше по Y, чем конец
        SortedLinkedList<EdgeDrawInfo> infoList = new SortedLinkedList<>((o1, o2) ->
                SharedFunctions.doubleCompare(o2.yBegin, o1.yBegin));
        renderSortEdges(poly.getEdges(), infoList);

        // Проход по сканирующим строкам
        int currentY = (int) infoList.getFirst().yBegin;
        LinkedList<EdgeDrawInfo> activeList = new LinkedList<>();
        activeList.add(infoList.pop());

        Color polyColor = poly.getColor();
        SortedLinkedList<XZElement> xzList = new SortedLinkedList<>((o1, o2) ->
                SharedFunctions.doubleCompare(o1.x, o2.x));

        while (infoList.size() != 0 || activeList.size() != 0) {
            // Пока в infoList в начале содержится ребро, у которого y_верх≥curY: добавить это ребро в activeList
            while (infoList.size() != 0 && infoList.getFirst().yBegin >= currentY) {
                activeList.add(infoList.pop());
            }

            // Сформировать массив currentXZ на основе activeList
            for (EdgeDrawInfo edgeInfo : activeList) {
                xzList.add(new XZElement(edgeInfo));
            }

            drawXZList(xzList, currentY, polyColor);
            activeListStep(activeList);
            currentY--;
        }
    }
}

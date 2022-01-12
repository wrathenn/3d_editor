package repositories;

import Jama.Matrix;
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
    public Camera camera;

    private double[][] zBuffer;
    private UUID[][] polyReferences;
    private String[][] pointReferences;

    private final Intensity diffusionI = new Intensity(0.5, 0.5, 0.5);
    private final Intensity backGroundI = new Intensity(0.35, 0.35, 0.35);

    private int width;
    private int height;

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
            polyReferences = new UUID[width][height];
            pointReferences = new String[width][height];
        }
        this.width = width;
        this.height = height;
    }

    // ----- Методы для канваса ----- //

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

    private void drawLine(Point p1, Point p2, Color c, int thickness) {
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
                drawPixel(xt, y, z, c);
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
                drawPixel(x, yt, z, c);
            }
        }
    }

    private boolean checkBoundaries(int x, int y) {
        return x >= width || x < 0 || y >= height || y < 0;
    }

    private boolean checkBuffer(int x, int y, double z) {
        return zBuffer[x][y] < z;
    }

    private void _drawPixel(int x, int y, double z, Color c) {
        zBuffer[x][y] = z;
        canvas.setColor(c);
        canvas.drawLine(x, y, x, y);
    }

    private void drawPixel(int x, int y, double z, Color c) {
        if (checkBoundaries(x, y) || checkBuffer(x, y, z)) {
            return;
        }
        _drawPixel(x, y, z, c);
    }

    private void drawPixel(int x, int y, double z, Color c, String pointId) {
        if (checkBoundaries(x, y) || checkBuffer(x, y, z)) {
            return;
        }
        _drawPixel(x, y, z, c);
        pointReferences[x][y] = pointId;
    }

    private void drawPixel(int x, int y, double z, Color c, UUID polyId) {
        if (checkBoundaries(x, y) || checkBuffer(x, y, z)) {
            return;
        }
        _drawPixel(x, y, z, c);
        polyReferences[x][y] = polyId;
    }

    public Color setColor(Color c) {
        Color old = canvas.getColor();
        canvas.setColor(c);
        return old;
    }

    // ----- Методы для отрисовки ----- //

    // ----- Буферы

    public void clearBuffer() {
        for (int i = 0; i < width; i++) {
            for (int k = 0; k < height; k++) {
                zBuffer[i][k] = Integer.MAX_VALUE;
                pointReferences[i][k] = null;
                polyReferences[i][k] = null;
            }
        }
    }

    public UUID getPolyId(int x, int y) {
        return polyReferences[x][y];
    }

    public String getPointName(int x, int y) {
        return pointReferences[x][y];
    }

    // ----- Работа с точкой

    public void findPointColor(PointDraw point, Vector polyNormal) {
        // Почленное умножение нормали точки на вектор к наблюдателю
        double diffusionLN = Point.scalarProduct(point.getViewerVector(), polyNormal);
        // Нахождение диффузной составляющей интенсивности
        Intensity intensity = Intensity.multiplyEquals(diffusionI, diffusionLN);
        // Прибавить фоновую составляющую
        intensity.addEquals(backGroundI);

        point.setIntensity(intensity);
    }

    // ----- Полигон

    public void drawPolygon(PolygonDraw poly, UUID polyId, HashMap<String, PointDraw> pointsOufOfView) {
        for (PointDraw p : poly.getPoints()) {
            if (checkBoundaries((int) Math.round(p.getX()), (int) Math.round(p.getY()))
                    || pointsOufOfView.containsKey(p.getNameID())) {
                return;
            }
        }
        // В каждом ребре начало будет выше по Y, чем конец
        SortedLinkedList<EdgeDrawInfo> infoList = new SortedLinkedList<>((o1, o2) -> {
            int res1 = SharedFunctions.doubleCompare(o2.yBegin, o1.yBegin);
            if (res1 == 0) {
                return SharedFunctions.doubleCompare(o2.yBegin - o2.lenY, o1.yBegin - o1.lenY);
            }
            return res1;
        });
        renderSortEdges(poly.getEdges(), infoList);

        // Проход по сканирующим строкам
        int currentY = (int) Math.round(infoList.getFirst().yBegin);
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

            drawXZList(xzList, currentY, polyColor, polyId);
            activeListStep(activeList);
            currentY--;
        }
    }

    private void renderSortEdges(List<EdgeDraw> edges, SortedLinkedList<EdgeDrawInfo> infoList) {
        for (EdgeDraw e : edges) {
            EdgeDrawInfo newInfo = new EdgeDrawInfo(e);
            infoList.add(newInfo);
        }
    }

    private void drawXZList(SortedLinkedList<XZElement> xzList, int currentY, Color polyColor, UUID polyId) {
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

            int xb = (int) Math.round(xzBegin.x);
            int xe = (int) Math.round(xzEnd.x);

            double dz = (xzEnd.z - xzBegin.z) / (xzEnd.x - xzBegin.x);
            Intensity dI = xzEnd.intensity.minus(xzBegin.intensity).divideEquals(xe - xb);

            while (xb < xe) {
                Color c1 = xzBegin.intensity.applyColor(polyColor);

                drawPixel(xb, currentY, xzBegin.z, c1, polyId);

                xb++;
                xzBegin.z += dz;
                xzBegin.intensity.addEquals(dI);
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
            edgeInfo.currentI.addEquals(edgeInfo.dI);

            if (SharedFunctions.doubleCompare(edgeInfo.lenY, 0f) == 0 ||
                    SharedFunctions.doubleCompare(edgeInfo.lenY, 0f) == -1) {
                activeList.remove(i);
                i--;
            }
        }
    }

    public void transformPointOnCamera(PointDraw p) {
        camera.pointToCameraCoordinates(p);
        camera.pointViewerVector(p);
        camera.pointToCameraScreen(p);
        camera.pointToCanvas(p);
    }

    // ----- Для интерфейса

    private void drawPointName(PointDraw p, Point pointReal, int pointNameMode) {
        switch (pointNameMode) {
            case 1:
                canvas.drawString(p.getNameID(), (int) p.getX() - 7, (int) p.getY() - 7);
                break;
            case 2:
                canvas.drawString(String.format("%s(%.3f, %.3f, %.3f)",
                                p.getNameID(),
                                pointReal.getX(),
                                pointReal.getY(),
                                pointReal.getZ()
                        ),
                        (int) p.getX() - 7, (int) p.getY() - 7);
                break;
            default:
                break;
        }
    }

    public void drawPoint(PointDraw point, Point pointReal, boolean isSelected, int pointNameMode) {
        int x = (int) point.getX();
        int y = (int) point.getY();
        double z = point.getZ();

        if (checkBoundaries(x - 4, y - 4) || checkBoundaries(x + 4, y + 4)) {
            return;
        }

        if (SharedFunctions.doubleCompare(zBuffer[x][y], z) >= 0) {
            String nameId = point.getNameID();

            canvas.setColor(isSelected ? Color.blue : Color.black);
            canvas.fillRect(x - 3, y - 3, 7, 7);
            drawPointName(point, pointReal, pointNameMode);

            for (int i = -2; i < 3; i++) {
                for (int k = -2; k < 5; k++) {
                    pointReferences[x - i][y - k] = nameId;
                }
            }
        }
    }
}

package controllers;

import libs.HashMapUnique;
import models.draw.Camera;
import models.draw.EdgeDraw;
import models.draw.PointDraw;
import models.draw.PolygonDraw;
import models.scene.Edge;
import models.scene.Point;
import models.scene.Polygon;
import org.jetbrains.annotations.Nullable;
import repositories.DrawerZBuffer;
import repositories.SceneRepository;

import java.awt.*;
import java.util.*;
import java.util.List;

public class DrawController {
    private DrawerZBuffer drawerVisitor;

    // ----- Конструкторы ----- //

    public DrawController() {
    }

    public DrawController(DrawerZBuffer drawerVisitor) {
        this.drawerVisitor = drawerVisitor;
    }

    // ----- Сеттеры и Геттеры ----- //

    public void setDrawerVisitor(DrawerZBuffer drawerVisitor) {
        this.drawerVisitor = drawerVisitor;
    }

    public void setCanvas(Graphics canvas) {
        this.drawerVisitor.setCanvas(canvas);
    }

    public void setCamera(Camera camera) {
        this.drawerVisitor.setCamera(camera);
    }

    // ----- Нормальные методы ----- //

    private @Nullable PointDraw findPointDrawByName(String name, List<PointDraw> pointList) {
        for (PointDraw p : pointList) {
            if (name.equals(p.getNameID())) {
                return p;
            }
        }
        return null;
    }

    private @Nullable EdgeDraw findEdgeDrawByPointNames(Edge edge, List<EdgeDraw> edgeList) {
        for (EdgeDraw e : edgeList) {
            if (edge.getBegin().getNameID().equals(e.getBegin().getNameID()) && edge.getEnd().getNameID().equals(e.getEnd().getNameID())) {
                return e;
            }
            if (edge.getBegin().getNameID().equals(e.getEnd().getNameID()) && edge.getEnd().getNameID().equals(e.getBegin().getNameID())) {
                return e;
            }
        }
        return null;
    }

    private void drawCopy(SceneRepository repo,
                          HashMap<String, PointDraw> pointsToDraw,
                          HashMap<UUID, PolygonDraw> polygonsToDraw) {

        for (Map.Entry<String, Point> pointEntry : repo.getPoints().entrySet()) {
            pointsToDraw.put(pointEntry.getKey(), new PointDraw(pointEntry.getValue()));
        }

        for (Map.Entry<UUID, Polygon> polyEntry : repo.getPolygons().entrySet()) {
            Polygon oldPoly = polyEntry.getValue();

            ArrayList<Edge> oldEdges = oldPoly.getEdges();
            PointDraw[] points = new PointDraw[oldPoly.getEdges().size()];
            for (int i = 0; i < oldEdges.size(); i++) {
                points[i] = pointsToDraw.get(oldEdges.get(i).getBegin().getNameID());
            }

            polygonsToDraw.put(polyEntry.getKey(), new PolygonDraw(points, oldPoly.getColor()));
        }
    }

    private void findPointsColorInPolygon(PolygonDraw p) {
        for (EdgeDraw e : p.getEdges()) {
            if (e.getBegin().getIntensity() == null) {
                drawerVisitor.findPointColor(e.getBegin(), p.getNormalLine());
            }
            if (e.getEnd().getIntensity() == null) {
                drawerVisitor.findPointColor(e.getEnd(), p.getNormalLine());
            }
        }
    }

    private void clearPointsColorInPolygon(PolygonDraw p) {
        for (EdgeDraw e : p.getEdges()) {
            if (e.getBegin().getIntensity() != null) {
                e.getBegin().setIntensity(null);
            }
            if (e.getEnd().getIntensity() != null) {
                e.getEnd().setIntensity(null);
            }
        }
    }

    private void drawPreprocessing(HashMap<String, PointDraw> points,
                                   HashMap<UUID, PolygonDraw> polygons) {

        for (PointDraw p : points.values()) {
            drawerVisitor.transformPointToCameraCoordinates(p);
            drawerVisitor.findViewerVector(p);
        }

        for (PolygonDraw poly : polygons.values()) {
            poly.findNormalLine();
            // Найти цвет каждой точки, не находить, если уже найден
            findPointsColorInPolygon(poly);

            // Отрисовка полигона
            drawerVisitor.drawPolygon(poly);

            // Очистить цвет каждой точки
            clearPointsColorInPolygon(poly);
        }
    }

    public void draw(Graphics canvas, Camera camera, SceneRepository sceneRepository) {
        drawerVisitor.setSize(camera.getScreenWidth(), camera.getScreenHeight());
        drawerVisitor.clearBuffer();

        drawerVisitor.setCanvas(canvas);
        drawerVisitor.setCamera(camera);

        HashMap<String, PointDraw> pointsToDraw = new HashMap<>();
        HashMap<UUID, PolygonDraw> polygonsToDraw = new HashMap<>();

        drawCopy(sceneRepository, pointsToDraw, polygonsToDraw);

        canvas.clearRect(0, 0, camera.getScreenWidth(), camera.getScreenHeight());
        camera.createLookAt();
        drawPreprocessing(pointsToDraw, polygonsToDraw);
    }
}

package controllers;

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
import java.util.ArrayList;
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
                          ArrayList<PointDraw> pointsToDraw,
                          ArrayList<EdgeDraw> edgesToDraw,
                          ArrayList<PolygonDraw> polygonsToDraw) {

        for (Point p : repo.getPoints()) {
            pointsToDraw.add(new PointDraw(p));
        }

        for (Edge e : repo.getEdges()) {
            PointDraw begin = findPointDrawByName(e.getBegin().getNameID(), pointsToDraw);
            PointDraw end = findPointDrawByName(e.getEnd().getNameID(), pointsToDraw);
            edgesToDraw.add(new EdgeDraw(begin, end));
        }

        for (Polygon p : repo.getPolygons()) {
            ArrayList<EdgeDraw> edgesForNewPoly = new ArrayList<>();

            for (Edge e : p.getEdges()) {
                EdgeDraw newEdge = findEdgeDrawByPointNames(e, edgesToDraw);
                edgesForNewPoly.add(newEdge);
            }

            polygonsToDraw.add(new PolygonDraw(edgesForNewPoly, p.getColor()));
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

    private void drawPreprocessing(ArrayList<PointDraw> points,
                                   ArrayList<PolygonDraw> polygons) {

        for (PointDraw p : points) {
            drawerVisitor.transformPointToCameraCoordinates(p);

            drawerVisitor.findViewerVector(p);
        }

        for (PolygonDraw poly : polygons) {
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

        ArrayList<PointDraw> pointsToDraw = new ArrayList<>();
        ArrayList<EdgeDraw> edgesToDraw = new ArrayList<>();
        ArrayList<PolygonDraw> polygonsToDraw = new ArrayList<>();

        drawCopy(sceneRepository, pointsToDraw, edgesToDraw, polygonsToDraw);

        canvas.clearRect(0, 0, camera.getScreenWidth(), camera.getScreenHeight());
        camera.createLookAt();
        drawPreprocessing(pointsToDraw, polygonsToDraw);
    }
}

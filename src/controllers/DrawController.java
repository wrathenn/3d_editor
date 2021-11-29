package controllers;

import models.*;
import models.Point;
import models.Polygon;
import org.jetbrains.annotations.Nullable;
import repositories.DrawerZBuffer;
import repositories.SceneRepository;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

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
            if (edge.begin.nameID.equals(e.begin.getNameID()) && edge.end.nameID.equals(e.end.getNameID())) {
                return e;
            }
            if (edge.begin.nameID.equals(e.end.getNameID()) && edge.end.nameID.equals(e.begin.getNameID())) {
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
            PointDraw begin = findPointDrawByName(e.begin.nameID, pointsToDraw);
            PointDraw end = findPointDrawByName(e.end.nameID, pointsToDraw);
            edgesToDraw.add(new EdgeDraw(begin, end));
        }

        for (Polygon p : repo.getPolygons()) {
            ArrayList<EdgeDraw> edgesForNewPoly = new ArrayList<>();

            for (Edge e : p.getEdges()) {
                EdgeDraw newEdge = findEdgeDrawByPointNames(e, edgesToDraw);
                edgesForNewPoly.add(newEdge);
            }

            polygonsToDraw.add(new PolygonDraw(edgesForNewPoly));
        }
    }

    private void findPointsColorInPolygon(PolygonDraw p) {
        for (EdgeDraw e : p.getEdges()) {
            if (e.begin.intensity == null) {
                drawerVisitor.findPointColor(e.begin, p.normalLine);
            }
            if (e.end.intensity == null) {
                drawerVisitor.findPointColor(e.end, p.normalLine);
            }
        }
    }

    private void clearPointsColorInPolygon(PolygonDraw p) {
        for (EdgeDraw e : p.getEdges()) {
            if (e.begin.intensity != null) {
                e.begin.intensity = null;
            }
            if (e.end.intensity != null) {
                e.end.intensity = null;
            }
        }
    }

    private void drawPreprocessing(ArrayList<PointDraw> points,
                                   ArrayList<PolygonDraw> polygons) {

        for (PolygonDraw poly : polygons) {
            poly.findNormalLine();
        }

        for (PointDraw p : points) {
            drawerVisitor.transformPointCamera(p);
            drawerVisitor.findViewerVector(p);
        }

        for (PolygonDraw poly : polygons) {
            poly.findNormalLine();
            // Найти цвет каждой точки, не находить, если уже найдет
            findPointsColorInPolygon(poly);

            // Отрисовка полигона
            drawerVisitor.drawPolygon(poly);

            // Очистить цвет каждой точки
            clearPointsColorInPolygon(poly);
        }
    }


    public void draw(Graphics canvas, Camera camera, SceneRepository sceneRepository) {
        canvas.clearRect(0, 0, camera.getScreenWidth(), camera.getScreenHeight());
        canvas.drawRect(0, 0, camera.getScreenWidth() - 1, camera.getScreenHeight() - 1);

        drawerVisitor.setSize(camera.getScreenWidth(), camera.getScreenHeight());
        drawerVisitor.clearBuffer();

        drawerVisitor.setCanvas(canvas);
        drawerVisitor.setCamera(camera);

        ArrayList<PointDraw> pointsToDraw = new ArrayList<>();
        ArrayList<EdgeDraw> edgesToDraw = new ArrayList<>();
        ArrayList<PolygonDraw> polygonsToDraw = new ArrayList<>();

        drawCopy(sceneRepository, pointsToDraw, edgesToDraw, polygonsToDraw);
        drawPreprocessing(pointsToDraw, polygonsToDraw);
    }
}

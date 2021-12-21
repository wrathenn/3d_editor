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
import java.util.*;
import java.util.List;

public class DrawController {
    private DrawerZBuffer drawer;
    private UUID selectedPolyId;

    // ----- Конструкторы ----- //

    public DrawController() {
    }

    public DrawController(DrawerZBuffer drawer) {
        this.drawer = drawer;
    }

    // ----- Сеттеры и Геттеры ----- //

    public void setDrawer(DrawerZBuffer drawer) {
        this.drawer = drawer;
    }

    public void setCanvas(Graphics canvas) {
        this.drawer.setCanvas(canvas);
    }

    public void setCamera(Camera camera) {
        this.drawer.setCamera(camera);
    }

    public void setSelectedPolyId(UUID selectedPolyId) {
        this.selectedPolyId = selectedPolyId;
    }

    public UUID getSelectedPolyId() {
        return selectedPolyId;
    }

    // ----- Нормальные методы ----- //

    public void draw(Graphics canvas, Camera camera, SceneRepository sceneRepository) {
        drawer.setSize(camera.getScreenWidth(), camera.getScreenHeight());
        drawer.clearBuffer();

        drawer.setCanvas(canvas);
        drawer.setCamera(camera);

        HashMap<String, PointDraw> pointsToDraw = new HashMap<>();
        HashMap<UUID, PolygonDraw> polygonsToDraw = new HashMap<>();

        drawCopy(sceneRepository, pointsToDraw, polygonsToDraw);

        canvas.clearRect(0, 0, camera.getScreenWidth(), camera.getScreenHeight());
        camera.createLookAt();
        drawPreprocessing(pointsToDraw, polygonsToDraw);
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

    private void drawPreprocessing(HashMap<String, PointDraw> points,
                                   HashMap<UUID, PolygonDraw> polygons) {

        for (PointDraw p : points.values()) {
            drawer.transformPointToCameraCoordinates(p);
            drawer.findViewerVector(p);
            drawer.drawPointName(p);
        }

        for (Map.Entry<UUID, PolygonDraw> polyEntry : polygons.entrySet()) {
            PolygonDraw poly = polyEntry.getValue();
            poly.findNormalLine();
            if (polyEntry.getKey() == selectedPolyId) {
                poly.setColor(new Color(72,72,72,128));
            }
            // Найти цвет каждой точки, не находить, если уже найден
            findPointsColorInPolygon(poly);

            // Отрисовка полигона
            drawer.drawPolygon(poly, polyEntry.getKey());

            // Очистить цвет каждой точки
            clearPointsColorInPolygon(poly);
        }
    }

    private void findPointsColorInPolygon(PolygonDraw p) {
        for (EdgeDraw e : p.getEdges()) {
            if (e.getBegin().getIntensity() == null) {
                drawer.findPointColor(e.getBegin(), p.getNormalLine());
            }
            if (e.getEnd().getIntensity() == null) {
                drawer.findPointColor(e.getEnd(), p.getNormalLine());
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

    public UUID getPolyId(int x, int y) {
        return drawer.getPolyId(x, y);
    }
}

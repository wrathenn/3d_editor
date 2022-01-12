package controllers;

import io.GlobalLogger;
import models.draw.Camera;
import models.draw.EdgeDraw;
import models.draw.PointDraw;
import models.draw.PolygonDraw;
import models.scene.Edge;
import models.scene.Point;
import models.scene.Polygon;
import models.scene.Vector;
import repositories.DrawerZBuffer;
import repositories.SceneRepository;

import java.awt.*;
import java.util.*;
import java.util.logging.Level;

public class DrawController {
    private DrawerZBuffer drawer;

    // ----- Конструкторы ----- //

    public DrawController() {
    }

    public DrawController(DrawerZBuffer drawer) {
        this.drawer = drawer;
    }

    // ----- Сеттеры и Геттеры ----- //

    public void setCanvas(Graphics canvas) {
        this.drawer.setCanvas(canvas);
    }

    public void setCamera(Camera camera) {
        this.drawer.setCamera(camera);
    }

    // -- Выборы на холсте -- //
    private UUID selectedPolyId;
    private final ArrayList<String> selectedPointNames = new ArrayList<>();

    public void setSelectedPolyId(UUID selectedPolyId) {
        if (selectedPointNames.size() == 0) {
            this.selectedPolyId = selectedPolyId;
        }
    }

    public UUID getSelectedPolyId() {
        return selectedPolyId;
    }

    public void addSelectedPointName(String id) {
        if (selectedPolyId != null) {
            return;
        }

        if (selectedPointNames.contains(id)) {
            return;
        }

        selectedPointNames.add(id);
    }

    public ArrayList<String> getSelectedPointNames() {
        return selectedPointNames;
    }

    public void clearSelected() {
        selectedPointNames.clear();
        selectedPolyId = null;
    }

    // ----- Нормальные методы ----- //

    public void draw(Graphics canvas, Camera camera, SceneRepository sceneRepository, int pointNameMode) {
        if (canvas == null) {
            return;
        }

//        long cnt = 0;
//        for (int i = 0; i < 1000; i++) {


        long tBegin = System.currentTimeMillis();
//        GlobalLogger.getLogger().log(Level.INFO, "draw_begin_time (ms) = " + tBegin);

        {
            drawer.setSize(camera.getScreenWidth(), camera.getScreenHeight());
            drawer.clearBuffer();

            drawer.setCanvas(canvas);
            drawer.setCamera(camera);

            HashMap<String, PointDraw> pointsToDraw = new HashMap<>();
            HashMap<UUID, PolygonDraw> polygonsToDraw = new HashMap<>();

            drawCopy(sceneRepository, pointsToDraw, polygonsToDraw);

            canvas.setColor(Color.white);
            canvas.fillRect(0, 0, camera.getScreenWidth(), camera.getScreenHeight());
//        canvas.clearRect(0, 0, camera.getScreenWidth(), camera.getScreenHeight());
            camera.createLookAt();
            drawConveyor(pointsToDraw, polygonsToDraw, pointNameMode, sceneRepository.getPoints());
        }

        long tEnd = System.currentTimeMillis();
        GlobalLogger.getLogger().log(Level.INFO, "draw_end_time (ms) = " + tEnd);
        GlobalLogger.getLogger().log(Level.INFO, "draw_time (ms) = " + (tEnd - tBegin));
//            cnt += (tEnd - tBegin);
//        }
//        GlobalLogger.getLogger().log(Level.INFO, "avg time (ms) = " + (double) cnt / 1000);
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

    private void drawConveyor(HashMap<String, PointDraw> points,
                              HashMap<UUID, PolygonDraw> polygons,
                              int pointNameMode, HashMap<String, Point> oldPoints) {
        HashMap<String, PointDraw> pointsOutOfView = new HashMap<>();
        for (PointDraw p : points.values()) {
            {
                Vector po = p.getPoint().minus(drawer.camera.position);
                Vector look = drawer.camera.forward;
                if (po.getAngle(look) < 0) {
                    pointsOutOfView.put(p.getNameID(), new PointDraw(p));
                }
            }

            drawer.transformPointOnCamera(p);
        }

        for (Map.Entry<UUID, PolygonDraw> polyEntry : polygons.entrySet()) {
            PolygonDraw poly = polyEntry.getValue();
            if (polyEntry.getKey() == selectedPolyId) {
                poly.setColor(Color.blue);
            }

            poly.findNormalLine();

            // Найти цвет каждой точки, не находить, если уже найден
            findPointsColorInPolygon(poly);

            // Отрисовка полигона
            drawer.drawPolygon(poly, polyEntry.getKey(), pointsOutOfView);

            // Очистить цвет каждой точки
            clearPointsColorInPolygon(poly);
        }

        for (PointDraw p : points.values()) {
            if (pointsOutOfView.containsKey(p.getNameID())) {
                continue;
            }
            boolean isSelected = selectedPointNames.contains(p.getNameID());
            drawer.drawPoint(p, oldPoints.get(p.getNameID()), isSelected, pointNameMode);
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
            e.getBegin().setIntensity(null);
            e.getEnd().setIntensity(null);
        }
    }

    public String getPointName(int x, int y) {
        return drawer.getPointName(x, y);
    }

    public UUID getPolyId(int x, int y) {
        return drawer.getPolyId(x, y);
    }
}

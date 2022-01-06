package repositories;

import exceptions.ExistedNameException;
import io.GlobalLogger;
import libs.HashMapUnique;
import models.draw.Camera;
import models.scene.Point;
import models.scene.Edge;
import models.scene.Polygon;

import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;

public class SceneRepository {
    private HashMapUnique<String, Point> points;
    private HashMapUnique<UUID, Polygon> polygons;

    private Camera camera;

    // ----- Конструкторы ----- //

    public SceneRepository() {
        points = new HashMapUnique<>();
        polygons = new HashMapUnique<>();

        camera = new Camera();
    }

    // ----- Геттеры и Сеттеры ----- //

    public HashMapUnique<String, Point> getPoints() {
        return points;
    }

    public HashMapUnique<UUID, Polygon> getPolygons() {
        return polygons;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    // ----- Методы ----- //

    public void add(Point p) throws ExistedNameException {
        GlobalLogger.getLogger().log(Level.INFO, "adding point " + p);
        points.put(p.getNameID(), p);
    }

    public void add(Polygon p) {
        GlobalLogger.getLogger().log(Level.INFO, "adding polygon " + p);
        polygons.put(UUID.randomUUID(), p);
    }

    public Point getPoint(String name) {
        return points.get(name);
    }

    public Polygon getPolygon(UUID id) {
        return polygons.get(id);
    }

    public Camera getCamera() {
        return camera;
    }

    public void delete(Point p) {
        GlobalLogger.getLogger().log(Level.INFO, "delete point " + p.toString());
    }

    public void delete(Edge e) {
        GlobalLogger.getLogger().log(Level.INFO, "delete edge " + e.toString());
    }

    public void delete(Polygon p) {
        GlobalLogger.getLogger().log(Level.INFO, "delete polygon " + p.toString());
    }

    public void movePoints(ArrayList<String> nameIds, double x, double y, double z) {
        for (String nameId : nameIds) {
            Point p = points.get(nameId);
            if (p == null) {
                continue;
            }

            p.setX(p.getX() + x);
            p.setY(p.getY() + y);
            p.setZ(p.getZ() + z);
        }
    }

    public void movePoly(UUID polyId, double x, double y, double z) {
        Polygon poly = polygons.get(polyId);
        if (poly == null) {
            return;
        }

        for (Point p : poly.getPoints()) {
            p.setX(p.getX() + x);
            p.setY(p.getY() + y);
            p.setZ(p.getZ() + z);
        }
    }
}

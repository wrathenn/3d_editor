package repositories;

import exceptions.ExistedNameException;
import exceptions.NotExistedNameException;
import libs.HashMapUnique;
import models.draw.Camera;
import models.scene.Point;
import models.scene.Edge;
import models.scene.Polygon;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

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
        if (points.containsKey(p.getNameID())) {
            throw new ExistedNameException(String.format(
                    "Ошибка. Уже существует Point с именем %s.", p.getNameID()
            ));
        }
        System.out.printf("log - добавление точки %s.%n", p);
        points.put(p.getNameID(), p);
    }

    public void add(Polygon p) {
        System.out.printf("log - добавление полигона %s.%n", p);
        polygons.put(UUID.randomUUID(), p);
    }

    public Point getPoint(String name) {
        return points.get(name);
    }

    public Camera getCamera() {
        return camera;
    }

    public void delete(Point p) {
        System.out.println("Удаление точки " + p.toString());
    }

    public void delete(Edge e) {
        System.out.println("Удаление ребра " + e.toString());
    }

    public void delete(Polygon p) {
        System.out.println("Удаление полигона " + p.toString());
    }
}

package repositories;

import exceptions.ExistedNameException;
import exceptions.NotExistedNameException;
import models.Camera;
import models.Point;
import models.Edge;
import models.Polygon;

import java.util.ArrayList;
import java.util.Objects;

public class SceneRepository {
    private ArrayList<Point> points;
    private ArrayList<Edge> edges;
    private ArrayList<Polygon> polygons;

    private ArrayList<Camera> cameras;

    // ----- Конструкторы ----- //

    public SceneRepository() {
        points = new ArrayList<>();
        edges = new ArrayList<>();
        polygons = new ArrayList<>();

        cameras = new ArrayList<>();
    }

    // ----- Геттеры и Сеттеры ----- //

    public ArrayList<Point> getPoints() {
        return points;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }

    // ----- Методы ----- //

    public void add(Point p) throws ExistedNameException {
        try {
            findPoint(p.getNameID());
            throw new ExistedNameException("Уже существует точка с именем " + p.getNameID());
        } catch (NotExistedNameException e) {
            System.out.println("Добавление точки " + p.getNameID());
            points.add(p);
        }
    }

    public void add(Edge e) {
        System.out.println("Добавление ребра " + e.toString());
        edges.add(e);
    }

    public void add(Polygon p) {
        System.out.println("Добавление полигона " + p.toString());
        polygons.add(p);
    }

    public void add(Camera c) {
        System.out.println("Добавление камеры " + c.toString());
        cameras.add(c);
    }

    public ArrayList<Camera> getCameras() {
        return cameras;
    }

    public Camera getCamera(int index) {
        return cameras.get(index);
    }

    public void addCamera(Camera camera) {
        this.cameras.add(camera);
    }

    public Point findPoint(String name) throws NotExistedNameException {
        for (Point p : points) {
            if (Objects.equals(p.getNameID(), name)) {
                return p;
            }
        }
        throw new NotExistedNameException("Не существует точки с именем" + name);
    }

    public ArrayList<Polygon> find(Point p) {
        ArrayList<Polygon> found = new ArrayList<>();

        for (Polygon current : polygons) {
            for (Edge e : current.getEdges()) {
                if (Objects.equals(p, e.getBegin()) || Objects.equals(p, e.getEnd())) {
                    found.add(current);
                    break;
                }
            }
        }

        return found;
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

    public void delete(Camera c) {
        System.out.println("Удаление камеры " + c.toString());
    }
}

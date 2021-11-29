package repositories;

import exceptions.ExistedNameException;
import exceptions.NotExistedNameException;
import models.draw.Camera;
import models.scene.Point;
import models.scene.Edge;
import models.scene.Polygon;

import java.awt.*;
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
        {
//            // Кубик
//            points.add(new Point("M1", -50, 50, 0));
//            points.add(new Point("M2", 50, 50, 0));
//            points.add(new Point("M3", 50, -50, 0));
//            points.add(new Point("M4", -50, -50, 0));
//            points.add(new Point("M5", -50, 50, 100));
//            points.add(new Point("M6", 50, 50, 100));
//            points.add(new Point("M7", 50, -50, 100));
//            points.add(new Point("M8", -50, -50, 100));
            // треугольник
            points.add(new Point("M1", 0, 250, 0));
            points.add(new Point("M2", -250, 60, 25));
            points.add(new Point("M3", 200, 0, 75));
        }
        edges = new ArrayList<>();
        {
//            // Кубик
//            edges.add(new Edge(points.get(0), points.get(1)));
//            edges.add(new Edge(points.get(1), points.get(2)));
//            edges.add(new Edge(points.get(2), points.get(3)));
//            edges.add(new Edge(points.get(3), points.get(0)));
//
//            edges.add(new Edge(points.get(0), points.get(4)));
//            edges.add(new Edge(points.get(1), points.get(5)));
//            edges.add(new Edge(points.get(2), points.get(6)));
//            edges.add(new Edge(points.get(3), points.get(7)));
//
//
//            edges.add(new Edge(points.get(4), points.get(5)));
//            edges.add(new Edge(points.get(5), points.get(6)));
//            edges.add(new Edge(points.get(6), points.get(7)));
//            edges.add(new Edge(points.get(7), points.get(4)));

            edges.add(new Edge(points.get(0), points.get(1)));
            edges.add(new Edge(points.get(0), points.get(2)));
            edges.add(new Edge(points.get(1), points.get(2)));
        }
        polygons = new ArrayList<>();
        {
            polygons.add(new Polygon(edges, Color.blue)); // TODO: костыль убрать
        }

        cameras = new ArrayList<>();
        cameras.add(new Camera());
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

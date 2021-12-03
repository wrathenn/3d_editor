package repositories;

import exceptions.ExistedNameException;
import exceptions.NotExistedNameException;
import models.draw.Camera;
import models.scene.Point;
import models.scene.Edge;
import models.scene.Polygon;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class SceneRepository {
    private ArrayList<Point> points;
    private ArrayList<Edge> edges;
    private ArrayList<Polygon> polygons;

    private Camera camera;

    // ----- Конструкторы ----- //

    public void addCube() {
        ArrayList<Point> ps = new ArrayList<>(Arrays.asList(
                new Point("A1", -100, 100, 120),
                new Point("A2", 100, +100, 120),
                new Point("A3", +100, -100, 120),
                new Point("A4", -100, -100, 120),
                new Point("B1", -100, 100, 220),
                new Point("B2", 100, +100, 220),
                new Point("B3", +100, -100, 220),
                new Point("B4", -100, -100, 220)
        ));

        ArrayList<Edge> es = new ArrayList<>(Arrays.asList(
                new Edge(ps.get(0), ps.get(1)),
                new Edge(ps.get(1), ps.get(2)),
                new Edge(ps.get(2), ps.get(3)),
                new Edge(ps.get(3), ps.get(0)),

                new Edge(ps.get(4), ps.get(5)),
                new Edge(ps.get(5), ps.get(6)),
                new Edge(ps.get(6), ps.get(7)),
                new Edge(ps.get(7), ps.get(4)),

                new Edge(ps.get(0), ps.get(4)),
                new Edge(ps.get(1), ps.get(5)),
                new Edge(ps.get(2), ps.get(6)),
                new Edge(ps.get(3), ps.get(7))
        ));

        ArrayList<Edge> p1 = new ArrayList<>(Arrays.asList(
                es.get(0),
                es.get(1),
                es.get(2),
                es.get(3)
        ));

        ArrayList<Edge> p2 = new ArrayList<>(Arrays.asList(
                es.get(4),
                es.get(5),
                es.get(6),
                es.get(7)
        ));

        ArrayList<Edge> p3 = new ArrayList<>(Arrays.asList(
                es.get(1),
                es.get(5),
                es.get(9),
                es.get(10)
        ));

        ArrayList<Edge> p4 = new ArrayList<>(Arrays.asList(
                es.get(3),
                es.get(7),
                es.get(8),
                es.get(11)
        ));

        ArrayList<Edge> p5 = new ArrayList<>(Arrays.asList(
                es.get(0),
                es.get(4),
                es.get(8),
                es.get(9)
        ));

        ArrayList<Edge> p6 = new ArrayList<>(Arrays.asList(
                es.get(2),
                es.get(6),
                es.get(10),
                es.get(11)
        ));


        ArrayList<Polygon> polys = new ArrayList<>(Arrays.asList(
                new Polygon(p1, Color.red),
                new Polygon(p2, Color.blue),
                new Polygon(p3, Color.magenta),
                new Polygon(p4, Color.orange),
                new Polygon(p5, Color.green),
                new Polygon(p6, Color.cyan)
        ));

        points.addAll(ps);
        edges.addAll(es);
        polygons.addAll(polys);
    }

    private void testComplex() {
        ArrayList<Point> ps = new ArrayList<>(Arrays.asList(
                new Point("Z1", -100, 100, 120),
                new Point("Z2", 0, +5, 120),
                new Point("Z3", +100, 100, 120),
                new Point("Z4", +100, -100, 120),
                new Point("Z5", -100, -100, 120)
        ));

        ArrayList<Edge> es = new ArrayList<>(Arrays.asList(
                new Edge(ps.get(0), ps.get(1)),
                new Edge(ps.get(1), ps.get(2)),
                new Edge(ps.get(2), ps.get(3)),
                new Edge(ps.get(3), ps.get(4)),
                new Edge(ps.get(4), ps.get(0))
        ));

        Polygon p = new Polygon(es, Color.blue);

        points.addAll(ps);
        edges.addAll(es);
        polygons.add(p);
    }

    public SceneRepository() {
        points = new ArrayList<>();
        edges = new ArrayList<>();
        polygons = new ArrayList<>();


        addCube();
//        testComplex();
        camera = new Camera();
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

    public Camera getCamera() {
        return camera;
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

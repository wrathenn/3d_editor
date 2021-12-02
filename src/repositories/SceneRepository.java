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

    private ArrayList<Camera> cameras;

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
                new Polygon(p2, Color.red),
                new Polygon(p3, Color.red),
                new Polygon(p4, Color.red),
                new Polygon(p5, Color.red),
                new Polygon(p6, Color.red)
        ));

        points.addAll(ps);
        edges.addAll(es);
        polygons.addAll(polys);

    }

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
//            points.add(new Point("M1", 0, 250, 10));
//            points.add(new Point("M2", -250, 60, 25));
//            points.add(new Point("M3", 200, 0, 75));
//            points.add(new Point("M4", 0, -80, 150));
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

//            edges.add(new Edge(points.get(0), points.get(1))); // 0
//            edges.add(new Edge(points.get(0), points.get(2))); // 1
//
//            edges.add(new Edge(points.get(1), points.get(2))); // 2
//
//            edges.add(new Edge(points.get(1), points.get(3))); // 3
//            edges.add(new Edge(points.get(2), points.get(3))); // 4
        }
        polygons = new ArrayList<>();
        {
//            polygons.add(new Polygon(
//                    new ArrayList<Edge>(Arrays.asList(edges.get(0), edges.get(1), edges.get(2))), Color.blue)
//            );
//            polygons.add(new Polygon(
//                    new ArrayList<Edge>(Arrays.asList(edges.get(2), edges.get(3), edges.get(4))), Color.red)
//            );
        }

        addCube();

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

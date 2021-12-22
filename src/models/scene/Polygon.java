package models.scene;

import libs.SharedFunctions;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class Polygon {
    private ArrayList<Edge> edges;
    private Color color;

    // -----  Конструкторы ----- //

    public Polygon(ArrayList<Edge> edges, Color c) {
        this.edges = edges;
        this.color = c;
    }

    public Polygon(Point[] points, Color c) throws IllegalArgumentException {
        if (points.length < 3) {
            throw new IllegalArgumentException("Incorrect dots amount to make polygon - " + points.length + " (need 3 or more)");
        }

        edges = new ArrayList<Edge>();
        for (int i = 1; i < points.length; i++) {
            edges.add(new Edge(points[i - 1], points[i]));
        }
        edges.add(new Edge(points[points.length - 1], points[0]));

        color = c;
    }

    Polygon(Edge[] edges, Color c) {
        if (edges.length < 3) {
            throw new IllegalArgumentException("Incorrect edges amount to make polygon - " + edges.length + " (need 3 or more)");
        }

        this.edges = new ArrayList<Edge>();
        this.edges.addAll(Arrays.asList(edges));

        color = c;
    }

    // ----- Геттеры и Сеттеры ----- //

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public Edge getEdge(int index) {
        return edges.get(index);
    }

    public void setEdge(int i, Edge edge) {
        edges.set(i, edge);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    // ----- Остальные методы ----- //

    public void removePoint(Point p) {
        int onEndIndex = -1, onBeginIndex = -1;
        for (int i = 0; i < edges.size(); i++) {
            Edge e = edges.get(i);
            if (Objects.equals(e.getBegin(), p)) {
                onBeginIndex = i;
            } else if (Objects.equals(e.getEnd(), p)) {
                onEndIndex = i;
            }
        }

        Edge newEdge = new Edge(edges.get(onEndIndex).getBegin(), edges.get(onBeginIndex).getEnd());
        int max = Math.max(onEndIndex, onBeginIndex);
        int min = Math.min(onEndIndex, onBeginIndex);
        edges.remove(max);
        edges.add(max, newEdge);
        edges.remove(min);
    }

    @Override
    public String toString() {
        String es = edges.stream().map(Edge::toString).collect(Collectors.joining(","));
        return String.format("Polygon{edges=[%s], color=%s}", es, color);
    }

    public Vector findNormalLine() {
        Vector normalLine;
        Point p1, p2, p3;
        for (int i = 0; i < edges.size(); i++) {
            p1 = edges.get(i).getBegin();
            p2 = edges.get(i).getEnd();
            p3 = edges.get(i == edges.size() - 1 ? 0 : i + 1).getEnd();

            double vx1 = p1.getX() - p2.getX();
            double vx2 = p2.getX() - p3.getX();
            double vy1 = p1.getY() - p2.getY();
            double vy2 = p2.getY() - p3.getY();
            double vz1 = p1.getZ() - p2.getZ();
            double vz2 = p2.getZ() - p3.getZ();

            double nx = vy1 * vz2 - vz1 * vy2;
            double ny = vz1 * vx2 - vx1 * vz2;
            double nz = vx1 * vy2 - vy1 * vx2;

            // Точки на одной прямой
            if (SharedFunctions.doubleCompare(nx, 0) == 0
                    && SharedFunctions.doubleCompare(ny, 0) == 0
                    && SharedFunctions.doubleCompare(nz, 0) == 0) {
                continue;
            }

            double len = Math.sqrt(nx * nx + ny * ny + nz * nz);
            nx /= len;
            ny /= len;
            nz /= len;

            normalLine = new Vector(nx, ny, nz);
            return normalLine;
        }

        return null;
    }

    public Point[] getPoints() {
        int size = edges.size();
        Point[] points = new Point[size];

        for (int i = 0; i < size; i++) {
            points[i] = edges.get(i).getBegin();
        }

        return points;
    }
}

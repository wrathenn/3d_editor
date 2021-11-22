package models;

import repositories.Visitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Polygon implements Shape {
    private ArrayList<Edge> edges;

    public Polygon() {
        this.edges = new ArrayList<Edge>();
    }

    public Polygon(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public Polygon(Point[] points) throws IllegalArgumentException {
        if (points.length < 3) {
            throw new IllegalArgumentException("Incorrect dots amount to make polygon - " + points.length + " (need 3 or more)");
        }

        edges = new ArrayList<Edge>();
        for (int i = 1; i < points.length; i++) {
            edges.add(new Edge(points[i - 1], points[i]));
        }
        edges.add(new Edge(points[0], points[points.length - 1]));
    }

    Polygon(Edge[] edges) {
        if (edges.length < 3) {
            throw new IllegalArgumentException("Incorrect edges amount to make polygon - " + edges.length + " (need 3 or more)");
        }

        this.edges = new ArrayList<Edge>();
        this.edges.addAll(Arrays.asList(edges));
    }

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

    public void removePoint(Point p) {
        int onEndIndex = -1, onBeginIndex = -1;
        for (int i = 0; i < edges.size(); i++) {
            Edge e = edges.get(i);
            if (Objects.equals(e.getBegin(), p)) {
                onBeginIndex = i;
            }
            else if (Objects.equals(e.getEnd(), p)) {
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

    public Polygon deepCopy() {
        ArrayList<Edge> newEdges = new ArrayList<>();
        for (Edge e: edges) {
            newEdges.add(e.deepCopy());
        }
        return new Polygon(newEdges);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

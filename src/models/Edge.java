package models;

import repositories.Visitor;

import java.util.Objects;

public class Edge implements Shape {
    private Point begin;
    private Point end;

    Edge() {
    }

    public Edge(Point begin, Point end) {
        this.begin = begin;
        this.end = end;
    }

    Point[] getDots() {
        return new Point[]{begin, end};
    }

    void setDots(Point begin, Point end) {
        this.begin = begin;
        this.end = end;
    }

    void setBegin(Point begin) {
        this.begin = begin;
    }

    void setEnd(Point end) {
        this.end = end;
    }

    public Point getBegin() {
        return begin;
    }

    public Point getEnd() {
        return end;
    }

    public boolean has(Point p) {
        if (Objects.equals(p, begin) || Objects.equals(p, end)) {
            return true;
        }
        return false;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

package models;

import repositories.Visitor;

import java.util.Objects;

public class Edge implements Shape {
    public Point begin;
    public Point end;

    public Edge(Point begin, Point end) {
        this.begin = begin;
        this.end = end;
    }

    public Point getBegin() {
        return begin;
    }

    public Point getEnd() {
        return end;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Edge\n\tpBegin:\t" + begin.toString() + "\n\tpEnd:\t" + end.toString();
    }
}

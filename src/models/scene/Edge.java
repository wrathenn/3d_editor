package models.scene;

public class Edge {
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
    public String toString() {
        return "Edge\n\tpBegin:\t" + begin.toString() + "\n\tpEnd:\t" + end.toString();
    }
}

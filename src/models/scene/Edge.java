package models.scene;

public class Edge {
    private Point begin;
    private Point end;

    // ----- Конструкторы ----- //

    public Edge(Point begin, Point end) {
        this.begin = begin;
        this.end = end;
    }

    // ----- Геттеры и Сеттеры ----- //

    public Point getBegin() {
        return begin;
    }

    public void setBegin(Point begin) {
        this.begin = begin;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    // ----- Object ----- //

    @Override
    public String toString() {
        return "Edge\n\tpBegin:\t" + begin.toString() + "\n\tpEnd:\t" + end.toString();
    }
}

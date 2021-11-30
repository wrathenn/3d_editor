package models.draw;

public class EdgeDraw {
    private PointDraw begin;
    private PointDraw end;

    // ----- Конструкторы ----- //

    public EdgeDraw(PointDraw begin, PointDraw end) {
        this.begin = begin;
        this.end = end;
    }

    // ----- Геттеры и Сеттеры ----- //

    public PointDraw getBegin() {
        return begin;
    }

    public void setBegin(PointDraw begin) {
        this.begin = begin;
    }

    public PointDraw getEnd() {
        return end;
    }

    public void setEnd(PointDraw end) {
        this.end = end;
    }

    // ----- Object ----- //

    @Override
    public String toString() {
        return "EdgeDraw{" +
                "begin=" + begin +
                ", end=" + end +
                '}';
    }
}

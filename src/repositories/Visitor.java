package repositories;

import models.Edge;
import models.Point;
import models.Polygon;

public interface Visitor {
    public void visit(Point p);

    public void visit(Edge e);

    public void visit(Polygon p);
}

package scene.actions;

import models.*;
import models.Point;
import models.Polygon;
import shared.DrawVisitor;

import java.awt.*;

public class DrawerVisitor extends DrawVisitor {

    private float zBuffer[][];

    public DrawerVisitor() {
        super();
        zBuffer = new float[1280][800];
    }

    public DrawerVisitor(Graphics canvas) {
        super(canvas);
        zBuffer = new float[1280][800];
    }

    public void clearBuffer() {
        zBuffer = new float[1280][800];
    }

    @Override
    public void visit(Point p) {
        canvas.drawOval((int) p.getX(), (int) p.getY(), 2, 2);
        canvas.drawString("P", (int) p.getX() - 2, (int) p.getY() - 2);

        System.out.println("Drawing point");
    }

    @Override
    public void visit(Edge e) {
        System.out.println("Drawing edge");
    }

    @Override
    public void visit(Polygon p) {
        for (Edge e : p.getEdges()) {
            canvas.drawLine((int) e.getBegin().getX(), (int) e.getBegin().getY(), (int) e.getEnd().getX(), (int) e.getEnd().getY());
        }

        System.out.println("Drawing Polygon");
    }
}

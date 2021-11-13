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

    private float[] interpolate(float i0, float d0, float i1, float d1) {
        if (i0 == i1) {
            return new float[]{d0};
        }

        int size = (int) (i1 - i0) + 1;
        float[] values = new float[size];
        float a = (d1 - d0) / (i1 - i0);
        float d = d0;

        for (int i = 0; i < size; i++) {
            values[i] = d;
            d += a;
        }

        System.out.println("len---" + values.length);

        return values;
    }

    private void drawLine(Point p1, Point p2) {
        canvas.setColor(Color.ORANGE);
        canvas.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
//        /*
        canvas.setColor(Color.GREEN);
        if (Math.abs(p2.x - p1.x) > Math.abs(p2.y - p1.y)) {
            // Прямая ближе к горизонтальной
            if (p1.x > p2.x) {
                Point temp = p1;
                p1 = p2;
                p2 = temp;
            }

            float[] values = interpolate(p1.x, p1.y, p2.x, p2.y);
            int i = 0;
            for (float x = p1.x; x <= p2.x; x++, i++) {
                int xt = (int) x;
                int y = (int) values[i];
                canvas.drawLine(xt, y, xt, y);
            }
        } else {
            if (p1.y > p2.y) {
                Point temp = p1;
                p1 = p2;
                p2 = temp;
            }

            float[] values = interpolate(p1.y, p1.x, p2.y, p2.x);
            int i = 0;
            for (float y = p1.y; y <= p2.y; y++, i++) {
                int yt = (int) y;
                int x = (int) values[i];
                canvas.drawLine(x, yt, x, yt);
            }
        }
//         */
    }

    @Override
    public void visit(Point p) {
        canvas.drawOval((int) p.x, (int) p.y, 2, 2);
        canvas.drawString(p.getNameID(), (int) p.x - 2, (int) p.y - 2);

        System.out.println("Drawing point");
    }

    @Override
    public void visit(Edge e) {
        drawLine(e.getBegin(), e.getEnd());
//        canvas.drawLine((int) e.getBegin().x,
//                (int) e.getBegin().y,
//                (int) e.getEnd().x,
//                (int) e.getEnd().y);

        System.out.println("Drawing edge");
    }

    @Override
    public void visit(Polygon p) {
        for (Edge e : p.getEdges()) {
            canvas.drawLine((int) e.getBegin().x,
                    (int) e.getBegin().y,
                    (int) e.getEnd().x,
                    (int) e.getEnd().y);
        }

        System.out.println("Drawing Polygon");
    }
}

package models;

import libs.SharedFunctions;

import java.util.ArrayList;

public class PolygonDraw {
    public Point normalLine;

    public ArrayList<EdgeDraw> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<EdgeDraw> edges) {
        this.edges = edges;
    }

    private ArrayList<EdgeDraw> edges;

    public PolygonDraw(ArrayList<EdgeDraw> edges) {
        this.edges = edges;
    }

    public void findNormalLine() {
        Point p1, p2, p3;
        for (int i = 0; i < edges.size(); i++) {
            p1 = edges.get(i).begin.point;
            p2 = edges.get(i).end.point;
            p3 = edges.get(i == edges.size() - 1 ? 0 : i + 1).end.point;

            double vx1 = p1.x - p2.x;
            double vx2 = p2.x - p3.x;
            double vy1 = p1.y - p2.y;
            double vy2 = p2.y - p3.y;
            double vz1 = p1.z - p2.z;
            double vz2 = p2.z - p3.z;

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

            normalLine = new Point(nx, ny, nz);
            return;
        }
    }
}

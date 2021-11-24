package controllers;

import exceptions.ExistedNameException;
import exceptions.NotExistedNameException;
import models.*;
import repositories.SceneRepository;

import java.util.ArrayList;

public class SceneController {

    private SceneRepository sceneRepository;

    // ----- Конструкторы ----- //

    public SceneController(SceneRepository sceneStore) {
        this.sceneRepository = sceneStore;
    }

    // ----- Сеттеры и Геттеры ----- //

    public void setSceneRepository(SceneRepository sceneRepository) {
        this.sceneRepository = sceneRepository;
    }

    public SceneRepository getSceneRepository() {
        return sceneRepository;
    }

    // ----- Методы ----- //

    public void add(Shape shape) throws ExistedNameException {
        if (shape instanceof Point) {
            sceneRepository.add((Point) shape);
        } else if (shape instanceof Edge) {
            sceneRepository.add((Edge) shape);
        } else if (shape instanceof Polygon) {
            sceneRepository.add((Polygon) shape);
        } else if (shape instanceof Camera) {
            sceneRepository.add((Camera) shape);
        }
    }

    public void delete(Shape shape) {
        if (shape instanceof Point) {
            sceneRepository.delete((Point) shape);
        } else if (shape instanceof Edge) {
            sceneRepository.delete((Edge) shape);
        } else if (shape instanceof Polygon) {
            sceneRepository.delete((Polygon) shape);
        } else if (shape instanceof Camera) {
            sceneRepository.delete((Camera) shape);
        }
    }

    public ArrayList<Shape> getShapesToDraw() {
        ArrayList<Shape> shapes = new ArrayList<>();

//        ArrayList<Point> points = sceneRepository.getPoints();
//        for (Point p : points) {
//            if (p.getNameID().charAt(0) == 't') {
//                shapes.add(p);
//            }
//        }
//
//        ArrayList<Edge> edges = sceneRepository.getEdges();
//        shapes.addAll(edges);

        ArrayList<Polygon> polygons = sceneRepository.getPolygons();
        for (Polygon p: polygons) {
            shapes.add(p.deepCopy());
        }

        return shapes;
    }

    public Point findPoint(String name) throws NotExistedNameException {
        return sceneRepository.findPoint(name);
    }

    public Camera getCamera(int index) {
        return sceneRepository.getCamera(index);
    }
}

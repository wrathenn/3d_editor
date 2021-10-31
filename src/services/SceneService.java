package services;

import exceptions.ExistedNameException;
import models.*;
import repositories.SceneRepository;

import java.util.ArrayList;

public class SceneService {

    private SceneRepository sceneRepository;

    // ----- Конструкторы ----- //

    public SceneService() {
    }

    public SceneService(SceneRepository sceneStore) {
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

        }
        else if (shape instanceof Edge) {
            sceneRepository.add((Edge) shape);
        }
        else if (shape instanceof Polygon) {
            sceneRepository.add((Polygon) shape);
        }
        else if (shape instanceof Camera) {
            sceneRepository.add((Camera) shape);
        }
    }

    public void delete(Shape shape) {
        if (shape instanceof Point) {
            sceneRepository.delete((Point) shape);
        }
        else if (shape instanceof Edge) {
            sceneRepository.delete((Edge) shape);
        }
        else if (shape instanceof Polygon) {
            sceneRepository.delete((Polygon) shape);
        }
        else if (shape instanceof Camera) {
            sceneRepository.delete((Camera) shape);
        }
    }

    public ArrayList<Shape> getShapesToDraw() {

        final ArrayList<Point> points = sceneRepository.getPoints();
        ArrayList<Shape> shapes = new ArrayList<>(points);

        ArrayList<Polygon> polygons = sceneRepository.getPolygons();
        shapes.addAll(polygons);

        return shapes;
    }
}

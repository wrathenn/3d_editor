package controllers;

import exceptions.ExistedNameException;
import exceptions.NotExistedNameException;
import models.draw.Camera;
import models.scene.Edge;
import models.scene.Point;
import models.scene.Polygon;
import repositories.SceneRepository;

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

    public void add(Point p) throws ExistedNameException {
        sceneRepository.add(p);
    }

    public void add(Edge e) {
        sceneRepository.add(e);
    }

    public void add(Polygon p) {
        sceneRepository.add(p);
    }

    public void delete(Point p) {
        sceneRepository.delete(p);
    }

    public void delete(Edge e) {
        sceneRepository.delete(e);
    }

    public void delete(Polygon p) {
        sceneRepository.delete(p);
    }

    public Point findPoint(String name) throws NotExistedNameException {
        return sceneRepository.findPoint(name);
    }

    public Camera getCamera(int index) {
        return sceneRepository.getCamera(index);
    }
}

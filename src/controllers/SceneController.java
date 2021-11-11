package controllers;

import exceptions.ExistedNameException;
import exceptions.NotExistedNameException;
import models.Camera;
import models.Point;
import models.Shape;
import repositories.SceneRepository;
import services.SceneService;

import java.util.ArrayList;

public class SceneController {

    private SceneService service;

    // ----- Конструкторы ----- //

    public SceneController() {
    }

    public SceneController(SceneService service) {
        this.service = service;
    }

    // ----- Сеттеры и Геттеры ----- //

    public void setService(SceneService service) {
        this.service = service;
    }

    public SceneRepository getStore() {
        return service.getSceneRepository();
    }

    // ----- Методы ----- //

    public void add(Shape s) throws ExistedNameException {
        service.add(s);
    }

    public void delete(Shape s) {
        service.delete(s);
    }

    public ArrayList<Shape> getShapesToDraw() {
        return service.getShapesToDraw();
    }

    public Point findPoint(String name) throws NotExistedNameException {
        return service.findPoint(name);
    }
}

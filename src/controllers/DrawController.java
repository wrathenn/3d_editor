package controllers;

import models.Camera;
import models.Shape;
import services.DrawService;

import java.awt.*;
import java.util.ArrayList;

public class DrawController {

    private DrawService service;

    // ----- Конструкторы ----- //

    public DrawController() {
    }

    public DrawController(DrawService service) {
        this.service = service;
    }

    // ----- Сеттеры и Геттеры ----- //

    public void setService(DrawService service) {
        this.service = service;
    }

    // ----- Нормальные методы ----- //

    public void draw(Graphics canvas, Camera camera, ArrayList<Shape> shapes) {
        service.setCanvas(canvas);
        service.setCamera(camera);

        for (Shape s : shapes) {
            service.draw(s);
        }
    }
}

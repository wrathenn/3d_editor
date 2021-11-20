package controllers;

import models.Camera;
import models.Shape;
import repositories.DrawVisitor;

import java.awt.*;
import java.util.ArrayList;

public class DrawController {

    private DrawVisitor drawVisitor;

    // ----- Конструкторы ----- //

    public DrawController() {
    }

    public DrawController(DrawVisitor drawVisitor) {
        this.drawVisitor = drawVisitor;
    }

    // ----- Сеттеры и Геттеры ----- //

    public void setDrawVisitor(DrawVisitor drawVisitor) {
        this.drawVisitor = drawVisitor;
    }

    public void setCanvas(Graphics canvas) {
        this.drawVisitor.setCanvas(canvas);
    }

    public void setCamera(Camera camera) {
        this.drawVisitor.setCamera(camera);
    }

    // ----- Нормальные методы ----- //

    public void draw(Shape shape) {
        shape.accept(drawVisitor);
    }
    // ----- Нормальные методы ----- //

    public void draw(Graphics canvas, Camera camera, ArrayList<Shape> shapes) {
        canvas.clearRect(0, 0, camera.getScreenWidth(), camera.getScreenHeight());
        canvas.drawRect(0, 0, camera.getScreenWidth() - 1, camera.getScreenHeight() - 1);

        drawVisitor.setCanvas(canvas);
        drawVisitor.setCamera(camera);

        for (Shape s : shapes) {
            s.accept(drawVisitor);
        }
    }
}

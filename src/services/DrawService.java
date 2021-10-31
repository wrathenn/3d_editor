package services;

import models.Camera;
import shared.DrawVisitor;


import java.awt.*;

import models.Shape;

public class DrawService {

    private DrawVisitor drawVisitor;

    // ----- Конструкторы ----- //

    public DrawService() {
    }

    public DrawService(DrawVisitor drawVisitor) {
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
}

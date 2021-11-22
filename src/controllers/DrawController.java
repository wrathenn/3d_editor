package controllers;

import models.Camera;
import models.Shape;
import repositories.DrawerVisitor;

import java.awt.*;
import java.util.ArrayList;

public class DrawController {

    private DrawerVisitor drawerVisitor;

    // ----- Конструкторы ----- //

    public DrawController() {
    }

    public DrawController(DrawerVisitor drawerVisitor) {
        this.drawerVisitor = drawerVisitor;
    }

    // ----- Сеттеры и Геттеры ----- //

    public void setDrawerVisitor(DrawerVisitor drawerVisitor) {
        this.drawerVisitor = drawerVisitor;
    }

    public void setCanvas(Graphics canvas) {
        this.drawerVisitor.setCanvas(canvas);
    }

    public void setCamera(Camera camera) {
        this.drawerVisitor.setCamera(camera);
    }

    // ----- Нормальные методы ----- //

    public void draw(Graphics canvas, Camera camera, ArrayList<Shape> shapes) {
        canvas.clearRect(0, 0, camera.getScreenWidth(), camera.getScreenHeight());
        canvas.drawRect(0, 0, camera.getScreenWidth() - 1, camera.getScreenHeight() - 1);

        drawerVisitor.setSize(camera.getScreenWidth(), camera.getScreenHeight());
        drawerVisitor.clearBuffer();

        drawerVisitor.setCanvas(canvas);
        drawerVisitor.setCamera(camera);

        for (Shape s : shapes) {
            s.accept(drawerVisitor);
        }
    }
}

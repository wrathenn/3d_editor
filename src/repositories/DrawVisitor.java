package repositories;


import models.Camera;

import java.awt.*;

public abstract class DrawVisitor implements Visitor {
    protected Graphics canvas;
    protected Camera camera;

    public DrawVisitor() {
    }

    public DrawVisitor(Graphics canvas) {
        this.canvas = canvas;
    }

    public void setCanvas(Graphics canvas) {
        this.canvas = canvas;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}

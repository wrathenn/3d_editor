package views;

import models.Camera;
import repositories.SceneRepository;
import models.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CanvasView extends JPanel implements MouseListener {

    public SceneRepository sceneRepository;

    private int oldX = 0;
    private int oldY = 0;

    public CanvasView() {
        super();
        addMouseListener(this);
    }

    public CanvasView(SceneRepository sceneStore) {
        super();
        addMouseListener(this);

        this.sceneRepository = sceneStore;
    }

    public void setSceneRepository(SceneRepository sceneRepository) {
        this.sceneRepository = sceneRepository;
    }

    @Override
    public void paintComponent(Graphics g) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        oldX = e.getX();
        oldY = e.getY();

        System.out.println("pressed - " + oldX + ", " + oldY);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int curX = e.getX();
        int curY = e.getY();

        Camera camera = sceneRepository.getCamera(0);
        camera.setX(camera.getX() + (curX - oldX));
        camera.setY(camera.getY() + (curY - oldY));
        System.out.println("released");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("entered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("exited - " + getSize().width + ", " + getSize().height);
    }
}

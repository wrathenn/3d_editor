package views;

import models.draw.Camera;
import repositories.SceneRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class CanvasView extends JPanel implements MouseListener, MouseMotionListener {

    public SceneRepository sceneRepository;

    public CanvasView() {
        super();
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public CanvasView(SceneRepository sceneStore) {
        super();
        addMouseListener(this);
        addMouseMotionListener(this);

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

    private int oldX = 0;
    private int oldY = 0;

    @Override
    public void mouseDragged(MouseEvent e) {
        int curX = e.getX();
        int curY = e.getY();

        Camera camera = sceneRepository.getCamera(0);
        camera.setX(camera.getX() + (curX - oldX));
        camera.setY(camera.getY() + (curY - oldY));

        System.out.println("dragging: from " + oldX + ", " + oldY + " to " + curX + ", " + curY);
        System.out.println("\tCamera: " + camera.getX() + ", " + camera.getY());
        oldX = curX;
        oldY = curY;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        System.out.println("asd2");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        oldX = e.getX();
        oldY = e.getY();
        System.out.println("pressed - " + e.getX() + ", " + e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("released - " + e.getX() + ", " + e.getY());
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

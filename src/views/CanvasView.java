package views;

import models.Camera;
import repositories.SceneRepository;
import models.Point;

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

        oldX = curX;
        oldY = curY;
        System.out.println("asd");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        System.out.println("asd2");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("pressed - " + oldX + ", " + oldY);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
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

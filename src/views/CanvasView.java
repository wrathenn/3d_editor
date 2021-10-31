package views;

import repositories.SceneRepository;
import models.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CanvasView extends JPanel implements MouseListener {

    public SceneRepository sceneRepository;

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
        g.setColor(Color.blue);
        g.drawOval(100, 100, 160, 175);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

//        sceneRepository.add(new Point(x, y, 0));
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
        System.out.println("exited");
    }
}

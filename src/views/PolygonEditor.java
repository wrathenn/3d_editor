package views;

import models.scene.Polygon;
import views.user_input.PolygonEditorMenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PolygonEditor extends JFrame {
    PolygonEditorMenuBar menu;

    Polygon poly;

    PolygonEditorCanvas inside;

    public PolygonEditor(Polygon p) {
        super();
        poly = p;

        initGUI();
    }

    private final int DEFAULT_WIDTH = 512;
    private final int DEFAULT_HEIGHT = 512;

    private void initGUI() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        menu = new PolygonEditorMenuBar();
        setJMenuBar(menu);

        inside = new PolygonEditorCanvas();
        inside.setSize(this.getSize());
        inside.setBackground(Color.green);
        setContentPane(inside);
    }

    private static class PolygonEditorCanvas extends JPanel implements MouseListener {
        public PolygonEditorCanvas() {
            super();
            addMouseListener(this);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("asd");
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}

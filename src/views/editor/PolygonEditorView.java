package views.editor;

import models.scene.Polygon;
import views.user_input.PolygonEditorMenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PolygonEditorView extends JFrame {
    PolygonEditorMenuBar menu;

    PolygonEditor polygonEditor;
    PolygonEditorCanvas canvas;

    public PolygonEditorView(Polygon p) {
        super();
        initGUI();
        polygonEditor = new PolygonEditor(p);

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        menu = new PolygonEditorMenuBar();
        setJMenuBar(menu);

        canvas = new PolygonEditorCanvas();
        canvas.setSize(this.getSize());
        setContentPane(canvas);
    }

    public void start() {
        setVisible(true);
        polygonEditor.countFinalPoly();
        polygonEditor.setCanvas(canvas);
        canvas.setDrawCallback((g) -> polygonEditor.drawPoly(g));
    }

    private final int DEFAULT_WIDTH = 512;
    private final int DEFAULT_HEIGHT = 512;

    private void initGUI() {

    }

    private interface drawCallback {
        void callback(Graphics g);
    }

    private static class PolygonEditorCanvas extends JPanel implements MouseListener {
        public PolygonEditorCanvas() {
            super();
            addMouseListener(this);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (drawCallback != null) {
                drawCallback.callback(g);
            }
        }

        private drawCallback drawCallback;
        public void setDrawCallback(drawCallback cb) {
            drawCallback = cb;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("asd");
            paintComponent(getGraphics());
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

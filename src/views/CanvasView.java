package views;

import models.draw.Camera;
import repositories.SceneRepository;
import views.callbacks.RenderCallback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CanvasView extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    public SceneRepository sceneRepository;
    public RenderCallback renderCallback;

    private double mouseSensitivity = 1;
    private double keyboardSensitivity = 2;


    public CanvasView() {
        super();
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        setKeyBinds();
    }

    public CanvasView(SceneRepository sceneStore) {
        this();

        this.sceneRepository = sceneStore;
    }

    public void setSceneRepository(SceneRepository sceneRepository) {
        this.sceneRepository = sceneRepository;
    }

    private void setKeyBinds() {
        ActionMap actionMap = getActionMap();
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "W");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "S");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "D");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "A");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0), "Q");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), "E");

        actionMap.put("W", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sceneRepository.getCamera().moveZ(keyboardSensitivity);
                renderCallback.render();
            }
        });
        actionMap.put("S", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sceneRepository.getCamera().moveZ(-keyboardSensitivity);
                renderCallback.render();
            }
        });
        actionMap.put("D", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sceneRepository.getCamera().moveX(keyboardSensitivity);
                renderCallback.render();
            }
        });
        actionMap.put("A", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sceneRepository.getCamera().moveX(-keyboardSensitivity);
                renderCallback.render();
            }
        });
        actionMap.put("Q", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sceneRepository.getCamera().moveY(keyboardSensitivity);
                renderCallback.render();
            }
        });
        actionMap.put("E", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sceneRepository.getCamera().moveY(-keyboardSensitivity);
                renderCallback.render();
            }
        });
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

        Camera camera = sceneRepository.getCamera();
        camera.moveX((curX - oldX));
        camera.moveY((curY - oldY));

        System.out.println("dragging: from " + oldX + ", " + oldY + " to " + curX + ", " + curY);
        System.out.println("\tCamera: " + camera.getX() + ", " + camera.getY());
        oldX = curX;
        oldY = curY;

        renderCallback.render();
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

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        Camera cam = sceneRepository.getCamera();
        int distance = e.getScrollAmount() * e.getWheelRotation();
        cam.setScreenDistance(cam.getScreenDistance() + distance);
        System.out.println(distance);
        renderCallback.render();
    }
}

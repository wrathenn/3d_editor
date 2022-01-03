package views;

import views.callbacks.CameraMoveCallback;
import views.callbacks.MovePointsCallback;
import views.callbacks.SelectCallback;
import views.callbacks.RenderCallback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CanvasView extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
    public RenderCallback renderCallback;
    private SelectCallback selectCallback;
    private SelectCallback unselectPointsCallback;
    private CameraMoveCallback cameraMoveCallback;

    private MovePointsCallback movePointsCallback;

    private double rotateSensitivity = 0.25f;
    private double keyboardSensitivity = 2;

    // ----- Конструктор ----- //

    public CanvasView() {
        super();
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        setKeyBinds();
    }

    @Override
    public void paintComponent(Graphics g) {
        renderCallback.render(g);
    }

    // ----- Геттеры и Сеттеры ----- //
    public void setSelectCallback(SelectCallback selectCallback) {
        this.selectCallback = selectCallback;
    }

    public void setUnselectPointsCallback(SelectCallback unselectPointsCallback) {
        this.unselectPointsCallback = unselectPointsCallback;
    }

    public void setCameraMoveCallback(CameraMoveCallback cameraMoveCallback) {
        this.cameraMoveCallback = cameraMoveCallback;
    }

    public void setMovePointsCallback(MovePointsCallback movePointsCallback) {
        this.movePointsCallback = movePointsCallback;
    }

    // ----- Настройки ----- //

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
                cameraMoveCallback.moveZ(keyboardSensitivity);
                paintComponent(getGraphics());
            }
        });
        actionMap.put("S", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cameraMoveCallback.moveZ(-keyboardSensitivity);
                paintComponent(getGraphics());
            }
        });
        actionMap.put("D", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cameraMoveCallback.moveX(keyboardSensitivity);
                paintComponent(getGraphics());
            }
        });
        actionMap.put("A", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cameraMoveCallback.moveX(-keyboardSensitivity);
                paintComponent(getGraphics());
            }
        });
        actionMap.put("Q", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cameraMoveCallback.moveY(keyboardSensitivity);
                paintComponent(getGraphics());
            }
        });
        actionMap.put("E", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cameraMoveCallback.moveY(-keyboardSensitivity);
                paintComponent(getGraphics());
            }
        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            selectCallback.callback(e.getX(), e.getY());
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            unselectPointsCallback.callback(e.getX(), e.getY());
        }
        paintComponent(getGraphics());
    }

    private int oldX = 0;
    private int oldY = 0;
    private final double movePointSensitivity = 3;

    @Override
    public void mouseDragged(MouseEvent e) {
        int curX = e.getX();
        int curY = e.getY();

        if (e.isShiftDown() || e.isAltDown() || e.isControlDown()) {
            int dMouse = curX - oldX;
            double dx = (e.isShiftDown() ? 1 : 0) * movePointSensitivity * dMouse;
            double dy = (e.isAltDown() ? 1 : 0) * movePointSensitivity * dMouse;
            double dz = (e.isControlDown() ? 1 : 0) * movePointSensitivity * dMouse;
            movePointsCallback.callback(dx, dy, dz);
        }
        else {
            cameraMoveCallback.rotateXY((curX - oldX) * rotateSensitivity, (curY - oldY) * rotateSensitivity);
        }

        oldX = curX;
        oldY = curY;
        paintComponent(getGraphics());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
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
        double distance = e.getScrollAmount() * e.getWheelRotation();
        if (distance == 0) {
            return;
        }

        cameraMoveCallback.moveScreen(distance);
        paintComponent(getGraphics());
    }
}

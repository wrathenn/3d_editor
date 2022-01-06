import controllers.DrawController;
import controllers.SceneController;
import groovy.lang.GroovyClassLoader;
import io.GlobalLogger;
import models.draw.Camera;
import models.scene.Polygon;
import repositories.SceneRepository;
import repositories.DrawerZBuffer;
import views.MainMenuBar;
import views.callbacks.CameraMoveCallback;
import views.callbacks.MovePointsCallback;
import views.editor.PolygonEditorView;
import views.CanvasView;
import views.user_input.MoveView;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MainApplication extends JFrame {
    private final int DEFAULT_WIDTH = 960;
    private final int DEFAULT_HEIGHT = 640;

    private CanvasView canvas;
    private MainMenuBar menu;

    private int pointNameFlag = 1;

    // architecture stuff
    private SceneController sceneController;
    private DrawController drawController;

    private MovePointsCallback sharedMovePointsCallback;

    public MainApplication(String title) {
        super(title);
        setShared();
        initGUI();

        sceneController = new SceneController(new SceneRepository());
        drawController = new DrawController(new DrawerZBuffer(canvas.getSize()));

        setMenuCallbacks();
        setCanvasCallbacks();
    }

    private void initGUI() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        menu = new MainMenuBar();
        setJMenuBar(menu);

        Container pane = getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));

        canvas = new CanvasView();
        pane.add(canvas);
    }

    private void changeSceneRepository(SceneRepository newRepo) {
        sceneController.setSceneRepository(newRepo);
    }

    private void setShared() {
        sharedMovePointsCallback = (x, y, z) -> {
            ArrayList<String> selected = drawController.getSelectedPointNames();
            if (selected.size() != 0) {
                sceneController.movePoints(selected, x, y, z);
                return;
            }

            UUID selectedPoly = drawController.getSelectedPolyId();
            if (selectedPoly != null) {
                sceneController.movePoly(selectedPoly, x, y, z);
            }
        };
    }

    private void setCanvasCallbacks() {
        canvas.renderCallback = (Graphics g) -> {
            Camera camera = sceneController.getCamera();
            camera.setScreenHeight(canvas.getHeight());
            camera.setScreenWidth(canvas.getWidth());
            drawController.draw(g, sceneController.getCamera(), sceneController.getSceneRepository(), pointNameFlag);
        };

        canvas.setSelectCallback((x, y) -> {
            String id = drawController.getPointName(x, y);
            if (id != null) {
                drawController.addSelectedPointName(id);
                GlobalLogger.getLogger().log(Level.INFO, "selected new Point with Name=" + id);
                return;
            }

            UUID polyId = drawController.getPolyId(x, y);
            if (polyId != null) {
                drawController.setSelectedPolyId(polyId);
                GlobalLogger.getLogger().log(Level.INFO, "selected new Polygon with Name=" + polyId);
            }
        });

        canvas.setUnselectPointsCallback((x, y) -> drawController.clearSelected());

        canvas.setCameraMoveCallback(new CameraMoveCallback() {
            @Override
            public void moveX(double sens) {
                sceneController.getCamera().moveX(sens);
            }

            @Override
            public void moveY(double sens) {
                sceneController.getCamera().moveY(sens);
            }

            @Override
            public void moveZ(double sens) {
                sceneController.getCamera().moveZ(sens);
            }

            @Override
            public void rotateXY(double x, double y) {
                Camera c = sceneController.getCamera();
                c.rotateX(x);
                c.rotateY(y);
            }

            @Override
            public void moveScreen(double dist) {
                Camera c = sceneController.getCamera();
                c.setScreenDistance(c.getScreenDistance() + dist);
            }
        });

        canvas.setMovePointsCallback(sharedMovePointsCallback);
    }

    private void setMenuCallbacks() {
        menu.setOpenActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    SceneRepository newRepo = sceneController.readFromFile(fileChooser.getSelectedFile().toString());
                    changeSceneRepository(newRepo);
                    canvas._repaint();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Ошибка при открытии файла");
                    GlobalLogger.getLogger().severe("open from file error" + ex.getMessage() +
                            "\n" + Arrays.toString(ex.getStackTrace()));
                }
            }
        });

        menu.setSaveActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    sceneController.saveToFile(fileChooser.getSelectedFile().toString());
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(this, "Ошибка при сохранении");
                    GlobalLogger.getLogger().severe("save to file error" + ioException.getMessage() +
                            "\n" + Arrays.toString(ioException.getStackTrace()));
                }
            }
        });

        menu.setExitActionListener(e -> {
            this.dispose();
        });

        menu.setMoveActionListener(e -> {
            if (drawController.getSelectedPolyId() == null && drawController.getSelectedPointNames().size() == 0) {
                JOptionPane.showMessageDialog(this, "Не выбраны элементы для редактирования");
                GlobalLogger.getLogger().severe("No elements for moving");
                return;
            }

            MoveView moveView = new MoveView();
            moveView.setAddCallback((x, y, z) -> {
                sharedMovePointsCallback.callback(x, y, z);
                canvas._repaint();
            });
            moveView.setVisible(true);
        });

        menu.setEditPolyActionListener(e -> {
            UUID selected = drawController.getSelectedPolyId();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Не выбран многоугольник");
                GlobalLogger.getLogger().severe("Polygon not selected");
                return;
            }

            Polygon polygon = sceneController.getPolygonById(selected);
            if (polygon == null) {
                JOptionPane.showMessageDialog(this, "Многоугольник был удален");
                GlobalLogger.getLogger().severe("Polygon with UUID=" + selected + " already deleted");
                return;
            }

            PolygonEditorView frame = new PolygonEditorView(polygon);
            frame.start();
        });

        menu.setChangePointNameModeListener(e -> {
            pointNameFlag = (pointNameFlag + 1) % 3;
            canvas._repaint();
        });
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        JFrame frame = new MainApplication("Редактор поверхностей. Шацкий Р.Е.");
        frame.setVisible(true);
    }
}

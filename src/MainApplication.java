import controllers.DrawController;
import controllers.SceneController;
import exceptions.ExistedNameException;
import exceptions.NotExistedNameException;
import models.draw.Camera;
import models.scene.Point;
import models.scene.Polygon;
import repositories.SceneRepository;
import repositories.DrawerZBuffer;
import views.ButtonPanel;
import views.MainMenuBar;
import views.editor.PolygonEditorView;
import views.user_input.AddPointView;
import views.CanvasView;
import views.user_input.AddPolygonView;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class MainApplication extends JFrame {
    private final int DEFAULT_WIDTH = 960;
    private final int DEFAULT_HEIGHT = 640;

    private final int BUTTON_PANEL_WIDTH = 250;

    private ButtonPanel buttonPanel;
    private CanvasView canvas;
    private MainMenuBar menu;

    // architecture stuff
    private SceneController sceneController;
    private DrawController drawController;

    public MainApplication(String title) {
        super(title);
        initGUI();

        sceneController = new SceneController(new SceneRepository());

        drawController = new DrawController(new DrawerZBuffer(canvas.getSize()));

        canvas.setSceneRepository(sceneController.getSceneRepository());

        canvas.renderCallback = (g) -> {
            Camera camera = sceneController.getCamera();
            camera.setScreenHeight(canvas.getHeight());
            camera.setScreenWidth(canvas.getWidth());
            drawController.draw(g, sceneController.getCamera(), sceneController.getSceneRepository());
        };

        setButtonPanelCallbacks();
        setMenuCallbacks();
        setCanvasCallbacks();

        // DBG
        try {
            changeSceneRepository(sceneController.readFromFile("D:/courseCG/scenes/cube.txt"));
        }
        catch (Exception e) {
            System.out.println("no");
        }
    }

    private void initGUI() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        menu = new MainMenuBar();
        setJMenuBar(menu);

        Container pane = getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));

        buttonPanel = new ButtonPanel();
        buttonPanel.setMaximumSize(new Dimension(BUTTON_PANEL_WIDTH, DEFAULT_HEIGHT));
        pane.add(buttonPanel);

        canvas = new CanvasView();
        canvas.setMaximumSize(new Dimension(DEFAULT_WIDTH - BUTTON_PANEL_WIDTH, DEFAULT_HEIGHT));
        pane.add(canvas);
    }

    private void changeSceneRepository(SceneRepository newRepo) {
        sceneController.setSceneRepository(newRepo);
        canvas.setSceneRepository(newRepo);
    }

    private void setCanvasCallbacks() {
        canvas.setSelectPolyCallback((x, y) -> {
            UUID id = drawController.getPolyId(x, y);
            if (id != null) {
                drawController.setSelectedPolyId(id);
                System.out.println("log - Selected new Poly with ID=" + id);
            } else {
                drawController.setSelectedPolyId(null);
                System.out.println("log - Unselected Poly");
            }
        });
    }

    private void setMenuCallbacks() {
        menu.setOpenActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    SceneRepository newRepo = sceneController.readFromFile(fileChooser.getSelectedFile().toString());
                    changeSceneRepository(newRepo);
                } catch (IOException ex) {
                    System.out.println("log - " + Arrays.toString(ex.getStackTrace()));
                }
            }
        });
    }

    private void setButtonPanelCallbacks() {
        buttonPanel.setAddPointButtonActionListener(e -> {
            AddPointView frame = new AddPointView();
            frame.setAddCallback(s -> {
                try {
                    sceneController.add(s);
                } catch (ExistedNameException e1) {
                    System.out.println(e1.getMessage());
                }
            });

            frame.setVisible(true);
        });

        buttonPanel.setAddPolygonButtonActionListener(e -> {
            AddPolygonView frame = new AddPolygonView();

            frame.setAddCallback(pointNames -> {
                ArrayList<Point> points = new ArrayList<>();
                for (String pName : pointNames) {
                    try {
                        points.add(sceneController.findPoint(pName));
                    } catch (NotExistedNameException ex) {
                        System.out.println(ex.getMessage());
                        return;
                    }
                }

                Point[] pArr = new Point[points.size()];
                Polygon newPoly = new Polygon(points.toArray(pArr), new Color(0, 256, 256));
                sceneController.add(newPoly);
            });

            frame.setVisible(true);
        });

        buttonPanel.setEditPolygonActionListener(e -> {
            UUID selected = drawController.getSelectedPolyId();
            if (selected == null) {
                System.out.println("log - Не выбран многоугольник"); // TODO: errorview
                return;
            }

            Polygon polygon = sceneController.getPolygonById(selected);
            if (polygon == null) {
                System.out.println("log - Выбран уже не существующий многоугольник"); // TODO: errorview
                return;
            }

            PolygonEditorView frame = new PolygonEditorView(polygon);
            frame.start();
        });
    }

    public static void main(String[] args) {
        JFrame frame = new MainApplication("Редактор поверхностей. Шацкий Р.Е.");
        frame.setVisible(true);
    }
}

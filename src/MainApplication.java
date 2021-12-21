import controllers.DrawController;
import controllers.SceneController;
import exceptions.ExistedNameException;
import exceptions.NotExistedNameException;
import models.draw.Camera;
import models.scene.Edge;
import models.scene.Point;
import models.scene.Polygon;
import repositories.SceneRepository;
import repositories.DrawerZBuffer;
import views.ButtonPanel;
import views.CustomMenuBar;
import views.callbacks.RenderCallback;
import views.user_input.AddEdgeView;
import views.user_input.AddPointView;
import views.CanvasView;
import views.user_input.AddPolygonView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainApplication extends JFrame {
    private final int DEFAULT_WIDTH = 960;
    private final int DEFAULT_HEIGHT = 640;

    private final int BUTTON_PANEL_WIDTH = 100;

    ButtonPanel buttonPanel;
    CanvasView canvas;
    CustomMenuBar menu;

    // architecture stuff
    private SceneController sceneController;
    private DrawController drawController;

    private void initGUI() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        menu = new CustomMenuBar();
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

    public MainApplication(String title) {
        super(title);
        initGUI();

        sceneController = new SceneController(new SceneRepository());

        drawController = new DrawController(new DrawerZBuffer(canvas.getSize()));

        canvas.setSceneRepository(sceneController.getSceneRepository());

        canvas.renderCallback = () -> {
            Camera camera = sceneController.getCamera();
            camera.setScreenHeight(canvas.getHeight());
            camera.setScreenWidth(canvas.getWidth());
            drawController.draw(canvas.getGraphics(), sceneController.getCamera(), sceneController.getSceneRepository());
        };

        setButtonPanelCallbacks();
        setMenuCallbacks();
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
        buttonPanel.setDrawButtonCallback(canvas.renderCallback);
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
                Polygon newPoly = new Polygon(points.toArray(pArr), new Color(200, 200, 200));
                sceneController.add(newPoly);
            });

            frame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        JFrame frame = new MainApplication("Редактор поверхностей. Шацкий Р.Е.");
        frame.setVisible(true);
    }
}

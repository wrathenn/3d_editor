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
import views.callbacks.RenderCallback;
import views.user_input.AddEdgeView;
import views.user_input.AddPointView;
import views.CanvasView;
import views.user_input.AddPolygonView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainApplication extends JFrame {
    private final int DEFAULT_WIDTH = 960;
    private final int DEFAULT_HEIGHT = 640;

    private final int BUTTON_PANEL_WIDTH = 100;

    CanvasView canvas;

    JButton drawButton;
    JButton addPointButton;
    JButton addEdgeButton;
    JButton addPolygonButton;

    // architecture stuff
    private SceneController sceneController;
    private DrawController drawController;

    private JPanel buildButtonPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
        {
            drawButton = new JButton("draw");
            drawButton.setSize(drawButton.getMaximumSize());
            infoPanel.add(drawButton);
        }
        {
            addPointButton = new JButton("Add point");
            addPointButton.setSize(infoPanel.getSize().width, 20);
            infoPanel.add(addPointButton);
        }
        {
            addEdgeButton = new JButton("Add edge");
            addEdgeButton.setSize(infoPanel.getSize().width, 20);
            infoPanel.add(addEdgeButton);
        }
        {
            addPolygonButton = new JButton("Add Polygon");
            addPolygonButton.setSize(infoPanel.getSize().width, 20);
            infoPanel.add(addPolygonButton);
        }

        infoPanel.setMaximumSize(new Dimension(BUTTON_PANEL_WIDTH, DEFAULT_HEIGHT));
        infoPanel.setBackground(Color.gray);

        return infoPanel;
    }

    private void initGUI() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container pane = getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));

        pane.add(buildButtonPanel());

        canvas = new CanvasView();
        canvas.setMaximumSize(new Dimension(DEFAULT_WIDTH - BUTTON_PANEL_WIDTH, DEFAULT_HEIGHT));
        pane.add(canvas);
    }

    public MainApplication(String title) {
        super(title);
        initGUI();

        sceneController = new SceneController(new SceneRepository());

        drawController = new DrawController(new DrawerZBuffer(canvas.getSize()));

        canvas.setSceneRepository(sceneController.getSceneRepository());

        drawButton.addActionListener(e -> canvas.renderCallback.render());

        canvas.renderCallback = () -> {
            Camera camera = sceneController.getCamera();
            camera.setScreenHeight(canvas.getHeight());
            camera.setScreenWidth(canvas.getWidth());
//            camera.setDimensions(canvas.getWidth(), canvas.getHeight());
            drawController.draw(canvas.getGraphics(), sceneController.getCamera(), sceneController.getSceneRepository());
        };

        addPointButton.addActionListener(e -> {
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

        addEdgeButton.addActionListener(e -> {
            AddEdgeView frame = new AddEdgeView();

            frame.setAddCallback((p1Name, p2Name) -> {
                Point p1, p2;
                try {
                    p1 = sceneController.findPoint(p1Name);
                    p2 = sceneController.findPoint(p2Name);
                } catch (NotExistedNameException ex) {
                    System.out.println(ex.getMessage());
                    return; // TODO: errorView
                }

                sceneController.add(new Edge(p1, p2));
            });

            frame.setVisible(true);
        });

        addPolygonButton.addActionListener(e -> {
            AddPolygonView frame = new AddPolygonView();

            frame.setAddCallback(pointNames -> {
                ArrayList<Point> points = new ArrayList<>();
                for (String pName : pointNames) {
                    try {
                        points.add(sceneController.findPoint(pName));
                    } catch (NotExistedNameException ex) {
                        System.out.println(ex.getMessage());
                        return; // TODO: errorView
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
        JFrame frame = new MainApplication("Test");
        frame.setVisible(true);
    }
}

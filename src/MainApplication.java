import controllers.DrawController;
import controllers.SceneController;
import exceptions.ExistedNameException;
import exceptions.NotExistedNameException;
import models.Camera;
import models.Edge;
import models.Point;
import models.Polygon;
import repositories.SceneRepository;
import repositories.DrawerVisitor;
import views.user_input.AddEdgeView;
import views.user_input.AddPointView;
import views.CanvasView;
import views.user_input.AddPolygonView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainApplication extends JFrame {
    private final int DEFAULT_WIDTH = 640;
    private final int DEFAULT_HEIGHT = 640;

    CanvasView canvas;

    JButton drawButton;
    JButton addPointButton;
    JButton addEdgeButton;
    JButton addPolygonButton;

    // architecture stuff
    private SceneController sceneController;
    private DrawController drawController;

    private void initGUI() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container pane = getContentPane();
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        pane.setLayout(new GridBagLayout());
        GridBagConstraints config = new GridBagConstraints();

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

        config.gridy = 0;
        config.weightx = 1;
        config.weighty = 1;
        config.fill = GridBagConstraints.BOTH;
        infoPanel.setMaximumSize(new Dimension(100, 0));
        infoPanel.setPreferredSize(new Dimension(100, 0));
        infoPanel.setMinimumSize(new Dimension(100, 0));
        infoPanel.setBackground(Color.gray);
        pane.add(infoPanel, config);

        canvas = new CanvasView();
//        canvas.setSize(300, 300); // TODO
        canvas.setBackground(Color.green);
        config.gridx = 1;
        config.weightx = 4;
        pane.add(canvas, config);
    }

    public MainApplication(String title) {
        super(title);
        initGUI();

        sceneController = new SceneController(new SceneRepository());

        drawController = new DrawController(new DrawerVisitor());

        canvas.setSceneRepository(sceneController.getSceneRepository());

        drawButton.addActionListener(e -> {
            Camera camera = sceneController.getCamera(0);
            camera.setDimensions(canvas.getWidth(), canvas.getHeight());
            drawController.draw(canvas.getGraphics(), sceneController.getCamera(0), sceneController.getShapesToDraw());
        });

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

                try {
                    sceneController.add(new Edge(p1, p2));
                } catch (ExistedNameException ex) {
                    System.out.println(ex.getMessage());
                }
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
                Polygon newPoly = new Polygon(points.toArray(pArr));

                try {
                    sceneController.add(newPoly);
                } catch (ExistedNameException ex) {
                    System.out.println(ex.getMessage());
                    // TODO: errorView
                }
            });

            frame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        JFrame frame = new MainApplication("Test");
        frame.setVisible(true);
    }
}

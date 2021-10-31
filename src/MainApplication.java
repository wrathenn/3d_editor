import controllers.DrawController;
import controllers.SceneController;
import exceptions.ExistedNameException;
import models.Camera;
import models.Shape;
import repositories.SceneRepository;
import scene.actions.DrawerVisitor;
import services.DrawService;
import services.SceneService;
import views.user_input.AddPointView;
import views.CanvasView;
import views.ShapeCallback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApplication extends JFrame {
    private final int DEFAULT_WIDTH = 1280;
    private final int DEFAULT_HEIGHT = 800;

    CanvasView canvas;

    JButton drawButton;
    JButton addPointButton;
    JButton addEdgeButton;

    // architecture stuff
    private SceneController sceneController;
    private DrawController drawController;

    private Camera currentCamera = new Camera();

    private void initGUI() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container pane = getContentPane();
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        pane.setLayout(new GridBagLayout());
        GridBagConstraints config = new GridBagConstraints();

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));

        drawButton = new JButton("draw");

        drawButton.setSize(drawButton.getMaximumSize());
        infoPanel.add(drawButton);

        addPointButton = new JButton("Add point");
        addPointButton.setSize(infoPanel.getSize().width, 20);
        infoPanel.add(addPointButton);

        addEdgeButton = new JButton("Add edge");
        ;
        addEdgeButton.setSize(infoPanel.getSize().width, 20);
        infoPanel.add(addEdgeButton);

        config.gridy = 0;
        config.weightx = 1;
        config.weighty = 1;
        config.fill = GridBagConstraints.BOTH;
        pane.add(infoPanel, config);

        canvas = new CanvasView();
        canvas.setBackground(Color.ORANGE);
        config.gridx = 1;
        config.weightx = 4;
        pane.add(canvas, config);
    }

    public MainApplication(String title) {
        super(title);
        initGUI();

        sceneController =
                new SceneController(
                        new SceneService(
                                new SceneRepository()
                        )
                );

        drawController =
                new DrawController(
                        new DrawService(
                                new DrawerVisitor()
                        )
                );

        canvas.setSceneRepository(sceneController.getStore());

        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawController.draw(canvas.getGraphics(), currentCamera, sceneController.getShapesToDraw());
            }
        });

        addPointButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddPointView frame = new AddPointView();
                frame.setAddCallback(new ShapeCallback() {
                    @Override
                    public void callback(Shape s) {
                        try {
                            sceneController.add(s);
                        } catch (ExistedNameException e) {
                            System.out.println("Есть точка с таким именем");
                        }
                    }
                });

                frame.setVisible(true);
            }
        });

        addEdgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new MainApplication("Test");
        frame.setVisible(true);
    }
}

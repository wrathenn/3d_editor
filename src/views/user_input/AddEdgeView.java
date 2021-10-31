//package views.user_input;
//
//import models.Point;
//import views.ShapeCallback;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class AddEdgeView extends JFrame {
//
//    private final JLabel headingLabel = new JLabel("Input Edge parameters");
//
//    private final JLabel point1Label = new JLabel("Input P1:");
//    private final JTextField inputP1 = new JTextField("P1", 1);
//
//    private final JLabel point2Label = new JLabel("Input P2:");
//    private final JTextField inputP2 = new JTextField("P1", 1);
//
//
//    private final JButton addButton = new JButton("Add Edge");
//
//    private ShapeCallback addCallback;
//
//    public void setAddCallback(ShapeCallback addCallback) {
//        this.addCallback = addCallback;
//    }
//
//    public AddEdgeView() throws HeadlessException {
//        super("Create new point");
//
//        setMinimumSize(new Dimension(200, 160));;
//        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//
//        Container pane = this.getContentPane();
//        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
//
//        pane.setLayout(new GridBagLayout());
//        GridBagConstraints config = new GridBagConstraints();
//
//        config.gridx = 0;
//        config.gridy = 0;
//        config.gridwidth = 2;
//        config.ipadx = 10;
//        config.weightx = 0.5;
//        config.fill = GridBagConstraints.VERTICAL;
//        pane.add(headingLabel, config);
//
//        inputXLabel.setHorizontalAlignment(SwingConstants.LEFT);
//        config.gridx = 0;
//        config.gridy = 1;
//        config.gridwidth = 1;
//        config.fill = GridBagConstraints.VERTICAL;
//        pane.add(inputXLabel, config);
//
//        config.gridx = 1;
//        config.gridy = 1;
//        config.fill = GridBagConstraints.BOTH;
//        pane.add(inputX, config);
//
//        inputYLabel.setHorizontalAlignment(SwingConstants.LEFT);
//        config.gridx = 0;
//        config.gridy = 2;
//        config.fill = GridBagConstraints.VERTICAL;
//        pane.add(inputYLabel, config);
//
//        config.gridx = 1;
//        config.gridy = 2;
//        config.fill = GridBagConstraints.BOTH;
//        pane.add(inputY, config);
//
//        inputYLabel.setHorizontalAlignment(SwingConstants.LEFT);
//        config.gridx = 0;
//        config.gridy = 3;
//        config.fill = GridBagConstraints.VERTICAL;
//        pane.add(inputZLabel, config);
//
//        config.gridx = 1;
//        config.gridy = 3;
//        config.fill = GridBagConstraints.BOTH;
//        pane.add(inputZ, config);
//
//        config.gridx = 0;
//        config.gridy = 4;
//        config.gridwidth = 3;
//        config.weightx = 10;
//        config.fill = GridBagConstraints.BOTH;
//        pane.add(addButton, config);
//
//        addButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                addCallback.callback(new Point(
//                        Float.parseFloat(inputX.getText()),
//                        Float.parseFloat(inputY.getText()),
//                        Float.parseFloat(inputZ.getText())
//                ));
//                dispose();
//            }
//        });
//    }
//}

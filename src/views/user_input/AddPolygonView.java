package views.user_input;

import views.AddEdgeCallback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPolygonView extends JFrame {

    private final JLabel headingLabel = new JLabel("Input Polygon parameters");

    private final JLabel point1Label = new JLabel("Input Edges:");
    private final JTextField inputP1 = new JTextField("P1", 1);

    private final JLabel point2Label = new JLabel("Input P2:");
    private final JTextField inputP2 = new JTextField("P1", 1);


    private final JButton addButton = new JButton("Add Edge");

    private AddEdgeCallback addCallback;

    public void setAddCallback(AddEdgeCallback addCallback) {
        this.addCallback = addCallback;
    }

    public AddPolygonView() throws HeadlessException {
        super("Create new point");

        setMinimumSize(new Dimension(200, 160));;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Container pane = this.getContentPane();
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        pane.setLayout(new GridBagLayout());
        GridBagConstraints config = new GridBagConstraints();

        int yPosition = 0;

        config.gridx = 0;
        config.gridy = yPosition;
        config.gridwidth = 2;
        config.ipadx = 10;
        config.weightx = 0.5;
        config.fill = GridBagConstraints.VERTICAL;
        pane.add(headingLabel, config);

        yPosition += 1;

        point1Label.setHorizontalAlignment(SwingConstants.LEFT);
        config.gridx = 0;
        config.gridy = yPosition;
        config.gridwidth = 1;
        config.fill = GridBagConstraints.VERTICAL;
        pane.add(point1Label, config);

        config.gridx = 1;
        config.gridy = yPosition;
        config.fill = GridBagConstraints.BOTH;
        pane.add(inputP1, config);

        yPosition += 1;

        point2Label.setHorizontalAlignment(SwingConstants.LEFT);
        config.gridx = 0;
        config.gridy = yPosition;
        config.fill = GridBagConstraints.VERTICAL;
        pane.add(point2Label, config);

        config.gridx = 1;
        config.gridy = yPosition;
        config.fill = GridBagConstraints.BOTH;
        pane.add(inputP2, config);

        yPosition += 1;

        config.gridx = 0;
        config.gridy = yPosition;
        config.gridwidth = 3;
        config.weightx = 10;
        config.fill = GridBagConstraints.BOTH;
        pane.add(addButton, config);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCallback.callback(inputP1.getText(), inputP2.getText());
                dispose();
            }
        });
    }
}

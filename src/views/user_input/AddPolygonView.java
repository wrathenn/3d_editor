package views.user_input;

import models.Point;

import views.callbacks.AddEdgeCallback;
import views.callbacks.AddPolygonCallback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddPolygonView extends JFrame {
    private final JLabel headingLabel = new JLabel("Input Polygon parameters");

    private final JLabel pointsLabel = new JLabel("Input Points (P1, P2, ..., Pn):");
    private final JTextField inputPoints = new JTextField("", 1);

    private final JButton addButton = new JButton("Add Polygon");

    private AddPolygonCallback addCallback;

    public void setAddCallback(AddPolygonCallback addCallback) {
        this.addCallback = addCallback;
    }

    public AddPolygonView() {
        super("Create new polygon");

        setMinimumSize(new Dimension(200, 160));
        ;
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

        yPosition++;

        pointsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        config.gridx = 0;
        config.gridy = yPosition;
        config.gridwidth = 1;
        config.fill = GridBagConstraints.VERTICAL;
        pane.add(pointsLabel, config);

        yPosition++;

        config.gridx = 0;
        config.gridy = yPosition;
        config.fill = GridBagConstraints.BOTH;
        pane.add(inputPoints, config);

        yPosition++;

        config.gridx = 0;
        config.gridy = yPosition;
        config.gridwidth = 3;
        config.weightx = 10;
        config.fill = GridBagConstraints.BOTH;
        pane.add(addButton, config);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pStr = inputPoints.getText();
                String[] pNames = pStr.split(", ");

                // TODO: проверка на ошибку
                addCallback.callback(pNames);
                dispose();
            }
        });
    }
}

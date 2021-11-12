package views.user_input;

import models.Point;
import views.callbacks.AddPointCallback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPointView extends JFrame {

    private final JLabel headingLabel = new JLabel("Input Point parameters");

    private final JLabel inputNameLabel = new JLabel("Input Name:");
    private final JTextField inputName = new JTextField("Name", 1);

    private final JLabel inputXLabel = new JLabel("Input x:");
    private final JTextField inputX = new JTextField("X", 1);
    private final JLabel inputYLabel = new JLabel("Input y:");
    private final JTextField inputY = new JTextField("Y", 1);
    private final JLabel inputZLabel = new JLabel("Input z:");
    private final JTextField inputZ = new JTextField("Z", 1);

    private final JButton addButton = new JButton("Add Point");

    private AddPointCallback addCallback;

    public void setAddCallback(AddPointCallback addCallback) {
        this.addCallback = addCallback;
    }

    public AddPointView() throws HeadlessException {
        super("Create new point");

        setMinimumSize(new Dimension(200, 160));;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Container pane = this.getContentPane();
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        pane.setLayout(new GridBagLayout());
        GridBagConstraints config = new GridBagConstraints();

        // Заголовок
        config.gridx = 0;
        config.gridy = 0;
        config.gridwidth = 2;
        config.ipadx = 10;
        config.weightx = 0.5;
        config.fill = GridBagConstraints.VERTICAL;
        pane.add(headingLabel, config);

        int yPosition = 1;

        // Ввод имени
        inputNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        config.gridx = 0;
        config.gridy = yPosition;
        config.gridwidth = 1;
        config.fill = GridBagConstraints.VERTICAL;
        pane.add(inputNameLabel, config);

        config.gridx = 1;
        config.gridy = yPosition;
        config.fill = GridBagConstraints.BOTH;
        pane.add(inputName, config);

        yPosition += 1;

        // Ввод X
        inputXLabel.setHorizontalAlignment(SwingConstants.LEFT);
        config.gridx = 0;
        config.gridy = yPosition;
        config.gridwidth = 1;
        config.fill = GridBagConstraints.VERTICAL;
        pane.add(inputXLabel, config);

        config.gridx = 1;
        config.gridy = yPosition;
        config.fill = GridBagConstraints.BOTH;
        pane.add(inputX, config);

        yPosition += 1;

        // Ввод Y
        inputYLabel.setHorizontalAlignment(SwingConstants.LEFT);
        config.gridx = 0;
        config.gridy = yPosition;
        config.fill = GridBagConstraints.VERTICAL;
        pane.add(inputYLabel, config);

        config.gridx = 1;
        config.gridy = yPosition;
        config.fill = GridBagConstraints.BOTH;
        pane.add(inputY, config);

        yPosition += 1;

        // Ввод Z
        inputYLabel.setHorizontalAlignment(SwingConstants.LEFT);
        config.gridx = 0;
        config.gridy = yPosition;
        config.fill = GridBagConstraints.VERTICAL;
        pane.add(inputZLabel, config);

        config.gridx = 1;
        config.gridy = yPosition;
        config.fill = GridBagConstraints.BOTH;
        pane.add(inputZ, config);

        yPosition += 1;

        // Кнопка готово
        config.gridx = 0;
        config.gridy = yPosition;
        config.gridwidth = 3;
        config.weightx = 10;
        config.fill = GridBagConstraints.BOTH;
        pane.add(addButton, config);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCallback.callback(new Point(
                        inputName.getText(),
                        Float.parseFloat(inputX.getText()),
                        Float.parseFloat(inputY.getText()),
                        Float.parseFloat(inputZ.getText())
                ));
                dispose();
            }
        });
    }
}

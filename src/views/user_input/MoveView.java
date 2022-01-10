package views.user_input;

import io.GlobalLogger;
import models.scene.Point;
import views.callbacks.MovePointsCallback;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;

public class MoveView extends JFrame {

    private final JLabel headingLabel = new JLabel("Перемещение выделенных элементов");

    private final JLabel inputXLabel = new JLabel("По x:");
    private final JTextField inputX = new JTextField("0", 1);
    private final JLabel inputYLabel = new JLabel("По y:");
    private final JTextField inputY = new JTextField("0", 1);
    private final JLabel inputZLabel = new JLabel("По z:");
    private final JTextField inputZ = new JTextField("0", 1);

    private final JButton addButton = new JButton("Переместить");

    private MovePointsCallback moveCallback;

    public void setAddCallback(MovePointsCallback addCallback) {
        this.moveCallback = addCallback;
    }

    public MoveView() throws HeadlessException {
        super("Перемещение");

        setMinimumSize(new Dimension(320, 160));

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

        addButton.addActionListener(e -> {
            try {
                double x = Double.parseDouble(inputX.getText());
                double y = Double.parseDouble(inputY.getText());
                double z = Double.parseDouble(inputZ.getText());
                moveCallback.callback(x, y, z);
                dispose();
            } catch (NumberFormatException exception) {
                GlobalLogger.getLogger().log(Level.INFO,
                        String.format("MoveView - incorrect arguments x=(%s), y=(%s), z=(%s)",
                                inputX.getText(), inputY.getText(), inputZ.getText())
                );
                JOptionPane.showMessageDialog(this, "Некорректные аргументы перемещения");
            }
        });
    }
}
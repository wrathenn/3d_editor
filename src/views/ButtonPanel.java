package views;

import views.callbacks.AddPointCallback;
import views.callbacks.RenderCallback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel {
    private JButton addPointButton;
    private JButton addPolygonButton;
    private JButton editPolygon;

    public ButtonPanel() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        addPointButton = new JButton("Add point");
        addPointButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        addPointButton.setBackground(Color.ORANGE);
        this.add(addPointButton);

        addPolygonButton = new JButton("Add Polygon");
        addPolygonButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        addPolygonButton.setBackground(Color.ORANGE);
        this.add(addPolygonButton);

        editPolygon = new JButton("Редактировать");
        editPolygon.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        editPolygon.setBackground(Color.ORANGE);
        this.add(editPolygon);

        this.setBackground(Color.black);
    }

    public void setAddPointButtonActionListener(ActionListener actionListener) {
        addPointButton.addActionListener(actionListener);
    }

    public void setAddPolygonButtonActionListener(ActionListener actionListener) {
        addPolygonButton.addActionListener(actionListener);
    }

    public void setEditPolygonActionListener(ActionListener actionListener) {
        editPolygon.addActionListener(actionListener);
    }
}

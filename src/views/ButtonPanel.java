package views;

import views.callbacks.AddPointCallback;
import views.callbacks.RenderCallback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel {
    private JButton drawButton;
    private JButton addPointButton;
    private JButton addPolygonButton;

    public ButtonPanel() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        {
            drawButton = new JButton("draw");
            drawButton.setSize(drawButton.getMaximumSize());
            this.add(drawButton);
        }
        {
            addPointButton = new JButton("Add point");
            addPointButton.setSize(this.getSize().width, 20);
            this.add(addPointButton);
        }
        {
            addPolygonButton = new JButton("Add Polygon");
            addPolygonButton.setSize(this.getSize().width, 20);
            this.add(addPolygonButton);
        }
        this.setBackground(Color.gray);
    }

    public void setDrawButtonCallback(RenderCallback callback) {
        drawButton.addActionListener(e -> callback.render());
    }

    public void setAddPointButtonActionListener(ActionListener actionListener) {
        addPointButton.addActionListener(actionListener);
    }

    public void setAddPolygonButtonActionListener(ActionListener actionListener){
        addPolygonButton.addActionListener(actionListener);
    }
}

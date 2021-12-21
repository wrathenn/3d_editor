package views.user_input;

import models.scene.Polygon;

import javax.swing.*;

public class PolygonEditorMenuBar extends JMenuBar {
    private JMenuItem save;
    private JMenuItem exit;

    private JMenuItem edit1;

    public PolygonEditorMenuBar() {
        super();
        add(createPolyMenu());
        add(createEditMenu());
    }

    private JMenu createPolyMenu() {
        JMenu fileMenu = new JMenu("Полигон");
        save = new JMenuItem("Сохранить");
        exit = new JMenuItem("Выход");

        fileMenu.add(save);
        fileMenu.add(exit);

        return fileMenu;
    }

    private JMenu createEditMenu() {
        JMenu editMenu = new JMenu("Редактировать");

        edit1 = new JMenuItem("Temp1");

        editMenu.add(edit1);

        return editMenu;
    }
}

package views;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MainMenuBar extends JMenuBar {
    private JMenuItem open;
    private JMenuItem save;
    private JMenuItem exit;

    private JMenuItem editPoly;
    private JMenuItem moveElement;
    private JMenuItem changePointNameMode;

    public MainMenuBar() {
        super();
        add(createFileMenu());
        add(createEditMenu());
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("Файл");

        open = new JMenuItem("Открыть");
        save = new JMenuItem("Сохранить как...");
        exit = new JMenuItem("Выход");

        fileMenu.add(open);
        fileMenu.add(save);
        fileMenu.addSeparator();
        fileMenu.add(exit);

        return fileMenu;
    }

    private JMenu createEditMenu() {
        JMenu editMenu = new JMenu("Редактировать");

        editPoly = new JMenuItem("Полигон");
        moveElement = new JMenuItem("Переместить");
        changePointNameMode = new JMenuItem("Сменить режим названий");

        editMenu.add(editPoly);
        editMenu.add(moveElement);
        editMenu.addSeparator();
        editMenu.add(changePointNameMode);

        return editMenu;
    }

    public void setOpenActionListener(ActionListener e) {
        open.addActionListener(e);
    }

    public void setSaveActionListener(ActionListener e) {
        save.addActionListener(e);
    }

    public void setExitActionListener(ActionListener e) {
        exit.addActionListener(e);
    }

    public void setMoveActionListener(ActionListener e) {
        moveElement.addActionListener(e);
    }

    public void setEditPolyActionListener(ActionListener e) {
        editPoly.addActionListener(e);
    }

    public void setChangePointNameModeListener(ActionListener e) {
        changePointNameMode.addActionListener(e);
    }
}

package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomMenuBar extends JMenuBar {
    private JMenuItem open;
    private JMenuItem save;
    private JMenuItem exit;

    public CustomMenuBar() throws HeadlessException {
        super();
        add(createFileMenu());
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

    public void setOpenActionListener(ActionListener e) {
        open.addActionListener(e);
    }
}

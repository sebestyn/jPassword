package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuPanel extends JMenuBar {
    public MenuPanel(){

        JMenu IEMenu = new JMenu("Import/Export");
        JMenuItem importMenu = new JMenuItem("Import");
        JMenuItem exportMenu = new JMenuItem("Export");
        IEMenu.add(importMenu);
        IEMenu.add(exportMenu);

        // Settingg
        JMenu settingsMenu = new JMenu("Settings");
        JRadioButtonMenuItem resetOn3InvalidLogin = new JRadioButtonMenuItem("Reset in 3 invalid login attemps");
        JMenuItem resetMenu = new JMenuItem("Reset now");
        settingsMenu.add(resetOn3InvalidLogin);
        settingsMenu.add(resetMenu);

        this.add(IEMenu);
        this.add(settingsMenu);
    }

}

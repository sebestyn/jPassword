package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuPanel extends JMenuBar {
    public MenuPanel(){

        // File
        JMenu FileMenu = new JMenu("File");
        JMenuItem saveMenu = new JMenuItem("Save");
        JMenuItem loadMenu = new JMenuItem("Load");
        FileMenu.add(saveMenu);
        FileMenu.add(loadMenu);

        // Item
        JMenu ItemMenu = new JMenu("Item");
        JMenuItem addPasswordMenu = new JMenuItem("Add Password");
        JMenuItem addNoteMenu = new JMenuItem("Add Note");
        JMenuItem addFolderMenu = new JMenuItem("Add Folder");
        JMenuItem removePasswordMenu = new JMenuItem("Remove Password");
        JMenuItem removeNoteMenu = new JMenuItem("Remove Note");
        JMenuItem removeFolderMenu = new JMenuItem("Remove Folder");
        ItemMenu.add(addPasswordMenu);
        ItemMenu.add(addNoteMenu);
        ItemMenu.add(addFolderMenu);
        ItemMenu.add(removePasswordMenu);
        ItemMenu.add(removeNoteMenu);
        ItemMenu.add(removeFolderMenu);


        // Settings
        JMenu settingsMenu = new JMenu("Settings");
        JRadioButtonMenuItem resetOn3InvalidLogin = new JRadioButtonMenuItem("Reset in 3 invalid login attemps");
        JMenuItem resetMenu = new JMenuItem("Reset now");
        settingsMenu.add(resetOn3InvalidLogin);
        settingsMenu.add(resetMenu);

        this.add(FileMenu);
        this.add(ItemMenu);
        this.add(settingsMenu);
    }

}

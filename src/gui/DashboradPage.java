package gui;

import dataTypes.Folder;
import dataTypes.Note;
import dataTypes.Password;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

public class DashboradPage extends JPanel {
    JPanel leftPanel = new JPanel();
    JPanel rightPanel = new JPanel();
    JTree folderTree = new JTree();;
    JScrollPane scrollTable;

    public DashboradPage(){

        this.setLayout(new BorderLayout());

        leftPanel.setBackground(Color.red);
        leftPanel.setPreferredSize(new Dimension(200,100));
        leftPanel.setLayout(new GridLayout(1,1));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        rightPanel.setBackground(Color.orange);
        rightPanel.setPreferredSize(new Dimension(100,100));
        rightPanel.setLayout(new GridLayout(1,1));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.CENTER);

    }

    /**
     * Setup elemtents from Model data
     * @param mainFolder
     */
    public void setup(Folder mainFolder) {
        // Folder Tree
        DefaultMutableTreeNode root = Global.convertFolderToTreeNode(mainFolder);
        folderTree = new JTree(root);

        folderTree.setRootVisible(false);
        folderTree.setBackground(Color.LIGHT_GRAY);
        leftPanel.add(folderTree);

        // First show main Passwords
        this.togglePasswordsItem(true, mainFolder.getPasswords());
    }

    /**
     * Megjeleníti a megadott jelszavakat egy táblázatban
     * @param visible látható-e
     * @param passwords jelszavak
     */
    public void togglePasswordsItem(boolean visible, HashSet<Password> passwords){
        if(!visible) {
            return;
        }
        Object[] columnNames = {"Name", "Username", "Password", "Encryption"};
        Object[][] datas = Global.convertPasswordHashSetToObjectArrays(passwords);
        JTable table = new JTable(datas, columnNames);
        table.setEnabled(false);
        scrollTable = new JScrollPane(table);
        rightPanel.add(scrollTable);
    }

    /**
     * Megjeleníti a megadott feljegyzéseket egy táblázatban
     * @param visible látható-e
     * @param notes feljegyzések
     */
    public void toggleNotesItem(boolean visible, HashSet<Note> notes){
        if(!visible) {
            return;
        }
        Object[] columnNames = {"Name", "Note", "Encryption"};
        Object[][] datas = Global.convertNotedHashSetToObjectArrays(notes);
        JTable table = new JTable(datas, columnNames);
        table.setEnabled(false);
        scrollTable = new JScrollPane(table);
        rightPanel.add(scrollTable);
    }
    public void reset(){
        rightPanel.removeAll();
    }

}

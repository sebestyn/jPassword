package gui;

import dataTypes.Folder;
import dataTypes.Note;
import dataTypes.Password;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.HashSet;

public class DashboradPage extends JPanel {
    final JPanel leftPanel = new JPanel();
    final JPanel rightPanel = new JPanel();

    JTree folderTree = new JTree();
    final JButton newFolderButton = new JButton("Új mappa");
    final JButton removeFolderButton = new JButton("Mappa törlése");
    JTable table = new JTable();
    JScrollPane scrollTable = new JScrollPane();
    final JButton newItemButton = new JButton("Új elem");
    final JButton removeItemButton = new JButton("Elem törlése");
    final JTextField searchInput = new JTextField();

    PasswordInputPage passwordInputPage = new PasswordInputPage();
    NoteInputPage noteInputPage = new NoteInputPage();


    public DashboradPage(){

        this.setLayout(new BorderLayout());

        leftPanel.setLayout(new BorderLayout());
        //leftPanel.setBackground(Color.red);
        leftPanel.setPreferredSize(new Dimension(200,100));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JPanel leftSouthPanel = new JPanel(new GridLayout(3,1));
        leftSouthPanel.add(newFolderButton);
        leftSouthPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        leftSouthPanel.add(removeFolderButton);
        leftPanel.add(leftSouthPanel, BorderLayout.SOUTH);

        //rightPanel.setLayout(new GridLayout(1,1));
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(new Color(141, 137, 203));
        rightPanel.setPreferredSize(new Dimension(100,100));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.CENTER);

    }

    /**
     * Megjeleníti a Folder listát
     * @param mainFolder Folder aminek az elemeit megjeleníti
     */
    public void showFolderListItem(Folder mainFolder) {

        if(folderTree.getParent() == leftPanel){
            leftPanel.remove(folderTree);
        }

        // Folder Tree
        DefaultMutableTreeNode root = Global.convertFolderToTreeNode(mainFolder);
        folderTree = new JTree(root);
        leftPanel.add(folderTree, BorderLayout.NORTH);
        leftPanel.repaint();
    }

    private void editTable(Object[] columnNames, Object[][] datas){
        if(scrollTable.getParent() == rightPanel){
            leftPanel.remove(scrollTable);
        }
        // Search input
        JPanel rightNorthPanel = new JPanel(new FlowLayout());
        rightNorthPanel.add(new JLabel("Keresés: "));
        searchInput.setPreferredSize(new Dimension(200,20));
        rightNorthPanel.add(searchInput);
        rightPanel.add(rightNorthPanel, BorderLayout.NORTH);

        // Password-Note Table
        table = new JTable(datas, columnNames);
        table.setAutoCreateRowSorter(true);
        table.setDefaultEditor(Object.class, null);
        table.setShowHorizontalLines(true);
        table.setGridColor(Color.orange);
        table.setRowSelectionAllowed(true);
        scrollTable = new JScrollPane(table);
        rightPanel.add(scrollTable, BorderLayout.CENTER);

        // Buttons
        JPanel itemButtons = new JPanel(new FlowLayout());
        itemButtons.add(newItemButton);
        itemButtons.add(removeItemButton);
        rightPanel.add(itemButtons, BorderLayout.SOUTH);

        rightPanel.repaint();
    }

    /**
     * Megjeleníti a megadott jelszavakat egy táblázatban
     * @param passwords jelszavak
     */
    public void showPasswordsItem(HashSet<Password> passwords){
        Object[] columnNames = {"Name", "Username", "Password", "Encryption"};
        Object[][] datas = Global.convertPasswordHashSetToObjectArrays(passwords);
        editTable(columnNames, datas);
    }

    /**
     * Megjeleníti a megadott feljegyzéseket egy táblázatban
     * @param notes feljegyzések
     */
    public void showNotesItem(HashSet<Note> notes){
        Object[] columnNames = {"Name", "Note", "Encryption"};
        Object[][] datas = Global.convertNotedHashSetToObjectArrays(notes);
        editTable(columnNames, datas);
    }


    public void removeRightPanel() {
        rightPanel.removeAll();
        rightPanel.repaint();
    }

    public void showNewPasswordPage(){
        this.removeRightPanel();
        passwordInputPage = new PasswordInputPage();
        rightPanel.add(passwordInputPage);
    }

    public void showNewNotePage() {
        this.removeRightPanel();
        noteInputPage = new NoteInputPage();
        rightPanel.add(noteInputPage);
    }
}

package gui.View;

import dataTypes.Folder;
import dataTypes.Note;
import dataTypes.Password;
import gui.Global;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.HashSet;

public class DashboradPage extends JPanel {
    final JPanel leftPanel = new JPanel();
    final JPanel rightPanel = new JPanel();

    JTree folderTree = new JTree();
    ScrollPane scrollFolderTree = new ScrollPane();

    DataPasswords dataPasswords;
    DataNotes dataNotes;
    final JButton newFolderButton = new JButton("Új mappa");
    final JButton removeFolderButton = new JButton("Mappa törlése");
    JTable table = new JTable();
    JScrollPane scrollTable = new JScrollPane();
    final JButton newItemButton = new JButton("Új elem");
    final JButton removeItemButton = new JButton("Elem törlése");
    final JTextField searchInput = new JTextField();

    PasswordInputPage passwordInputPage = new PasswordInputPage();
    NoteInputPage noteInputPage = new NoteInputPage();

    public JTree getFolderTree() {
        return folderTree;
    }
    public JButton getNewFolderButton() {
        return newFolderButton;
    }
    public JButton getRemoveFolderButton() {
        return removeFolderButton;
    }
    public JTable getTable() {
        return table;
    }
    public JButton getNewItemButton() {
        return newItemButton;
    }
    public JButton getRemoveItemButton() {
        return removeItemButton;
    }
    public JTextField getSearchInput() {
        return searchInput;
    }
    public PasswordInputPage getPasswordInputPage() {
        return passwordInputPage;
    }
    public NoteInputPage getNoteInputPage() {
        return noteInputPage;
    }
    public DataPasswords getDataPasswords() {
        return dataPasswords;
    }
    public DataNotes getDataNotes() {
        return dataNotes;
    }

    public DashboradPage(){

        this.setLayout(new BorderLayout());

        // Left Panel
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(200,100));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JPanel leftSouthPanel = new JPanel(new GridLayout(4,1));
        leftSouthPanel.add(Box.createRigidArea(new Dimension(0, 1)));
        leftSouthPanel.add(newFolderButton);
        leftSouthPanel.add(Box.createRigidArea(new Dimension(0, 1)));
        leftSouthPanel.add(removeFolderButton);
        leftPanel.add(leftSouthPanel, BorderLayout.SOUTH);

        // Right Panel
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(new Color(141, 137, 203));
        rightPanel.setPreferredSize(new Dimension(100,100));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        showTempOpenFileText();

        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.CENTER);

    }

    public void showTempOpenFileText(){
        JLabel title = new JLabel("Nyiss meg egy fájlt baloldalt kétszer kattintva");
        ImageIcon logo = new ImageIcon("./doc/logo/100x100.png");
        title.setIcon(logo);
        title.setFont(new Font("Calibri", Font.BOLD, 20));
        title.setVerticalTextPosition(JLabel.BOTTOM);
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setBackground(new Color(141, 137, 203));
        title.setOpaque(true);
        title.setVerticalAlignment(JLabel.BOTTOM);
        title.setHorizontalAlignment(JLabel.CENTER);
        this.removeRightPanel();
        rightPanel.add(title, BorderLayout.NORTH);
    }

    /**
     * Megjeleníti a Folder listát
     * @param mainFolder Folder aminek az elemeit megjeleníti
     */
    public void showFolderListItem(Folder mainFolder) {

        if(scrollFolderTree.getParent() == leftPanel){
            leftPanel.remove(scrollFolderTree);
        }

        // Folder Tree
        DefaultMutableTreeNode root = Global.convertFolderToTreeNode(mainFolder);
        folderTree = new JTree(root);
        scrollFolderTree = new ScrollPane();
        scrollFolderTree.add(folderTree);
        leftPanel.add(scrollFolderTree, BorderLayout.CENTER);
        leftPanel.repaint();
    }

    private void showTableTools(){
        rightPanel.removeAll();

        // Search input
        JPanel rightNorthPanel = new JPanel(new FlowLayout());
        rightNorthPanel.add(new JLabel("Keresés: "));
        searchInput.setPreferredSize(new Dimension(200,20));
        rightNorthPanel.add(searchInput);
        rightPanel.add(rightNorthPanel, BorderLayout.NORTH);

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
        showTableTools();
        dataPasswords = new DataPasswords(passwords);
        table = new JTable(dataPasswords);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);
        scrollTable = new JScrollPane(table);
        rightPanel.add(scrollTable, BorderLayout.CENTER);
    }

    /**
     * Megjeleníti a megadott feljegyzéseket egy táblázatban
     * @param notes feljegyzések
     */
    public void showNotesItem(HashSet<Note> notes){
        showTableTools();
        dataNotes = new DataNotes(notes);
        table = new JTable(dataNotes);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);
        scrollTable = new JScrollPane(table);
        rightPanel.add(scrollTable, BorderLayout.CENTER);
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

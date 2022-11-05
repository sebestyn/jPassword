package gui.Controller;

import crypt.CryptType;
import dataTypes.Folder;
import dataTypes.Note;
import dataTypes.Password;
import gui.Model;
import gui.View.DashboradPage;
import gui.View.View;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;

public class DashboardController {
    final Model model;
    final View view;
    final DashboradPage dashboradPage;

    /**
     * Konstruktor a model és a Dashboard view-t összekötő Controllerhez
     * @param model az adatokat és funkciókat tartalmazó model
     * @param view a megjelenítést tartalmaző view
     */
    public DashboardController(Model model, View view) {
        this.model = model;
        this.view = view;
        this.dashboradPage = view.getDashboradPage();

        // Listeners
        dashboradPage.getFolderTree().addTreeSelectionListener(new FolderItemSelectListener());
        dashboradPage.getNewFolderButton().addActionListener(new NewFolderButtonClickListener());
        dashboradPage.getRemoveFolderButton().addActionListener(new RemoveFolderButtonClickListener());
        dashboradPage.getNewItemButton().addActionListener(new NewItemButtonClickListener());
        dashboradPage.getRemoveItemButton().addActionListener(new RemoveItemButtonClickListener());
        dashboradPage.getSearchInput().addKeyListener(new SearchInputKeyListener());
    }


    /**
     * Dashboard page: A Folder fa elem kiválasztása listener
     */
    class FolderItemSelectListener implements TreeSelectionListener {
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            dashboradPage.showTempOpenFileText();
            view.setVisible(true);

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) dashboradPage.getFolderTree().getLastSelectedPathComponent();

            if(node == null){
                return;
            }

            // Get item folder and type
            ArrayList<String> selectedItemPath = new ArrayList<>();
            String selectedItemType = node.toString();
            Folder selectedIFolder = model.getMainFolder();
            while(node.getParent() != null){
                if(node.getParent() instanceof DefaultMutableTreeNode){
                    selectedItemPath.add(0, node.getParent().toString());
                    node = (DefaultMutableTreeNode) node.getParent();
                }
            }
            for(String pathName: selectedItemPath){
                selectedIFolder = selectedIFolder.getFolder(pathName);
            }

            // Ha mappa
            if(!selectedItemType.equals("passwords") && !selectedItemType.equals("notes")){
                model.setActualFolder(selectedIFolder.getFolder(selectedItemType));
                return;
            }

            // Jelszavak kilistazasa
            if(selectedItemType.equals("passwords")){
                dashboradPage.getSearchInput().setText("");
                model.setActualFolder(selectedIFolder);
                HashSet<Password> passwords = selectedIFolder.getPasswords();
                dashboradPage.showPasswordsItem(passwords);
            }

            // Notes kilistazasa
            else {
                dashboradPage.getSearchInput().setText("");
                model.setActualFolder(selectedIFolder);
                HashSet<Note> notes = selectedIFolder.getNotes();
                dashboradPage.showNotesItem(notes);
            }

        }
    }
    /**
     * Dashboard page: A Folder fa listener újra definiállása
     */
    public void resetFolderItemSelectListener(){
        dashboradPage.getFolderTree().addTreeSelectionListener(new FolderItemSelectListener());
    }
    /**
     * Dashboard page: Új mappa gomb megnyomása listener
     */
    class NewFolderButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String folderName = JOptionPane.showInputDialog("Új Folder neve:");
            if(folderName.trim().equals("") || folderName.equals("passwords") || folderName.equals("notes")){
                JOptionPane.showMessageDialog(dashboradPage, "Ilyen nevű mappát nem lehet létrehozni!");
                return;
            }
            model.getActualFolder().addFolder(new Folder(folderName, model.getActualFolder()));
            dashboradPage.showFolderListItem(model.getMainFolder());
            dashboradPage.getFolderTree().addTreeSelectionListener(new FolderItemSelectListener());
            view.setVisible(true);
        }
    }
    /**
     * Dashboard page: Mappa törlése gomb megnyomása listener
     */
    class RemoveFolderButtonClickListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(model.getActualFolder() == model.getMainFolder()){
                JOptionPane.showMessageDialog(dashboradPage, "Ezt a mappát nem lehet törölni!");
                return;
            }
            int biztos = JOptionPane.showConfirmDialog(view, "Biztos törlöd?", "Vigyázz", JOptionPane.YES_NO_OPTION);
            if (biztos == JOptionPane.YES_OPTION){
                model.getActualFolder().getParentFolder().removeFolder(model.getActualFolder());
                dashboradPage.showFolderListItem(model.getMainFolder());
                dashboradPage.getFolderTree().addTreeSelectionListener(new FolderItemSelectListener());
            }
            view.setVisible(true);
        }
    }
    /**
     * Dashboard page: Keresés input listener
     */
    class SearchInputKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }
        @Override
        public void keyPressed(KeyEvent e) {

        }
        @Override
        public void keyReleased(KeyEvent e) {
            String searchInput = dashboradPage.getSearchInput().getText();
            // Get page type
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) dashboradPage.getFolderTree().getLastSelectedPathComponent();
            String pageType = node.toString();
            // Ha Passwords
            if(pageType.equals("passwords")){
                HashSet<Password> filteredPasswords = model.getActualFolder().searchPassword(searchInput);
                dashboradPage.removeRightPanel();
                dashboradPage.showPasswordsItem(filteredPasswords);
                dashboradPage.getSearchInput().requestFocus();
            }

            // Ha Notes
            else if(pageType.equals("notes")){
                HashSet<Note> filteredNotes = model.getActualFolder().searchNote(searchInput);
                dashboradPage.removeRightPanel();
                dashboradPage.showNotesItem(filteredNotes);
                dashboradPage.getSearchInput().requestFocus();
            }

            view.setVisible(true);
        }
    }
    /**
     * Dashboard page: Új elem gomb megnyomása listener
     */
    class NewItemButtonClickListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get page type
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) dashboradPage.getFolderTree().getLastSelectedPathComponent();
            String pageType = node.toString();
            // Ha Passwords
            if(pageType.equals("passwords")){
                dashboradPage.showNewPasswordPage();
                // Listener
                dashboradPage.getPasswordInputPage().getSaveButton().addActionListener(new SaveNewPasswordButtonClickListener());
            }

            // Ha Notes
            else if(pageType.equals("notes")){
                dashboradPage.showNewNotePage();
                // Listener
                dashboradPage.getNoteInputPage().getSaveButton().addActionListener(new SaveNewNoteButtonClickListener());
            }

            view.setVisible(true);
        }
    }
    /**
     * Dashboard page: Elem törlése gomb megnyomása listener
     */
    class RemoveItemButtonClickListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = dashboradPage.getTable().getSelectedRow();
            if(row<0){
                return;
            }
            int actual_row = (dashboradPage.getTable().getRowSorter().convertRowIndexToModel(row));
            // Get page type
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) dashboradPage.getFolderTree().getLastSelectedPathComponent();
            String pageType = node.toString();
            // Ha Passwords
            if(pageType.equals("passwords")){
                String name = dashboradPage.getTable().getModel().getValueAt(actual_row, 0).toString();
                String username = dashboradPage.getTable().getModel().getValueAt(actual_row, 1).toString();
                String password = dashboradPage.getTable().getModel().getValueAt(actual_row, 2).toString();
                String crypt_type_string = String.valueOf(dashboradPage.getTable().getModel().getValueAt(actual_row, 3).toString());
                CryptType crypt_type = CryptType.valueOf(crypt_type_string);
                model.getActualFolder().removePassword(new Password(crypt_type, name, username, password));
                dashboradPage.removeRightPanel();
                dashboradPage.showPasswordsItem(model.getActualFolder().getPasswords());
            }

            // Ha Notes
            else if(pageType.equals("notes")){
                String name = dashboradPage.getTable().getModel().getValueAt(actual_row, 0).toString();
                String note = dashboradPage.getTable().getModel().getValueAt(actual_row, 1).toString();
                String crypt_type_string = String.valueOf(dashboradPage.getTable().getModel().getValueAt(actual_row, 2).toString());
                CryptType crypt_type = CryptType.valueOf(crypt_type_string);
                model.getActualFolder().removeNote(new Note(crypt_type, name, note));
                dashboradPage.removeRightPanel();
                dashboradPage.showNotesItem(model.getActualFolder().getNotes());
            }

            view.setVisible(true);
        }
    }


    /**
     * New Password page: Jelszó mentése gomb listener
     */
    class SaveNewPasswordButtonClickListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = dashboradPage.getPasswordInputPage().getNameInput().getText();
            String username = dashboradPage.getPasswordInputPage().getUsernameInput().getText();
            String password = dashboradPage.getPasswordInputPage().getPasswordInput().getText();
            String crypt_type_string = String.valueOf(dashboradPage.getPasswordInputPage().getCryptType_list().getSelectedItem());
            CryptType crypt_type = CryptType.valueOf(crypt_type_string);

            if(name.equals("") || password.equals("")){
                return;
            }

            Password newPass = new Password(crypt_type, name, username, password);

            dashboradPage.getDataPasswords().addPassword(newPass);

            //model.getActualFolder().addPassword(newPass);
            dashboradPage.removeRightPanel();
            dashboradPage.showPasswordsItem(model.getActualFolder().getPasswords());

            view.setVisible(true);
        }
    }
    /**
     * New Note page: Note mentése gomb listener
     */
    class SaveNewNoteButtonClickListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = dashboradPage.getNoteInputPage().getNameInput().getText();
            String note = dashboradPage.getNoteInputPage().getNoteInput().getText();
            String crypt_type_string = String.valueOf(dashboradPage.getNoteInputPage().getCryptType_list().getSelectedItem());
            CryptType crypt_type = CryptType.valueOf(crypt_type_string);

            if(name.equals("") || note.equals("")){
                return;
            }

            Note newNote = new Note(crypt_type, name, note);

            dashboradPage.getDataNotes().addNote(newNote);

            //model.getActualFolder().addNote(newNote);k
            dashboradPage.removeRightPanel();
            dashboradPage.showNotesItem(model.getActualFolder().getNotes());

            view.setVisible(true);
        }
    }


}

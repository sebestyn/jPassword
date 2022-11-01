package gui;

import crypt.CryptType;
import crypt.CryptoException;
import dataTypes.Folder;
import dataTypes.Note;
import dataTypes.Password;
import org.jetbrains.annotations.NotNull;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashSet;


public class Controller {

    Model model;
    View view;

    /**
     * Konstruktor a model és view összekötő Controller-hez
     * @param model az adatokat és funkciókat tartalmazó model
     * @param view a megjelenítést tartalmaző view
     */
    public Controller(Model model, @NotNull View view){
        this.model = model;
        this.view = view;

        /* Login */
        // Listeners
        view.loginPage.loginButton.addActionListener(new LoginListener());
        view.loginPage.passwordInput.addKeyListener(new LoginPasswordEnterListener());
        // Check: need to change login page to registration mode
        if(model.need_to_regigster()){
            view.loginPage.setToRegistration();
        }
        // Show Login Page
        view.toggleLoginPage(true);

        /* Menu */
        view.menuPanel.saveMenu.addActionListener(new MenuSaveListener());
        view.menuPanel.loadMenu.addActionListener(new MenuLoadListener());
        view.menuPanel.resetMenu.addActionListener(new MenuResetListener());
        view.menuPanel.resetOn3InvalidLogin.addActionListener(new MenuResetAfter3AttempsListener());
    }

    /**
     * Login Page: Jelszó ellenőrzés és program futtatás az eredménytől függően
     */
    private void runLogin() {
        String passwordInput = view.loginPage.getPasswordInput().getText();
        try {
            String successLogin = model.login(passwordInput);
            if(successLogin.equals("success")){
                model.loadData(passwordInput);
                this.runDashboard();
            }
            else if(successLogin.equals("factoryReset")){
                JOptionPane.showMessageDialog(view.loginPage, "3 hibás próbálkozás miatt mindent töröltünk!", "Oh nooo", JOptionPane.ERROR_MESSAGE);
                view.removeAll();
                view.setVisible(false);
                view.dispose();
            }
            else {
                JOptionPane.showMessageDialog(view.loginPage, successLogin, "Oh nooo", JOptionPane.ERROR_MESSAGE);
                view.loginPage.getPasswordInput().setText("");
            }
        }  catch (Exception ex) {
            JOptionPane.showMessageDialog(view.loginPage, ex.getMessage(), "Oh nooo", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Login Page: Jelszó elmentése és továbbhaladás a következő oldalra
     */
    private void runRegistration(){
        String passwordInput = view.loginPage.getPasswordInput().getText();
        try{
            model.register(passwordInput);
            model.loadData(passwordInput);
            this.runDashboard();
        } catch (Exception ex){
            JOptionPane.showMessageDialog(view.loginPage, ex.getMessage(), "Oh nooo", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Login Page: Listener a belépés gombhoz
     */
    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(view.loginPage.getRegistration()){
                runRegistration();
            } else {
                runLogin();
            }
        }
    }
    /**
     * Login Page: Listener a jelszó bemeneten történe enter gomb nyomáshoz
     */
    class LoginPasswordEnterListener implements KeyListener{
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER && view.loginPage.getPasswordInput().getText().length() > 0){
                if(view.loginPage.getRegistration()){
                    runRegistration();
                } else {

                    runLogin();
                }
            }
        }

        @Override
        public void keyTyped(KeyEvent e){}
        @Override
        public void keyReleased(KeyEvent e){}
    }


    /**
     * Dashboard elemek felállítása és futtatása
     */
    private void runDashboard(){
        view.clear();
        view.toggleMenuPanel(true);
        view.toggleDashboardPage(true, model.getMainFolder());

        // Listeners
        view.dashboradPage.folderTree.addTreeSelectionListener(new FolderItemSelectListener());
        view.dashboradPage.newFolderButton.addActionListener(new NewFolderButtonClickListener());
        view.dashboradPage.removeFolderButton.addActionListener(new RemoveFolderButtonClickListener());
        view.dashboradPage.newItemButton.addActionListener(new NewItemButtonClickListener());
        view.dashboradPage.removeItemButton.addActionListener(new RemoveItemButtonClickListener());
        view.dashboradPage.searchInput.addKeyListener(new SearchInputKeyListener());
    }
    /**
     * Dashboard page: A Folder fa elem kiválasztása listener
     */
    class FolderItemSelectListener implements TreeSelectionListener {
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            view.dashboradPage.removeRightPanel();

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) view.dashboradPage.folderTree.getLastSelectedPathComponent();

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
            if(selectedItemType != "passwords" && selectedItemType != "notes"){
                model.setActualFolder(selectedIFolder.getFolder(selectedItemType));
                return;
            }

            // Jelszavak kilistazasa
            if(selectedItemType == "passwords"){
                view.dashboradPage.searchInput.setText("");
                model.setActualFolder(selectedIFolder);
                HashSet<Password> passwords = selectedIFolder.getPasswords();
                view.dashboradPage.showPasswordsItem(passwords);
                return;
            }

            // Notes kilistazasa
            if(selectedItemType == "notes"){
                view.dashboradPage.searchInput.setText("");
                model.setActualFolder(selectedIFolder);
                HashSet<Note> notes = selectedIFolder.getNotes();
                view.dashboradPage.showNotesItem(notes);
                return;
            }

        }
    }
    /**
     * Dashboard page: Új mappa gomb megnyomása listener
     */
    class NewFolderButtonClickListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String folderName = JOptionPane.showInputDialog("Új Folder neve:");
            model.getActualFolder().addFolder(new Folder(folderName, model.getActualFolder()));
            view.dashboradPage.showFolderListItem(model.getMainFolder());
            view.dashboradPage.folderTree.addTreeSelectionListener(new FolderItemSelectListener());
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
                return;
            }
            model.getActualFolder().getParentFolder().removeFolder(model.getActualFolder());
            view.dashboradPage.showFolderListItem(model.getMainFolder());
            view.dashboradPage.folderTree.addTreeSelectionListener(new FolderItemSelectListener());
            view.setVisible(true);
        }
    }
    /**
     * Dashboard page: Keresés input listener
     */
    class SearchInputKeyListener implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {

        }
        @Override
        public void keyPressed(KeyEvent e) {

        }
        @Override
        public void keyReleased(KeyEvent e) {
            String searchInput = view.dashboradPage.searchInput.getText();
            // Get page type
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) view.dashboradPage.folderTree.getLastSelectedPathComponent();
            String pageType = node.toString();
            // Ha Passwords
            if(pageType == "passwords"){
                HashSet<Password> filteredPasswords = model.getActualFolder().searchPassword(searchInput);
                view.dashboradPage.removeRightPanel();
                view.dashboradPage.showPasswordsItem(filteredPasswords);
                view.dashboradPage.searchInput.requestFocus();
            }

            // Ha Notes
            else if(pageType == "notes"){
                HashSet<Note> filteredNotes = model.getActualFolder().searchNote(searchInput);
                view.dashboradPage.removeRightPanel();
                view.dashboradPage.showNotesItem(filteredNotes);
                view.dashboradPage.searchInput.requestFocus();
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
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) view.dashboradPage.folderTree.getLastSelectedPathComponent();
            String pageType = node.toString();
            // Ha Passwords
            if(pageType == "passwords"){
                view.dashboradPage.showNewPasswordPage();
                // Listener
                view.dashboradPage.passwordInputPage.saveButton.addActionListener(new SaveNewPasswordButtonClickListener());
            }

            // Ha Notes
            else if(pageType == "notes"){
                view.dashboradPage.showNewNotePage();
                // Listener
                view.dashboradPage.noteInputPage.saveButton.addActionListener(new SaveNewNoteButtonClickListener());
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
            int row = view.dashboradPage.table.getSelectedRow();
            if(row<0){
                return;
            }
            // Get page type
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) view.dashboradPage.folderTree.getLastSelectedPathComponent();
            String pageType = node.toString();
            // Ha Passwords
            if(pageType == "passwords"){
                String name = view.dashboradPage.table.getModel().getValueAt(row, 0).toString();
                String username = view.dashboradPage.table.getModel().getValueAt(row, 1).toString();
                String password = view.dashboradPage.table.getModel().getValueAt(row, 2).toString();
                String crypt_type_string = String.valueOf(view.dashboradPage.table.getModel().getValueAt(row, 3).toString());
                CryptType crypt_type = CryptType.valueOf(crypt_type_string);
                model.getActualFolder().removePassword(new Password(crypt_type, name, username, password));
                view.dashboradPage.removeRightPanel();
                view.dashboradPage.showPasswordsItem(model.getActualFolder().getPasswords());
            }

            // Ha Notes
            else if(pageType == "notes"){
                String name = view.dashboradPage.table.getModel().getValueAt(row, 0).toString();
                String note = view.dashboradPage.table.getModel().getValueAt(row, 1).toString();
                String crypt_type_string = String.valueOf(view.dashboradPage.table.getModel().getValueAt(row, 2).toString());
                CryptType crypt_type = CryptType.valueOf(crypt_type_string);
                model.getActualFolder().removeNote(new Note(crypt_type, name, note));
                view.dashboradPage.removeRightPanel();
                view.dashboradPage.showNotesItem(model.getActualFolder().getNotes());
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
            String name = view.dashboradPage.passwordInputPage.nameInput.getText();
            String username = view.dashboradPage.passwordInputPage.usernameInput.getText();
            String password = view.dashboradPage.passwordInputPage.passwordInput.getText();
            String crypt_type_string = String.valueOf(view.dashboradPage.passwordInputPage.cryptType_list.getSelectedItem());
            CryptType crypt_type = CryptType.valueOf(crypt_type_string);

            if(name.equals("") || password.equals("")){
                return;
            }

            Password newPass = new Password(crypt_type, name, username, password);

            model.getActualFolder().addPassword(newPass);
            view.dashboradPage.removeRightPanel();
            view.dashboradPage.showPasswordsItem(model.getActualFolder().getPasswords());

            view.setVisible(true);
        }
    }

    /**
     * New Note page: Note mentése gomb listener
     */
    class SaveNewNoteButtonClickListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = view.dashboradPage.noteInputPage.nameInput.getText();
            String note = view.dashboradPage.noteInputPage.noteInput.getText();
            String crypt_type_string = String.valueOf(view.dashboradPage.noteInputPage.cryptType_list.getSelectedItem());
            CryptType crypt_type = CryptType.valueOf(crypt_type_string);

            if(name.equals("") || note.equals("")){
                return;
            }

            Note newNote = new Note(crypt_type, name, note);

            model.getActualFolder().addNote(newNote);
            view.dashboradPage.removeRightPanel();
            view.dashboradPage.showNotesItem(model.getActualFolder().getNotes());

            view.setVisible(true);
        }
    }

    /**
     * Menu: Save data to files listener
     */
    class MenuSaveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                model.saveData();
                JOptionPane.showMessageDialog(view, "Sikeresen elmentve", "Oh yess", JOptionPane.PLAIN_MESSAGE);
                view.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Oh nooo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /**
     * Menu: Load data to files listener
     */
    class MenuLoadListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                model.getMainFolder().makeEmpty();
                model.loadData(model.getMasterPassword().getValue());
                view.dashboradPage.removeRightPanel();
                view.dashboradPage.showFolderListItem(model.getMainFolder());
                view.dashboradPage.folderTree.addTreeSelectionListener(new FolderItemSelectListener());
                view.setVisible(true);
                JOptionPane.showMessageDialog(view, "Sikeresen betöltve", "Oh yess", JOptionPane.PLAIN_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Oh nooo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /**
     * Menu: Factory reset
     */
    class MenuResetListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int biztos = JOptionPane.showConfirmDialog(view, "Biztos törölsz mindent?", "Vigyázz", JOptionPane.YES_NO_OPTION);
            if (biztos == JOptionPane.YES_OPTION) {
                try {
                    model.factoryReset();
                    view.removeAll();
                    view.setVisible(false);
                    view.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, ex.getMessage(), "Oh nooo", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    /**
     * Menu: Factory reset after 3 attemps
     */
    class MenuResetAfter3AttempsListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean isReset = view.menuPanel.resetOn3InvalidLogin.isSelected();
            model.settings.setFactoryReset(isReset);
        }
    }

}

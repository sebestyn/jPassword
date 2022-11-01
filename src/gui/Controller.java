package gui;

import crypt.CryptType;
import dataTypes.Folder;
import dataTypes.Note;
import dataTypes.Password;
import org.jetbrains.annotations.NotNull;

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

    }

    /**
     * Login Page: Jelszó ellenőrzés és program futtatás az eredménytől függően
     */
    private void runLogin() {
        String passwordInput = view.loginPage.getPasswordInput().getText();
        try {
            boolean successLogin = model.login(passwordInput);
            if(successLogin){
                model.loadData(passwordInput);
                this.runDashboard();
            } else {
                JOptionPane.showMessageDialog(view.loginPage, "Helytelen jelszó", "Oh nooo", JOptionPane.ERROR_MESSAGE);
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
                System.out.println(model.getActualFolder().toString());
                return;
            }

            // Jelszavak kilistazasa
            if(selectedItemType == "passwords"){
                HashSet<Password> passwords = selectedIFolder.getPasswords();
                view.dashboradPage.showPasswordsItem(passwords);
                return;
            }

            // Notes kilistazasa
            if(selectedItemType == "notes"){
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
            System.out.println(model.getActualFolder());
            model.getActualFolder().addFolder(new Folder(folderName, model.getActualFolder()));
            view.dashboradPage.showFolderListItem(model.getMainFolder());
            view.dashboradPage.folderTree.addTreeSelectionListener(new FolderItemSelectListener());
            view.setVisible(true);
        }
    }
    /**
     * Dashboard page: Új mappa gomb megnyomása listener
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
     * Dashboard page: Új elem gomb megnyomása listener
     */
    class NewItemButtonClickListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            view.dashboradPage.showNewPasswordPage();
            // Listener
            view.dashboradPage.passwordInputPage.saveButton.addActionListener(new SaveNewPasswordButtonClickListener());

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
            System.out.println(row);
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
                System.out.println("Üres");
                return;
            }

            System.out.println(name);
            System.out.println(username);
            System.out.println(password);
            System.out.println(crypt_type_string);

            Password newPass = new Password(crypt_type, name, username, password);
            System.out.println(newPass);

            model.getActualFolder().addPassword(newPass);
            view.dashboradPage.removeRightPanel();
            view.dashboradPage.showPasswordsItem(model.getActualFolder().getPasswords());

            view.setVisible(true);
        }
    }


}

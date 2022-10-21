package gui;

import dataTypes.Folder;
import dataTypes.Note;
import dataTypes.Password;
import org.jetbrains.annotations.NotNull;

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
        view.addLoginListener(new LoginListener());
        view.addLoginPasswordEnterListener(new LoginPasswordEnterListener());
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
                view.loginPage.setMessage("Helytelen jelszó");
                view.loginPage.getPasswordInput().setText("");
            }
        }  catch (Exception ex) {
            view.loginPage.setMessage(ex.getMessage());
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
            view.loginPage.setMessage(ex.getMessage());
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
        view.toggleDashboardPage(true, model.mainFolder);
        view.addFolderItemSelectListener(new FolderItemSelectListener());
    }
    /**
     * Dashboard page: A Folder fa megjelenítése
     */
    class FolderItemSelectListener implements TreeSelectionListener {
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) view.dashboradPage.folderTree.getLastSelectedPathComponent();

            // Ha mappa -> nem csinál semmit
            if(!node.isLeaf()){
                return;
            }

            // Get item folder and type
            ArrayList<String> selectedItemPath = new ArrayList<>();
            String selectedItemType = node.toString();
            Folder selectedIFolder = model.mainFolder;
            while(node.getParent() != null){
                if(node.getParent() instanceof DefaultMutableTreeNode){
                    selectedItemPath.add(0, node.getParent().toString());
                    node = (DefaultMutableTreeNode) node.getParent();
                }
            }
            for(String pathName: selectedItemPath){
                selectedIFolder = selectedIFolder.getFolder(pathName);
            }

            if(selectedItemType == "passwords"){
                HashSet<Password> passwords = selectedIFolder.getPasswords();
                view.dashboradPage.reset();
                view.dashboradPage.togglePasswordsItem(true, passwords);
            }

            else if(selectedItemType == "notes"){
                HashSet<Note> notes = selectedIFolder.getNotes();
                view.dashboradPage.reset();
                view.dashboradPage.toggleNotesItem(true, notes);
            }

        }
    }
}

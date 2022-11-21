package main.gui.Controller;

import main.gui.Model;
import main.gui.View.LoginPage;
import main.gui.View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginController {
    final Model model;
    final View view;
    final LoginPage loginPage;

    /**
     * Konstruktor a model és a Login view-t összekötő Controllerhez
     * @param model az adatokat és funkciókat tartalmazó model
     * @param view a megjelenítést tartalmaző view
     */
    public LoginController(Model model, View view) {
        this.model = model;
        this.view = view;
        this.loginPage = view.getLoginPage();

        // Listeners
        loginPage.getLoginButton().addActionListener(new LoginListener());
        loginPage.getPasswordInput().addKeyListener(new LoginPasswordEnterListener());

        // Check: need to change login page to registration mode
        if(model.need_to_regigster()){
            loginPage.setToRegistration();
        }

        // Show Login Page
        view.toggleLoginPage(true);
    }

    /**
     * Login Page: Jelszó ellenőrzés és program futtatás az eredménytől függően
     */
    private void runLogin() {
        String passwordInput = loginPage.getPasswordInput().getText();
        try {
            String successLogin = model.login(passwordInput);
            if(successLogin.equals("success")){
                model.loadData(passwordInput);
                this.runDashboard();
            }
            else if(successLogin.equals("factoryReset")){
                JOptionPane.showMessageDialog(loginPage, "3 hibás próbálkozás miatt mindent töröltünk!", "Oh nooo", JOptionPane.ERROR_MESSAGE);
                view.removeAll();
                view.setVisible(false);
                view.dispose();
                System.exit(401);
            }
            else {
                JOptionPane.showMessageDialog(loginPage, successLogin, "Oh nooo", JOptionPane.ERROR_MESSAGE);
                loginPage.getPasswordInput().setText("");
            }
        }  catch (Exception ex) {
            JOptionPane.showMessageDialog(loginPage, ex.getMessage(), "Oh nooo", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Login Page: Jelszó elmentése és továbbhaladás a következő oldalra
     */
    private void runRegistration(){
        String passwordInput = loginPage.getPasswordInput().getText();
        try{
            model.register(passwordInput);
            model.loadData(passwordInput);
            this.runDashboard();
        } catch (Exception ex){
            JOptionPane.showMessageDialog(loginPage, ex.getMessage(), "Oh nooo", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Login Page: Listener a belépés gombhoz
     */
    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(loginPage.getRegistration()){
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
            if (e.getKeyCode() == KeyEvent.VK_ENTER && loginPage.getPasswordInput().getText().length() > 0){
                if(loginPage.getRegistration()){
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
     * Dashboard és Menu elemek felállítása és futtatása
     */
    private void runDashboard(){
        view.clear();
        view.toggleMenuPanel(true);
        view.toggleDashboardPage(true, model.getMainFolder());

        final DashboardController dashboardController = new DashboardController(model, view);
        new MenuController(model, view, dashboardController);

    }

}

package gui;

import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


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

        // Listeners -> Login Page
        view.addLoginListener(new LoginListener());
        view.addLoginPasswordEnterListener(new LoginPasswordEnterListener());

        // Check if need change to registration mode
        if(model.need_to_regigster()){
            view.getLoginPage().setToRegistration();
        }

        // Show Login Page
        view.toggleLoginPage(true);
        view.setVisible(true);

    }

    /**
     * Login Page: Jelszó ellenőrzés és program futtatás az eredménytől függően
     */
    private void runLogin() {
        String passwordInput = view.getLoginPage().getPasswordInput().getText();
        try {
            boolean successLogin = model.login(passwordInput);
            if(successLogin){
                view.toggleLoginPage(false);
                view.toggleDashboardPage(true);
            } else {
                view.getLoginPage().setMessage("Invalid password");
            }
        }  catch (Exception ex) {
            view.getLoginPage().setMessage(ex.getMessage());
        }
    }

    /**
     * Login Page: Jelszó elmentése és továbbhaladás a következő oldalra
     */
    private void runRegistration(){
        String passwordInput = view.getLoginPage().getPasswordInput().getText();
        try{
            model.register(passwordInput);
            view.toggleLoginPage(false);
            view.toggleDashboardPage(true);
        } catch (Exception ex){
            view.getLoginPage().setMessage(ex.getMessage());
        }
    }
    /**
     * Login Page: Listener a belépés gombhoz
     */
    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(view.getLoginPage().getRegistration()){
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
            if (e.getKeyCode() == KeyEvent.VK_ENTER){
                if(view.getLoginPage().getRegistration()){
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
}

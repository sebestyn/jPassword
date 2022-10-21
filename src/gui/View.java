package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class View extends JFrame {

    Model model;
    LoginPage loginPage = new LoginPage();
    MenuPanel menuPanel = new MenuPanel();
    DashboradPage dashboradPage = new DashboradPage();

    /**
     * Konstruktor a View megjelenítő ablakhoz
     * @param model az adatokat és funkciókat tartalmazó model
     */
    public View(Model model){
        // Set model
        this.model = model;
        // Size
        this.setPreferredSize(new Dimension(700,500));
        this.setSize(new Dimension(700,500));
        this.setMinimumSize(new Dimension(400,250));
        // Exit
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Header
        this.setTitle("JPassword");
        ImageIcon logo = new ImageIcon("./doc/logo/trans.png");
        this.setIconImage(logo.getImage());
    }

    /**
     * Az összes elem törlése az ablakból
     */
    public void clear(){
        this.getContentPane().removeAll();
        this.getContentPane().repaint();
    }

    /**
     * Login Page: oldal lathatóságának állítása
     * @param visible true ha látható
     */
    public void toggleLoginPage(boolean visible){
        if(visible){
            this.add(loginPage);
        } else {
            this.remove(loginPage);
        }
        this.setVisible(true);
    }

    /**
     * Login Page: Listener hozzáadása a belépés gombhoz
     * @param lis listener
     */
    public void addLoginListener(ActionListener lis){
        loginPage.getLoginButton().addActionListener(lis);
    }
    /**
     * Login Page: Listener hozzáadása a jelszó bevitel enter gombhoz
     * @param lis listener
     */
    public void addLoginPasswordEnterListener(KeyListener lis){
        loginPage.getPasswordInput().addKeyListener(lis);
    }
    /**
     * Visszaadja a Login Page-t
     * @return
     */
    public LoginPage getLoginPage(){
        return this.loginPage;
    }


    /**
     * Dashboard Page: a dashboard láthatóság állítása
     * @param visible true ha látható
     */
    public void toggleDashboardPage(boolean visible) {
        if(visible){
            this.add(dashboradPage);
        } else {
            this.remove(dashboradPage);
        }
        this.setVisible(true);
    }

    /**
     * Menu: a felső menü láthatóságát állítja
     * @param visible true ha látható
     */
    public void toggleMenuPanel(boolean visible) {
        if(visible){
            this.setJMenuBar(menuPanel);
        } else {
            this.setJMenuBar(null);
        }
        this.setVisible(true);
    }
}

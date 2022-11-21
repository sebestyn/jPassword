package main.gui.View;

import main.gui.dataTypes.Folder;
import main.gui.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class View extends JFrame {

    final Model model;

    final LoginPage loginPage = new LoginPage();
    final MenuPanel menuPanel = new MenuPanel();
    final DashboradPage dashboradPage = new DashboradPage();

    // Get
    public LoginPage getLoginPage() {
        return loginPage;
    }

    public MenuPanel getMenuPanel() {
        return menuPanel;
    }

    public DashboradPage getDashboradPage() {
        return dashboradPage;
    }


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
        // Header
        this.setTitle("JPassword");
        ImageIcon logo = new ImageIcon("./doc/logo/trans.png");
        this.setIconImage(logo.getImage());
        // Exit - save
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                try {
                    model.saveData();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(new JPanel(), e.getMessage(), "Oh nooo", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
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
     * Dashboard Page: a dashboard láthatóság állítása
     *
     * @param visible    true ha látható
     * @param mainFolder Folder amit meg akarunk jeleníteni
     */
    public void toggleDashboardPage(boolean visible, Folder mainFolder) {
        if(visible){
            dashboradPage.showFolderListItem(mainFolder);
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
            menuPanel.init(model.getSettings());
            this.setJMenuBar(menuPanel);
        } else {
            this.setJMenuBar(null);
        }
        this.setVisible(true);
    }


}

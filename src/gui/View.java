package gui;

import crypt.CryptoException;
import dataTypes.Folder;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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
        // Header
        this.setTitle("JPassword");
        ImageIcon logo = new ImageIcon("./doc/logo/trans.png");
        this.setIconImage(logo.getImage());
        // Exit - save data
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
     * @param mainFolder
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
            this.setJMenuBar(menuPanel);
        } else {
            this.setJMenuBar(null);
        }
        this.setVisible(true);
    }


}

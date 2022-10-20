package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JPanel  {
    JTextField passwordInput;
    JButton loginButton;
    JLabel messageLabel;
    boolean registration = false;

    /**
     * Login Page konstruktora: beállítja az alap megjelenítést
     */
    public LoginPage(){
        // Title
        JLabel title = new JLabel("JPassword");
        ImageIcon logo = new ImageIcon("./doc/logo/100x100.png");
        title.setIcon(logo);
        title.setFont(new Font("Calibri", Font.BOLD, 30));
        title.setVerticalTextPosition(JLabel.BOTTOM);
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setBackground(new Color(141, 137, 203));
        title.setOpaque(true);
        title.setVerticalAlignment(JLabel.BOTTOM);
        title.setHorizontalAlignment(JLabel.CENTER);

        // Password Input, Button
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        loginButton = new JButton("Belépés");
        loginButton.setPreferredSize(new Dimension(150, 30));
        passwordInput = new JTextField("", 20);
        messageLabel = new JLabel("");
        inputPanel.add(passwordInput);
        inputPanel.add(loginButton);
        inputPanel.add(messageLabel);
        inputPanel.setBackground(new Color(197, 115, 160));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10,250,1,250));

        // Login message
//        JPanel messagePanel = new JPanel();
//        messagePanel.setLayout(new FlowLayout());
//        messagePanel.setBackground(new Color(121, 201, 133));

        // Main Login Panel
        this.setLayout(new GridLayout(2,1));
        this.add(title);
        this.add(inputPanel);
        //this.add(messagePanel);
    }

    /**
     * Visszaadja a belépés gombot
     * @return belépés gomb
     */
    JButton getLoginButton(){
        return loginButton;
    }
    /**
     * Visszaadja a jelszó bemenet mezőt
     * @return jelszó bemenet
     */
    public JTextField getPasswordInput() {
        return passwordInput;
    }
    /**
     * Beállítja az üzenet mezőt
     * @param message üzenet
     */
    public void setMessage(String message) {
            messageLabel.setText(message);
    }

    /**
     * Atállítja a felületet Regisztrtációhoz
     */
    public void setToRegistration() {
        registration = true;
        loginButton.setText("Regisztráció");
    }

    /**
     * Visszaadja regisztráció módban van-e a Login Page
     * @return regisztráció módban van-e
     */
    public boolean getRegistration(){
        return this.registration;
    }
}

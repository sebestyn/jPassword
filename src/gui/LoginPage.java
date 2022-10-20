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
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        JLabel title = new JLabel("JPassword", SwingConstants.CENTER);
        // ImageIcon logo = new ImageIcon("./doc/logo/logo.png)");
        // title.setIcon(logo);
        title.setFont(new Font("Calibri", Font.BOLD, 30));
        titlePanel.add(title);
        titlePanel.setBackground(new Color(100,120,180));

        // Password Input, Button
        JPanel inputPanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        loginButton = new JButton("Belépés");
        passwordInput = new JTextField("", 20);
        inputPanel.add(passwordInput);
        inputPanel.add(loginButton);
        inputPanel.setBackground(new Color(197, 115, 160));

        // Login message
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new FlowLayout());
        messageLabel = new JLabel("");
        messagePanel.add(messageLabel);
        messagePanel.setBackground(new Color(121, 201, 133));

        // Main Login Panel
        this.setLayout(new GridLayout(3,1));
        this.add(titlePanel);
        this.add(inputPanel);
        this.add(messagePanel);
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

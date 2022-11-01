package gui;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends JPanel  {
    final JTextField passwordInput;
    final JButton loginButton;
    boolean registration = false;

    /**
     * Login Page konstruktora: beállítja az alap elemeket megjelenítést
     */
    public LoginPage(){
        // Title
        JLabel title = new JLabel("JPassword");
        ImageIcon logo = new ImageIcon("./doc/logo/100x100.png");
        title.setIcon(logo);
        title.setFont(new Font("Calibri", Font.BOLD, 40));
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
        passwordInput = new JPasswordField("", 15);
        passwordInput.setFont(new Font("Calibri", Font.PLAIN, 20));
        passwordInput.setHorizontalAlignment(JTextField.CENTER);
        inputPanel.add(passwordInput);
        inputPanel.add(loginButton);
        inputPanel.setBackground(new Color(197, 115, 160));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10,250,1,250));

        // Main Login Panel
        this.setLayout(new GridLayout(2,1));
        this.add(title);
        this.add(inputPanel);
    }

    /**
     * Visszaadja a jelszó bemenet mezőt
     * @return jelszó bemenet
     */
    public JTextField getPasswordInput() {
        return passwordInput;
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

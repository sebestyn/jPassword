package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage  {
    JFrame frame;
    JLabel title = new JLabel("JPassword");
    JButton loginButton = new JButton("Belépés");
    JTextField passwordInput = new JTextField("", 20);


    public LoginPage(JFrame frame){
        this.frame = frame;

        JPanel pTitle = new JPanel();
        pTitle.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        pTitle.setLayout(new FlowLayout());
        title.setFont(new Font("Calibri", Font.BOLD, 30));
        pTitle.add(title);

        JPanel pInput = new JPanel();
        pInput.add(passwordInput);
        loginButton.addActionListener(e -> loginButtonClicked(e));
        pInput.add(loginButton);

        JPanel pLogin = new JPanel(new BorderLayout());
        pLogin.add(pTitle, BorderLayout.NORTH);
        pLogin.add(pInput, BorderLayout.CENTER);

        frame.add(pLogin, BorderLayout.CENTER);
        frame.pack();
    }


    public void show() {
        frame.setVisible(true);
    }

    public void loginButtonClicked(ActionEvent e) {
        String passwordInputText = passwordInput.getText();
        passwordInput.setText("");
        System.out.println(passwordInputText);


    }

}

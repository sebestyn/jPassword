package gui;

import javax.swing.*;
import java.awt.*;

public class GUI {

    private final JFrame frame;

    public GUI(){
        // Create frame
        frame = init_JFrame();
    }


    private JFrame init_JFrame(){
        JFrame frame = new JFrame();
        // Size
        frame.setPreferredSize(new Dimension(800,500));
        frame.setMinimumSize(new Dimension(400,250));
        // Info
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("JPassword");
        return frame;
    }

    public void showAuthPage() {
        LoginPage loginPage = new LoginPage(frame);
        loginPage.show();

    }



}

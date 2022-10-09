package gui;

import javax.swing.*;
import java.awt.*;

public class GUI {

    private JFrame frame;
    private JPanel panel;

    public GUI(){
        // Create panel
        panel = init_JPanel();
        panel.add(new Button("Click me"));

        // Create frame and add panel to it
        frame = init_JFrame(panel);
    }

    private JPanel init_JPanel(){
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        panel.setLayout(new GridLayout(0, 1));
        return panel;
    }

    private JFrame init_JFrame(JPanel panel){
        JFrame frame = new JFrame();
        // Size
        frame.setPreferredSize(new Dimension(800,500));
        frame.setMinimumSize(new Dimension(400,250));
        frame.pack();
        // Info
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("JPassword");
        // Add Panel and make visible
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
        //frame.setState(Frame.ICONIFIED);
        //frame.setState(Frame.NORMAL);
        return frame;
    }
}

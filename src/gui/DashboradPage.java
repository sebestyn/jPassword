package gui;

import javax.swing.*;
import java.awt.*;

public class DashboradPage extends JPanel {


    public DashboradPage(){

        this.setLayout(new BorderLayout());

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();

        panel1.setBackground(Color.cyan);
        panel2.setBackground(Color.red);
        panel3.setBackground(Color.green);
        panel4.setBackground(Color.blue);
        panel5.setBackground(Color.orange);

        panel1.setPreferredSize(new Dimension(100,10));
        panel2.setPreferredSize(new Dimension(200,100));
        panel3.setPreferredSize(new Dimension(100,10));
        panel4.setPreferredSize(new Dimension(10,100));
        panel5.setPreferredSize(new Dimension(100,100));

        this.add(panel1, BorderLayout.NORTH);
        this.add(panel2, BorderLayout.WEST);
        this.add(panel3, BorderLayout.SOUTH);
        this.add(panel4, BorderLayout.EAST);
        this.add(panel5, BorderLayout.CENTER);
    }

}

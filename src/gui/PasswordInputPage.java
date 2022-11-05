package gui;

import crypt.CryptType;

import javax.swing.*;
import java.awt.*;
import java.util.stream.Stream;

public class PasswordInputPage extends JPanel {

    final JTextField nameInput = new JTextField();
    final JTextField usernameInput = new JTextField();
    final JTextField passwordInput = new JTextField();
    JComboBox<String> cryptType_list;
    final JButton saveButton = new JButton("Mentés");

    public PasswordInputPage() {

        this.setLayout(new GridLayout(6,1));

        JPanel sor1 = new JPanel(new FlowLayout());
        sor1.add(new JLabel("Név*"));
        nameInput.setPreferredSize(new Dimension(150, 20));
        sor1.add(nameInput);
        this.add(sor1);

        JPanel sor2 = new JPanel(new FlowLayout());
        sor2.add(new JLabel("Felhasználó név:"));
        usernameInput.setPreferredSize(new Dimension(150, 20));
        sor2.add(usernameInput);
        this.add(sor2);

        JPanel sor3 = new JPanel(new FlowLayout());
        sor3.add(new JLabel("Jelszó*:"));
        passwordInput.setPreferredSize(new Dimension(150, 20));
        sor3.add(passwordInput);
        this.add(sor3);

        JPanel sor4 = new JPanel(new FlowLayout());
        String[] crypt_types = Stream.of(CryptType.values()).map(Enum::name).toArray(String[]::new);
        if(crypt_types.length>0){
            cryptType_list = new JComboBox<>(crypt_types);
            sor4.add(cryptType_list);
        }
        this.add(sor4);

        JPanel sor5 = new JPanel(new FlowLayout());
        sor5.add(saveButton);
        this.add(sor5);

    }

}

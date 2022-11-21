package main.gui.View;

import crypt.CryptType;

import javax.swing.*;
import java.awt.*;
import java.util.stream.Stream;

public class NoteInputPage extends JPanel{

    public JTextField getNameInput() {
        return nameInput;
    }

    public JTextField getNoteInput() {
        return noteInput;
    }

    public JComboBox<String> getCryptType_list() {
        return cryptType_list;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    final JTextField nameInput = new JTextField();
    final JTextField noteInput = new JTextField();
    JComboBox<String> cryptType_list;
    final JButton saveButton = new JButton("Mentés");

    public NoteInputPage() {

        this.setLayout(new GridLayout(6,1));

        JPanel sor1 = new JPanel(new FlowLayout());
        sor1.add(new JLabel("Név*"));
        nameInput.setPreferredSize(new Dimension(150, 20));
        sor1.add(nameInput);
        this.add(sor1);

        JPanel sor2 = new JPanel(new FlowLayout());
        sor2.add(new JLabel("Leírás*:"));
        noteInput.setPreferredSize(new Dimension(150, 20));
        sor2.add(noteInput);
        this.add(sor2);

        JPanel sor3 = new JPanel(new FlowLayout());
        String[] crypt_types = Stream.of(CryptType.values()).map(Enum::name).toArray(String[]::new);
        if(crypt_types.length>0){
            cryptType_list = new JComboBox<>(crypt_types);
            sor3.add(cryptType_list);
        }
        this.add(sor3);

        JPanel sor4 = new JPanel(new FlowLayout());
        sor4.add(saveButton);
        this.add(sor4);

    }

}

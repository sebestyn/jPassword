package gui;

import dataTypes.Folder;
import dataTypes.Note;
import dataTypes.Password;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.HashSet;

public class Global {

    /**
     * Folder elemeit átkonvertálja DefaultMutableTreeNode elemekké és egymásba ágyazza
     * @param mainFolder Folder elemekkel
     * @return átkonvertált elem
     */
    public static DefaultMutableTreeNode convertFolderToTreeNode(Folder mainFolder){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(mainFolder.getName());
        // Passwords and notes
        root.add(new DefaultMutableTreeNode("passwords"));
        root.add(new DefaultMutableTreeNode("notes"));
        // Folders in folder
        for(Folder folder : mainFolder.getFolders()){
            root.add(convertFolderToTreeNode(folder));
        }
        return root;
    }

    public static Object[][] convertPasswordHashSetToObjectArrays(HashSet<Password> passwords){
        Object[][] arr = new Object[passwords.size()][4];

        int i = 0;
        for(Password pass: passwords){
            String[] sor = {pass.getName(), pass.getUsername(), pass.getPassword(), pass.getCryptType().toString()};
            arr[i] = sor;
            i++;
        }

        return arr;

    }


    public static Object[][] convertNotedHashSetToObjectArrays(HashSet<Note> notes){
        Object[][] arr = new Object[notes.size()][4];

        int i = 0;
        for(Note note: notes){
            String[] sor = {note.getName(), note.getNote(), note.getCryptType().toString()};
            arr[i] = sor;
            i++;
        }

        return arr;

    }
}

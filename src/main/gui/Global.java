package main.gui;

import main.gui.dataTypes.Folder;

import javax.swing.tree.DefaultMutableTreeNode;

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

}

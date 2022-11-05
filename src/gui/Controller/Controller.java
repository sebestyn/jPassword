package gui.Controller;

import crypt.CryptType;
import dataTypes.Folder;
import dataTypes.Note;
import dataTypes.Password;
import gui.Model;
import gui.View.DashboradPage;
import gui.View.View;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;


public class Controller {

    final Model model;
    final View view;

    /**
     * Konstruktor a model és view összekötő Controller-hez
     * @param model az adatokat és funkciókat tartalmazó model
     * @param view a megjelenítést tartalmaző view
     */
    public Controller(Model model, @NotNull View view){
        this.model = model;
        this.view = view;
        new LoginController(model, view);
    }
}

package gui.Controller;

import gui.Model;
import gui.View.View;

public class Controller {
    /**
     * Konstruktor a model és view összekötő Controller-hez
     * @param model az adatokat és funkciókat tartalmazó model
     * @param view a megjelenítést tartalmaző view
     */
    public Controller(Model model, View view){
        new LoginController(model, view);
    }
}

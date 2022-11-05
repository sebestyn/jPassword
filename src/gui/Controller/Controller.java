package gui.Controller;

import gui.Model;
import gui.View.View;
import org.jetbrains.annotations.NotNull;


public class Controller {
    /**
     * Konstruktor a model és view összekötő Controller-hez
     * @param model az adatokat és funkciókat tartalmazó model
     * @param view a megjelenítést tartalmaző view
     */
    public Controller(Model model, @NotNull View view){
        new LoginController(model, view);
    }
}

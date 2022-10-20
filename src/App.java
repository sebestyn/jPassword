import gui.Controller;
import gui.Model;
import gui.View;

// https://stackoverflow.com/questions/5831388/what-is-the-controller-in-java-swing
public class App {
    Model model;
    View view;
    Controller controller;

    public void runGUI(){

        model = new Model();
        view = new View(model);
        controller = new Controller(model, view);

        view.setVisible(true);
    }

    public void runConsole(){

    }
}

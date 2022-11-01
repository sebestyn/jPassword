import gui.Controller;
import gui.Model;
import gui.View;

import java.io.IOException;

// https://stackoverflow.com/questions/5831388/what-is-the-controller-in-java-swing
public class App {
    Model model;
    View view;
    Controller controller;

    public void runGUI() throws IOException {
        model = new Model();
        view = new View(model);
        controller = new Controller(model, view);
    }

    public void runConsole(){
        try {
            ConsoleApp consoleApp = new ConsoleApp(model);
            consoleApp.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

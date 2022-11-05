import gui.Controller.Controller;
import gui.Model;
import gui.View.View;

import java.io.IOException;

// https://stackoverflow.com/questions/5831388/what-is-the-controller-in-java-swing
public class App {
    Model model;

    public void runGUI() throws IOException {
        model = new Model();
        View view = new View(model);
        new Controller(model, view);
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

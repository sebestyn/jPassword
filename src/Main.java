import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        App mainApp = new App();
        try {
            mainApp.runGUI();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mainApp.runConsole();
    }
}
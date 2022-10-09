import java.io.IOException;

import crypt.Crypt;
import io.IO;
import gui.GUI;

public class Main {
    public static void main(String[] args) throws IOException {

        new GUI();

        Crypt cr = new Crypt();
        String pelda = cr.encrypt("alma");

        IO io = new IO();
        io.writeFile("pelda.txt", pelda);

        System.out.println(pelda);
        System.out.println("Hello world!");


    }
}
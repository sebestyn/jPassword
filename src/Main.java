import java.io.IOException;

import crypt.Cryption;
import io.IO;
import gui.GUI;

public class Main {
    public static void main(String[] args) throws IOException {

        new GUI();

        Cryption cr = new Cryption();
        String pelda = cr.encrypt("alma");

        IO io = new IO();
        io.writeFile("pelda.txt", pelda);

        System.out.println(pelda);
        System.out.println("Hello world!");


    }
}
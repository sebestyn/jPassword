import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedWriter w = new BufferedWriter(new FileWriter("./doc/secret.txt"));
        w.write("teststtttttttttttttt");
        w.close();

        System.out.println("Hello world!");


    }
}
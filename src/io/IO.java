package io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class IO {
    public void writeFile(String fileName, String message) throws IOException {

        String path = "./data/" + fileName;

        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(message);
        writer.close();

    }
}

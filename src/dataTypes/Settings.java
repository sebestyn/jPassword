package dataTypes;

import java.io.*;
import java.util.Scanner;

public class Settings {
    private final String filePath;
    private boolean factoryReset = false;

    public Settings(String path){ this.filePath = path + "/settings.txt"; }

    public boolean getFactoryReset(){
        return this.factoryReset;
    }

    public void save() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath));
        writer.write(String.valueOf(this.factoryReset));
        writer.close();
    }

    public void load() throws IOException {
        try {
            Scanner reader = new Scanner(new File(this.filePath));
            if(reader.hasNextLine()){
                this.factoryReset = reader.nextLine().equals("true");
            }
            reader.close();
        } catch (FileNotFoundException e) {
            this.save();
        }


    }

    public void setFactoryReset(boolean isReset) {
        this.factoryReset = isReset;
    }
}

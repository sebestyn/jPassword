package io;

import crypt.CryptType;
import dataTypes.MasterPassword;

import java.io.*;

/**
 * A Mester-jelszóhoz tartozó Input/Output metódusokat tartalmazó osztály
 */
public class MasterPasswordIO{
    String filePath = "data/master.encrypt";
    public MasterPasswordIO(){}
    public MasterPasswordIO(String filePath){this.filePath = filePath;}

    /**
     * Mester-jelszó elmentése fájlba
     * @param password Mester-jelszó
     */
    public void savePassword(CryptType cryptType, String password) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath));
        writer.write(cryptType.toString());
        writer.newLine();
        writer.write(password);
        writer.close();
    }

    /**
     * Mester-jelszó beolvasása fájlból
     * @return Mester-jelszó
     */
    public MasterPassword loadPassword() throws IOException, IllegalArgumentException {
        BufferedReader reader = new BufferedReader(new FileReader(this.filePath));
        CryptType cryptType = CryptType.valueOf(reader.readLine());
        String masterPassword = reader.readLine();
        reader.close();
        return new MasterPassword(cryptType, masterPassword);
    }

    /**
     * Mester-jelszó már létezik-e
     * @return true ha mér létezik
     */
    public boolean isAlreadyExist() {
        try {
            MasterPassword masterPassword = loadPassword();
            return masterPassword.getValue() != null && !masterPassword.getValue().trim().isEmpty();
        } catch (IOException e) {
            return false;
        }
    }
}

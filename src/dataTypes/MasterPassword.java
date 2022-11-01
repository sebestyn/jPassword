package dataTypes;

import crypt.Crypt;
import crypt.CryptType;
import crypt.CryptoException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MasterPassword {
    final CryptType cryptType = CryptType.SHA256;
    final String filePath;
    String value;

    public MasterPassword(String path){ this.filePath = path + "/master.encrypt"; }
    public MasterPassword(String path, String value){
        this.filePath = path + "/master.encrypt";
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Mester-jelszó elmentése fájlba
     */
    public void savePassword() throws IOException, CryptoException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath));
        writer.write(Crypt.encrypt(this.cryptType, this.value));
        writer.close();
    }

    /**
     * Mester-jelszó beolvasása fájlból
     *
     * @return Mester-jelszó
     */
    public String loadPassword() throws IOException, IllegalArgumentException {
        BufferedReader reader = new BufferedReader(new FileReader(this.filePath));
        String hashedPassword = reader.readLine();
        reader.close();
        return hashedPassword;
    }

    /**
     * Megnézi a Mester-jelszó már létezik-e
     *
     * @return true ha mér létezik
     */
    public boolean isAlreadyExist() {
        try {
            String hashedPassword = loadPassword();
            return hashedPassword != null && !hashedPassword.trim().isEmpty();
        } catch (IOException e) {
            return false;
        }
    }

    public boolean isInputEqualsHash(String input, String hash) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, CryptoException, InvalidKeyException, InvalidAlgorithmParameterException {
        return Crypt.encrypt(CryptType.SHA256, input).equals(hash);
    }
    /**
     * Mester-jelszó törlése (egész fájl-t törli). Vigyázz: új jelszó létrehozásakor elvesznek a mentett jeslzavak
     */
    public void removePassword() {
        this.value = null;
        File mpFile = new File(filePath);
        boolean successRemove = mpFile.delete();
        System.out.println(successRemove);
    }


}





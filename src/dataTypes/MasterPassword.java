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
import java.security.spec.InvalidKeySpecException;

public class MasterPassword {
    private final CryptType cryptType = CryptType.SHA256;
    final String filePath = "data/master.encrypt";
    private String value;

    public MasterPassword(){}
    public MasterPassword(String value){
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
    public void savePassword() throws IOException, CryptoException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
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

    public boolean isInputEqualsHash(String input, String hash) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, CryptoException, InvalidKeyException, InvalidAlgorithmParameterException {
        return Crypt.encrypt(CryptType.SHA256, input).equals(hash);
    }
    /**
     * Mester-jelszó törlése (egész fájl-t törli). Vigyázz: új jelszó létrehozásakor elvesznek a mentett jeslzavak
     */
    public boolean removePassword() {
        this.value = null;
        File mpFile = new File(filePath);
        return mpFile.delete();
    }


}





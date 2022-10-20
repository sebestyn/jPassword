package gui;

import crypt.CryptoException;
import dataTypes.Folder;
import dataTypes.MasterPassword;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Model {

    String mainFolderPath = "./data";
    MasterPassword masterPassword;
    Folder mainFolder;
    Folder actualFolder;

    /**
     * Az adatokat és funkciókat tartalmazó Model konstruktor
     */
    public Model(){
        masterPassword = new MasterPassword(mainFolderPath);
        mainFolder= new Folder("mainFolder", null);
        actualFolder = mainFolder;
    }

    /**
     * Visszaadja kell-e regisztrálni vagy már regisztrált régebben
     * @return kell-e regisztrálni
     */
    public boolean need_to_regigster(){
        return !masterPassword.isAlreadyExist();
    }

    /**
     * Belépés mester jelszó ellenőrzéssel
     * @param passwordInput megadott meseter jelszó
     * @return helyes-e a mester jelszó
     */
    public boolean login(String passwordInput) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, CryptoException, InvalidKeyException, IOException {
        String passwordHash = masterPassword.loadPassword();
        return masterPassword.isInputEqualsHash(passwordInput, passwordHash);
    }

    /**
     * Regisztráció: mester jelszó elmentése
     * @param passwordInput mester jelszó
     */
    public void register(String passwordInput) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, CryptoException, InvalidKeyException {
        masterPassword.setValue(passwordInput);
        masterPassword.savePassword();
    }

}

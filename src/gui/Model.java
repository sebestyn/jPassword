package gui;

import crypt.Crypt;
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

    private String mainFolderPath = "./data";
    private MasterPassword masterPassword;
    private Folder mainFolder;
    private Folder actualFolder;

    /**
     * Az adatokat és funkciókat tartalmazó Model konstruktor
     */
    public Model(){
        masterPassword = new MasterPassword(mainFolderPath);
        mainFolder= new Folder("mainFolder", null);
        actualFolder = mainFolder;
    }

    public String getMainFolderPath() { return mainFolderPath; }
    public MasterPassword getMasterPassword() { return masterPassword; }
    public Folder getMainFolder() { return mainFolder; }
    public Folder getActualFolder() { return actualFolder; }
    public void setActualFolder(Folder f) { actualFolder = f; }

    /**
     * Adatok betöltése és dekódolása fájlokból
     * @param passwordInput
     */
    public void loadData(String passwordInput) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, BadPaddingException, CryptoException, InvalidKeyException {
        this.masterPassword.setValue(passwordInput);
        // Kriprográfia függvények beállítása a mester jelszóval
        Crypt.init(masterPassword);
        // Adatok betoltese a mainFolder-ből
        mainFolder.load(mainFolderPath);
    }

    /**
     * Adatok kódolása és kimentése fájlokba
     */
    public void saveData() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, BadPaddingException, CryptoException, InvalidKeyException {
        mainFolder.save(mainFolderPath);
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

    /**
     * Minden adat törlése
     */
    public void factoryReset() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, IOException, BadPaddingException, CryptoException, InvalidKeyException {
        mainFolder.makeEmpty();
        this.saveData();
        this.masterPassword.removePassword();
    }
}

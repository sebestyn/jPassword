package gui;

import crypt.Crypt;
import crypt.CryptoException;
import dataTypes.Folder;
import dataTypes.MasterPassword;
import dataTypes.Settings;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Model {
    final Settings settings;
    private final String mainFolderPath = "./data";
    private final MasterPassword masterPassword;
    private final Folder mainFolder;
    private Folder actualFolder;
    private int loginAttemp = 0;

    /**
     * Az adatokat és funkciókat tartalmazó Model konstruktor
     */
    public Model() throws IOException {
        settings = new Settings(mainFolderPath);
        settings.load();
        masterPassword = new MasterPassword(mainFolderPath);
        mainFolder= new Folder("mainFolder", null);
        actualFolder = mainFolder;
    }

    public String getMainFolderPath() { return mainFolderPath; }
    public MasterPassword getMasterPassword() { return masterPassword; }
    public Folder getMainFolder() { return mainFolder; }
    public Folder getActualFolder() { return actualFolder; }
    public void setActualFolder(Folder f) { actualFolder = f; }
    public Settings getSettings() {
        return settings;
    }

    /**
     * Adatok betöltése és dekódolása fájlokból
     * @param passwordInput Jelszo ami kikódolja az adatot
     */
    public void loadData(String passwordInput) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, BadPaddingException, InvalidKeyException {
        this.masterPassword.setValue(passwordInput);
        // Kriprográfia függvények beállítása a mester jelszóval
        Crypt.init(masterPassword);
        // Adatok betoltese a mainFolder-ből
        mainFolder.load(mainFolderPath);
        // Beállítások betöltése

    }

    /**
     * Adatok kódolása és kimentése fájlokba
     */
    public void saveData() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, BadPaddingException, CryptoException, InvalidKeyException {
        // Save settings
        settings.save();
        // Save data
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
    public String login(String passwordInput) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, CryptoException, InvalidKeyException, IOException {
        loginAttemp += 1;
        String passwordHash = masterPassword.loadPassword();
        if(masterPassword.isInputEqualsHash(passwordInput, passwordHash)){
            return "success";
        }
        else if(loginAttemp>=3 && settings.getFactoryReset()){
            this.factoryReset();
            return "factoryReset";
        }
        else if(settings.getFactoryReset()){
            return "Helytelen jelszó (törlés "+ (3-loginAttemp) +" próbálkozás után)";
        }
        return "Helytelen jelszó";
    }

    /**
     * Regisztráció: mester jelszó elmentése
     * @param passwordInput mester jelszó
     */
    public void register(String passwordInput) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, CryptoException, InvalidKeyException {
        masterPassword.setValue(passwordInput);
        masterPassword.savePassword();
    }

    /**
     * Minden adat törlése
     */
    public void factoryReset() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, CryptoException, InvalidKeyException {
        mainFolder.makeEmpty();
        this.saveData();
        this.masterPassword.removePassword();
    }
}

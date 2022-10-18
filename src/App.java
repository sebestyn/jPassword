import crypt.CryptoException;
import dataTypes.Folder;
import dataTypes.MasterPassword;
import gui.GUI;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class App {
    String mainFolderPath = "./data";
    MasterPassword masterPassword;
    Folder mainFolder;
    Folder actualFolder;

    public void init(){
        masterPassword = new MasterPassword(mainFolderPath);
        mainFolder= new Folder("mainFolder", null);
        actualFolder = mainFolder;
    }

    public void init(String mainFolderPath){
        this.mainFolderPath = mainFolderPath;
        this.init();
    }

    public void register(String passwordInput) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, CryptoException, InvalidKeyException {
        masterPassword.setValue(passwordInput);
        masterPassword.savePassword();
    }

    public boolean login(String passwordInput, String passwordHash) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, CryptoException, InvalidKeyException {
        return masterPassword.isInputEqualsHash(passwordInput, passwordHash);
    }

    public void runGUI(){
        GUI mainGUI = new GUI();
        mainGUI.showAuthPage();

    }

    public void runConsole(){

    }
}

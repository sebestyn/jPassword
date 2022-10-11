import crypt.AES;
import crypt.Crypt;
import crypt.CryptType;
import crypt.CryptoException;
import dataTypes.Folder;
import dataTypes.MasterPassword;
import dataTypes.Password;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class MainConsole {
    static MasterPassword masterPassword = new MasterPassword();
    static Folder mainFolder = new Folder("mainFolder");
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private static void auth() throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Regisztráció: nincs még mester jelszó
        if(!masterPassword.isAlreadyExist()){
            try {
                System.out.print("Adj meg egy mester jelszot: ");
                String typedPassword = reader.readLine();
                masterPassword.setValue(typedPassword);
                masterPassword.savePassword();
                System.out.println("Jelszó sikeresen elmentve!");
            } catch (Exception e) {
                System.out.println("Could not save password: " + e.getMessage());
                System.exit(1);
            }
        }

        // Belépés: már van mester jelszó
        else {
            try {
                System.out.print("Belépéshez adja meg a jelszót: ");
                String typedPassword = reader.readLine();
                String loadedHash = masterPassword.loadPassword();

                while(!masterPassword.isInputEqualsHash(typedPassword, loadedHash)){
                    System.out.print("Hibás jelszó! Add meg ujra: ");
                    typedPassword = reader.readLine();
                }
                masterPassword.setValue(typedPassword);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
            System.out.println("Sikeresen belépve!");
        }

        AES.generateKey(masterPassword);

    }
    
    public static void main(String[] args) throws IOException {

        // Login / register
        try {
            auth();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println();

        // Pelda jelszavak
        mainFolder.addPassword(new Password("tiktok.com", "jelszo123"));
        mainFolder.addPassword(new Password("google.com", "nagyonJoJelszo!"));
        try {
            mainFolder.save();
            mainFolder.load("data");
        } catch (Exception e) {
            System.err.println(e);
        }

        menuloop:
        while(true){
            // Menü
            System.out.printf("list -> jelszavak listaja %nnew -> uj jelszo %nmp-rm -> mester jelszo torles");
            switch (reader.readLine()){
                case "list":
                    break;
                case "new":
                    break;
                case "mp-rm":
                    masterPassword.removePassword();
                    System.exit(1);
                    break;
                case "exit":
                    break menuloop;

            }
        }

        reader.close();

    }
}

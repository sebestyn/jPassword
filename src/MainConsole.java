import crypt.AES;
import crypt.Crypt;
import crypt.CryptType;
import crypt.CryptoException;
import dataTypes.Folder;
import dataTypes.MasterPassword;
import dataTypes.Note;
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

    }
    
    public static void main(String[] args) throws IOException {

        // Login / register
        try {
            auth();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Kriprográfia függvények beállítása a mester jelszóval
        try {
            Crypt.init(masterPassword);
        } catch (Exception e) {
            System.err.println(e);
        }

        // Adatok betoltese a mainFolder-ből
        try {
            mainFolder.load("./data");
            System.out.println("Adatok sikeres betoltese");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Menü futtatása
        menuloop:
        while(true){
            System.out.printf("\n" +
                    "pelda -> pelda adatok letrehozasa %n" +
                    "save -> adatok kimentese %n" +
                    "load -> adatok betoltese %n" +
                    "list -> jelszavak listaja %n" +
                    "new p-> uj jelszo %n" +
                    "new n-> uj note %n" +
                    "remove f-> mappa torlese %n" +
                    "mp-rm -> mester jelszo torles %n" +
                    "exit -> kilepes" +
                    "\n");
            switch (reader.readLine()){
                case "pelda":
                    // Pelda adatok
                    mainFolder.addPassword(new Password("tiktok.com", "jelszo123"));
                    mainFolder.addNote(new Note("bevásárlólista", "alma\nkörte\nbanán"));
                    Folder tempFolder = new Folder("család");
                    tempFolder.addPassword(new Password(CryptType.SEBI,"gyerek", "passw"));
                    tempFolder.addPassword(new Password(CryptType.SEBI,"google.com", "bela", "nagyonJoJelszo!"));
                    tempFolder.addNote(new Note(CryptType.SEBI,"nevem", "Béla vagyok"));
                    tempFolder.addPFolder(new Folder("felesegem"));
                    mainFolder.addPFolder(tempFolder);
                    break;
                case "save":
                    try {
                        mainFolder.save("./data");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    break;
                case "load":
                    try {
                        mainFolder.load("data");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "list":
                    mainFolder.list("");
                    break;
                case "new p":
                    String name = reader.readLine();
                    String password = reader.readLine();
                    mainFolder.addPassword(new Password(name, password));
                    break;
                case "new n":
                    String name2 = reader.readLine();
                    String note = reader.readLine();
                    mainFolder.addNote(new Note(name2, note));
                    break;
                case "remove f":
                    mainFolder.remove();
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

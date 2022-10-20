import crypt.Crypt;
import crypt.CryptType;
import dataTypes.Folder;
import dataTypes.MasterPassword;
import dataTypes.Note;
import dataTypes.Password;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {
    static final String mainFolderPath = "./data";
    static MasterPassword masterPassword = new MasterPassword(mainFolderPath);
    static Folder mainFolder = new Folder("mainFolder", null);
    static Folder actualFolder = mainFolder;
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private static void auth() {
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
            throw new RuntimeException(e);
        }

        // Adatok betoltese a mainFolder-ből
        try {
            mainFolder.load(mainFolderPath);
            System.out.println("Adatok sikeres betoltese");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Menü futtatása
        menuloop:
        while(true){
            System.out.print("""
                    
                    MENU
                    pelda -> pelda adatok letrehozasa
                    save -> adatok kimentese
                    load -> adatok betoltese
                    list -> jelszavak listaja
                    cd f -> egy mappa kivalasztasa
                    cd .. -> egy mappa-val vissza
                    search p/n -> kereses jelszo/note
                    new p/n/f-> uj jelszo/note/mappa
                    rm p/n/f/mp ->  torles jelszo/note/mappa/mester-jelszo
                    exit -> kilepes
                    """);
            switch (reader.readLine()){
                case "pelda":
                    // Pelda adatok
                    mainFolder.addPassword(new Password(CryptType.AES,"instagram","nevem", "jelszooo"));
                    mainFolder.addPassword(new Password("tiktok.com", "jelszo123"));
                    mainFolder.addNote(new Note("bevásárlólista", "alma\nkörte\nbanán"));
                    Folder tempFolder = new Folder("család", mainFolder);
                    tempFolder.addPassword(new Password(CryptType.SEBI,"gyerek", "passw"));
                    tempFolder.addPassword(new Password(CryptType.SEBI,"google.com", "bela", "nagyonJoJelszo!"));
                    tempFolder.addNote(new Note(CryptType.SEBI,"nevem", "Béla vagyok"));
                    tempFolder.addFolder(new Folder("felesegem", tempFolder));
                    mainFolder.addFolder(tempFolder);
                    break;
                case "save":
                    try {
                        mainFolder.save(mainFolderPath);
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
                    actualFolder.list("");
                    break;
                case "cd f":
                    String selectedFolderName = reader.readLine();
                    actualFolder = actualFolder.getFolder(selectedFolderName);
                    break;
                case "cd ..":
                    actualFolder = actualFolder.getParentFolder();
                    break;
                case "search p":
                    System.out.println(actualFolder.searchPassword(reader.readLine()));
                    break;
                case "search n":
                    System.out.println(actualFolder.searchNote(reader.readLine()));
                    break;
                case "new p":
                    CryptType cryptType = CryptType.valueOf(reader.readLine());
                    String name = reader.readLine();
                    String username = reader.readLine();
                    String password = reader.readLine();
                    actualFolder.addPassword(new Password(cryptType, name, username, password));
                    break;
                case "new n":
                    CryptType cryptType2 = CryptType.valueOf(reader.readLine());
                    String name2 = reader.readLine();
                    String note = reader.readLine();
                    actualFolder.addNote(new Note(cryptType2, name2, note));
                    break;
                case "new f":
                    String name3 = reader.readLine();
                    actualFolder.addFolder(new Folder(name3, actualFolder));
                    break;
                case "rm p":
                    actualFolder.removePassword(new Password(CryptType.valueOf(reader.readLine()), reader.readLine(), reader.readLine(), reader.readLine()));
                    break;
                case "rm n":
                    actualFolder.removeNote(new Note(CryptType.valueOf(reader.readLine()), reader.readLine(), reader.readLine()));
                    break;
                case "rm f":
                    actualFolder.removeFolder(new Folder(reader.readLine(), actualFolder));
                    break;
                case "rm mp":
                    mainFolder.makeEmpty();
                    try {
                        mainFolder.save(mainFolderPath);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
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

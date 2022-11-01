import crypt.CryptType;
import dataTypes.Folder;
import dataTypes.Note;
import dataTypes.Password;
import gui.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleApp {
    final Model model;
    final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public ConsoleApp(Model m){
        this.model = m;
    }
    
    private void auth() {
        // Regisztráció: nincs még mester jelszó
        if(model.need_to_regigster()){
            try {
                System.out.print("Adj meg egy mester jelszot: ");
                String typedPassword = reader.readLine();
                model.register(typedPassword);
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
                String successLogin = "fail";
                while(!successLogin.equals("success")){
                    String typedPassword = reader.readLine();
                    successLogin = model.login(typedPassword);
                    if(successLogin.equals("success")){
                        model.loadData(typedPassword);
                    }
                    else if(successLogin.equals("factoryReset")){
                        System.out.print("Minden törölve!");
                        System.exit(1);
                    }
                    else {
                        System.out.print("Hibás jelszó! Add meg ujra: ");
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
            System.out.println("Sikeresen belépve!");
        }

    }
    
    public void run() throws IOException {

        // Login / register
        try {
            auth();
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
                    list ->  elemek listazasa a mappaban
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
                    model.getMainFolder().addPassword(new Password(CryptType.AES,"instagram.com","bela", "jelszooo"));
                    model.getMainFolder().addPassword(new Password(CryptType.SEBI,"facebook.com","bela", "ezeegyjelszo19"));
                    model.getMainFolder().addPassword(new Password(CryptType.SEBI,"baratnokereso.hu","bela", "123456789"));
                    model.getMainFolder().addPassword(new Password(CryptType.AES,"vanenet.hu","van", "gé656"));
                    model.getMainFolder().addPassword(new Password(CryptType.NONE,"tinder.com","nagyafal", "E.6fds+f"));
                    model.getMainFolder().addPassword(new Password("tiktok.com", "jelszo123"));
                    model.getMainFolder().addNote(new Note("bevásárlólista", "alma\nkörte\nbanán"));
                    model.getMainFolder().addNote(new Note(CryptType.SEBI,"nevem", "Béla vagyok"));
                    model.getMainFolder().addNote(new Note(CryptType.SEBI,"címem", "1934 Bp. Harsfa utca 234/B"));
                    model.getMainFolder().addNote(new Note(CryptType.AES,"bankártya", "3452 5463 4359 1234"));
                    model.getMainFolder().addNote(new Note(CryptType.NONE,"barátom", "nincs"));
                    model.getMainFolder().addNote(new Note(CryptType.NONE,"barátnőm", "nincs"));
                    model.getMainFolder().addNote(new Note("milyen nap is van", "nézd meg a naptárban!"));
                    Folder csalad = new Folder("család", model.getMainFolder());
                    csalad.addPassword(new Password(CryptType.NONE,"gyerek", "passw123"));
                    csalad.addPassword(new Password(CryptType.SEBI,"google.com", "bela.szabo", "nagyon.Jo.Jelszo!"));
                    csalad.addNote(new Note("mennyire szeretem a családom?", "nagyon"));
                    csalad.addNote(new Note("kell barátnő?", "kell"));
                    Folder felesegem = new Folder("felesegem", csalad);
                    felesegem.addPassword(new Password("megcsal-e-a-ferjem.org", "remelemNemTeFal"));
                    felesegem.addPassword(new Password(CryptType.AES,"gmail.com", "Barbi", "szeretemAFerjem12"));
                    csalad.addFolder(felesegem);
                    csalad.addFolder(new Folder("gyerekeim", csalad));
                    model.getMainFolder().addFolder(csalad);
                    break;
                case "save":
                    try {
                        model.getMainFolder().save(model.getMainFolderPath());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    break;
                case "load":
                    try {
                        model.getMainFolder().load("data");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "list":
                    model.getActualFolder().list("");
                    break;
                case "cd f":
                    String selectedFolderName = reader.readLine();
                    model.setActualFolder(model.getActualFolder().getFolder(selectedFolderName));
                    break;
                case "cd ..":
                    model.setActualFolder(model.getActualFolder().getParentFolder());
                    break;
                case "search p":
                    System.out.println(model.getActualFolder().searchPassword(reader.readLine()));
                    break;
                case "search n":
                    System.out.println(model.getActualFolder().searchNote(reader.readLine()));
                    break;
                case "new p":
                    CryptType cryptType = CryptType.valueOf(reader.readLine());
                    String name = reader.readLine();
                    String username = reader.readLine();
                    String password = reader.readLine();
                    model.getActualFolder().addPassword(new Password(cryptType, name, username, password));
                    break;
                case "new n":
                    CryptType cryptType2 = CryptType.valueOf(reader.readLine());
                    String name2 = reader.readLine();
                    String note = reader.readLine();
                    model.getActualFolder().addNote(new Note(cryptType2, name2, note));
                    break;
                case "new f":
                    String name3 = reader.readLine();
                    model.getActualFolder().addFolder(new Folder(name3, model.getActualFolder()));
                    break;
                case "rm p":
                    model.getActualFolder().removePassword(new Password(CryptType.valueOf(reader.readLine()), reader.readLine(), reader.readLine(), reader.readLine()));
                    break;
                case "rm n":
                    model.getActualFolder().removeNote(new Note(CryptType.valueOf(reader.readLine()), reader.readLine(), reader.readLine()));
                    break;
                case "rm f":
                    model.getActualFolder().removeFolder(new Folder(reader.readLine(), model.getActualFolder()));
                    break;
                case "rm mp":
                    model.getMainFolder().makeEmpty();
                    try {
                        model.getMainFolder().save(model.getMainFolderPath());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    model.getMasterPassword().removePassword();
                    System.exit(1);
                    break;
                case "exit":
                    break menuloop;

            }
        }

        reader.close();

    }
}

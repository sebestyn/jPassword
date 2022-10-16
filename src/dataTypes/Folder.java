package dataTypes;

import crypt.CryptType;
import crypt.CryptoException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.UUID;

public class Folder {
    private UUID id = UUID.randomUUID();
    private String forderName;
    private ArrayList<Password> passwords = new ArrayList<>();
    private ArrayList<Note> notes = new ArrayList<>();
    private ArrayList<Folder> folders = new ArrayList<>();

    public Folder(String forderName){
        this.forderName = forderName;
    }

    public void addPassword(Password pass){
        passwords.add(pass);
    }
    public void addNote(Note note){ notes.add(note); }
    public void addPFolder(Folder folder){
        folders.add(folder);
    }


    public void save(String path) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, CryptoException, InvalidKeyException {
        String folderPath = path + "/" + this.forderName;

        // Remove old datas // Source: https://softwarecave.org/2018/03/24/delete-directory-with-contents-in-java/
        Files.walk(Paths.get(folderPath))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);

        // Make new folder
        new File(folderPath).mkdirs();

        // Passwords
        BufferedWriter pass_writer = new BufferedWriter(new FileWriter(folderPath + "/password.encrypt"));
        for(Password pass: passwords){
            pass_writer.write(pass.getEncrypted());
        }
        pass_writer.close();

        // Notes
        BufferedWriter notes_writer = new BufferedWriter(new FileWriter(folderPath + "/notes.encrypt"));
        for(Note note: notes){
            notes_writer.write(note.getEncrypted());
        }
        notes_writer.close();

        // Folders
        for(Folder folder: folders){
            System.out.println(folder.toString());
            folder.save(folderPath);
        }
    }

    public void load(String path) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, CryptoException, InvalidKeyException {
        String folderPath = path + "/" + this.forderName;

        // Password
        Scanner pass_reader = new Scanner(new File(folderPath + "/password.encrypt"));
        while (pass_reader.hasNextLine()) {
            CryptType cryptType = CryptType.valueOf(pass_reader.nextLine());
            String  name = pass_reader.nextLine();
            String  username = pass_reader.nextLine();
            String  password = pass_reader.nextLine();
            Password readPassword = new Password(cryptType, name, username, password);
            passwords.add(readPassword);
        }
        pass_reader.close();

        // Notes
        Scanner notes_reader = new Scanner(new File(folderPath + "/notes.encrypt"));
        while (notes_reader.hasNextLine()) {
            CryptType cryptType = CryptType.valueOf(notes_reader.nextLine());
            String  name = notes_reader.nextLine();
            String  note = notes_reader.nextLine();
            Note readNote = new Note(cryptType, name, note);
            notes.add(readNote);
        }
        notes_reader.close();

        // Folders // Source: https://www.baeldung.com/java-list-directory-files
        DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(folderPath));
        for (Path childFolderPath : stream) {
            if (Files.isDirectory(childFolderPath)) {
                Folder childFolder = new Folder(childFolderPath.getFileName().toString());
                childFolder.load(folderPath);
                folders.add(childFolder);
            }
        }

    }

    public void remove(){
        // Passwords
        passwords.clear();
        // Notes
        notes.clear();
        // Folders
        for(Folder folder: folders){
            folder.remove();
        }
        folders.clear();
    }

    public void list() {
        // Folder name
        System.out.println("\n" + this);

        // Passwords
        for(Password pass: passwords){
            System.out.println(pass.toString());
        }
        // Notes
        for(Note note: notes){
            System.out.println(note.toString());
        }
        // Folders
        for(Folder folder: folders){
            folder.list();
        }
    }

    @Override
    public String toString() {
        return "Folder{" +
                "id=" + id +
                ", forderName='" + forderName + '\'' +
                ", passwords=" + passwords.size() +
                ", notes=" + notes.size() +
                ", folders=" + folders.size() +
                '}';
    }
}

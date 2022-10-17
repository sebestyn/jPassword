package dataTypes;

import crypt.Crypt;
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
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import java.util.stream.Collectors;

public class Folder{
    private final String forderName;
    private final Folder parentFolder;
    private final HashSet<Password> passwords = new HashSet<>();
    private final HashSet<Note> notes = new HashSet<>();
    private final HashSet<Folder> folders = new HashSet<>();

    public Folder(String forderName, Folder parentFolder){
        this.forderName = forderName;
        this.parentFolder = parentFolder;
    }

    public void addPassword(Password pass){
        passwords.add(pass);
    }
    public void addNote(Note note){ notes.add(note); }
    public void addFolder(Folder folder){
        folders.add(folder);
    }

    public Folder getParentFolder(){
        return this.parentFolder;
    }
    public Folder getFolder(String selectedFolderName){
        for (Folder f : folders) {
            if (f.forderName.equals(selectedFolderName)){
                return f;
            }
        }
        return this;
    }

    public void removePassword(Password password){
        passwords.remove(password);
    }
    public void removeNote(Note note){
        notes.remove(note);
    }
    public void removeFolder(Folder folder){
        folder.makeEmpty();
        folders.remove(folder);
    }

    public HashSet<Password> searchPassword(String value){
        final String finalValue = value.toLowerCase();
        return passwords.stream().filter(
                pass ->
                        (
                                pass.getCryptType().toString().toLowerCase().contains(finalValue) ||
                                        pass.getName().toLowerCase().contains(finalValue) ||
                                        pass.getUsername().toLowerCase().contains(finalValue) ||
                                        pass.getPassword().toLowerCase().contains(finalValue)
                        )
        ).collect(Collectors.toCollection(HashSet::new));
    }

    public HashSet<Note> searchNote(String value){
        final String finalValue = value.toLowerCase();
        return notes.stream().filter(
                note ->
                        (
                                note.getCryptType().toString().toLowerCase().contains(finalValue) ||
                                        note.getName().toLowerCase().contains(finalValue) ||
                                        note.getNote().toLowerCase().contains(finalValue)
                        )
        ).collect(Collectors.toCollection(HashSet::new));
    }

    public void load(String path) throws IOException, CryptoException {
        String folderPath = path + "/" + this.forderName;

        // Ha nem letezik -> csak letrehozza
        if(!Files.isDirectory(Paths.get(folderPath))){
            new File(folderPath).mkdirs();
            new File(folderPath + "/password.encrypt").createNewFile();
            new File(folderPath + "/notes.encrypt").createNewFile();
            return;
        }

        // Password
        Scanner pass_reader = new Scanner(new File(folderPath + "/password.encrypt"));
        while (pass_reader.hasNextLine()) {
            CryptType cryptType = CryptType.valueOf(pass_reader.nextLine());
            String  name = Crypt.decrypt(cryptType, pass_reader.nextLine());
            String  username = Crypt.decrypt(cryptType, pass_reader.nextLine());
            String  password = Crypt.decrypt(cryptType, pass_reader.nextLine());
            Password readPassword = new Password(cryptType, name, username, password);
            passwords.add(readPassword);
        }
        pass_reader.close();

        // Notes
        Scanner notes_reader = new Scanner(new File(folderPath + "/notes.encrypt"));
        while (notes_reader.hasNextLine()) {
            CryptType cryptType = CryptType.valueOf(notes_reader.nextLine());
            String  name = Crypt.decrypt(cryptType, notes_reader.nextLine());
            String  note = Crypt.decrypt(cryptType, notes_reader.nextLine());
            Note readNote = new Note(cryptType, name, note);
            notes.add(readNote);
        }
        notes_reader.close();

        // Folders // Source: https://www.baeldung.com/java-list-directory-files
        DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(folderPath));
        for (Path childFolderPath : stream) {
            if (Files.isDirectory(childFolderPath)) {
                Folder childFolder = new Folder(childFolderPath.getFileName().toString(), this);
                childFolder.load(folderPath);
                folders.add(childFolder);
            }
        }

    }

    public void save(String path) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, CryptoException, InvalidKeyException {
        String folderPath = path + "/" + this.forderName;

        // Benne lévő mappák törlése
        deleteDir(new File(folderPath));

        // Új üres mappa létrehozása
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
            folder.save(folderPath);
        }
    }

    public void makeEmpty(){
        // Passwords
        passwords.clear();
        // Notes
        notes.clear();
        // Folders
        for(Folder folder: folders){
            folder.makeEmpty();
        }
        folders.clear();
    }

    public void list(String level) {
        // Folder name
        System.out.println(level + this);
        level += " -- ";

        // Passwords
        for(Password pass: passwords){
            System.out.println(level + pass.toString());
        }
        // Notes
        for(Note note: notes){
            System.out.println(level + note.toString());
        }
        // Folders
        for(Folder folder: folders){
            folder.list(level);
        }
    }

    @Override
    public String toString() {
        return "Folder{" +
                "forderName='" + forderName + '\'' +
                ", passwords=" + passwords.size() +
                ", notes=" + notes.size() +
                ", folders=" + folders.size() +
                '}';
    }

    /**
     * Az összes adat törlése egy mappán belül
     * Forrás: <a href="https://stackoverflow.com/questions/20281835/how-to-delete-a-folder-with-files-using-java">...</a>
     * @param file Dir location
     */
    private void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDir(f);
            }
        }
        file.delete();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Folder folder = (Folder) o;
        return forderName.equals(folder.forderName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(forderName);
    }
}

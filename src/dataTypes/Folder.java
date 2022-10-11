package dataTypes;

import crypt.Crypt;
import crypt.CryptType;
import crypt.CryptoException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

public class Folder {
    private final CryptType cryptType = CryptType.AES;
    private String name;
    private ArrayList<Password> passwords = new ArrayList<Password>();
    private ArrayList<Note> notes = new ArrayList<Note>();
    private ArrayList<Folder> folders = new ArrayList<Folder>();

    public Folder(String name){
        this.name = name;
    }

    public void addPassword(Password pass){
        passwords.add(pass);
    }

    public void save() throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, CryptoException, InvalidKeyException {
        String folderPath = "data/" + this.name;
        new File(folderPath).mkdirs();
        BufferedWriter pass_writer = new BufferedWriter(new FileWriter(folderPath + "/password.encrypt"));

        for(Password pass: passwords){
            pass_writer.write(Crypt.encrypt(CryptType.AES, pass.getName()) + "\n");
            pass_writer.write(Crypt.encrypt(CryptType.AES, pass.getUsername()) + "\n");
            pass_writer.write(Crypt.encrypt(CryptType.AES, pass.getPassword()) + "\n");
        }

        pass_writer.close();
    }

    public void load(String path) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, CryptoException, InvalidKeyException {
        String folderPath = path + "/" + this.name;
        BufferedReader pass_reader = new BufferedReader(new FileReader(folderPath + "/password.encrypt"));

        String line = pass_reader.readLine();
        while (line != null) {
            System.out.println(Crypt.decrypt(CryptType.AES, line));
            // read next line
            line = pass_reader.readLine();
        }

        pass_reader.close();
    }

}

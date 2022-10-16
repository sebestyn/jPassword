package dataTypes;

import crypt.Crypt;
import crypt.CryptType;
import crypt.CryptoException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

public class Note {
    private UUID id = UUID.randomUUID();
    private CryptType cryptType = CryptType.NONE;
    private String name = "";
    private String note = "";

    public Note(String name, String note){
        this.name = Global.oneLineString(name);
        this. note = Global.oneLineString(note);
    }

    public Note(CryptType cryptType, String name, String note){
        this.cryptType = cryptType;
        this.name = Global.oneLineString(name);
        this. note = Global.oneLineString(note);
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", cryptType=" + cryptType +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public String getEncrypted() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, CryptoException, InvalidKeyException {
        return  this.cryptType + "\n" +
                Crypt.encrypt(this.cryptType, this.name) + "\n" +
                Crypt.encrypt(this.cryptType, this.note) + "\n";
    }
}

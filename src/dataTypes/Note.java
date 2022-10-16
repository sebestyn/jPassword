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
import java.util.Objects;

public class Note {
    private CryptType cryptType = CryptType.NONE;
    private final String name;
    private final String note;

    public Note(String name, String note){
        this.name = Global.oneLineString(name);
        this. note = Global.oneLineString(note);
    }

    public Note(CryptType cryptType, String name, String note){
        this.cryptType = cryptType;
        this.name = Global.oneLineString(name);
        this. note = Global.oneLineString(note);
    }

    public String getEncrypted() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, CryptoException, InvalidKeyException {
        return  this.cryptType + "\n" +
                Crypt.encrypt(this.cryptType, this.name) + "\n" +
                Crypt.encrypt(this.cryptType, this.note) + "\n";
    }

    @Override
    public String toString() {
        return "Note{" +
                "cryptType=" + cryptType +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note1 = (Note) o;
        return cryptType == note1.cryptType && name.equals(note1.name) && note.equals(note1.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cryptType, name, note);
    }
}

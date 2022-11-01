package dataTypes;

import crypt.CryptType;
import crypt.CryptoException;
import crypt.SEBI;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

class NoteTest {
    MasterPassword mp = new MasterPassword("./data","mester");
    Note n = new Note(CryptType.SEBI,"aaa", "bbb");

    @Test
    void getCryptType() {
        assertEquals(CryptType.SEBI, n.getCryptType());
    }

    @Test
    void getName() {
        assertEquals("aaa", n.getName());
    }

    @Test
    void getNote() {
        assertEquals("bbb", n.getNote());
    }

    @Test
    void getEncrypted() {
        SEBI.init(mp);
        try {
            assertEquals("SEBI\n" +
                    "206 204 224 \n" +
                    "207 205 225", n.getEncrypted().trim());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
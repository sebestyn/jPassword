package dataTypes;

import crypt.CryptType;
import crypt.SEBI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteTest {
    final MasterPassword mp = new MasterPassword("./data","mester");
    final Note n = new Note(CryptType.SEBI,"aaa", "bbb");

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
            assertEquals("""
                    SEBI
                    206 204 224\s
                    207 205 225""", n.getEncrypted().trim());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
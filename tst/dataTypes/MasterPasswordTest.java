package dataTypes;

import crypt.CryptoException;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

class MasterPasswordTest {
    MasterPassword mp = new MasterPassword("./data", "lol");

    @Test
    void getValue() {
        assertEquals("lol", mp.getValue());
    }

    @Test
    void setValue() {
        mp.setValue("valtozott");
        assertEquals("valtozott", mp.getValue());
    }

    @Test
    void isInputEqualsHash() {
        try {
            assertEquals(true, mp.isInputEqualsHash("alma", "cf43e029efe6476e1f7f84691f89c876818610c2eaeaeb881103790a48745b82"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void removePassword() {
        mp.removePassword();
        assertEquals(null, mp.getValue());
    }
}
package crypt;

import dataTypes.MasterPassword;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

class SEBITest {

    MasterPassword mp = new MasterPassword("./data","mester");

    @Test
    void encrypt_and_decrypt() throws NoSuchAlgorithmException, InvalidKeySpecException {
        SEBI.init(mp);
        try {
            String en = SEBI.encrypt("ez egy üzenet");
            String de = SEBI.decrypt(en);
            assertEquals("ez egy üzenet", de);

            String en2 = SEBI.encrypt("Ez egy nagyon hosszú rirkos üzenet");
            String de2 = SEBI.decrypt(en2);
            assertEquals("Ez egy nagyon hosszú rirkos üzenet", de2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
package crypt;

import dataTypes.MasterPassword;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

class AESTest {

    @Test
    void encrypt_and_decrypt() throws NoSuchAlgorithmException, InvalidKeySpecException {
        AES.generateKey(new MasterPassword("kuki"));
        try {
            String encrypted = AES.encrypt("ez egy üzenet");

            String decrypted = AES.decrypt(encrypted);
            assertEquals("ez egy üzenet", decrypted);
        } catch (Exception e) {
            fail("Could not encrypt!");
        }
    }

}
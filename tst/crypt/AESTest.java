package crypt;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

class AESTest {

    AES aes = new AES("password123");

    AESTest() throws NoSuchAlgorithmException, InvalidKeySpecException {}


    @Test
    @Order(1)
    void encrypt_and_decrypt() {
        try {
            String encrypted = aes.encrypt("ez egy üzenet");
            System.out.println(encrypted);

            try {
                String decrypted = aes.decrypt(encrypted);
                System.out.println(decrypted);
                assertEquals("ez egy üzenet", decrypted);
            } catch (Exception e) {
                fail("Could not decrypt: " + e.getMessage());
            }

        } catch (Exception e) {
            fail("Could not encrypt!");
        }
    }

}
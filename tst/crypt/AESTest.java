package crypt;

import dataTypes.MasterPassword;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

class AESTest {

    @Test
    void encrypt_and_decrypt() throws NoSuchAlgorithmException, InvalidKeySpecException {
        AES.init(new MasterPassword("kuki"));
        try {
            String en = AES.encrypt("ez egy üzenet");
            System.out.println(en);
            String de = AES.decrypt(en);
            System.out.println(de);
            assertEquals("ez egy üzenet", de);

            String en2 = AES.encrypt("Ez egy nagyon hosszú rirkos üzenet");
            System.out.println(en2);
            String de2 = AES.decrypt(en2);
            System.out.println(de2);
            assertEquals("Ez egy nagyon hosszú rirkos üzenet", de2);

            String en3 = AES.encrypt("ez egy üzenet");
            System.out.println(en3);
            String de3 = AES.decrypt(en3);
            System.out.println(de3);
            assertEquals("ez egy üzenet", de3);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
package crypt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CryptTest {

    @Test
    void mainEncryptMethod(){
        var crypt = new Crypt();
        var crypted = crypt.encrypt("alma");

        assertEquals("fqrf", crypted);
    }

}
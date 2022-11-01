package crypt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SHA256Test {

    SHA256 sha256;

    @BeforeEach
    void setUp() {
        sha256 = new SHA256();
    }

    @Test
    void encrypt() throws CryptoException {
        assertEquals("ca0a4d812197bf0bb307c264ae27cc3b4ceb563394f5ccfab3df366b274d03be", SHA256.encrypt("ez egy teszt"));
    }

    @Test
    void decrypt() {
        assertEquals("ca0a4d812197bf0bb307c264ae27cc3b4ceb563394f5ccfab3df366b274d03be", SHA256.decrypt("ca0a4d812197bf0bb307c264ae27cc3b4ceb563394f5ccfab3df366b274d03be"));


    }




}
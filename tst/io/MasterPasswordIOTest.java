package io;

import crypt.CryptType;
import dataTypes.MasterPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MasterPasswordIOTest {

    MasterPasswordIO mpIO;

    @BeforeEach
    void setUp() {
        mpIO = new MasterPasswordIO("data/test_MasterPassword.encrypt");
    }

    @Test
    void savePassword() {
        try {
            mpIO.savePassword(CryptType.SHA256, "alma");
        } catch (IOException e) {
            fail("Could not save file");
        }
    }

    @Test
    void loadPassword() {
        try {
            MasterPassword mp = mpIO.loadPassword();
            assertEquals(CryptType.SHA256, mp.getCryptType());
            assertEquals("alma", mp.getValue());
        } catch (IOException e) {
            fail("Could not load file");
        }
    }

    @Test
    void isAlreadyExist() {
        assertTrue(mpIO.isAlreadyExist());
    }
}
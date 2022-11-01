package dataTypes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MasterPasswordTest {
    final MasterPassword mp = new MasterPassword("./data", "lol");

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
            assertTrue(mp.isInputEqualsHash("alma", "cf43e029efe6476e1f7f84691f89c876818610c2eaeaeb881103790a48745b82"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void removePassword() {
        mp.removePassword();
        assertNull(mp.getValue());
    }
}
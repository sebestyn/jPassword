package dataTypes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FolderTest {
    final Folder main = new Folder("main", null);

    @Test
    void addPassword() {
        main.addPassword(new Password("test", "alma"));
        assertEquals(main.getPasswords().size(), 1);
    }

    @Test
    void addNote() {
        main.addNote(new Note("test", "alma"));
        assertEquals(main.getNotes().size(), 1);
    }

    @Test
    void addFolder() {
        main.addFolder(new Folder("test2", main));
        assertEquals(main.getFolders().size(), 1);
    }


    @Test
    void removePassword() {
        main.removePassword(new Password("test", "alma"));
        assertEquals(main.getPasswords().size(), 0);
    }

    @Test
    void makeEmpty(){
        main.makeEmpty();
        assertEquals(main.getFolders().size() + main.getNotes().size(), 0);
    }
}
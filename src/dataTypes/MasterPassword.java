package dataTypes;

import crypt.CryptType;

public class MasterPassword {
    private final CryptType cryptType;
    private final String value;

    public MasterPassword(CryptType c, String v) {
        cryptType = c;
        value = v;
    }

    public CryptType getCryptType() {
        return cryptType;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

}





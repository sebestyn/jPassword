package crypt;

import dataTypes.MasterPassword;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


public class Crypt {

    public static void init(MasterPassword masterPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        AES.init(masterPassword);
        SEBI.init(masterPassword);
    }

    /**
     *
     * @param message Az üzenet amit titkosítani szeretnél
     * @param cryptType A titkosító algoritmus típusa
     * @return A titkosított üzenet
     * @throws CryptoException Hiba a titkosítás közben
     */
    public static String encrypt(CryptType cryptType, String message) throws CryptoException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        if(message == null || message.equals("null")){
            return null;
        }
        return switch (cryptType) {
            case NONE -> message;
            case SEBI -> SEBI.encrypt(message);
            case SHA256 -> SHA256.encrypt(message);
            case BLOWFISH -> BLOWFISH.encrypt(message);
            case AES -> message; //AES.encrypt(message);
        };
    }

    /**
     * @param message   Az üzenet amit dekódolni szeretnél
     * @param cryptType A titkosító algoritmus típusa
     * @return A dekódolt üzenet
     * @throws CryptoException Hiba a dekódolás közben
     */
    public static String decrypt(CryptType cryptType, String message) throws CryptoException {
        if(message == null || message.equals("null")){
            return null;
        }
        return switch (cryptType) {
            case NONE -> message;
            case SEBI -> SEBI.decrypt(message);
            case SHA256 -> SHA256.decrypt(message);
            case BLOWFISH -> BLOWFISH.decrypt(message);
            case AES -> message; //AES.decrypt(message);
        };
    }

}

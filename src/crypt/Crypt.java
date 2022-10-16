package crypt;

import dataTypes.MasterPassword;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
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
        switch (cryptType){
            case NONE:
                return message;
            case SEBI:
                    return SEBI.encrypt(message);
            case SHA256:
                return SHA256.encrypt(message);
            case BLOWFISH:
                return BLOWFISH.encrypt(message);
            case AES:
                return AES.encrypt(message);
            default:
                throw new CryptoException("Invalid cryptType");
        }
    }

    /**
     * @param message   Az üzenet amit dekódolni szeretnél
     * @param cryptType A titkosító algoritmus típusa
     * @return A dekódolt üzenet
     * @throws CryptoException Hiba a dekódolás közben
     */
    public static String decrypt(CryptType cryptType, String message) throws CryptoException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        if(message == null || message.equals("null")){
            return null;
        }
        switch (cryptType){
            case NONE:
                return message;
            case SEBI:
                return SEBI.decrypt(message);
            case SHA256:
                return SHA256.decrypt(message);
            case BLOWFISH:
                return BLOWFISH.decrypt(message);
            case AES:
                return AES.decrypt(message);
            default:
                throw new CryptoException("Invalid cryptType");
        }
    }

}

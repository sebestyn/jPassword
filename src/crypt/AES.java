package crypt;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

// Source: https://www.section.io/engineering-education/implementing-aes-encryption-and-decryption-in-java/#table-of-contents
public class AES extends CryptAbs {
    SecretKey key;
    private final int KEY_SIZE = 128;
    private final int DATA_LENGTH = 128;
    private Cipher encryptionCipher;


    AES(String masterPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Generate key // Source https://stackoverflow.com/questions/9536827/generate-key-from-string
        byte[] salt = {(byte)0xc7, (byte)0x73, (byte)0x21, (byte)0x8c, (byte)0x7e, (byte)0xc8, (byte)0xee, (byte)0x99};
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(masterPassword.toCharArray(), salt, KEY_SIZE*KEY_SIZE, KEY_SIZE);
        key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

    }

    /**
     * Titkosító algoritmus
     *
     * @param message Az üzenet amit titkosítani szeretnél
     * @return A titkosított üzenet
     * @throws CryptoException Hiba a titkosítás közben
     */
    @Override
    public String encrypt(String message) throws Exception {
        byte[] dataInBytes = message.getBytes();
        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = encryptionCipher.doFinal(dataInBytes);
        String encryptedString = Base64.getEncoder().encodeToString(encryptedBytes);
        return encryptedString;
    }

    /**
     * Dekódoló algoritmus
     *
     * @param message Az üzenet amit dekódolni szeretnél
     * @return A dekódolt üzenet
     * @throws CryptoException Hiba a dekódolás közben
     */
    @Override
    public String decrypt(String message) throws Exception {
        byte[] dataInBytes = Base64.getDecoder().decode(message);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(DATA_LENGTH, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(dataInBytes);
        return new String(decryptedBytes);
    }


}

package crypt;

import dataTypes.MasterPassword;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

// Source: https://www.section.io/engineering-education/implementing-aes-encryption-and-decryption-in-java/#table-of-contents
public class AES {
    private static SecretKey key = null;
    private static final int DATA_LENGTH = 128;
    private static Cipher encryptionCipher;

    public static void init(MasterPassword masterPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Create key
        key = AES.generateKey(masterPassword);
    }

    /**
     * Titkosító algoritmus
     *
     * @param message Az üzenet amit titkosítani szeretnél
     * @return A titkosított üzenet
     */
    public static String encrypt(String message) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        byte[] dataInBytes = message.getBytes();
        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = encryptionCipher.doFinal(dataInBytes);
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Dekódoló algoritmus
     *
     * @param message Az üzenet amit dekódolni szeretnél
     * @return A dekódolt üzenet
     */
    public static String decrypt(String message) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        byte[] dataInBytes = Base64.getDecoder().decode(message);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(DATA_LENGTH, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(dataInBytes);
        return new String(decryptedBytes);
    }

    // Generate key // Source https://stackoverflow.com/questions/9536827/generate-key-from-string
    public static SecretKey generateKey(MasterPassword masterPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = {(byte)0xc7, (byte)0x73, (byte)0x21, (byte)0x8c, (byte)0x7e, (byte)0xc8, (byte)0xee, (byte)0x99};
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(masterPassword.getValue().toCharArray(), salt, 128*128, 128);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }


}

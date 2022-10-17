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
    static private final int KEY_SIZE = 128;
    static private final int T_LEN = 128;
    private static byte[] IV;

    public static void init(MasterPassword masterPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Create key
        key = AES.generateKey(masterPassword);
        IV = decode("e3IYYJC2hxe24/EO");
    }

    /**
     * Titkosító algoritmus
     *
     * @param message Az üzenet amit titkosítani szeretnél
     * @return A titkosított üzenet
     */
    public static String encrypt(String message) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        byte[] dataInBytes = message.getBytes();
        Cipher encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, IV);
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key, spec);
        byte[] encryptedBytes = encryptionCipher.doFinal(dataInBytes);
        return encode(encryptedBytes);
    }

    /**
     * Dekódoló algoritmus
     *
     * @param message Az üzenet amit dekódolni szeretnél
     * @return A dekódolt üzenet
     */
    public static String decrypt(String message) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] dataInBytes = decode(message);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, IV);
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(dataInBytes);
        return new String(decryptedBytes);
    }

    // Generate key // Source https://stackoverflow.com/questions/9536827/generate-key-from-string
    public static SecretKey generateKey(MasterPassword masterPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = {(byte)0xc7, (byte)0x73, (byte)0x21, (byte)0x8c, (byte)0x7e, (byte)0xc8, (byte)0xee, (byte)0x99};
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(masterPassword.getValue().toCharArray(), salt, KEY_SIZE*KEY_SIZE, KEY_SIZE);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    private static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private static byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

}

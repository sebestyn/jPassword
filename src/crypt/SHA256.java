package crypt;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA256 kriptográfiai algoritmus (making hash from String)
 */
public class SHA256 {

    /**
     * SHA256 Titkosító algoritmus
     * @param message Az üzenet amit titkosítani szeretnél
     * @return A titkosított üzenet
     * @throws CryptoException Hiba a titkosítás közben
     */
    public static String encrypt(String message) throws CryptoException {
        try {
            return toHexString(getSHA(message));
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException(e.getMessage());
        }
    }

    /**
     * Nincs Dekódoló algoritmusa!
     * @param message Az üzenet amit dekódolni szeretnél
     * @return A dekódolt üzenet
     * @throws CryptoException Hiba a dekódolás közben
     */
    public static String decrypt(String message) throws CryptoException {
        throw new CryptoException("Message encrypted with SHA256 can not be decrypted");
    }

    // Convert String to Hash bytes // Source: https://www.geeksforgeeks.org/sha-256-hash-in-java/
    private static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }
    // Convert bytes to String // Source: https://www.geeksforgeeks.org/sha-256-hash-in-java/
    private static String toHexString(byte[] hash) {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 64) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

}

package crypt;

/**
 * Az összes szükságes Kriptográfia methódust tartalmazza
 */
public abstract class CryptAbs {

    /**
     * Titkosító algoritmus
     * @param message Az üzenet amit titkosítani szeretnél
     * @return A titkosított üzenet
     * @throws CryptoException Hiba a titkosítás közben
     */
    public abstract String encrypt(String message) throws Exception;

    /**
     * Dekódoló algoritmus
     * @param message Az üzenet amit dekódolni szeretnél
     * @return A dekódolt üzenet
     * @throws CryptoException Hiba a dekódolás közben
     */
    public abstract String decrypt(String message) throws Exception;

}

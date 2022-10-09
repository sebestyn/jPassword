package crypt;

public class Crypt {

    /**
     *
     * @param message Az üzenet amit titkosítani szeretnél
     * @param cryptType A titkosító algoritmus típusa
     * @return A titkosított üzenet
     * @throws CryptoException Hiba a titkosítás közben
     */
    public String encrypt(CryptType cryptType, String message) throws CryptoException {

        switch (cryptType){
            case SHA256:
                var sha256 = new SHA256();
                return sha256.encrypt(message);
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
    public String decrypt(CryptType cryptType, String message) throws CryptoException {
        return null;
    }

}

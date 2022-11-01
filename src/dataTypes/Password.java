package dataTypes;

import crypt.Crypt;
import crypt.CryptType;
import crypt.CryptoException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class Password {
    private CryptType cryptType = CryptType.NONE;
    private final String name;
    private String username = "";
    private final String password;

    public Password(String name, String password){
        this.name = Global.oneLineString(name);
        this.password = Global.oneLineString(password);
    }
    public Password(CryptType cryptType, String name, String password){
        this.cryptType = cryptType;
        this.name = Global.oneLineString(name);
        this.password = Global.oneLineString(password);
    }

    public Password(CryptType cryptType, String name, String username, String password){
        this.cryptType = cryptType;
        this.name = Global.oneLineString(name);
        this.username = Global.oneLineString(username);
        this.password = Global.oneLineString(password);
    }

    public CryptType getCryptType(){
        return cryptType;
    }
    public String getName(){
        return name;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }



    public String getEncrypted() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, CryptoException, InvalidKeyException, InvalidAlgorithmParameterException {
        return  this.cryptType + "\n" +
                Crypt.encrypt(this.cryptType, this.name) + "\n" +
                Crypt.encrypt(this.cryptType, this.username) + "\n" +
                Crypt.encrypt(this.cryptType, this.password) + "\n";
    }

    @Override
    public String toString() {
        return "Password{" +
                "cryptType=" + cryptType +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password1 = (Password) o;
        return cryptType == password1.cryptType && name.equals(password1.name) && Objects.equals(username, password1.username) && password.equals(password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cryptType, name, username, password);
    }
}

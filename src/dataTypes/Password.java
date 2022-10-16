package dataTypes;

import crypt.Crypt;
import crypt.CryptType;
import crypt.CryptoException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

public class Password {
    private UUID id = UUID.randomUUID();
    private CryptType cryptType = CryptType.NONE;
    private String name = "";
    private String username = "";
    private String password = "";

    public Password(String name, String password){
        this.name = Global.oneLineString(name);
        this.password = Global.oneLineString(password);
    }
    public Password(CryptType cryptType, String name, String username, String password){
        this.cryptType = cryptType;
        this.name = Global.oneLineString(name);
        this.username = Global.oneLineString(username);
        this.password = Global.oneLineString(password);
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

    @Override
    public String toString() {
        return "Password{" +
                "id=" + id +
                ", cryptType=" + cryptType +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getEncrypted() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, CryptoException, InvalidKeyException {
        return  this.cryptType + "\n" +
                Crypt.encrypt(this.cryptType, this.name) + "\n" +
                Crypt.encrypt(this.cryptType, this.username) + "\n" +
                Crypt.encrypt(this.cryptType, this.password) + "\n";
    }
}

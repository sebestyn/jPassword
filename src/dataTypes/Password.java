package dataTypes;

import crypt.CryptType;

public class Password {
    private final CryptType cryptType = CryptType.AES;
    private String name;
    private String username;
    private String password;

    public Password(String name, String password){
        this.name = name;
        this.password = password;
    }
    public Password(String name, String username, String password){
        this.name = name;
        this.username = username;
        this.password = password;
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

}

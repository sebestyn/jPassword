package crypt;

import dataTypes.MasterPassword;

public class SEBI {
    static String key = null;

    public static void init(MasterPassword masterPassword) {
        key = masterPassword.getValue();
    }

    public static String encrypt(String message){
        return message;
    }

    public static String decrypt(String message){
        return message;
    }


}

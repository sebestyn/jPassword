package crypt;

import dataTypes.MasterPassword;

import java.util.Arrays;

public class SEBI {
    static String key = null;

    public static void init(MasterPassword masterPassword) {
        key = masterPassword.getValue();
    }

    public static String encrypt(String message){
        String encrypted = "";
        for(int i =0; i<message.length(); i++){
            encrypted += (int) message.charAt(i) + i * key.length() + (int) key.charAt(i % key.length()) + " ";
        }
        return encrypted;
    }

    public static String decrypt(String message){
        if(message != null && message.length() > 0){
            String decrypted = "";
            int[] message_ints = Arrays.stream(message.split(" ")).mapToInt(Integer::parseInt).toArray();
            for(int i =0; i<message_ints.length; i++){
                decrypted += (char)(message_ints[i] - i * key.length() - (int) key.charAt(i % key.length())) + "";
            }
            return decrypted;
        }
        return message;
    }


}

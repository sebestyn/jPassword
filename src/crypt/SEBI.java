package crypt;

import dataTypes.MasterPassword;

import java.util.Arrays;

public class SEBI {
    static String key = null;

    public static void init(MasterPassword masterPassword) {
        key = masterPassword.getValue();
    }

    public static String encrypt(String message){
        StringBuilder encrypted = new StringBuilder();
        for(int i =0; i<message.length(); i++){
            String encChar = (int) message.charAt(i) + i * key.length() + (int) key.charAt(i % key.length()) + " ";
            encrypted.append(encChar);
        }
        return encrypted.toString();
    }

    public static String decrypt(String message){
        if(message != null && message.length() > 0){
            StringBuilder decrypted = new StringBuilder();
            int[] message_ints = Arrays.stream(message.split(" ")).mapToInt(Integer::parseInt).toArray();
            for(int i =0; i<message_ints.length; i++){
                String decChar = (char)(message_ints[i] - i * key.length() - (int) key.charAt(i % key.length())) + "";
                decrypted.append(decChar);
            }
            return decrypted.toString();
        }
        return message;
    }


}

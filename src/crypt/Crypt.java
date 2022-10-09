package crypt;

/**
 * Include all cryption method
 */
public class Crypt {

    public String encrypt(String s){
        char[] chars = s.toCharArray();
        char[] encrypted = new char[chars.length];
        for(int i=0; i< chars.length; i++) {
            encrypted[i] = (char) (chars[i] + 5);
        }
        return new String(encrypted);
    }

}

import crypt.Crypt;
import crypt.CryptType;
import crypt.CryptoException;
import io.MasterPasswordIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainConsole {
    static Crypt crypt = new Crypt();
    static MasterPasswordIO mpIO = new MasterPasswordIO();
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static String input;

    public static void main(String[] args) throws IOException {

        System.out.println("Press enter to start...");
        while(!(input = reader.readLine()).equals("exit")){
            System.out.println(input);

            // Regisztráció: nincs még mester jelszó
            if(!mpIO.isAlreadyExist()){
                try {
                    System.out.print("Adj meg egy mester jelszot: ");
                    mpIO.savePassword(CryptType.SHA256, crypt.encrypt(CryptType.SHA256, reader.readLine()));
                    System.out.println("Jelszó sikeresen elmentve!");
                    continue;
                } catch (IOException e) {
                    System.out.println("Could not save password: " + e.getMessage());
                    System.exit(1);
                } catch (CryptoException e) {
                    System.out.println("Could not encrypt password: " + e.getMessage());
                    System.exit(1);
                }
            }
            // Belépés: már van mester jelszó
            else {
                try {
                    System.out.print("Belépéshez adja meg a jelszót: ");
                    String encryptedPassword = mpIO.loadPassword().getValue();
                    String inputPassword = crypt.encrypt(CryptType.SHA256, reader.readLine());
                    while(!inputPassword.equals(encryptedPassword)){
                        System.out.println("Hibás jelszó!");
                        inputPassword = crypt.encrypt(CryptType.SHA256, reader.readLine());
                    }
                } catch (CryptoException e) {
                    System.out.println("Could not encrypt password: " + e.getMessage());
                    System.exit(1);
                }
                System.out.println("Sikeresen belépve!");
            }


            // Menü
            System.out.printf("list -> jelszavak listaja %nnew -> uj jelszo");

        }



    }
}

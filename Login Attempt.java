

import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class LoginAttemptControl
{
    Scanner sc = new Scanner(System.in);

    int attempts = 0;
    long lockTime = 0;

    String correctUser = "admin";
    String correctPass = "1234";

    void log(String message)
    {
        try
        {
            FileWriter fw = new FileWriter("login_log.txt", true);

            fw.write(LocalDateTime.now() + " : " + message + "\n");

            fw.close();
        }
        catch(IOException e)
        {
            System.out.println("Error writing log");
        }
    }

    void login()
    {
        while(true)
        {
            if(System.currentTimeMillis() < lockTime)
            {
                long remaining =
                    (lockTime - System.currentTimeMillis()) / 1000;

                System.out.println("Account Locked!");
                System.out.println("Wait " + remaining + " seconds");

                break;
            }

            System.out.print("Enter username: ");
            String username = sc.nextLine();

            System.out.print("Enter password: ");
            String password = sc.nextLine();

            if(username.equals(correctUser) &&
               password.equals(correctPass))
            {
                System.out.println("Login Successful!");

                attempts = 0;

                log("Successful login: " + username);

                break;
            }
            else
            {
                attempts++;

                System.out.println("Invalid username or password");

                System.out.println("Failed Attempts: " + attempts + "/5");

                log("Failed login: " + username);

                if(attempts == 5)
                {
                    System.out.println("Account Locked for 15 minutes!");

                    lockTime = System.currentTimeMillis() +
                               (15 * 60 * 1000);

                    log("Account locked: " + username);

                    break;
                }
            }
        }
    }

    public static void main(String args[])
    {
        LoginAttemptControl obj = new LoginAttemptControl();

        obj.login();
    }
}

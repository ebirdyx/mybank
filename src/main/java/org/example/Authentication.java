package org.example;

public class Authentication {
    public static boolean authenticate(String username, String password) {
        if (username.equals("bob") && password.equals("secret")) {
            return true;
        }

        return false;
    }
}

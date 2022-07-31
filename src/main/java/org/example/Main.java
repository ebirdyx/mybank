package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();

        Authentication authSvc = new Authentication(db);

        LoginWindow login = new LoginWindow(authSvc);

        login.setVisible(true);

        if (login.isSucceeded()) {
            MyBank bank = new MyBank(db, authSvc.getAuthenticatedUser());
            new MainWindow(bank);
        }
    }
}
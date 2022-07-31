package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        LoginWindow login = new LoginWindow(new JFrame());

        login.setVisible(true);

        if (login.isSucceeded()) {
            Database db = new Database();
            MyBank bank = new MyBank(db);
            new MainWindow(bank);
        }
    }
}
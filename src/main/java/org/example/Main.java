package org.example;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        MyBank bank = new MyBank(db);
        new MainWindow(bank);
    }
}
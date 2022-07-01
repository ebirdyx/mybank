package org.example;

import java.util.*;

public class MyBank {
    private final String[] columns = new String[]{"ID", "Date", "Type", "Amount", "Description"};

    private final List<Transaction> transactions;

    public MyBank() {
        this.transactions = new ArrayList<>();
        this.transactions.add(new Transaction(1, new Date(2022, Calendar.MARCH, 5), 22.04, TransactionType.Credit, "Bought something"));
        this.transactions.add(new Transaction(2, new Date(2022, Calendar.APRIL, 6), 40.84, TransactionType.Debit, "Deposit something"));
    }

    public Money getTotal() {
        double total = transactions.stream()
                .map(Transaction::getValue)
                .reduce(0.0, Double::sum);

        return new Money(total);
    }

    public String[] getColumns() {
        return columns;
    }

    public String[][] getTransactionsTable() {
        return transactions.stream()
                .map(Transaction::toList)
                .toArray(String[][]::new);
    }

    public void createTransaction(double amount, TransactionType type, String description) {
        transactions.add(new Transaction(transactions.size()+1, new Date(), amount, type, description));
    }
}

package org.example;

import java.util.Date;

public class Transaction {
    private int id;
    private Date date;
    private Money amount;
    private TransactionType type;
    private String description;

    public Transaction(int id, Date date, double amount, TransactionType type, String description) {
        this.id = id;
        this.date = date;
        this.amount = new Money(amount);
        this.type = type;
        this.description = description;
    }

    public String[] toList() {
        return new String[]{""+id, date.toString(), type.toString(), amount.formatAsCurrency(), description};
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Money getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}

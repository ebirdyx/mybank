package org.example;

import java.util.Date;

public class Transaction {
    private int id;
    private Date date;
    private Money amount;
    private TransactionType type;
    private String description;

    public Transaction(int id, Date date, double amount, TransactionType type, String description) {
        // TODO: amount must be positive
        this.id = id;
        this.date = date;
        this.amount = new Money(amount);
        this.type = type;
        this.description = description;
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

    public double getValue() {
        if (type == TransactionType.Credit) {
            return amount.getValue() * -1;
        }

        return amount.getValue();
    }
}

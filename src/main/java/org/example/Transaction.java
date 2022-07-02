package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
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

    public String toSql() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        return "INSERT INTO transactions (id, date, type, amount, description) VALUES ("
                + id + ", '"
                + formatter.format(date) + "', '"
                + type.toString() + "', "
                + amount.getValue() + ", '"
                + description
                + "');";
    }

    public static Transaction makeTransactionFromSql(ResultSet rs) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = formatter.parse(rs.getString("date"), new ParsePosition(0));
            return new Transaction(rs.getInt("id"), date, rs.getDouble("amount"), TransactionType.valueOf(rs.getString("type")), rs.getString("description"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

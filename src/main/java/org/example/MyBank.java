package org.example;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyBank extends AbstractTableModel {
    private final String[] columns = new String[]{"ID", "Date", "Type", "Amount", "Description"};

    private final Database db;

    private User user;

    public MyBank(Database database, User user) {
        this.db = database;
        this.user = user;
        createTransactionsTable();
        if (!db.isTableSeeded("transactions")) {
            seedTransactions();
        }
    }

    private void seedTransactions() {
        Transaction tr1 = new Transaction(1, 1, new Date(2022 - 1900, Calendar.MARCH, 5, 10, 23, 20), 22.04, TransactionType.Credit, "Bought something");
        Transaction tr2 = new Transaction(2, 2, new Date(2022 - 1900, Calendar.APRIL, 6, 14, 57, 11), 40.84, TransactionType.Debit, "Deposit something");

        db.runQuery(tr1.toSql());
        db.runQuery(tr2.toSql());
    }

    private void createTransactionsTable() {
        String query = "CREATE TABLE IF NOT EXISTS transactions (\n"
                + "id integer,\n"
                + "userId integer,\n"
                + "date TEXT,\n"
                + "type TEXT,\n"
                + "amount REAL,\n"
                + "description TEXT,\n"
                + "PRIMARY KEY (id, userId)"
                + "); ";

        db.runQuery(query);
    }

    private List<Transaction> getTransactions() {
        String query = "SELECT * FROM transactions WHERE userId = "
                + user.getId() + ";";

        List<Transaction> transactions = new ArrayList<>();

        try {
            ResultSet rs = db.selectQuery(query);

            while (rs.next()) {
                transactions.add(Transaction.makeTransactionFromSql(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return transactions;
    }

    public Money getTotal() {
        double total = getTransactions().stream()
                .map(Transaction::getValue)
                .reduce(0.0, Double::sum);

        return new Money(total);
    }

    public void createTransaction(double amount, TransactionType type, String description) {
        int maxValue = 1;

        Optional<Transaction> transaction = getTransactions().stream()
                        .max((a, b) -> a.getId() > b.getId() ? 1 : -1);

        if (transaction.isPresent())
            maxValue = transaction.get().getId() + 1;

        db.runQuery(new Transaction(maxValue+1, user.getId(), new Date(), amount, type, description).toSql());

        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return getTransactions().size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Transaction tr = getTransactions().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return tr.getId();
            case 1:
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                return formatter.format(tr.getDate());
            case 2:
                return tr.getType().toString();
            case 3:
                return tr.getAmount().formatAsCurrency();
            case 4:
                return tr.getDescription();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}

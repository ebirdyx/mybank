package org.example;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyBank extends AbstractTableModel {
    private final String[] columns = new String[]{"ID", "Date", "Type", "Amount", "Description"};

    private final List<Transaction> transactions;

    public MyBank() {
        this.transactions = new ArrayList<>();
        this.transactions.add(new Transaction(1, new Date(2022 - 1900, Calendar.MARCH, 5, 10, 23, 20), 22.04, TransactionType.Credit, "Bought something"));
        this.transactions.add(new Transaction(2, new Date(2022 - 1900, Calendar.APRIL, 6, 14, 57, 11), 40.84, TransactionType.Debit, "Deposit something"));
    }

    public Money getTotal() {
        double total = transactions.stream()
                .map(Transaction::getValue)
                .reduce(0.0, Double::sum);

        return new Money(total);
    }

    public void createTransaction(double amount, TransactionType type, String description) {
        transactions.add(new Transaction(transactions.size()+1, new Date(), amount, type, description));
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return transactions.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Transaction tr = transactions.get(rowIndex);
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

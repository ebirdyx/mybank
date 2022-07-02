package org.example;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow(MyBank bank) {
        super("MyBank");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        JPanel totalPanel = new JPanel();
        JLabel totalLabel = new JLabel("Total: ");
        JLabel totalValue = new JLabel(bank.getTotal().formatAsCurrency());
        totalPanel.add(totalLabel);
        totalPanel.add(totalValue);

        JPanel tablePanel = new JPanel();
        JTable transactionsTable = new JTable(bank);
        tablePanel.add(new JScrollPane(transactionsTable));

        JPanel actionsPanel = new JPanel();
        JButton credit = new JButton("Credit");
        credit.addActionListener(e -> {
            newTransaction(bank, TransactionType.Credit);
            totalValue.setText(bank.getTotal().formatAsCurrency());
        });

        JButton debit = new JButton("Debit");
        debit.addActionListener(e -> {
            newTransaction(bank, TransactionType.Debit);
            totalValue.setText(bank.getTotal().formatAsCurrency());
        });

        actionsPanel.add(credit);
        actionsPanel.add(debit);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(totalPanel);
        add(tablePanel);
        add(actionsPanel);

        setVisible(true);
    }

    private void newTransaction(MyBank bank, TransactionType type) {
        JTextField amount = new JTextField();
        JTextArea description = new JTextArea();
        Object[] message = {
                "Amount:", amount,
                "Description:", description
        };

        int option = JOptionPane.showConfirmDialog(null, message, type.toString() + " transaction", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            bank.createTransaction(Double.parseDouble(amount.getText()), type, description.getText());
        }
    }
}

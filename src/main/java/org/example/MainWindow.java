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
        JTable transactionsTable = new JTable(bank.getTransactionsTable(), bank.getColumns());
        tablePanel.add(new JScrollPane(transactionsTable));

        JPanel actionsPanel = new JPanel();
        JButton credit = new JButton("Credit");
        JButton debit = new JButton("Debit");
        actionsPanel.add(credit);
        actionsPanel.add(debit);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(totalPanel);
        add(tablePanel);
        add(actionsPanel);

        setVisible(true);
    }
}

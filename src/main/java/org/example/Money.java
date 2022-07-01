package org.example;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Money {
    private double value;

    public Money(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public String formatAsCurrency() {
        NumberFormat formatter = new DecimalFormat("##.00");
        return formatter.format(value);
    }
}

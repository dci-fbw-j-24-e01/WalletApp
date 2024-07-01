package org.dci.walletapp;

import android.os.Build;

import java.time.LocalDateTime;

public class Transaction {
    private boolean income;
    private double amount;
    private LocalDateTime dateTime;
    private String source;
    private String description;

    public Transaction(double amount, String description, boolean income, String source) {
        this.amount = amount;
        this.description = description;
        this.income = income;
        this.source = source;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateTime = LocalDateTime.now();
        }
    }

    public Transaction(double amount, LocalDateTime dateTime, String description, boolean income, String source) {
        this.amount = amount;
        this.dateTime = dateTime;
        this.description = description;
        this.income = income;
        this.source = source;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIncome() {
        return income;
    }

    public void setIncome(boolean income) {
        this.income = income;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String incomeSource) {
        this.source = incomeSource;
    }
}
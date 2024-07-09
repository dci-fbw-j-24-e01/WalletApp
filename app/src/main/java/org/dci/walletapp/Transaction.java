package org.dci.walletapp;

import android.content.Context;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Transaction {

    private static List<Transaction> transactionsList;

    public static List<Transaction> getTransactionsList(Context context) {
        if (transactionsList == null) {
            transactionsList = JsonFilesOperations.getInstance().readTransactions(context);
        }
        return transactionsList;
    }

    private boolean income;
    private double amount;
    private LocalDateTime dateTime;
    private String category;
    private String description;

    public Transaction(double amount, String description, boolean income, String category) {
        this.amount = amount;
        this.description = description;
        this.income = income;
        this.category = category;
        dateTime = LocalDateTime.now();

        if (transactionsList == null) {
            transactionsList = new ArrayList<>();
        }
    }

    public Transaction(double amount, LocalDateTime dateTime, String description, boolean income, String category) {
        this.amount = amount;
        this.dateTime = dateTime;
        this.description = description;
        this.income = income;
        this.category = category;

        if (transactionsList == null) {
            transactionsList = new ArrayList<>();
        }
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String incomeSource) {
        this.category = incomeSource;
    }
}
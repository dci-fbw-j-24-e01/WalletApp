package org.dci.walletapp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
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
    }

    public Transaction(double amount, LocalDateTime dateTime, String description, boolean income, String category) {
        this.amount = amount;
        this.dateTime = dateTime;
        this.description = description;
        this.income = income;
        this.category = category;
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

    public String formatDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = getDateTime().format(formatter);
        return formatDateTime;
    }
}
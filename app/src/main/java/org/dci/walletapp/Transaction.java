package org.dci.walletapp;

import java.time.LocalDate;

public class Transaction {
    private boolean income;
    private double amount;
    private LocalDate date;
    private String category;
    private String description;

    public Transaction(double amount, String description, boolean income, String category) {
        this.amount = amount;
        this.description = description;
        this.income = income;
        this.category = category;
        date = LocalDate.now();
    }
    public Transaction(double amount, LocalDate date, String description, boolean income, String category) {
        this.amount = amount;
        this.date = date;
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
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public LocalDate getDate() {
        return date;
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
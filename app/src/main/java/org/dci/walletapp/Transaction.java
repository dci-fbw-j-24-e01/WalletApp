package org.dci.walletapp;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;

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

    public String getFormattedAmount(){
        return String.format(Locale.ENGLISH, "%.2f €", amount );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return income == that.income && Double.compare(amount, that.amount) == 0 && Objects.equals(dateTime, that.dateTime) && Objects.equals(category, that.category) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(income, amount, dateTime, category, description);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "income=" + income +
                ", amount=" + amount +
                ", dateTime=" + dateTime +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
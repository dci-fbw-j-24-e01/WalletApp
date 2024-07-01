package org.dci.walletapp;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Income {

    private double amount;
    private List<Source> sources;
    private LocalDateTime date;
    private String description;

    public Income(double amount, List<Source> sources, LocalDateTime date, String description) {
        this.amount = amount;
        this.sources = sources;
        this.date = date;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Income{" +
                "amount=" + amount +
                ", sources=" + sources +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }
}

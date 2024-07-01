package org.dci.walletapp;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Source {
    private String salary;
    private String bonus;
    private String others;

    public Source(String salary, String bonus, String others) {
        this.salary = salary;
        this.bonus = bonus;
        this.others = others;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Source source = (Source) o;
        return Objects.equals(salary, source.salary) && Objects.equals(bonus, source.bonus) && Objects.equals(others, source.others);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salary, bonus, others);
    }

    @NonNull
    @Override
    public String toString() {
        return "Source{" +
                "salary='" + salary + '\'' +
                ", bonus='" + bonus + '\'' +
                ", others='" + others + '\'' +
                '}';
    }
}

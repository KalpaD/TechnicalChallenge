package com.kds.serviceabilitycal.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Application {

    @JsonProperty("incomes")
    List<Item> incomes;
    @JsonProperty("expenses")
    List<Item> expenses;

    public Application() {
    }

    public List<Item> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<Item> incomes) {
        this.incomes = incomes;
    }

    public List<Item> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Item> expenses) {
        this.expenses = expenses;
    }
}

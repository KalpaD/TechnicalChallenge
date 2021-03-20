package com.kds.serviceabilitycal.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class Application {

    @JsonProperty("incomes")
    @Valid
    @NotNull(message = "incomes cannot be null")
    @Size(min = 1, message = "incomes list must contain at least one item")
    List<Item> incomes;

    @JsonProperty("expenses")
    @Valid
    @NotNull(message = "expenses cannot be null")
    @Size(min = 1, message = "expenses list must contain at least one item")
    List<Item> expenses;

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


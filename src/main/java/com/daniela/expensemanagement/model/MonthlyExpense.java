package com.daniela.expensemanagement.model;


import jakarta.persistence.Tuple;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyExpense {
    private Integer month;
    private Double total;
}

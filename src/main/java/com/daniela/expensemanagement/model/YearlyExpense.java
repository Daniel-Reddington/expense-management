package com.daniela.expensemanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YearlyExpense {
    private Integer year;
    private Double total;
}

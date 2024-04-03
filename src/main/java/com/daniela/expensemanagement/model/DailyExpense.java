package com.daniela.expensemanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DailyExpense {
    private LocalDate localDate;
    private Double total;
}

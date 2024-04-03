package com.daniela.expensemanagement.services.interfaces;

import com.daniela.expensemanagement.model.DailyExpense;
import com.daniela.expensemanagement.model.MonthlyExpense;
import com.daniela.expensemanagement.model.YearlyExpense;

import java.util.List;

public interface StatisticService {
    List<DailyExpense> dailyExpense(int year, int month);
    List<MonthlyExpense> monthlyExpense(int year);
    List<YearlyExpense> yearlyExpense();
}

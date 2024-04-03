package com.daniela.expensemanagement.services.impl;

import com.daniela.expensemanagement.entities.Expense;
import com.daniela.expensemanagement.entities.UserAccount;
import com.daniela.expensemanagement.model.DailyExpense;
import com.daniela.expensemanagement.model.MonthlyExpense;
import com.daniela.expensemanagement.model.YearlyExpense;
import com.daniela.expensemanagement.repositories.ExpenseRepository;
import com.daniela.expensemanagement.services.interfaces.StatisticService;
import com.daniela.expensemanagement.services.interfaces.UserAccountService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StatisticServiceImpl implements StatisticService {

    private final ExpenseRepository expenseRepository;
    private final UserAccountService userAccountService;

    @Override
    public List<DailyExpense> dailyExpense(int year, int month){
        return expenseRepository
                .findDailyTotalExpensesByMonth(userAccountService.getConnectedUser(),month, year);
    }

    @Override
    public List<MonthlyExpense> monthlyExpense(int year){
        return expenseRepository.findMonthlyTotalExpensesByYear(userAccountService.getConnectedUser(), year);
    }

    @Override
    public List<YearlyExpense> yearlyExpense(){
        return expenseRepository.findYearlyTotalExpenses(userAccountService.getConnectedUser());
    }


}

package com.daniela.expensemanagement.services.interfaces;

import com.daniela.expensemanagement.entities.Expense;
import com.daniela.expensemanagement.entities.UserAccount;

import java.util.List;

public interface ExpenseService {
    Expense save(Expense expense);
    Expense saveFromBudget(Expense expense, String budgetCategory);
    List<Expense> findAllByUserAccount(UserAccount userAccount);
}

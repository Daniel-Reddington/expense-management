package com.daniela.expensemanagement.services.interfaces;

import com.daniela.expensemanagement.entities.Budget;
import com.daniela.expensemanagement.entities.UserAccount;
import javafx.collections.ObservableList;

import java.util.List;

public interface BudgetService {
    Budget save(Budget budget);
    Budget saveFromIncome(Budget budget, String incomeSource);
    List<Budget> findAllByUserAccount(UserAccount userAccount);
    Budget findByCategoryAndUserAccount(String category, UserAccount userAccount);
    ObservableList<Budget> updatedObservableList(ObservableList<Budget> budgetObservableList, Budget budget);
}

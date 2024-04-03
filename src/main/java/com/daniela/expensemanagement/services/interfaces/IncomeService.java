package com.daniela.expensemanagement.services.interfaces;

import com.daniela.expensemanagement.entities.Income;
import com.daniela.expensemanagement.entities.UserAccount;
import javafx.collections.ObservableList;

import java.util.List;

public interface IncomeService {
    Income save(Income income);
    Income saveOrUpdate(Income income);
    List<Income> findAllByUserAccount(UserAccount userAccount);
    Income findBySourceAndUserAccount(String incomeSource, UserAccount userAccount);
    ObservableList<Income> updatedObservableList(ObservableList<Income> incomeObservableList, Income income);
}

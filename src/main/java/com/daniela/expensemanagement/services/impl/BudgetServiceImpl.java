package com.daniela.expensemanagement.services.impl;

import com.daniela.expensemanagement.entities.Budget;
import com.daniela.expensemanagement.entities.Income;
import com.daniela.expensemanagement.entities.UserAccount;
import com.daniela.expensemanagement.repositories.BudgetRepository;
import com.daniela.expensemanagement.repositories.IncomeRepository;
import com.daniela.expensemanagement.services.interfaces.BudgetService;
import com.daniela.expensemanagement.services.interfaces.IncomeService;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final IncomeService incomeService;

    @Override
    public Budget saveFromIncome(Budget budget, String incomeSource) {
        Income income = incomeService.findBySourceAndUserAccount(incomeSource, budget.getUserAccount());

        if(income.getAmount() >= budget.getAmount()){
            Double incomeAmount = income.getAmount() - budget.getAmount();
            income.setAmount(incomeAmount);

            Budget existBudget = budgetRepository.findByCategoryAndUserAccount(budget.getCategory(), budget.getUserAccount());

            if(existBudget != null){
                Double budgetAmount = existBudget.getAmount() + budget.getAmount();
                existBudget.setAmount(budgetAmount);
            }else {
                existBudget = budget;
            }

            existBudget.getIncomes().add(income);

            return budgetRepository.save(existBudget);
        }
        return null;

    }

    @Override
    public Budget save(Budget budget){
        return budgetRepository.save(budget);
    }

    @Override
    public List<Budget> findAllByUserAccount(UserAccount userAccount) {
        return budgetRepository.findAllByUserAccount(userAccount);
    }

    @Override
    public Budget findByCategoryAndUserAccount(String category, UserAccount userAccount) {
        return budgetRepository.findByCategoryAndUserAccount(category, userAccount);
    }

    @Override
    public ObservableList<Budget> updatedObservableList(ObservableList<Budget> budgetObservableList, Budget budget) {

        int index = budgetObservableList.indexOf(budgetObservableList.filtered(findedBudget-> findedBudget.getBudgetId() == budget.getBudgetId()).stream().findFirst().orElse(null));

        if(index != -1){
            budgetObservableList.set(index, budget);
        }else {
            budgetObservableList.add(budget);
        }
        return budgetObservableList;
    }
}

package com.daniela.expensemanagement.services.impl;

import com.daniela.expensemanagement.entities.Budget;
import com.daniela.expensemanagement.entities.Expense;
import com.daniela.expensemanagement.entities.UserAccount;
import com.daniela.expensemanagement.repositories.ExpenseRepository;
import com.daniela.expensemanagement.services.interfaces.BudgetService;
import com.daniela.expensemanagement.services.interfaces.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final BudgetService budgetService;
    @Override
    public Expense saveFromBudget(Expense expense, String budgetCategory) {

        Budget budget = budgetService.findByCategoryAndUserAccount(budgetCategory, expense.getUserAccount());
        if(budget != null && budget.getAmount() >= expense.getPrice()){
            Double amount = budget.getAmount() - expense.getPrice();
            budget.setAmount(amount);

            expense.setBudget(budget);
            return save(expense);
        }

        return null;
    }

    @Override
    public Expense save(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public List<Expense> findAllByUserAccount(UserAccount userAccount) {
        return expenseRepository.findAllByUserAccount(userAccount);
    }
}

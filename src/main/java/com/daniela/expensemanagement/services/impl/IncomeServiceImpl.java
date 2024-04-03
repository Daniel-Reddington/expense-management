package com.daniela.expensemanagement.services.impl;

import com.daniela.expensemanagement.entities.Income;
import com.daniela.expensemanagement.entities.UserAccount;
import com.daniela.expensemanagement.repositories.IncomeRepository;
import com.daniela.expensemanagement.services.interfaces.IncomeService;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;

    @Override
    public Income save(Income income) {
        return incomeRepository.save(income);
    }

    @Override
    public Income saveOrUpdate(Income income) {
        Income existIncome = incomeRepository.findBySourceAndUserAccount(income.getSource(), income.getUserAccount());
        Income savedIncome;

        if(existIncome != null){
            Double newAmount = income.getAmount() + existIncome.getAmount();
            existIncome.setAmount(newAmount);
            existIncome.setUpdatedAt(LocalDateTime.now());
            savedIncome = incomeRepository.save(existIncome);
        }else {
            savedIncome = save(income);
        }
        return savedIncome;
    }

    @Override
    public List<Income> findAllByUserAccount(UserAccount userAccount) {
        return incomeRepository.findAllByUserAccount(userAccount);
    }

    @Override
    public Income findBySourceAndUserAccount(String incomeSource, UserAccount userAccount) {
        return incomeRepository.findBySourceAndUserAccount(incomeSource.toUpperCase(), userAccount);
    }

    @Override
    public ObservableList<Income> updatedObservableList(ObservableList<Income> incomeObservableList, Income income) {

        int index = incomeObservableList.indexOf(incomeObservableList.filtered(findedIncome-> findedIncome.getIncomeId() == income.getIncomeId()).stream().findFirst().orElse(null));

        if(index != -1){
            incomeObservableList.set(index, income);
        }else {
            incomeObservableList.add(income);
        }
        return incomeObservableList;
    }
}

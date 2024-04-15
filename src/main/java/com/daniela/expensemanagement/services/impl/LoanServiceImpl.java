package com.daniela.expensemanagement.services.impl;

import com.daniela.expensemanagement.entities.Income;
import com.daniela.expensemanagement.entities.Loan;
import com.daniela.expensemanagement.repositories.LoanRepository;
import com.daniela.expensemanagement.services.interfaces.IncomeService;
import com.daniela.expensemanagement.services.interfaces.LoanService;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final IncomeService incomeService;
    @Override
    public Loan save(Loan loan, String incomeSource) {
        Income income = incomeService.findBySourceAndUserAccount(incomeSource, loan.getUserAccount());

        if(income != null && income.getAmount() >= loan.getTotalAmount()){
            income.setAmount(income.getAmount() - loan.getTotalAmount());

            loan.getIncomes().add(income);
            return loanRepository.save(loan);

        }
        return null;
    }

    @Override
    public ObservableList<Loan> updatedObservableList(ObservableList<Loan> loanObservableList, Loan loan) {
        int index = loanObservableList.indexOf(loanObservableList.filtered(findedLoan-> findedLoan.getLoanId() == loan.getLoanId()).stream().findFirst().orElse(null));

        if(index != -1){
            loanObservableList.set(index, loan);
        }else {
            loanObservableList.add(loan);
        }
        return loanObservableList;
    }
}

package com.daniela.expensemanagement.services.interfaces;

import com.daniela.expensemanagement.entities.Loan;
import com.daniela.expensemanagement.entities.UserAccount;
import javafx.collections.ObservableList;

import java.util.List;

public interface LoanService {
    Loan save(Loan loan, String incomeSource);
    Loan setLoanPay(Loan loan);
    ObservableList<Loan> updatedObservableList(ObservableList<Loan> loanObservableList, Loan loan);

    List<Loan> findAllByUserAccount(UserAccount connectedUser);
}

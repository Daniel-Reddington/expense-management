package com.daniela.expensemanagement.services.interfaces;

import com.daniela.expensemanagement.entities.Loan;
import javafx.collections.ObservableList;

public interface LoanService {
    Loan save(Loan loan, String incomeSource);
    ObservableList<Loan> updatedObservableList(ObservableList<Loan> loanObservableList, Loan loan);
}

package com.daniela.expensemanagement.repositories;

import com.daniela.expensemanagement.entities.Loan;
import com.daniela.expensemanagement.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUserAccount(UserAccount userAccount);
}

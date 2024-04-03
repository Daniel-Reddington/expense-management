package com.daniela.expensemanagement.repositories;

import com.daniela.expensemanagement.entities.Income;
import com.daniela.expensemanagement.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    Income findBySourceAndUserAccount(String incomeSource, UserAccount userAccount);
    List<Income> findAllByUserAccount(UserAccount userAccount);
}

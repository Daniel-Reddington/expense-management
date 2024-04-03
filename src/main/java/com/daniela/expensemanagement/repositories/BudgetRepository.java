package com.daniela.expensemanagement.repositories;

import com.daniela.expensemanagement.entities.Budget;
import com.daniela.expensemanagement.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Budget findByCategoryAndUserAccount(String category, UserAccount userAccount);

    List<Budget> findAllByUserAccount(UserAccount userAccount);
}

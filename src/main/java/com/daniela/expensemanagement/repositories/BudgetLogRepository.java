package com.daniela.expensemanagement.repositories;

import com.daniela.expensemanagement.entities.BudgetLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetLogRepository extends JpaRepository<BudgetLog, Long> {
}

package com.daniela.expensemanagement.repositories;

import com.daniela.expensemanagement.entities.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeLogRepository extends JpaRepository<Income, Long> {
}

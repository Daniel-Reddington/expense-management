package com.daniela.expensemanagement.repositories;

import com.daniela.expensemanagement.entities.Expense;
import com.daniela.expensemanagement.entities.UserAccount;
import com.daniela.expensemanagement.model.DailyExpense;
import com.daniela.expensemanagement.model.MonthlyExpense;
import com.daniela.expensemanagement.model.YearlyExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByUserAccount(UserAccount userAccount);
    @Query("SELECT new com.daniela.expensemanagement.model.DailyExpense(e.expenseDate, SUM(e.price)) FROM Expense e WHERE e.userAccount = :user AND MONTH(e.expenseDate) = :month AND YEAR(e.expenseDate) = :year GROUP BY e.expenseDate")
    List<DailyExpense> findDailyTotalExpensesByMonth(@Param("user") UserAccount user, @Param("month") int month, @Param("year") int year);
    @Query("SELECT new com.daniela.expensemanagement.model.MonthlyExpense(MONTH(e.expenseDate), SUM(e.price)) FROM Expense e WHERE e.userAccount = :user AND YEAR(e.expenseDate) = :year GROUP BY MONTH(e.expenseDate)")
    List<MonthlyExpense> findMonthlyTotalExpensesByYear(@Param("user") UserAccount user, @Param("year") int year);
    @Query("SELECT new com.daniela.expensemanagement.model.YearlyExpense(YEAR(e.expenseDate), SUM(e.price)) FROM Expense e WHERE e.userAccount = :user GROUP BY YEAR(e.expenseDate)")
    List<YearlyExpense> findYearlyTotalExpenses(@Param("user") UserAccount user);

}

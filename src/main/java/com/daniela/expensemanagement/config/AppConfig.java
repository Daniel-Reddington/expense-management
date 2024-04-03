package com.daniela.expensemanagement.config;

import com.daniela.expensemanagement.entities.Budget;
import com.daniela.expensemanagement.entities.Expense;
import com.daniela.expensemanagement.entities.Income;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.chart.XYChart;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;


@Configuration
public class AppConfig {

    @Bean
    public ObservableList<Income> incomeObservableList(){
        return FXCollections.observableArrayList();
    }

    @Bean
    public FilteredList<Income> filteredIncomeList(@Autowired ObservableList<Income> incomeObservableList) {
        return new FilteredList<>(incomeObservableList);
    }

    @Bean
    public ObservableList<Budget> budgetObservableList(){
        return FXCollections.observableArrayList();
    }

    @Bean
    public FilteredList<Budget> filteredBudgetList(@Autowired ObservableList<Budget> budgetObservableList) {
        return new FilteredList<>(budgetObservableList);
    }

    @Bean
    public ObservableList<Expense> expenseObservableList(){
        return FXCollections.observableArrayList();
    }

    @Bean
    public FilteredList<Expense> filteredExpenseList(@Autowired ObservableList<Expense> expenseObservableList) {
        return new FilteredList<>(expenseObservableList);
    }


}

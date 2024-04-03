package com.daniela.expensemanagement.controllers;

import com.daniela.expensemanagement.ExpenseManagementApplication;
import com.daniela.expensemanagement.SpringFXMLLoader;
import com.daniela.expensemanagement.entities.Budget;
import com.daniela.expensemanagement.entities.UserAccount;
import com.daniela.expensemanagement.enumeration.Currency;
import com.daniela.expensemanagement.services.interfaces.BudgetService;
import com.daniela.expensemanagement.services.interfaces.IncomeService;
import com.daniela.expensemanagement.services.interfaces.UserAccountService;
import com.daniela.expensemanagement.utils.Modal;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class BudgetController implements Initializable {

    @FXML
    private TableColumn<Budget, Double> amount;

    @FXML
    private TextField amountTextField;

    @FXML
    private TableView<Budget> budgetTableView;

    @FXML
    private TableColumn<Budget, String> category;

    @FXML
    private TextField categoryTextField;

    @FXML
    private TableColumn<Budget, LocalDateTime> createdAt;

    @FXML
    private TableColumn<Budget, Currency> currency;

    @FXML
    private ChoiceBox<Currency> currencyChoiceBox;

    @FXML
    private ChoiceBox<String> incomeSourceChoiceBox;

    @FXML
    private TextField keywordTextField;

    @FXML
    private TableColumn<Budget, LocalDateTime> updatedAt;

    private final BudgetService budgetService;
    private final UserAccountService userAccountService;
    private final IncomeService incomeService;
    private final ObservableList<Budget> budgetObservableList;
    private final FilteredList<Budget> budgetFilteredList;
    private final Modal modal;

    @FXML
    void save(ActionEvent event) {

        UserAccount userAccount = userAccountService.getConnectedUser();

        Budget budget = Budget
                .builder()
                .category(categoryTextField.getText())
                .amount(Double.valueOf(amountTextField.getText()))
                .currency(currencyChoiceBox.getValue())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .userAccount(userAccount)
                .incomes(new HashSet<>())
                .build();

        Budget savedBudget = budgetService.saveFromIncome(budget, incomeSourceChoiceBox.getValue());
        if(savedBudget == null){
            modal.openModal("/com.daniela.expensemanagement/alert.fxml");
            return;
        }
        budgetService.updatedObservableList(budgetObservableList, savedBudget);
    }

    public void findAllByUserAccount(UserAccount userAccount){
        budgetObservableList.addAll(budgetService.findAllByUserAccount(userAccount));
        budgetTableView.setItems(budgetObservableList);
    }

    @FXML
    void filter(KeyEvent event) {
        budgetTableView.setItems(budgetFilteredList);
        budgetFilteredList.setPredicate(budget ->
            budget.getCategory().toUpperCase().contains(keywordTextField.getText().toUpperCase())
        );
    }

    public void initializeIncomeSource(){
        List<String> incomeSources = incomeService
                .findAllByUserAccount(userAccountService.getConnectedUser())
                .stream()
                .map(income -> income.getSource())
                .collect(Collectors.toList());
        incomeSourceChoiceBox.getItems().addAll(incomeSources);
        incomeSourceChoiceBox.setValue(incomeSources.stream().findFirst().orElse(null));
    }

    private void initializeTableView(){
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        currency.setCellValueFactory(new PropertyValueFactory<>("currency"));
        createdAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        updatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));
    }

    private void disableTextField(){
        incomeSourceChoiceBox.setDisable(true);
        categoryTextField.setDisable(true);
        amountTextField.setDisable(true);
        currencyChoiceBox.setDisable(true);
    }

    void autocomplete() {
        Callback<AutoCompletionBinding.ISuggestionRequest, Collection<String>> suggestionProvider =
                request -> budgetObservableList
                        .stream()
                        .map(Budget::getCategory)
                        .filter(cat-> cat.toUpperCase().contains(request.getUserText().toUpperCase()))
                        .collect(Collectors.toSet());

        TextFields.bindAutoCompletion(categoryTextField, suggestionProvider);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        budgetObservableList.clear();
        initializeIncomeSource();
        initializeTableView();

        currencyChoiceBox.getItems().addAll(Currency.values());
        currencyChoiceBox.setValue(Currency.ARIARY);

        findAllByUserAccount(userAccountService.getConnectedUser());

        autocomplete();

        if(incomeSourceChoiceBox.getItems().isEmpty()){
            disableTextField();
        }
    }

}

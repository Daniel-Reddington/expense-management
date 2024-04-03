package com.daniela.expensemanagement.controllers;

import com.daniela.expensemanagement.entities.Budget;
import com.daniela.expensemanagement.entities.Expense;
import com.daniela.expensemanagement.entities.Income;
import com.daniela.expensemanagement.entities.UserAccount;
import com.daniela.expensemanagement.enumeration.Currency;
import com.daniela.expensemanagement.services.interfaces.BudgetService;
import com.daniela.expensemanagement.services.interfaces.ExpenseService;
import com.daniela.expensemanagement.services.interfaces.UserAccountService;
import com.daniela.expensemanagement.utils.Modal;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExpenseController implements Initializable {
    @FXML
    private TableColumn<Expense, Double> price;

    @FXML
    private ChoiceBox<String> budgetCategory;

    @FXML
    private TableColumn<Expense, String> category;

    @FXML
    private TableColumn<Expense, LocalDateTime> expenseDate;

    @FXML
    private TableColumn<Expense, Currency> currency;
    @FXML
    private TableColumn<Expense, String> motif;

    @FXML
    private ChoiceBox<Currency> currencyChoiceBox;

    @FXML
    private DatePicker expenseDatePicker;

    @FXML
    private TableView<Expense> expenseTableView;

    @FXML
    private TextField keywordTextField;

    @FXML
    private TextField motifTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField quantityTextField;


    private final UserAccountService userAccountService;
    private final ExpenseService expenseService;
    private final BudgetService budgetService;

    private final ObservableList<Expense> expenseObservableList;
    private final FilteredList<Expense> expenseFilteredList;
    private final Modal modal;

    @FXML
    void save(ActionEvent event) {

        UserAccount userAccount = userAccountService.getConnectedUser();

        Expense expense = Expense.builder()
                .motif(motifTextField.getText())
                .price(Double.valueOf(priceTextField.getText()))
                .quantity(Integer.valueOf(quantityTextField.getText()))
                .currency(currencyChoiceBox.getValue())
                .expenseDate(expenseDatePicker.getValue())
                .userAccount(userAccount)
                .build();

        Expense savedExpense = expenseService.saveFromBudget(expense, budgetCategory.getValue());

        if(savedExpense == null){
            modal.openModal("/com.daniela.expensemanagement/alert.fxml");
            return;
        }

        expenseObservableList.add(savedExpense);
    }

    @FXML
    void filter(KeyEvent event) {
        expenseTableView.setItems(expenseFilteredList);
        expenseFilteredList.setPredicate(expense ->
                expense.getMotif().toUpperCase()
                        .contains(keywordTextField.getText().toUpperCase()) ||
                        expense.getBudget().getCategory().toUpperCase()
                                .contains(keywordTextField.getText().toUpperCase())
        );
    }

    void findAllByUserAccount(UserAccount userAccount){
        expenseObservableList.addAll(expenseService.findAllByUserAccount(userAccount));
        expenseTableView.setItems(expenseObservableList);
    }

    private void initializeBudgetCategory(){
        List<String> category = budgetService
                .findAllByUserAccount(userAccountService.getConnectedUser())
                .stream()
                .map(budget -> budget.getCategory())
                .collect(Collectors.toList());

        budgetCategory.getItems().addAll(category);
        budgetCategory.setValue(category.stream().findFirst().orElse(null));
    }

    private void initializeTableView(){
        category.setCellValueFactory(cellData->{
            Expense expense = cellData.getValue();
            return new SimpleStringProperty(expense.getBudget().getCategory());
        });
        motif.setCellValueFactory(new PropertyValueFactory<>("motif"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        currency.setCellValueFactory(new PropertyValueFactory<>("currency"));
        expenseDate.setCellValueFactory(new PropertyValueFactory<>("expenseDate"));
    }

    private void disableTextField(){
        budgetCategory.setDisable(true);
        motifTextField.setDisable(true);
        quantityTextField.setDisable(true);
        priceTextField.setDisable(true);
        currencyChoiceBox.setDisable(true);
        expenseDatePicker.setDisable(true);
    }

    void autocomplete() {
        Callback<AutoCompletionBinding.ISuggestionRequest, Collection<String>> suggestionProvider =
                request -> expenseObservableList
                        .stream()
                        .map(Expense::getMotif)
                        .filter(motif-> motif.toUpperCase().contains(request.getUserText().toUpperCase()))
                        .collect(Collectors.toSet());

        TextFields.bindAutoCompletion(motifTextField, suggestionProvider);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        expenseObservableList.clear();

        expenseDatePicker.setValue(LocalDate.now());

        initializeBudgetCategory();
        initializeTableView();

        currencyChoiceBox.getItems().addAll(Currency.values());
        currencyChoiceBox.setValue(Currency.ARIARY);

        findAllByUserAccount(userAccountService.getConnectedUser());
        autocomplete();

        if(budgetCategory.getItems().isEmpty()){
            disableTextField();
        }
    }
}

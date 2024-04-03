package com.daniela.expensemanagement.controllers;

import com.daniela.expensemanagement.entities.Budget;
import com.daniela.expensemanagement.entities.Income;
import com.daniela.expensemanagement.entities.UserAccount;
import com.daniela.expensemanagement.enumeration.Currency;
import com.daniela.expensemanagement.services.interfaces.IncomeService;
import com.daniela.expensemanagement.services.interfaces.UserAccountService;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class IncomeController implements Initializable {

    @FXML
    private TextField amountTextField;

    @FXML
    private ChoiceBox<Currency> currencyChoiceBox;

    @FXML
    private TableView<Income> incomeTableView;
    @FXML
    private TableColumn<Income, String> source;
    @FXML
    private TableColumn<Income, Double> amount;
    @FXML
    private TableColumn<Income, Currency> currency;
    @FXML
    private TableColumn<Income, LocalDateTime> createdAt;
    @FXML
    private TableColumn<Income, LocalDateTime> updatedAt;
    @FXML
    private TextField keywordTextField;


    @FXML
    private TextField sourceTextField;

    private final IncomeService incomeService;

    private final UserAccountService userAccountService;

    private final ObservableList<Income> incomeObservableList;
    private final FilteredList<Income> incomeFilteredList;

    @FXML
    void save(ActionEvent event) {
        UserAccount userAccount = userAccountService.getConnectedUser();

        Income income = Income
                .builder()
                .amount(Double.valueOf(amountTextField.getText()))
                .currency(currencyChoiceBox.getValue())
                .source(sourceTextField.getText().toUpperCase())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .userAccount(userAccount)
                .build();

        Income savedIncome = incomeService.saveOrUpdate(income);
        //incomeObservableList.add(savedIncome);
        incomeService.updatedObservableList(incomeObservableList, savedIncome);
    }

    @FXML
    void filter(KeyEvent event) {
        incomeTableView.setItems(incomeFilteredList);
        incomeFilteredList
                .setPredicate(income ->
                        income.getSource().toUpperCase()
                                .contains(keywordTextField.getText().toUpperCase()));
    }

    void autocomplete() {
        Callback<AutoCompletionBinding.ISuggestionRequest, Collection<String>> suggestionProvider =
                request -> incomeObservableList
                        .stream()
                        .map(Income::getSource)
                        .filter(source-> source.toUpperCase().contains(request.getUserText().toUpperCase()))
                        .collect(Collectors.toSet());

        TextFields.bindAutoCompletion(sourceTextField, suggestionProvider);
    }

    private void findAllByUserAccount(){
        incomeObservableList.addAll(incomeService.findAllByUserAccount(userAccountService.getConnectedUser()));
        incomeTableView.setItems(incomeObservableList);
    }

    private void initializeTableView(){
        source.setCellValueFactory(new PropertyValueFactory<>("source"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        currency.setCellValueFactory(new PropertyValueFactory<>("currency"));
        createdAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        updatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        incomeObservableList.clear();

        currencyChoiceBox.setValue(Currency.ARIARY);
        currencyChoiceBox.getItems().addAll(Currency.values());

        initializeTableView();
        findAllByUserAccount();

        autocomplete();
    }

}

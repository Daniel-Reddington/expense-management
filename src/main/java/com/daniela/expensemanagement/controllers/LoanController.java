package com.daniela.expensemanagement.controllers;

import com.daniela.expensemanagement.entities.Loan;
import com.daniela.expensemanagement.entities.UserAccount;
import com.daniela.expensemanagement.enumeration.Currency;
import com.daniela.expensemanagement.services.interfaces.LoanService;
import com.daniela.expensemanagement.services.interfaces.UserAccountService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoanController {
    @FXML
    private TableColumn<Loan, Double> amount;

    @FXML
    private TextField amountTextField;

    @FXML
    private TableColumn<Loan, LocalDateTime> borrowDate;

    @FXML
    private TableColumn<Loan, String> borrowerName;

    @FXML
    private TextField borrowerNameTextField;

    @FXML
    private TableColumn<Loan, Currency> currency;

    @FXML
    private ChoiceBox<Currency> currencyChoiceBox;

    @FXML
    private TableView<Loan> loanTableView;

    @FXML
    private TableColumn<Loan, Boolean> isReturn;

    @FXML
    private TextField keywordTextField;

    @FXML
    private TableColumn<Loan, LocalDate> returnDate;

    @FXML
    private DatePicker returnDatePicker;

    @FXML
    private TableColumn<Loan, String> source;

    @FXML
    private ChoiceBox<String> incomeChoiceBox;

    private final UserAccountService userAccountService;
    private final LoanService loanService;

    private final ObservableList<Loan> loanObservableList;

    @FXML
    void filter(KeyEvent event) {

    }

    @FXML
    void save(ActionEvent event) {

        UserAccount userAccount = userAccountService.getConnectedUser();

        Loan loan = Loan
                .builder()
                .borrowerName(borrowerNameTextField.getText())
                .totalAmount(Double.valueOf(amountTextField.getText()))
                .currency(currencyChoiceBox.getValue())
                .borrowDate(LocalDateTime.now())
                .returnDate(returnDatePicker.getValue())
                .isReturn(false)
                .incomes(new HashSet<>())
                .userAccount(userAccount)
                .build();

        Loan savedLoan = loanService.save(loan, incomeChoiceBox.getValue());

        loanService.updatedObservableList(loanObservableList, savedLoan);

    }

    private void initializeTableView(){
        source.setCellValueFactory(new PropertyValueFactory<>("source"));
        borrowerName.setCellValueFactory(new PropertyValueFactory<>("borrowerName"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        currency.setCellValueFactory(new PropertyValueFactory<>("currency"));
        borrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        isReturn.setCellValueFactory(new PropertyValueFactory<>("isReturn"));
    }
}

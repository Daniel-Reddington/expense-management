package com.daniela.expensemanagement.controllers;

import com.daniela.expensemanagement.entities.Loan;
import com.daniela.expensemanagement.entities.UserAccount;
import com.daniela.expensemanagement.enumeration.Currency;
import com.daniela.expensemanagement.services.interfaces.IncomeService;
import com.daniela.expensemanagement.services.interfaces.LoanService;
import com.daniela.expensemanagement.services.interfaces.UserAccountService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoanController implements Initializable {
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
    private TableColumn<Loan, LocalDate> previewDate;

    @FXML
    private TextField keywordTextField;

    @FXML
    private TableColumn<Loan, LocalDate> returnDate;

    @FXML
    private DatePicker previewDatePicker;

    @FXML
    private ChoiceBox<String> incomeChoiceBox;
    @FXML
    private TableColumn<Loan, Button> payement;

    private final UserAccountService userAccountService;
    private final LoanService loanService;
    private final IncomeService incomeService;

    private final ObservableList<Loan> loanObservableList;
    private final FilteredList<Loan> loanFilteredList;


    @FXML
    void save(ActionEvent event) {

        UserAccount userAccount = userAccountService.getConnectedUser();

        Loan loan = Loan
                .builder()
                .borrowerName(borrowerNameTextField.getText())
                .totalAmount(Double.valueOf(amountTextField.getText()))
                .currency(currencyChoiceBox.getValue())
                .borrowDate(LocalDateTime.now())
                .previewDate(previewDatePicker.getValue())
                .isReturn(false)
                .userAccount(userAccount)
                .build();

        Loan savedLoan = loanService.save(loan, incomeChoiceBox.getValue());

        loanObservableList.add(savedLoan);
        log.info("loan observable list : {}", loanObservableList);

    }

    private void findAllByUserAccount(){
        loanObservableList.addAll(loanService.findAllByUserAccount(userAccountService.getConnectedUser()));
        loanTableView.setItems(loanObservableList);
    }

    @FXML
    void filter(KeyEvent event) {
        loanTableView.setItems(loanFilteredList);
        loanFilteredList.setPredicate(loan ->
                loan.getBorrowerName().toUpperCase().contains(keywordTextField.getText().toUpperCase())
        );
    }

    private void initializeTableView(){
        borrowerName.setCellValueFactory(new PropertyValueFactory<>("borrowerName"));
        amount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        currency.setCellValueFactory(new PropertyValueFactory<>("currency"));
        borrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        previewDate.setCellValueFactory(new PropertyValueFactory<>("previewDate"));
        returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        payement.setCellFactory(param -> new TableCell<>() {
            private final Button paymentButton = new Button("", new FontAwesomeIconView(FontAwesomeIcon.CHECK));

            {
                paymentButton.setOnAction(event -> {
                    // Récupérer la ligne associée au bouton
                    Loan loan = getTableRow().getItem();
                    Loan updatedLoan = loanService.setLoanPay(loan);
                    loanService.updatedObservableList(loanObservableList, updatedLoan);
                    log.info("loan : {}", loan);

                });
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Loan loan = getTableRow().getItem();
                    if (loan != null) {
                        if (loan.getIsReturn()) {
                            // Colorer le bouton en vert si isReturn est vrai
                            paymentButton.setStyle("-fx-background-color: green;");
                            paymentButton.setDisable(true);
                        } else {
                            // Colorer le bouton en rouge si isReturn est faux
                            paymentButton.setStyle("-fx-background-color: red;");
                            paymentButton.setDisable(false);
                        }
                        setGraphic(paymentButton);
                    } else {
                        setGraphic(null);
                    }
                }
            }
        });
    }

    public void initializeIncomeSource(){
        List<String> incomeSources = incomeService
                .findAllByUserAccount(userAccountService.getConnectedUser())
                .stream()
                .map(income -> income.getSource())
                .collect(Collectors.toList());
        incomeChoiceBox.getItems().addAll(incomeSources);
        incomeChoiceBox.setValue(incomeSources.stream().findFirst().orElse(null));
    }

    private void disableTextField(){
        incomeChoiceBox.setDisable(true);
        borrowerNameTextField.setDisable(true);
        amountTextField.setDisable(true);
        currencyChoiceBox.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loanObservableList.clear();
        initializeTableView();
        initializeIncomeSource();
        findAllByUserAccount();
        currencyChoiceBox.getItems().addAll(Currency.values());
        currencyChoiceBox.setValue(Currency.ARIARY);

        if(incomeChoiceBox.getItems().isEmpty()){
            disableTextField();
        }
    }
}

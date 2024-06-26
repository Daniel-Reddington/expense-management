package com.daniela.expensemanagement.controllers;

import com.daniela.expensemanagement.ExpenseManagementApplication;
import com.daniela.expensemanagement.SpringFXMLLoader;
import com.daniela.expensemanagement.model.ResetPasswordResponse;
import com.daniela.expensemanagement.model.Status;
import com.daniela.expensemanagement.services.interfaces.UserAccountService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.UnknownHostException;

@Component
@RequiredArgsConstructor
@Slf4j
public class ForgotPasswordController {

    @FXML
    private TextField emailOrPhoneNumber;

    @FXML
    private HBox hboxError;

    @FXML
    private Label internetErrorLabel;
    private final SpringFXMLLoader fxmlLoader;

    private final UserAccountService userAccountService;

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    void send(ActionEvent event) {
        ResetPasswordResponse response = userAccountService.sendEmail(emailOrPhoneNumber.getText());

        if(response.status().equals(Status.SUCCESS)){
            close(event);
        } else if (response.status().equals(Status.MAIL_NOT_FOUND)) {
            internetErrorLabel.setText("User not exist!");
            hboxError.setVisible(true);
            log.info("response mail not found : {}", response);
        } else if (response.status().equals(Status.MAIL_ERROR)) {
            internetErrorLabel.setText("Mail not sending");
            hboxError.setVisible(true);
            log.info("response mail error : {}", response);
        } else if (response.status().equals(Status.INTERNET)) {
            internetErrorLabel.setText("Please check your internet connection");
            hboxError.setVisible(true);
            log.info("response internet : {}", response);
        }

    }
}

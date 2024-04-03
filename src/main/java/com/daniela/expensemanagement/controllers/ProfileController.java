package com.daniela.expensemanagement.controllers;

import com.daniela.expensemanagement.entities.UserAccount;
import com.daniela.expensemanagement.services.interfaces.UserAccountService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProfileController implements Initializable {

    @FXML
    private Button cancelBtn;
    @FXML
    private Button changePasswordBtn;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField emailTextField;

    @FXML
    private GridPane gridPaneEdited;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField usernameTextField;

    private Label passwordLabel;
    private PasswordField passwordField;
    private Label confirmPasswordLabel;
    private PasswordField confirmPasswordField;
    private final UserAccountService userAccountService;
    private boolean isClicked = false;

    @FXML
    void cancel(ActionEvent event) {
        cancelBtn.setVisible(false);
        changePasswordBtn.setDisable(false);
        isClicked = false;

        gridPaneEdited.getChildren().remove(gridPaneEdited.getChildren().size() - 1);

        gridPaneEdited.getChildren().remove(gridPaneEdited.getChildren().size() - 1);
        gridPaneEdited.getChildren().remove(gridPaneEdited.getChildren().size() - 1);

        gridPaneEdited.getChildren().remove(gridPaneEdited.getChildren().size() - 1);

    }

    private void generateNewLine(){
        passwordLabel = new Label("Password");
        passwordLabel.setFont(new Font(14));
        passwordLabel.setTextFill(Paint.valueOf("WHITE"));

        int lastRow = gridPaneEdited.getRowConstraints().size();
        passwordField = new PasswordField();
        gridPaneEdited.add(passwordLabel, 0, lastRow);
        gridPaneEdited.add(passwordField, 1, lastRow);

        confirmPasswordLabel = new Label("Confirm Password");
        confirmPasswordLabel.setFont(new Font(14));
        confirmPasswordLabel.setTextFill(Paint.valueOf("WHITE"));

        confirmPasswordField = new PasswordField();
        gridPaneEdited.add(confirmPasswordLabel, 0, lastRow + 1);
        gridPaneEdited.add(confirmPasswordField, 1, lastRow + 1);
    }
    @FXML
    void changePassword(ActionEvent event) {
        cancelBtn.setVisible(true);
        changePasswordBtn.setDisable(true);

        generateNewLine();
        isClicked = true;
    }
    @FXML
    void update(ActionEvent event) {
        String username = usernameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String email = emailTextField.getText();

        UserAccount userAccount = userAccountService.getConnectedUser();

        userAccount.setUsername(username.equals("") ? userAccount.getUsername() : username);
        userAccount.setPhoneNumber(phoneNumber.equals("") ? userAccount.getPhoneNumber() : phoneNumber);
        userAccount.setEmail(email.equals("") ? userAccount.getEmail() : email);

        if(isClicked == true &&
                (passwordField !=null && confirmPasswordField!=null) &&
                passwordField.getText().equals(confirmPasswordField.getText())){
            userAccount.setPassword(passwordField.getText());
        }

        UserAccount savedUser = userAccountService.save(userAccount);
        userAccountService.setConnectedUser(savedUser);

        emailLabel.setText(savedUser.getEmail());
        phoneNumberLabel.setText(savedUser.getPhoneNumber());
        usernameLabel.setText(savedUser.getUsername());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancelBtn.setVisible(false);
        UserAccount connectedUser = userAccountService.getConnectedUser();

        emailLabel.setText(connectedUser.getEmail());
        phoneNumberLabel.setText(connectedUser.getPhoneNumber());
        usernameLabel.setText(connectedUser.getUsername());
    }
}

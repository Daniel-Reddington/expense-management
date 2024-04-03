package com.daniela.expensemanagement.controllers;

import com.daniela.expensemanagement.ExpenseManagementApplication;
import com.daniela.expensemanagement.SpringFXMLLoader;
import com.daniela.expensemanagement.entities.UserAccount;
import com.daniela.expensemanagement.services.interfaces.UserAccountService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RegisterController implements Initializable {

    @FXML
    private Label alertMessage;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private TextField email;
    @FXML
    private TextField phoneNumber;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    private final UserAccountService userAccountService;
    private final SpringFXMLLoader fxmlLoader;

    @FXML
    private BorderPane borderPane;
    private Parent fxml;

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
    void create() {
        if(password.getText().equals(confirmPassword.getText())){
            UserAccount userAccount = UserAccount
                    .builder()
                    .email(email.getText())
                    .phoneNumber(phoneNumber.getText())
                    .username(username.getText())
                    .password(password.getText())
                    .createdAt(LocalDateTime.now())
                    .build();
            userAccountService.save(userAccount);

            alertMessage.getStyleClass().clear();
            alertMessage.getStyleClass().add("alert-success");
            alertMessage.setText("Successful, go to login");

        }else {
            alertMessage.getStyleClass().clear();
            alertMessage.getStyleClass().add("alert-danger");
            alertMessage.setText("Failed, verify your password");
        }
    }

    @FXML
    void signIn(ActionEvent event) throws IOException {
        try {
            String url = "/com.daniela.expensemanagement/login.fxml";
            fxml = (Parent) fxmlLoader.load(url);

            borderPane.getChildren().removeAll();
            borderPane.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("register controller");
    }
}

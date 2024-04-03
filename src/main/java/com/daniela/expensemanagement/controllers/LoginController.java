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
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginController implements Initializable {
    @FXML
    private Label alertError;

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
    void createAccount(ActionEvent event) throws IOException {
        try {
            String url = "/com.daniela.expensemanagement/register.fxml";
            fxml = (Parent) fxmlLoader.load(url);

            borderPane.getChildren().removeAll();
            borderPane.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void switchToHome() throws IOException {
        String url = "/com.daniela.expensemanagement/home.fxml";
        Object registerScreen = fxmlLoader.load(url);

        Scene scene = new Scene((Parent) registerScreen);
        scene.getStylesheets().add(ExpenseManagementApplication.class.getResource("/com.daniela.expensemanagement/css/style.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    @FXML
    void forgotPassword(ActionEvent event) throws IOException {
        String url = "/com.daniela.expensemanagement/forgot-password.fxml";
        Object registerScreen = fxmlLoader.load(url);

        Scene scene = new Scene((Parent) registerScreen);
        scene.getStylesheets().add(ExpenseManagementApplication.class.getResource("/com.daniela.expensemanagement/css/style.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    @FXML
    void logIn(ActionEvent event) throws IOException {
        UserAccount userEntry = UserAccount
                .builder()
                .username(username.getText())
                .password(password.getText())
                .build();

        UserAccount userAccount = userAccountService.checkUser(userEntry);

        if(userAccount == null){
            alertError.setVisible(true);
        }else {
            alertError.setVisible(false);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
            switchToHome();
        }
        log.info("connected user {}", userAccountService.getConnectedUser());
    }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userAccountService.setConnectedUser(null);
    }
}

package com.daniela.expensemanagement.controllers;

import com.daniela.expensemanagement.ExpenseManagementApplication;
import com.daniela.expensemanagement.SpringFXMLLoader;
import com.daniela.expensemanagement.services.interfaces.UserAccountService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
@Slf4j
public class HomeController implements Initializable, AutoCloseable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private VBox buttonContainer;

    private Parent fxml;

    private final SpringFXMLLoader fxmlLoader;
    private final UserAccountService userAccountService;
    private final List<Button> buttonsList;

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
        switchToLogin();
    }

    void switchToLogin() throws IOException {
        String url = "/com.daniela.expensemanagement/login.fxml";
        Object registerScreen = fxmlLoader.load(url);

        Scene scene = new Scene((Parent) registerScreen);
        scene.getStylesheets().add(ExpenseManagementApplication.class.getResource("/com.daniela.expensemanagement/css/style.css").toExternalForm());

        //ExpenseManagementApplication.setRoot((Parent) registerScreen);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void switchToBudget(ActionEvent event) {
        handleButtonClick((Button) event.getSource());
        try {
            String url = "/com.daniela.expensemanagement/budget.fxml";
            fxml = (Parent) fxmlLoader.load(url);

            anchorPane.getChildren().removeAll();
            anchorPane.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void switchToExpense(ActionEvent event) {
        handleButtonClick((Button) event.getSource());
        try {
            String url = "/com.daniela.expensemanagement/expense.fxml";
            fxml = (Parent) fxmlLoader.load(url);
            anchorPane.getChildren().removeAll();
            anchorPane.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void switchToIncome(ActionEvent event) {
        handleButtonClick((Button) event.getSource());
        try {
            String url = "/com.daniela.expensemanagement/income.fxml";
            fxml = (Parent) fxmlLoader.load(url);

            anchorPane.getChildren().removeAll();
            anchorPane.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void switchToNotification(ActionEvent event) {
        handleButtonClick((Button) event.getSource());
    }

    @FXML
    void switchToProfile(ActionEvent event) {
        handleButtonClick((Button) event.getSource());
        try {
            String url = "/com.daniela.expensemanagement/profile.fxml";
            fxml = (Parent) fxmlLoader.load(url);

            anchorPane.getChildren().removeAll();
            anchorPane.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void switchToStatistic(ActionEvent event) {
        handleButtonClick((Button) event.getSource());
        try {
            String url = "/com.daniela.expensemanagement/statistic.fxml";
            fxml = (Parent) fxmlLoader.load(url);

            anchorPane.getChildren().removeAll();
            anchorPane.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        userAccountService.setConnectedUser(null);
    }

    private void handleButtonClick(Button clickedButton) {
        for (Button button : buttonsList) {
            button.setStyle("");
        }
        clickedButton.setStyle("-fx-background-color: yellow; -fx-text-fill:black");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Node node : buttonContainer.getChildren()) {
            if (node instanceof Button button) {
                buttonsList.add(button);
            }
        }
        buttonsList.get(0).fire();
        log.info("connected user for home controller", userAccountService.getConnectedUser());
    }
}

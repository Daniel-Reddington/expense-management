package com.daniela.expensemanagement.utils;

import com.daniela.expensemanagement.ExpenseManagementApplication;
import com.daniela.expensemanagement.SpringFXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class Modal {

    private final SpringFXMLLoader fxmlLoader;
    public void openModal(String url) {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        Parent modalRoot;
        try {
            modalRoot = (Parent) fxmlLoader.load(url);
            Scene scene = new Scene(modalRoot);
            scene.getStylesheets().add(ExpenseManagementApplication.class.getResource("/com.daniela.expensemanagement/css/style.css").toExternalForm());
            modalStage.setScene(scene);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Alert");
            modalStage.setResizable(true);
            modalStage.initStyle(StageStyle.UNDECORATED);
            modalStage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

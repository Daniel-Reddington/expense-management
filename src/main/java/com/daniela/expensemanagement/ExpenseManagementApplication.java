package com.daniela.expensemanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(".env")
public class ExpenseManagementApplication extends Application {

	public static ConfigurableApplicationContext applicationContext;
	private static Scene scene;

	public static void main(String[] args) {
		launch();
	}


	@Override
	public void start(Stage stage) throws Exception {
		applicationContext = SpringApplication.run(ExpenseManagementApplication.class);

		SpringFXMLLoader loader = applicationContext.getBean(SpringFXMLLoader.class);

		Parent root = (Parent) loader.load("/com.daniela.expensemanagement/login.fxml");
		stage.setTitle("javafx spring boot");
		stage.initStyle(StageStyle.UNDECORATED);
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/com.daniela.expensemanagement/css/style.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void stop() {
		applicationContext.close();
	}

	public static void setRoot(Parent view) {
		scene.setRoot(view);
		scene.getStylesheets().add(ExpenseManagementApplication.class.getResource("/com.daniela.expensemanagement/css/style.css").toExternalForm());

	}
}



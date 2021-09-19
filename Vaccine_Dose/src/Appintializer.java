/**
 * @author - Hw_Dulanjana
 * @date - 9/9/2021
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class Appintializer extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent parent = FXMLLoader.load(this.getClass().getResource("view/LoginForm.fxml"));
            Scene scene = new Scene(parent);
            primaryStage.resizableProperty().setValue(Boolean.FALSE);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login Form");
            primaryStage.centerOnScreen();
            primaryStage.show();
            //normal exit kill..
            primaryStage.setOnCloseRequest(event -> {
                System.exit(0);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

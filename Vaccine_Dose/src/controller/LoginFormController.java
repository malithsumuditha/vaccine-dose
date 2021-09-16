package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author - Hw_Dulanjana
 * @date - 9/9/2021
 */
public class LoginFormController {
    public PasswordField txtPassword;
    public TextField txtID;
    public AnchorPane root;
    public Button btnSignIn;

    public void txtPasswordOnAction(ActionEvent actionEvent) {
    }

    public void btnSignInOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/DashBordMainViewForm.fxml"));
        Scene scene = new Scene(parent);

        Stage primaryStage = (Stage) this.btnSignIn.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Vaccination");


    }
}

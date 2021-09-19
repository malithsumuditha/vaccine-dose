package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author : MalithHP <malithsumuditha11@gmail.com>
 * @since : 9/16/2021
 **/
public class DashBordMainViewFormController implements Initializable {
    public AnchorPane mainPanel;
    public AnchorPane changingPanel;
    public Label lblDateAndTime;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Parent parent = null;
        try {
            parent = FXMLLoader.load(this.getClass().getResource("../view/DashBordForm.fxml"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        changingPanel.getChildren().clear();
        changingPanel.getChildren().add(parent);

        setDateAndTime(lblDateAndTime);

    }

    public void btnDashBordOnAction() throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/DashBordForm.fxml"));
        changingPanel.getChildren().clear();
        changingPanel.getChildren().add(parent);
    }
    public void btnRegisterOnAction() throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/RegForm.fxml"));
        changingPanel.getChildren().clear();
        changingPanel.getChildren().add(parent);

    }

    public static void setDateAndTime(Label timeLabel){

            timeLabel.setText(null);
            Thread timerThread = new Thread(() -> {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final String time = simpleDateFormat.format(new Date());
                    Platform.runLater(() -> {
                        timeLabel.setText(time);

                    });
                }
            });   timerThread.start();


    }

    public void btnVaccineOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/VaccineRegForm.fxml"));
        changingPanel.getChildren().clear();
        changingPanel.getChildren().add(parent);

    }

    public void btnSignOutOnAction(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you Want to Sign Out.. ? ",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get().equals(ButtonType.YES)){

            Parent parent =FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
            Scene scene =new Scene(parent);
            Stage primaryStage = (Stage) mainPanel.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.setTitle("Login Form");
        }


    }

    public void btnVaccinationOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent =FXMLLoader.load(this.getClass().getResource("../view/VaccinationForm.fxml"));
        changingPanel.getChildren().clear();
        changingPanel.getChildren().add(parent);
    }

    public void btnPHIRegisterOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/PHIRegForm.fxml"));
        changingPanel.getChildren().clear();
        changingPanel.getChildren().add(parent);
    }
}

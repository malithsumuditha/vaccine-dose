package controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    public JFXButton btnDashBord;
    public JFXButton btnRegister;
    public JFXButton btnVaccine;
    public JFXButton btnVaccination;
    public JFXButton btnPHIRegister;
    public Separator sptDashbord;
    public Separator sptRegister;
    public Separator sptVaccine;
    public Separator sptVaccination;
    public Separator sptPHIRegister;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Parent parent = null;
        try {
            parent = FXMLLoader.load(this.getClass().getResource("/view/DashBordForm.fxml"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        changingPanel.getChildren().clear();
        changingPanel.getChildren().add(parent);

        setDateAndTime(lblDateAndTime);
        //

    }

    public void btnDashBordOnAction() throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("/view/DashBordForm.fxml"));
        changingPanel.getChildren().clear();
        changingPanel.getChildren().add(parent);
    }
    public void btnRegisterOnAction() throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("/view/RegForm.fxml"));
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
        Parent parent = FXMLLoader.load(this.getClass().getResource("/view/VaccineRegForm.fxml"));
        changingPanel.getChildren().clear();
        changingPanel.getChildren().add(parent);

    }

    public void btnSignOutOnAction(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you Want to Sign Out.. ? ",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get().equals(ButtonType.YES)){

            Parent parent =FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));
            Scene scene =new Scene(parent);
            Stage primaryStage = (Stage) mainPanel.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.setTitle("Login Form");
        }


    }

    public void btnVaccinationOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent =FXMLLoader.load(this.getClass().getResource("/view/VaccinationForm.fxml"));
        changingPanel.getChildren().clear();
        changingPanel.getChildren().add(parent);
    }

    public void btnPHIRegisterOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("/view/PHIRegForm.fxml"));
        changingPanel.getChildren().clear();
        changingPanel.getChildren().add(parent);
    }

    public void btnDashBordOnMouseClicked(MouseEvent mouseEvent) {
        btnDashBord.setStyle("-fx-background-color: #3742fa");
        btnPHIRegister.setStyle("-fx-background-color: #3498db");
        btnRegister.setStyle("-fx-background-color: #3498db");
        btnVaccination.setStyle("-fx-background-color: #3498db");
        btnVaccine.setStyle("-fx-background-color:  #3498db");
    }

    public void btnDashBordOnMouseEntered(MouseEvent mouseEvent) {
        onMouseEnterChange(btnDashBord,sptDashbord);
    }

    public void btnDashBordOnMouseExited(MouseEvent mouseEvent) {
        onMouseExitChange(btnDashBord,sptDashbord);
    }
    
    public void btnRegisterOnMouseClick(MouseEvent mouseEvent) {
        btnRegister.setStyle("-fx-background-color: #3742fa");
        btnPHIRegister.setStyle("-fx-background-color: #3498db");
        btnDashBord.setStyle("-fx-background-color: #3498db");
        btnVaccination.setStyle("-fx-background-color: #3498db");
        btnVaccine.setStyle("-fx-background-color:  #3498db");
    }

    public void btnRegisterOnMouseEnter(MouseEvent mouseEvent) {
        onMouseEnterChange(btnRegister,sptRegister);
    }

    public void btnRegisterOnMouseExit(MouseEvent mouseEvent) {
        onMouseExitChange(btnRegister,sptRegister);
    }

    public void btnVaccineOnMouseClicked(MouseEvent mouseEvent) {
        btnVaccine.setStyle("-fx-background-color: #3742fa");
        btnPHIRegister.setStyle("-fx-background-color: #3498db");
        btnDashBord.setStyle("-fx-background-color: #3498db");
        btnVaccination.setStyle("-fx-background-color: #3498db");
        btnRegister.setStyle("-fx-background-color:  #3498db");
    }

    public void btnVaccineOnMouseEnter(MouseEvent mouseEvent) {
        onMouseEnterChange(btnVaccine,sptVaccine);
    }

    public void btnVaccineOnMouseExit(MouseEvent mouseEvent) {
        onMouseExitChange(btnVaccine,sptVaccine);
    }

    public void onMouseEnterChange(JFXButton btn,Separator separator){
        btn.setFont(Font.font(null, FontWeight.BOLD,25));
        VBox.setMargin(separator, new Insets(0,20,0,20));
    }

    public void onMouseExitChange(JFXButton btn,Separator separator){
        btn.setFont(Font.font(null, FontWeight.BOLD,22));
        VBox.setMargin(separator, new Insets(0,40,0,40));
    }

    public void btnVaccinationOnMouseClicked(MouseEvent mouseEvent) {
        btnVaccination.setStyle("-fx-background-color: #3742fa");
        btnPHIRegister.setStyle("-fx-background-color: #3498db");
        btnDashBord.setStyle("-fx-background-color: #3498db");
        btnRegister.setStyle("-fx-background-color: #3498db");
        btnVaccine.setStyle("-fx-background-color:  #3498db");
    }





    public void btnPHIRegisterOnMouseClicked(MouseEvent mouseEvent) {
        btnPHIRegister.setStyle("-fx-background-color: #3742fa");
        btnRegister.setStyle("-fx-background-color: #3498db");
        btnDashBord.setStyle("-fx-background-color: #3498db");
        btnVaccination.setStyle("-fx-background-color: #3498db");
        btnVaccine.setStyle("-fx-background-color:  #3498db");
    }

    public void btnPHIRegisterOnMouseEnter(MouseEvent mouseEvent) {
        onMouseEnterChange(btnPHIRegister,sptPHIRegister);
    }


    public void btnPHIRegisterOnMouseExit(MouseEvent mouseEvent) {
        onMouseExitChange(btnPHIRegister,sptPHIRegister);
    }


    public void btnEditProfile(ActionEvent actionEvent) {
    }

    public void btnVaccinationOnMouseEnter(MouseEvent mouseEvent) {
        onMouseEnterChange(btnVaccination,sptVaccination);
    }

    public void btnVaccinationOnMouseExit(MouseEvent mouseEvent) {
        onMouseExitChange(btnVaccination,sptVaccination);
    }
}

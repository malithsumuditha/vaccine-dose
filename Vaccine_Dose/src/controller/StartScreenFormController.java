package controller;

import db.DBConnection;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import javafx.stage.StageStyle;


import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

/**
 * @author : MalithHP <malithsumuditha11@gmail.com>
 * @since : 9/23/2021
 **/
public class StartScreenFormController implements Initializable {
    public Label lblLoadingText;
    public static Label lblLoadingTextt;
    public ProgressBar progressBar;
    public static ProgressBar progressBarr;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblLoadingTextt = lblLoadingText;
        progressBarr = progressBar;
        progressBarr.setStyle("-fx-accent: red;");
        System.out.println("sl");
    }

    public String checkFunction(){

        final String[] message = {""};

        Thread t1 = new Thread(() -> {

            message[0]="Turning On Modules...";
            Platform.runLater(() -> lblLoadingTextt.setText(message[0]));
            Platform.runLater(() -> progressBarr.setProgress(0.02));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            message[0]="Loading Modules...";
            Platform.runLater(() -> lblLoadingTextt.setText(message[0]));
            Platform.runLater(() -> progressBarr.setProgress(0.15));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t3 = new Thread(() -> {

                message[0]="Connecting to DataBase...";
                Platform.runLater(() -> lblLoadingTextt.setText(message[0]));
                Platform.runLater(() -> progressBarr.setProgress(0.4));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        });


        Thread t4 = new Thread(() -> {
            Connection connection = null;
            connection = DBConnection.getInstance().getConnection();

            if (connection!=null){
                message[0]="Connection Successfully...";
                Platform.runLater(() -> lblLoadingTextt.setText(message[0]));
                Platform.runLater(() -> progressBarr.setProgress(0.7));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Thread t5 = new Thread(() -> {
                    message[0]="Launching Application...";
                    Platform.runLater(() -> lblLoadingTextt.setText(message[0]));
                    Platform.runLater(() -> progressBarr.setProgress(1.0));
                    try {

                        Thread.sleep(500);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

                Thread t6 = new Thread(() -> {
                    message[0]="Open Main Stage";
                    Platform.runLater(() -> lblLoadingTextt.setText(message[0]));

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                Thread.sleep(500);


                                Stage stage = new Stage();
                                Parent parent = FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));
                                Scene scene = new Scene(parent);
                                stage.setScene(scene);
                                stage.show();
                                stage.centerOnScreen();

                                stage.resizableProperty().setValue(Boolean.FALSE);
                                //normal exit kill..
                                stage.setOnCloseRequest(event -> {
                                    System.exit(0);
                                });



                            } catch (IOException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });


                });

                try {
                    t5.start();
                    t5.join();
                    t6.start();
                    t6.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }else {

                Platform.runLater(() ->{
                    Alert alert = new Alert(Alert.AlertType.ERROR,"DataBase Connection is Failed... ");
                    alert.showAndWait();
                }  );
            }


        });






        try {
            t1.start();
            t1.join();
            t2.start();
            t2.join();
            t3.start();
            t3.join();
            t4.start();
            t4.join();

        } catch (InterruptedException e) {

            e.printStackTrace();
        }



        return message[0];
    }

}

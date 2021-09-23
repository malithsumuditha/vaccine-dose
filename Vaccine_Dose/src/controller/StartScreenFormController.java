package controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
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
            message[0]="Connection to DataBase...";
            Platform.runLater(() -> lblLoadingTextt.setText(message[0]));
            Platform.runLater(() -> progressBarr.setProgress(0.4));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t4 = new Thread(() -> {
            message[0]="Connection Successfully...";
            Platform.runLater(() -> lblLoadingTextt.setText(message[0]));
            Platform.runLater(() -> progressBarr.setProgress(0.7));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t5 = new Thread(() -> {
            message[0]="Launching Application...";
            Platform.runLater(() -> lblLoadingTextt.setText(message[0]));
            Platform.runLater(() -> progressBarr.setProgress(1.0));
            try {
                Thread.sleep(1000);
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
                        Thread.sleep(1000);
                        Stage stage = new Stage();
                        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
                        Scene scene = new Scene(parent);
                        stage.setScene(scene);
                        stage.show();
                        stage.centerOnScreen();


                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });


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
            t5.start();
            t5.join();
            t6.start();
            t6.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        return message[0];
    }


//    public void initialize(){
//
//    }
//
//    public void loadingScreen(){
//
//        try {
//
//            for (int i=0; i<=100; i++){
//                Thread.sleep(100);
//                lblLoadingText.setText(i+"%");
//
//                if (i==10){
//                    lblLoadingText.setText("Turning On Modules...");
//                }
//                if (i==20){
//                    lblLoadingText.setText("Loading Modules...");
//                }
//                if (i==50){
//                    lblLoadingText.setText("Connection to DataBase...");
//                }
//                if (i==70){
//                    lblLoadingText.setText("Connection Successfully...");
//                }
//                if (i==80){
//                    lblLoadingText.setText("Launching Application...");
//                }
//
//                progressBar.progressProperty().setValue(i);
//
//            }
//
//        }
//        catch (Exception e){
//
//            JOptionPane.showMessageDialog(null,e);
//
//        }
//
//  }
}

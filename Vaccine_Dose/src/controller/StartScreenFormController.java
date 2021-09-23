package controller;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import javax.swing.*;

/**
 * @author : MalithHP <malithsumuditha11@gmail.com>
 * @since : 9/23/2021
 **/
public class StartScreenFormController {
    public Label lblLoadingText;
    public ProgressBar progressBar;

    public void initialize(){
        loadingScreen();
    }

    public void loadingScreen(){

        try {

            for (int i=0; i<=100; i++){
                Thread.sleep(100);
                lblLoadingText.setText(i+"%");

                if (i==10){
                    lblLoadingText.setText("Turning On Modules...");
                }
                if (i==20){
                    lblLoadingText.setText("Loading Modules...");
                }
                if (i==50){
                    lblLoadingText.setText("Connection to DataBase...");
                }
                if (i==70){
                    lblLoadingText.setText("Connection Successfully...");
                }
                if (i==80){
                    lblLoadingText.setText("Launching Application...");
                }

                progressBar.progressProperty().setValue(i);

            }

        }
        catch (Exception e){

            JOptionPane.showMessageDialog(null,e);

        }







    }

}

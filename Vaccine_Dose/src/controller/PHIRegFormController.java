package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

/**
 * @author - Hw_Dulanjana
 * @date - 9/19/2021
 */
public class PHIRegFormController {
    public JFXTextField txtPHIName;
    public JFXRadioButton rdbPHIMale;
    public JFXRadioButton rdbPHIFemale;
    public JFXTextField txtPHIAddress;
    public JFXTextField txtPHIContact;
    public JFXTextField txtPHINIC;
    public Label lblPHIID;
    public JFXButton btnPHIAdd;
    public JFXButton btnPHIUpdate;
    public JFXButton btnPHIDelete;
    public JFXButton btnPHIReset;
    public JFXTextField txtPHICity;
    public JFXPasswordField txtAccPasssword;
    public JFXPasswordField txtConfirmPassword;
    public Button btnChooseImage;
    public FileInputStream img;
    public File file;
    public ImageView imgImageView;

    public void initialize(){
        autogenarate();
        txtPHIName.requestFocus();
    }

    public void rdbPHIMaleOnAction(ActionEvent actionEvent) {
        rdbPHIFemale.setSelected(false);
    }

    public void rdbPHIFemaleOnAction(ActionEvent actionEvent) {
        rdbPHIMale.setSelected(false);
    }

    public void btnPHIAddOnAction(ActionEvent actionEvent) {
        datainsert();
    }

    public void btnPHIUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnPHIDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnPHIResetOnAction(ActionEvent actionEvent) {
    }
    public void datainsert(){
        String Password = txtAccPasssword.getText();
        String CPassword = txtConfirmPassword.getText();
        if(txtPHIName.getText().isEmpty()){
            ErrorMassage("PHI Name");
            ErrorBorderCl(txtPHIName);
            txtPHIName.clear();
            txtPHIName.requestFocus();
        }else if(txtPHIAddress.getText().isEmpty()){
            ErrorMassage("PHI Address");
            ErrorBorderCl(txtPHIAddress);
            txtPHIAddress.clear();
            txtPHIAddress.requestFocus();
        }else if(txtPHIContact.getText().isEmpty()){
            ErrorMassage("Contact Number");
            ErrorBorderCl(txtPHIContact);
            txtPHIContact.clear();
            txtPHIContact.requestFocus();
        }else if(txtPHINIC.getText().isEmpty()){
            ErrorBorderCl(txtPHINIC);
            ErrorMassage("NIC Number");
            txtPHINIC.clear();
            txtPHINIC.requestFocus();
        }else if (txtAccPasssword.getText().isEmpty()){
            ErrorMassage("Password");
            ErrorBorderClPWD(txtAccPasssword);
            txtAccPasssword.clear();
            txtAccPasssword.requestFocus();
        }else if(txtConfirmPassword.getText().isEmpty()){
            ErrorBorderClPWD(txtConfirmPassword);
            ErrorMassage("Confirm Password");
            txtConfirmPassword.clear();
            txtConfirmPassword.requestFocus();
        }else if(txtPHICity.getText().isEmpty()){
            ErrorMassage("working City");
            ErrorBorderCl(txtPHICity);
            txtPHICity.clear();
            txtPHICity.requestFocus();
        }else if(CPassword!=Password){
            ErrorBorderClPWD(txtConfirmPassword);
            ErrorBorderClPWD(txtAccPasssword);
            txtAccPasssword.clear();
            txtConfirmPassword.clear();
            txtAccPasssword.requestFocus();
        }else if(CPassword.equals(Password)){
            String PName = txtPHIName.getText();
            String PAddress = txtPHIAddress.getText();
            String PContact = txtPHIContact.getText();
            String PNic = txtPHINIC.getText();
            String PCity = txtPHICity.getText();
            String gender = null;
            String PID = lblPHIID.getText();

            if (rdbPHIFemale.isSelected()) {
                gender = "Female";
            } else if (rdbPHIMale.isSelected()) {
                gender = "Male";
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Something Error ! , Please TRy Again.. ");
                alert.showAndWait();
            }

            Connection connection = DBConnection.getInstance().getConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("insert into PHIReg Values(?,?,?,?,?,?,?,?)");
                preparedStatement.setObject(1, PID);
                preparedStatement.setObject(2, PName);
                preparedStatement.setObject(3, PAddress);
                preparedStatement.setObject(4, PContact);
                preparedStatement.setObject(5, PNic);
                preparedStatement.setObject(6, gender);
                preparedStatement.setObject(7, PCity);
                preparedStatement.setObject(8, Password);

//                FileChooser fileChooser = new FileChooser();
//                file = fileChooser.showSaveDialog(btnChooseImage.getScene().getWindow());
//                FileInputStream fileInputStream = new FileInputStream(file);
//
//                try {
//
//                    preparedStatement.setBinaryStream(9,fileInputStream,fileInputStream.available());
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }


                int i = preparedStatement.executeUpdate();

//                Image image = new Image(fileInputStream);
//                imgImageView.setImage(image);


                if (i != 0) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Successful Added !! ");
                    alert.showAndWait();
                    txtClear();
                    txtPHIName.requestFocus();
                    autogenarate();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Something Error ! , Please TRy Again.. ");
                    alert.showAndWait();
                }
            } catch (SQLException throwables) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Something Error ! , Please TRy Again.. ");
                throwables.printStackTrace();
            }
        }else{
            System.out.println("Wrong");
        }
    }
    public void autogenarate(){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select PID from PHIReg order by PID desc limit 1");
            boolean IfExif = resultSet.next();

            if (IfExif){
                String oldV = resultSet.getString(1);
                oldV = oldV.substring(2, oldV.length());

                int NewID = Integer.parseInt(oldV);
                NewID= NewID+1;

                if(NewID < 10){
                    lblPHIID.setText("PH00"+NewID);
                }else if(NewID<100){
                    lblPHIID.setText("PH0"+NewID);
                }else{
                    lblPHIID.setText("PH"+NewID);
                }
            }
            else{
                lblPHIID.setText("PH001");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public void txtClear(){
        txtPHIName.clear();
        txtPHICity.clear();
        txtPHIAddress.clear();
        txtAccPasssword.clear();
        txtConfirmPassword.clear();
        txtPHINIC.clear();
        txtPHIContact.clear();
    }
    public void ErrorMassage(String errorField){
        Alert alert = new Alert(Alert.AlertType.ERROR," "+errorField+" Field is cannot be emty, Please Fill this field..! ");
        alert.showAndWait();
    }
    public void ErrorBorderCl(JFXTextField name){
        name.setStyle("-fx-border-color:red");
    }
    public void ErrorBorderClPWD(JFXPasswordField name){
        name.setStyle("-fx-border-color:red");
    }
    public void NullBorderCl(){


        txtPHIName.setStyle("-fx-border-color:null");
        txtPHIAddress.setStyle("-fx-border-color:null");
        txtPHIContact.setStyle("-fx-border-color:null");
        txtAccPasssword.setStyle("-fx-border-color:null");
        txtConfirmPassword.setStyle("-fx-border-color:null");
        txtPHINIC.setStyle("-fx-border-color:null");
        txtPHICity.setStyle("-fx-border-color:null");

//        txtVaccineName.setStyle("-fx-border-color:null");
//        txtCompany.setStyle("-fx-border-color:null");
//        txtMCountry.setStyle("-fx-border-color:null");



    }



    public void openAndSave(){

        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showSaveDialog(btnChooseImage.getScene().getWindow());
        try {
            FileInputStream fileInputStream = new FileInputStream(file);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
    public void loadImage(){

    }
}

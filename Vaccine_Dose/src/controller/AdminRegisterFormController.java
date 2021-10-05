package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author : MalithHP <malithsumuditha11@gmail.com>
 * @since : 9/28/2021
 **/
public class AdminRegisterFormController {
    public JFXTextField txtName;
    public JFXRadioButton rdbMale;
    public JFXRadioButton rdbFemale;
    public JFXTextField txtContact;
    public JFXTextField txtNIC;
    public Label lblAdminID;
    public JFXTextField txtEmail;
    public JFXTextField txtPassword;
    public JFXTextField txtConfirmPassword;
    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnReset;
    public JFXTextField txtUserName;

    public void initialize(){
       // autogenarate();
    }
    public void InsertData(){
        String AName = txtName.getText();
        String AContact = txtContact.getText();
        String ANIC = txtNIC.getText();
        String Gender = null;
        String AEmail = txtEmail.getText();
        String AUserName = txtUserName.getText();
        String Password = txtPassword.getText();
        String CPassword = txtConfirmPassword.getText();
        String AID = lblAdminID.getText();

        if(rdbMale.isSelected()){
            Gender = "Male";
        }else if(rdbFemale.isSelected()){
            Gender = "Female";
        }else{
            ErrorMassage("Gender ");
        }
        if(txtName.getText().isEmpty()){
            ErrorBorderCl(txtName);
            ErrorMassage("Ädmin Name");
            txtName.clear();
            txtName.requestFocus();
        }else if(txtContact.getText().isEmpty()){
            ErrorBorderCl(txtContact);
            ErrorMassage("Admin Contact Number");
            txtName.clear();
            txtName.requestFocus();
        }else if(txtNIC.getText().isEmpty()){
            ErrorBorderCl(txtNIC);
            ErrorMassage("Admin NIC number");
            txtNIC.clear();
            txtNIC.requestFocus();
        }else if(txtEmail.getText().isEmpty()) {
            ErrorBorderCl(txtEmail);
            ErrorMassage("Admin Email address");
            txtEmail.clear();
            txtEmail.requestFocus();
        }else if(txtUserName.getText().isEmpty()){
            ErrorBorderCl(txtUserName);
            ErrorMassage("User Name");
            txtUserName.clear();
            txtUserName.requestFocus();
        }else if(txtPassword.getText().isEmpty()){
            ErrorBorderCl(txtPassword);
            ErrorMassage("Password");
            txtPassword.clear();
            txtPassword.requestFocus();
        }else if(txtConfirmPassword.getText().equals(txtPassword.getText())){
            NullBorderCl();
            System.out.println("Done");
//            Connection connection = DBConnection.getInstance().getConnection();
//            try {
//                PreparedStatement preparedStatement = connection.prepareStatement("Insert into AdminReg values");
//
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }

        }else{
            Alert alert=new Alert(Alert.AlertType.ERROR,"Passsword and Confirm Password are not same !!");
            alert.showAndWait();
            ErrorBorderCl(txtPassword);
            ErrorBorderCl(txtConfirmPassword);
            txtPassword.clear();
            txtConfirmPassword.clear();
            txtPassword.requestFocus();
        }

    }
    public void autogenarate(){
        PersonRegFormController.autoGenerateID(lblAdminID,"AdminReg","A");
    }

    public void rdbMaleOnAction(ActionEvent actionEvent) {
    }

    public void rdbFemaleOnAction(ActionEvent actionEvent) {
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        InsertData();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        //
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
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
        txtName.setStyle("-fx-border-color:null");
        txtNIC.setStyle("-fx-border-color:null");
        txtContact.setStyle("-fx-border-color:null");
        txtPassword.setStyle("-fx-border-color:null");
        txtConfirmPassword.setStyle("-fx-border-color:null");
        txtEmail.setStyle("-fx-border-color:null");
        txtUserName.setStyle("-fx-border-color:null");
    }
}

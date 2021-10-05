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
    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnReset;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public JFXPasswordField txtConfirmPassword;

    public void initialize(){
       autogenarate();
       txtName.requestFocus();
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
        String id = lblAdminID.getText();

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
            ErrorBorderClPWD(txtPassword);
            ErrorMassage("Password");
            txtPassword.clear();
            txtPassword.requestFocus();
        }else if(txtConfirmPassword.getText().equals(txtPassword.getText())){
            NullBorderCl();
            Connection connection = DBConnection.getInstance().getConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("insert into AdminReg values(?,?,?,?,?,?,?,?)");
                preparedStatement.setObject(1,id);
                preparedStatement.setObject(2,AName);
                preparedStatement.setObject(3,AContact);
                preparedStatement.setObject(4,ANIC);
                preparedStatement.setObject(5,Gender);
                preparedStatement.setObject(6,AEmail);
                preparedStatement.setObject(7,AUserName);
                preparedStatement.setObject(8,Password);
                int i = preparedStatement.executeUpdate();
                if(i!=0){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Successfull Added !!");
                    alert.showAndWait();

                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Something Error !! Please try again.");
                    alert.showAndWait();
                }

            } catch (SQLException throwables) {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Something Error !! Please try again.");
                alert.showAndWait();
                throwables.printStackTrace();
            }
            allvalueClear();
            autogenarate();
            txtName.requestFocus();

        }else{
            Alert alert=new Alert(Alert.AlertType.ERROR,"Passsword and Confirm Password are not same !!");
            alert.showAndWait();
            ErrorBorderClPWD(txtPassword);
            ErrorBorderClPWD(txtConfirmPassword);
            txtPassword.clear();
            txtConfirmPassword.clear();
            txtPassword.requestFocus();
        }

    }
    public void autogenarate(){
        PersonRegFormController.autoGenerateID(lblAdminID,"AdminReg","A");
    }

    public void rdbMaleOnAction(ActionEvent actionEvent) {
        rdbFemale.setSelected(false);
    }

    public void rdbFemaleOnAction(ActionEvent actionEvent) {
        rdbMale.setSelected(false);
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
        allvalueClear();
        txtName.requestFocus();
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
    public void allvalueClear(){
        txtName.clear();
        txtContact.clear();
        txtNIC.clear();
        txtUserName.clear();
        txtPassword.clear();
        txtConfirmPassword.clear();
        txtEmail.clear();
    }
}

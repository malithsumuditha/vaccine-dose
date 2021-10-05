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
 * @author - Hw_Dulanjana
 * @date - 9/20/2021
 */
public class DoctorRegFOrmController {
    public JFXTextField txtDName;
    public JFXRadioButton rdbDMale;
    public JFXRadioButton rdbDFemale;
    public JFXButton btnDAdd;
    public JFXTextField txtDContact;
    public JFXTextField txtDNic;
    public Label lblDoctorID;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnReset;
    public JFXPasswordField txtAccPassword;
    public JFXPasswordField txtCPassword;
    public JFXButton btnAdd1;

    public void initialize(){
        PersonRegFormController.autoGenerateID(lblDoctorID,"doctor","D");
    }

    public void rdbDMaleOnAction(ActionEvent actionEvent) {
        rdbDFemale.setSelected(false);
    }

    public void rdbDFemaleOnAction(ActionEvent actionEvent) {
        rdbDMale.setSelected(false);
    }

    public void btnDAddOnAction(ActionEvent actionEvent) {

        Connection connection = DBConnection.getInstance().getConnection();
        String id = lblDoctorID.getText();
        String name = txtDName.getText();
        String contact = txtDContact.getText();
        String nic = txtDNic.getText();
        String password = txtAccPassword.getText();
        String cPassword = txtCPassword.getText();
        String gender = null;

        if (rdbDMale.isSelected()){
            gender = "Male";
        }else if (rdbDFemale.isSelected()){
            gender = "Female";
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Select Gender");
            alert.showAndWait();
        }
        if(txtDName.getText().isEmpty()){
            errorAlert("Please Enter Name");
            setBorderColor(txtDName,"red");

        }else if (txtDContact.getText().isEmpty()){
            errorAlert("Please Enter Contact");
            setBorderColor(txtDName,"null");
            setBorderColor(txtDContact,"red");
        }else if (txtDNic.getText().isEmpty()){
            errorAlert("Please Enter NIC");
            setBorderColor(txtDNic,"red");
            setBorderColor(txtDContact,"null");
        }else if (txtAccPassword.getText().isEmpty()){
            errorAlert("Please Enter Password");
            txtAccPassword.setStyle("-fx-border-color:red");
            setBorderColor(txtDNic,"null");
        }else if (txtCPassword.getText().isEmpty()){
            errorAlert("Please Enter Confirm Password");
            txtCPassword.setStyle("-fx-border-color:red");
            txtAccPassword.setStyle("-fx-border-color:null");
        } else if (txtAccPassword!=txtCPassword) {
            errorAlert("Please enter same Password for confirm Password");
            txtCPassword.setStyle("-fx-border-color:red");
            txtAccPassword.setStyle("-fx-border-color:red");
        }
        


        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into doctor values(?,?,?,?,?,?)");
            preparedStatement.setObject(1,id);
            preparedStatement.setObject(2,name);
            preparedStatement.setObject(3,contact);
            preparedStatement.setObject(4,nic);
            preparedStatement.setObject(5,gender);
            preparedStatement.setObject(6,password);

            int i = preparedStatement.executeUpdate();

            if (i!=0){
                confirmAlert("Successfully Added");
                setReset();
            }else {
                errorAlert("Something Error");
            }

        } catch (SQLException throwables) {
            errorAlert("Something Error");
            throwables.printStackTrace();
        }


    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnDResetOnAction(ActionEvent actionEvent) {
        setReset();
    }

    public void btnFileChooserAddOnAction(ActionEvent actionEvent) {
    }

    public void confirmAlert(String displayAlert){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,displayAlert);
        alert.showAndWait();
    }

    public void errorAlert(String displayAlert){
        Alert alert = new Alert(Alert.AlertType.ERROR,displayAlert);
        alert.showAndWait();
    }

    public void setReset(){
        PersonRegFormController.autoGenerateID(lblDoctorID,"doctor","D");
        txtDName.clear();
        txtDContact.clear();
        txtDNic.clear();
        rdbDMale.setSelected(false);
        rdbDFemale.setSelected(false);
        txtAccPassword.clear();
        txtCPassword.clear();
        txtDName.requestFocus();
    }

    public void setBorderColor(JFXTextField name,String color){
        name.setStyle("-fx-border-color:"+color);
    }
}

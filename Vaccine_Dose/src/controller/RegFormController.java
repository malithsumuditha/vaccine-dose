package controller;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.*;

/**
 * @author - Hw_Dulanjana
 * @date - 9/12/2021
 */
public class RegFormController {
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXTextField txtNIC;
    public JFXRadioButton rdbMale;
    public JFXRadioButton rdbFemale;
    public Label lblPersonID;

    public void initialize(){
        txtName.requestFocus();
        autoGenerateID();
    }

    public void btnAddOnAction(ActionEvent actionEvent) {

        String id = lblPersonID.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String nic = txtNIC.getText();
        String gender=null;

        if (rdbFemale.isSelected()){
            gender = "Female";
        }else if (rdbMale.isSelected()){
            gender="Male";
        }

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into person values(?,?,?,?,?,?)");
            preparedStatement.setObject(1,id);
            preparedStatement.setObject(2,name);
            preparedStatement.setObject(3,address);
            preparedStatement.setObject(4,contact);
            preparedStatement.setObject(5,nic);
            preparedStatement.setObject(6,gender);

            int i = preparedStatement.executeUpdate();

            if (i != 0){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Successfully Added...");
                alert.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Something Error...");
                alert.showAndWait();
            }


        } catch (SQLException throwables) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Something Error...");
            alert.showAndWait();
            throwables.printStackTrace();
        }
        txtName.requestFocus();
        autoGenerateID();
        clearTextFields();


    }

    public void rdbFemaleOnAction(ActionEvent actionEvent) {
        rdbMale.setSelected(false);
    }

    public void rdbMaleOnAction(ActionEvent actionEvent) {
        rdbFemale.setSelected(false);
    }

    public void autoGenerateID(){

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select id from person order by id desc limit 1");

            boolean isExit = resultSet.next();

            if (isExit){
                String oldId = resultSet.getString(1);
                oldId = oldId.substring(1, oldId.length());

                int intID = Integer.parseInt(oldId);
                intID=intID+1;

                if (intID<10){
                    lblPersonID.setText("P000"+intID);
                }else if (intID<100){
                    lblPersonID.setText("P00"+intID);
                }else if (intID<1000){
                    lblPersonID.setText("P0"+intID);
                }else{
                    lblPersonID.setText("P"+intID);
                    }

            }else {
                lblPersonID.setText("P0001");

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void clearTextFields(){
        txtName.clear();
        txtContact.clear();
        txtAddress.clear();
        txtNIC.clear();
        rdbFemale.setSelected(false);
        rdbMale.setSelected(false);
    }

}

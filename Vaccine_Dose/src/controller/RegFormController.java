package controller;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author - Hw_Dulanjana
 * @date - 9/12/2021
 */
public class RegFormController {
    public JFXTextField txtPersonId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXTextField txtNIC;
    public JFXRadioButton rdbMale;
    public JFXRadioButton rdbFemale;

    public void btnAddOnAction(ActionEvent actionEvent) {

        String id = txtPersonId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String nic = txtNIC.getText();
        String gender = "Male";

        if (rdbFemale.isSelected()){
            gender = "Female";
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


    }

    public void rdbFemaleOnAction(ActionEvent actionEvent) {
        rdbMale.setSelected(false);
    }

    public void rdbMaleOnAction(ActionEvent actionEvent) {
        rdbFemale.setSelected(false);
    }
}

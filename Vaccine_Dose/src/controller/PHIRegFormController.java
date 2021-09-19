package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

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
        String PName = txtPHIName.getText();
        String PAddress = txtPHIAddress.getText();
        String PContact = txtPHIContact.getText();
        String PNic = txtPHINIC.getText();
        String Password = txtAccPasssword.getText();
        String CPassword = txtConfirmPassword.getText();
        String PCity = txtPHICity.getText();
        String gender =null;
        String PID = lblPHIID.getText();

        if (rdbPHIFemale.isSelected()){
            gender = "Female";
        }else if(rdbPHIMale.isSelected()){
            gender="Male";
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Something Error ! , Please TRy Again.. ");
            alert.showAndWait();
        }

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into PHIReg Values(?,?,?,?,?,?,?,?)");
            preparedStatement.setObject(1,PID);
            preparedStatement.setObject(2,PName);
            preparedStatement.setObject(3,PAddress);
            preparedStatement.setObject(4,PContact);
            preparedStatement.setObject(5,PNic);
            preparedStatement.setObject(6,gender);
            preparedStatement.setObject(7,PCity);
            preparedStatement.setObject(8,Password);

            int i = preparedStatement.executeUpdate();
            if(i !=0){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Successful Added !! ");
                alert.showAndWait();
                txtClear();
                txtPHIName.requestFocus();
                autogenarate();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR,"Something Error ! , Please TRy Again.. ");
                alert.showAndWait();
            }
        } catch (SQLException throwables) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Something Error ! , Please TRy Again.. ");
            throwables.printStackTrace();
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
                oldV = oldV.substring(1, oldV.length());

                int NewID = Integer.parseInt(oldV);
                NewID= NewID+1;

                if(NewID < 10){
                    lblPHIID.setText("H00"+NewID);
                }else if(NewID<100){
                    lblPHIID.setText("H0"+NewID);
                }else{
                    lblPHIID.setText("H"+NewID);
                }
            }
            else{
                lblPHIID.setText("H001");
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
}

package controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import tm.ViewRegVaccineTM;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author - Hw_Dulanjana
 * @date - 9/18/2021
 */
public class VaccineRegFormController {
    public JFXTextField txtVaccineName;
    public JFXTextField txtMCountry;
    public JFXTextField txtCompany;
    public Label lblVaccineCode;
    public JFXListView<ViewRegVaccineTM> lstVaccineView;

    public void initialize(){
        txtVaccineName.requestFocus();
        autoGenarateCode();
        Loadlist();

    }
    public void btnVaccineAddOnAction(ActionEvent actionEvent) {
        if(txtVaccineName.getText().isEmpty()){
            ErrorBorderCl(txtVaccineName);
            ErrorMassage("Vaccine name");
            txtVaccineName.clear();
            txtVaccineName.requestFocus();
        }else if(txtMCountry.getText().isEmpty()){
            ErrorBorderCl(txtMCountry);
            ErrorMassage("Manufacture Country");
            txtMCountry.clear();
            txtMCountry.requestFocus();
        }else if(txtCompany.getText().isEmpty()){
            ErrorBorderCl(txtCompany);
            ErrorMassage("Manufacture Company");
            txtCompany.clear();
            txtCompany.requestFocus();
        }else {
            NullBorderCl();
            String VName = txtVaccineName.getText();
            String MCountry = txtMCountry.getText();
            String VCompany = txtCompany.getText();
            String VCode = lblVaccineCode.getText();
            Connection connection = DBConnection.getInstance().getConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("insert into VaccineReg values(?,?,?,?)");
                preparedStatement.setObject(1, VCode);
                preparedStatement.setObject(2, VName);
                preparedStatement.setObject(3, MCountry);
                preparedStatement.setObject(4, VCompany);
                int i = preparedStatement.executeUpdate();
                if (i != 0) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Successful Added !! ");
                    alert.showAndWait();
                    textAllClear();
                    txtVaccineName.requestFocus();
                    autoGenarateCode();
                    Loadlist();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Somthing Error !! Please try again.");
                    alert.showAndWait();
                }

            } catch (SQLException throwables) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Something Error !! Please try again. ");
                alert.showAndWait();
                throwables.printStackTrace();
            }
        }
    }
    public void autoGenarateCode(){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select vcode from vaccinereg order by vcode desc limit 1");
            boolean ifNext = resultSet.next();

            if(ifNext){
                String oldValue = resultSet.getString(1);
                oldValue =oldValue.substring(1,oldValue.length());
                int newID = Integer.parseInt(oldValue);
                newID=newID+1;
                if(newID<10){
                    lblVaccineCode.setText("V00"+newID);
                }else if(newID<100){
                    lblVaccineCode.setText("V0"+newID);
                }else{
                    lblVaccineCode.setText("V");
                }
            }else{
                lblVaccineCode.setText("V001");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public void textAllClear () {
        txtVaccineName.clear();
        txtCompany.clear();
        txtMCountry.clear();

    }
    public void Loadlist(){
        ObservableList<ViewRegVaccineTM> items = lstVaccineView.getItems();
        items.clear();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from vaccinereg");

            while(resultSet.next()){
                String vcode = resultSet.getString(1);
                String vname = resultSet.getString(2);
                String mcountry = resultSet.getString(3);
                String mcompany = resultSet.getString(4);
                ViewRegVaccineTM viewRegVaccineTM = new ViewRegVaccineTM(vcode,vname,mcountry,mcompany);
                items.add(viewRegVaccineTM);
            }
            lstVaccineView.refresh();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void ErrorMassage(String errorField){
        Alert alert = new Alert(Alert.AlertType.ERROR," "+errorField+" Field is cannot be emty, Please Fill this field..! ");
        alert.showAndWait();
    }
    public void ErrorBorderCl(JFXTextField name){
        name.setStyle("-fx-border-color:red");
    }
    public void NullBorderCl(){
        txtVaccineName.setStyle("-fx-border-color:null");
        txtCompany.setStyle("-fx-border-color:null");
        txtMCountry.setStyle("-fx-border-color:null");
    }
}

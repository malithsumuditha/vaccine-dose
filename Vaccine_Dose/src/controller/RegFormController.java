package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import tm.ViewAllPersonsTM;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public Label lblAllRegistered;
    public AnchorPane personRegisterPanel;
    public TableView<ViewAllPersonsTM> tblViewPersons;
    public JFXButton btnAdd;


    public void initialize(){
        tblViewPersons.setVisible(false);
        txtName.requestFocus();
        autoGenerateID();
        countAllRegisteredPersons();

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
            PreparedStatement preparedStatement = connection.prepareStatement("insert into person values(?,?,?,?,?,?,?)");
            preparedStatement.setObject(1,id);
            preparedStatement.setObject(2,name);
            preparedStatement.setObject(3,address);
            preparedStatement.setObject(4,contact);
            preparedStatement.setObject(5,nic);
            preparedStatement.setObject(6,gender);
            preparedStatement.setObject(7,setTimeDate());


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
        countAllRegisteredPersons();


    }

    public void rdbFemaleOnAction() {
        rdbMale.setSelected(false);
    }

    public void rdbMaleOnAction() {
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

    public void countAllRegisteredPersons(){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select count(*) from person");

            String count = null;
            int intCount=0;
            while (resultSet.next()){
               count= resultSet.getString(1);
                intCount = Integer.parseInt(count);
            }
            if (intCount<10){
                lblAllRegistered.setText("0"+intCount);
            }else {
                lblAllRegistered.setText(""+intCount);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
    public String setTimeDate(){

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String dateTime = df.format(now);
        return dateTime;
    }

    public void btnViewAllOnAction(ActionEvent actionEvent) throws IOException {

        tblViewPersons.setVisible(true);
        loadList();

    }

    public void loadList(){
        ObservableList<ViewAllPersonsTM> items = tblViewPersons.getItems();
        items.clear();

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from person");

            while (resultSet.next()){
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                String contact = resultSet.getString(4);
                String nic = resultSet.getString(5);
                String gender = resultSet.getString(6);
                String registerTime = resultSet.getString(7);

                ViewAllPersonsTM viewAllPersonsTM = new ViewAllPersonsTM(id,name,address,contact,nic,gender,registerTime);
                items.add(viewAllPersonsTM);
            }
            tblViewPersons.refresh();

            //only for tables
            tblViewPersons.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
            tblViewPersons.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
            tblViewPersons.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
            tblViewPersons.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));
            tblViewPersons.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("nic"));
            tblViewPersons.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("gender"));
            tblViewPersons.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("registerTime"));

            tblViewPersons.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ViewAllPersonsTM>() {
                @Override
                public void changed(ObservableValue<? extends ViewAllPersonsTM> observable, ViewAllPersonsTM oldValue, ViewAllPersonsTM newValue) {

                    btnAdd.setDisable(true);

                    ViewAllPersonsTM selectedItem = tblViewPersons.getSelectionModel().getSelectedItem();

                    String id = selectedItem.getId();
                    String name = selectedItem.getName();
                    String address = selectedItem.getAddress();
                    String contact = selectedItem.getContact();
                    String nic = selectedItem.getNic();
                    String gender = selectedItem.getGender();

                    lblPersonID.setText(id);
                    txtName.setText(name);
                    txtAddress.setText(address);
                    txtContact.setText(contact);
                    txtNIC.setText(nic);
                    if (gender.equals("Male")){
                        rdbMale.setSelected(true);
                        rdbMaleOnAction();
                    }else {
                        rdbFemale.setSelected(true);
                        rdbFemaleOnAction();

                    }

                }
            });




        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}

<<<<<<< HEAD:Vaccine_Dose/src/controller/RegFormController.java
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import tm.ViewAllPersonsTM;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnAdd;
    public JFXButton btnReset;
    public JFXTextField txtSearchMemID;



    public void initialize(){
        tblViewPersons.setVisible(false);
        autoGenerateID(lblPersonID,"person","P");
        txtName.requestFocus();
        countAllRegisteredPersons();
        setDisableUpdateDelete(true);
        selectTableData();
        loadList();

        //to request focus to text field in children Pane
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtName.requestFocus();
            }
        });

        //add Listener to filterField
        txtSearchMemID.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search2((String) oldValue,(String) newValue);
            }
        });


    }

    public void btnAddOnAction(ActionEvent actionEvent) {

       String num= txtContact.getText();

       if (txtName.getText().isEmpty()){
           showErrorMessage("Name");
           txtName.requestFocus();
           txtName.clear();
           errorBorderColor(txtName,"red");

       }else if(txtAddress.getText().isEmpty()){
           showErrorMessage("Address");
           txtAddress.requestFocus();
           txtAddress.clear();
           errorBoderColorAllNull();
           errorBorderColor(txtAddress,"red");
       }else if (txtContact.getText().isEmpty()){
           showErrorMessage("Contact");
           txtContact.requestFocus();
           txtContact.clear();
           errorBoderColorAllNull();
           errorBorderColor(txtContact,"red");
       }else if (txtNIC.getText().isEmpty()){
           showErrorMessage("NIC");
           txtNIC.requestFocus();
           txtNIC.clear();
           errorBoderColorAllNull();
           errorBorderColor(txtNIC,"red");
       }else {
           errorBoderColorAllNull();

           if (numberCheck(num)){
               errorBoderColorAllNull();

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
               }else{
                   showErrorMessage("Gender");
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
                   loadList();


               } catch (SQLException throwables) {
                   Alert alert = new Alert(Alert.AlertType.ERROR,"Something Error...");
                   alert.showAndWait();
                   throwables.printStackTrace();
               }
               txtName.requestFocus();
               autoGenerateID(lblPersonID,"person","P");
               clearTextFields();
               countAllRegisteredPersons();


           }else {
               Alert alert = new Alert(Alert.AlertType.ERROR,"Please Enter a Valid Number....");
               alert.showAndWait();
               errorBorderColor(txtContact,"red");
               txtContact.requestFocus();
           }


       }





    }

    public void rdbFemaleOnAction() {
        rdbMale.setSelected(false);
    }

    public void rdbMaleOnAction() {
        rdbFemale.setSelected(false);
    }

    public static void autoGenerateID(Label lbl,String tablename,String fLetter){

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select id from "+tablename+ " order by id desc limit 1");

            boolean isExit = resultSet.next();

            if (isExit){
                String oldId = resultSet.getString(1);
                oldId = oldId.substring(1, oldId.length());

                int intID = Integer.parseInt(oldId);
                intID=intID+1;

                if (intID<10){
                    lbl.setText(fLetter+"000"+intID);
                }else if (intID<100){
                    lbl.setText(fLetter+"00"+intID);
                }else if (intID<1000){
                    lbl.setText(fLetter+"0"+intID);
                }else{
                    lbl.setText(fLetter+""+intID);
                    }

            }else {
                lbl.setText(fLetter+"0001");

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
    public static String setTimeDate(){

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String dateTime = df.format(now);
        return dateTime;
    }


    public void btnViewAllOnAction(ActionEvent actionEvent) throws IOException {

        tblViewPersons.setVisible(true);
        loadList();
        txtSearchMemID.clear();
        txtSearchMemID.requestFocus();

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


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        ViewAllPersonsTM selectedItem = tblViewPersons.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do You want to delete... ? ", ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get().equals(ButtonType.YES)){
            Connection connection = DBConnection.getInstance().getConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("delete from person where id= ?");
                preparedStatement.setObject(1,selectedItem.getId());
                preparedStatement.executeUpdate();

                loadList();
                setDisableUpdateDelete(true);
                btnAdd.setDisable(false);
                clearTextFields();


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        }


    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String name = txtName.getText();
        String address = txtAddress.getText();
        String nic = txtNIC.getText();
        String contact = txtContact.getText();

        ViewAllPersonsTM selectedItem = tblViewPersons.getSelectionModel().getSelectedItem();

        Connection connection = DBConnection.getInstance().getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update person set name =?,address=?,contact=?,nic=? where id=?");
            preparedStatement.setObject(1,name);
            preparedStatement.setObject(2,address);
            preparedStatement.setObject(3,contact);
            preparedStatement.setObject(4,nic);
            preparedStatement.setObject(5,selectedItem.getId());

            preparedStatement.executeUpdate();

            loadList();
            setDisableUpdateDelete(true);
            btnAdd.setDisable(false);
            clearTextFields();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public void btnResetOnAction() {
        tblViewPersons.getSelectionModel().clearSelection();
        clearTextFields();
        btnAdd.setDisable(false);
        setDisableUpdateDelete(true);
        txtName.requestFocus();
        autoGenerateID(lblPersonID,"person","P");
        errorBoderColorAllNull();


    }

    public void setDisableUpdateDelete(boolean isDisable){

        btnUpdate.setDisable(isDisable);
        btnDelete.setDisable(isDisable);

    }

    public void selectTableData(){
        tblViewPersons.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ViewAllPersonsTM>() {
            @Override
            public void changed(ObservableValue<? extends ViewAllPersonsTM> observable, ViewAllPersonsTM oldValue, ViewAllPersonsTM newValue) {

                btnAdd.setDisable(true);
                setDisableUpdateDelete(false);

                ViewAllPersonsTM selectedItem = tblViewPersons.getSelectionModel().getSelectedItem();

                if (selectedItem==null){
                    return;
                }

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
                //to request focus to text field in not responding Time
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        txtName.requestFocus();
                    }
                });


            }
        });
    }

    public boolean numberCheck(String num){

        boolean b = num.charAt(0) == '0' && num.length() == 10 && num.matches("[0-9]+");
        return b;
    }

    public void showErrorMessage(String errorName){
        Alert alert = new Alert(Alert.AlertType.ERROR,"Text field "+errorName+" Can not be Empty...");
        alert.showAndWait();
    }

    public void errorBorderColor(JFXTextField name,String color){
        name.setStyle("-fx-border-color:"+color);

    }
    public void errorBoderColorAllNull(){
        txtName.setStyle("-fx-border-color:null");
        txtAddress.setStyle("-fx-border-color:null");
        txtContact.setStyle("-fx-border-color:null");
        txtNIC.setStyle("-fx-border-color:null");
    }

    public void txtSearchMemIdOnAction(ActionEvent actionEvent) {
        searchPerson();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        searchPerson();
    }

    public void searchPerson(){

        String id = txtSearchMemID.getText();

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from person where id =?");
            preparedStatement.setObject(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            boolean next = resultSet.next();

            if (next){
                String pid = resultSet.getString(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                String contact = resultSet.getString(4);
                String nic = resultSet.getString(5);
                String gender = resultSet.getString(6);

                if(gender.equals("Male")){
                    rdbMale.setSelected(true);
                    rdbMaleOnAction();
                }else {
                    rdbFemale.setSelected(true);
                    rdbFemaleOnAction();
                }

                lblPersonID.setText(pid);
                txtName.setText(name);
                txtAddress.setText(address);
                txtContact.setText(contact);
                txtNIC.setText(nic);

                txtSearchMemID.clear();
                tblViewPersons.refresh();
                setDisableUpdateDelete(false);
                btnAdd.setDisable(true);
                txtName.requestFocus();



            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Wrong Person ID ... ");
                alert.showAndWait();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    ObservableList<ViewAllPersonsTM> masterData = FXCollections.observableArrayList();
    //filter table by id or last name
    public void search2(String oldValue, String newValue){

        ObservableList<ViewAllPersonsTM> filteredList = FXCollections.observableArrayList();

        if(txtSearchMemID == null || (newValue.length() < oldValue.length()) || newValue == null) {
            tblViewPersons.setItems(masterData);
            loadList();
        }
        else {
            newValue = newValue.toUpperCase();
            for(ViewAllPersonsTM personsTM : tblViewPersons.getItems()) {
                String filterId = personsTM.getId();
                String filterName = personsTM.getName();
                if(filterId.toUpperCase().contains(newValue) || filterName.toUpperCase().contains(newValue)) {

                    filteredList.add(personsTM);
                }
            }
            tblViewPersons.setItems(filteredList);
        }


    }

}
=======
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import tm.ViewAllPersonsTM;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * @author - Hw_Dulanjana
 * @date - 9/12/2021
 */
public class PersonRegFormController {



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
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnAdd;
    public JFXButton btnReset;
    public JFXTextField txtSearchMemID;



    public void initialize(){
        tblViewPersons.setVisible(false);
        autoGenerateID(lblPersonID,"person","P");
        txtName.requestFocus();
        countAllRegisteredPersons();
        setDisableUpdateDelete(true);
        selectTableData();
        loadList();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtName.requestFocus();
            }
        });

        //add Listener to filterField
        txtSearchMemID.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search2((String) oldValue,(String) newValue);
            }
        });


    }

    public void btnAddOnAction(ActionEvent actionEvent) {

       String num= txtContact.getText();

       if (txtName.getText().isEmpty()){
           showErrorMessage("Name");
           txtName.requestFocus();
           txtName.clear();
           errorBorderColor(txtName,"red");

       }else if(txtAddress.getText().isEmpty()){
           showErrorMessage("Address");
           txtAddress.requestFocus();
           txtAddress.clear();
           errorBoderColorAllNull();
           errorBorderColor(txtAddress,"red");
       }else if (txtContact.getText().isEmpty()){
           showErrorMessage("Contact");
           txtContact.requestFocus();
           txtContact.clear();
           errorBoderColorAllNull();
           errorBorderColor(txtContact,"red");
       }else if (txtNIC.getText().isEmpty()){
           showErrorMessage("NIC");
           txtNIC.requestFocus();
           txtNIC.clear();
           errorBoderColorAllNull();
           errorBorderColor(txtNIC,"red");
       }else {
           errorBoderColorAllNull();

           if (numberCheck(num)){
               errorBoderColorAllNull();

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
               }else{
                   showErrorMessage("Gender");
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
                   loadList();


               } catch (SQLException throwables) {
                   Alert alert = new Alert(Alert.AlertType.ERROR,"Something Error...");
                   alert.showAndWait();
                   throwables.printStackTrace();
               }
               txtName.requestFocus();
               autoGenerateID(lblPersonID,"person","P");
               clearTextFields();
               countAllRegisteredPersons();


           }else {
               Alert alert = new Alert(Alert.AlertType.ERROR,"Please Enter a Valid Number....");
               alert.showAndWait();
               errorBorderColor(txtContact,"red");
               txtContact.requestFocus();
           }


       }





    }

    public void rdbFemaleOnAction() {
        rdbMale.setSelected(false);
    }

    public void rdbMaleOnAction() {
        rdbFemale.setSelected(false);
    }

    public static void autoGenerateID(Label lbl,String tablename,String fLetter){

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select id from "+tablename+ " order by id desc limit 1");

            boolean isExit = resultSet.next();

            if (isExit){
                String oldId = resultSet.getString(1);
                oldId = oldId.substring(1, oldId.length());

                int intID = Integer.parseInt(oldId);
                intID=intID+1;

                if (intID<10){
                    lbl.setText(fLetter+"000"+intID);
                }else if (intID<100){
                    lbl.setText(fLetter+"00"+intID);
                }else if (intID<1000){
                    lbl.setText(fLetter+"0"+intID);
                }else{
                    lbl.setText(fLetter+""+intID);
                    }

            }else {
                lbl.setText(fLetter+"0001");

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
    public static String setTimeDate(){

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String dateTime = df.format(now);
        return dateTime;
    }


    public void btnViewAllOnAction(ActionEvent actionEvent) throws IOException {

        tblViewPersons.setVisible(true);
        loadList();
        txtSearchMemID.clear();
        txtSearchMemID.requestFocus();

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


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        ViewAllPersonsTM selectedItem = tblViewPersons.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do You want to delete... ? ", ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get().equals(ButtonType.YES)){
            Connection connection = DBConnection.getInstance().getConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("delete from person where id= ?");
                preparedStatement.setObject(1,selectedItem.getId());
                preparedStatement.executeUpdate();

                loadList();
                setDisableUpdateDelete(true);
                btnAdd.setDisable(false);
                clearTextFields();


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        }


    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String name = txtName.getText();
        String address = txtAddress.getText();
        String nic = txtNIC.getText();
        String contact = txtContact.getText();

        ViewAllPersonsTM selectedItem = tblViewPersons.getSelectionModel().getSelectedItem();

        Connection connection = DBConnection.getInstance().getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update person set name =?,address=?,contact=?,nic=? where id=?");
            preparedStatement.setObject(1,name);
            preparedStatement.setObject(2,address);
            preparedStatement.setObject(3,contact);
            preparedStatement.setObject(4,nic);
            preparedStatement.setObject(5,selectedItem.getId());

            preparedStatement.executeUpdate();

            loadList();
            setDisableUpdateDelete(true);
            btnAdd.setDisable(false);
            clearTextFields();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public void btnResetOnAction() {
        tblViewPersons.getSelectionModel().clearSelection();
        clearTextFields();
        btnAdd.setDisable(false);
        setDisableUpdateDelete(true);
        txtName.requestFocus();
        autoGenerateID(lblPersonID,"person","P");
        errorBoderColorAllNull();


    }

    public void setDisableUpdateDelete(boolean isDisable){

        btnUpdate.setDisable(isDisable);
        btnDelete.setDisable(isDisable);

    }

    public void selectTableData(){
        tblViewPersons.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ViewAllPersonsTM>() {
            @Override
            public void changed(ObservableValue<? extends ViewAllPersonsTM> observable, ViewAllPersonsTM oldValue, ViewAllPersonsTM newValue) {

                btnAdd.setDisable(true);
                setDisableUpdateDelete(false);

                ViewAllPersonsTM selectedItem = tblViewPersons.getSelectionModel().getSelectedItem();

                if (selectedItem==null){
                    return;
                }

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
    }

    public boolean numberCheck(String num){

        boolean b = num.charAt(0) == '0' && num.length() == 10 && num.matches("[0-9]+");
        return b;
    }

    public void showErrorMessage(String errorName){
        Alert alert = new Alert(Alert.AlertType.ERROR,"Text field "+errorName+" Can not be Empty...");
        alert.showAndWait();
    }

    public void errorBorderColor(JFXTextField name,String color){
        name.setStyle("-fx-border-color:"+color);

    }
    public void errorBoderColorAllNull(){
        txtName.setStyle("-fx-border-color:null");
        txtAddress.setStyle("-fx-border-color:null");
        txtContact.setStyle("-fx-border-color:null");
        txtNIC.setStyle("-fx-border-color:null");
    }

    public void txtSearchMemIdOnAction(ActionEvent actionEvent) {
        searchPerson();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        searchPerson();
    }

    public void searchPerson(){

        String id = txtSearchMemID.getText();

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from person where id =?");
            preparedStatement.setObject(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            boolean next = resultSet.next();

            if (next){
                String pid = resultSet.getString(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                String contact = resultSet.getString(4);
                String nic = resultSet.getString(5);
                String gender = resultSet.getString(6);

                if(gender.equals("Male")){
                    rdbMale.setSelected(true);
                    rdbMaleOnAction();
                }else {
                    rdbFemale.setSelected(true);
                    rdbFemaleOnAction();
                }

                lblPersonID.setText(pid);
                txtName.setText(name);
                txtAddress.setText(address);
                txtContact.setText(contact);
                txtNIC.setText(nic);

                txtSearchMemID.clear();
                tblViewPersons.refresh();
                setDisableUpdateDelete(false);
                btnAdd.setDisable(true);
                txtName.requestFocus();



            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Wrong Person ID ... ");
                alert.showAndWait();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    ObservableList<ViewAllPersonsTM> masterData = FXCollections.observableArrayList();
    //filter table by id or last name
    public void search2(String oldValue, String newValue){

        ObservableList<ViewAllPersonsTM> filteredList = FXCollections.observableArrayList();

        if(txtSearchMemID == null || (newValue.length() < oldValue.length()) || newValue == null) {
            tblViewPersons.setItems(masterData);
            loadList();
        }
        else {
            newValue = newValue.toUpperCase();
            for(ViewAllPersonsTM personsTM : tblViewPersons.getItems()) {
                String filterId = personsTM.getId();
                String filterName = personsTM.getName();
                if(filterId.toUpperCase().contains(newValue) || filterName.toUpperCase().contains(newValue)) {

                    filteredList.add(personsTM);
                }
            }
            tblViewPersons.setItems(filteredList);
        }


    }

}
>>>>>>> 31aaffcd09431cdd8d476f2c6addffa1b8b219bc:Vaccine_Dose/src/controller/PersonRegFormController.java

package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tm.ViewAllPersonsListTM;
import tm.ViewAllPersonsTM;
import tm.ViewAllVaccinatedPersonsTM;
import tm.ViewDoseCmbTM;

import java.sql.*;

/**
 * @author : MalithHP <malithsumuditha11@gmail.com>
 * @since : 9/19/2021
 **/
public class VaccinationFormController {
    public Label lblPersonID;
    public Label lblAge;
    public JFXTextField txtLocation;
    public Label lblPersonName;
    public ComboBox cmbSelectDose;
    public ComboBox cmbSelectVaccineName;
    public JFXButton btnVaccinate;
    public ListView<ViewAllPersonsListTM> lstViewPersons;
    public JFXTextField txtSearchMemID;
    public Label lblGender;
    public TableView<ViewAllVaccinatedPersonsTM> tblViewAllVaccinatedPerson;
    public Label lblVid;

    public void initialize(){
        loadList();
        doseSelectComboBox();
        vaccineSelectComboBox();
        selectListItem();
        generateAutoID();
        setTableSelectedItem();
        loadDataToTable();



    }

    public void cmbSelectDoseOnAction(ActionEvent actionEvent) {

    }

    public void btnVaccinatedOnAction(ActionEvent actionEvent) {

        tblViewAllVaccinatedPerson.getSelectionModel().clearSelection();
        lstViewPersons.getSelectionModel().clearSelection();

        String id = lblVid.getText();
        String name = lblPersonName.getText();
        String age = lblAge.getText();
        String location = txtLocation.getText();
        String time = RegFormController.setTimeDate();
        String person_id = lblPersonID.getText();
        String dose = cmbSelectDose.getValue().toString();
        String vaccine_name = cmbSelectVaccineName.getValue().toString();
        String gender = lblGender.getText();


        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into vaccination(id,name,age,vaccineName,dose,regDateDose1,location,person_id,gender) values(?,?,?,?,?,?,?,?,?) ");
            preparedStatement.setObject(1,id);
            preparedStatement.setObject(2,name);
            preparedStatement.setObject(3,age);
            preparedStatement.setObject(4,vaccine_name);
            preparedStatement.setObject(5,dose);
            preparedStatement.setObject(6,time);
            preparedStatement.setObject(7,location);
            preparedStatement.setObject(8,person_id);
            preparedStatement.setObject(9,gender);

            int i = preparedStatement.executeUpdate();
            txtLocation.clear();
            generateAutoID();
            loadDataToTable();
            tblViewAllVaccinatedPerson.refresh();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public void txtSearchMemIdOnAction(ActionEvent actionEvent) {

    }

    public void btnSearchOnAction(ActionEvent actionEvent) {

    }

    public void loadList(){
        ObservableList<ViewAllPersonsListTM> items = lstViewPersons.getItems();
        items.clear();

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select id,name,gender from person");

            while (resultSet.next()){
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String gender = resultSet.getString(3);


                ViewAllPersonsListTM viewAllPersonsListTM = new ViewAllPersonsListTM(id,name,gender);
                items.add(viewAllPersonsListTM);
            }
            lstViewPersons.refresh();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void doseSelectComboBox(){
        cmbSelectDose.getItems().add("Firs Dose");
        cmbSelectDose.getItems().add("Second Dose");
    }

    public void vaccineSelectComboBox(){
        cmbSelectVaccineName.getItems().add("Sputnic V");
        cmbSelectVaccineName.getItems().add("Pfizer");
        cmbSelectVaccineName.getItems().add("Astrosenica");
        cmbSelectVaccineName.getItems().add("Sinopharm");
        cmbSelectVaccineName.getItems().add("Moderna");
    }

    public void selectListItem(){



        lstViewPersons.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ViewAllPersonsListTM>() {
            @Override
            public void changed(ObservableValue<? extends ViewAllPersonsListTM> observable, ViewAllPersonsListTM oldValue, ViewAllPersonsListTM newValue) {
                ViewAllPersonsListTM selectedItem = lstViewPersons.getSelectionModel().getSelectedItem();

                if (selectedItem==null){
                    return;
                }
                lblPersonID.setText(selectedItem.getId());
                lblPersonName.setText(selectedItem.getName());
                lblGender.setText(selectedItem.getGender());

                txtLocation.clear();
                generateAutoID();

                setDisableAddBtnAndCmbVaccineName(false);

                clearFields();

                //to unselect table data item
                tblViewAllVaccinatedPerson.getSelectionModel().clearSelection();

            }
        });

    }

    public void setTableSelectedItem(){


        tblViewAllVaccinatedPerson.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ViewAllVaccinatedPersonsTM>() {
            @Override
            public void changed(ObservableValue<? extends ViewAllVaccinatedPersonsTM> observable, ViewAllVaccinatedPersonsTM oldValue, ViewAllVaccinatedPersonsTM newValue) {
                ViewAllVaccinatedPersonsTM selectedItem = tblViewAllVaccinatedPerson.getSelectionModel().getSelectedItem();

                if (selectedItem==null){
                    return;
                }
                lblVid.setText(selectedItem.getId());
                lblPersonID.setText(selectedItem.getPerson_id());
                lblAge.setText(selectedItem.getAge());
                lblGender.setText(selectedItem.getGender());
                lblPersonName.setText(selectedItem.getName());
                txtLocation.setText(selectedItem.getLocation());
                cmbSelectVaccineName.setValue(selectedItem.getVaccineName());
                cmbSelectDose.setValue("Second Dose");

                setDisableAddBtnAndCmbVaccineName(true);
                //to unselect list data item
                lstViewPersons.getSelectionModel().clearSelection();

            }
        });
    }

    public void loadDataToTable(){

        ObservableList<ViewAllVaccinatedPersonsTM> items = tblViewAllVaccinatedPerson.getItems();

        items.clear();

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select id,name,age,vaccineName,regDateDose1,regDateDose2,location,person_id,gender from vaccination");

            while (resultSet.next()){
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String age = resultSet.getString(3);
                String vaccineName = resultSet.getString(4);
                String regDateDose1 = resultSet.getString(5);
                String regDateDose2 = resultSet.getString(6);
                String location = resultSet.getString(7);
                String person_id = resultSet.getString(8);
                String gender = resultSet.getString(9);

                ViewAllVaccinatedPersonsTM viewAllVaccinatedPersonsTM = new ViewAllVaccinatedPersonsTM();

                viewAllVaccinatedPersonsTM.setId(id);
                viewAllVaccinatedPersonsTM.setName(name);
                viewAllVaccinatedPersonsTM.setAge(age);
                viewAllVaccinatedPersonsTM.setVaccineName(vaccineName);
                viewAllVaccinatedPersonsTM.setRegDateDose1(regDateDose1);
                viewAllVaccinatedPersonsTM.setRegDateDose2(regDateDose2);
                viewAllVaccinatedPersonsTM.setLocation(location);
                viewAllVaccinatedPersonsTM.setPerson_id(person_id);
                viewAllVaccinatedPersonsTM.setGender(gender);

                items.add(viewAllVaccinatedPersonsTM);

                tblViewAllVaccinatedPerson.refresh();

                //only for tables
                tblViewAllVaccinatedPerson.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
                tblViewAllVaccinatedPerson.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
                tblViewAllVaccinatedPerson.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("age"));
                tblViewAllVaccinatedPerson.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("vaccineName"));
                tblViewAllVaccinatedPerson.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("regDateDose1"));
                tblViewAllVaccinatedPerson.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("regDateDose2"));
                tblViewAllVaccinatedPerson.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("location"));
                tblViewAllVaccinatedPerson.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("person_id"));



            }



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void generateAutoID(){
        RegFormController.autoGenerateID(lblVid,"vaccination","D");
    }

    public void setDisableAddBtnAndCmbVaccineName(boolean isDisable){
        btnVaccinate.setDisable(isDisable);
        cmbSelectVaccineName.setDisable(isDisable);
    }
    public void clearFields(){
        txtLocation.clear();
        txtSearchMemID.clear();
        cmbSelectVaccineName.setValue(null);
        cmbSelectDose.setValue(null);
    }

}

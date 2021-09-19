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


    }

    public void cmbSelectDoseOnAction(ActionEvent actionEvent) {

    }

    public void btnVaccinatedOnAction(ActionEvent actionEvent) {

        String id = lblVid.getText();
        String name = lblPersonName.getText();
        String age = lblAge.getText();
        String location = txtLocation.getText();
        String time = RegFormController.setTimeDate();
        String person_id = lblPersonID.getText();
        String dose = cmbSelectDose.getValue().toString();
        String vaccine_name = cmbSelectVaccineName.getValue().toString();


        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into vaccination(id,name,age,vaccineName,dose,regDateDose1,location,person_id) values(?,?,?,?,?,?,?,?) ");
            preparedStatement.setObject(1,id);
            preparedStatement.setObject(2,name);
            preparedStatement.setObject(3,age);
            preparedStatement.setObject(4,vaccine_name);
            preparedStatement.setObject(5,dose);
            preparedStatement.setObject(6,time);
            preparedStatement.setObject(7,location);
            preparedStatement.setObject(8,person_id);

            int i = preparedStatement.executeUpdate();
            txtLocation.clear();
            generateAutoID();


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

            }
        });

    }

    public void loadDataToTable(){
//        Connection connection = DBConnection.getInstance().getConnection();
//        connection.prepareStatement("insert into vaccination ")
    }

    public void generateAutoID(){
        RegFormController.autoGenerateID(lblVid,"vaccination","D");
    }


}

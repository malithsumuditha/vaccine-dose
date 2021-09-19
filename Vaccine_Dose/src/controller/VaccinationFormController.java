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
import javafx.scene.control.cell.PropertyValueFactory;
import tm.ViewAllPersonsListTM;
import tm.ViewAllPersonsTM;
import tm.ViewDoseCmbTM;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public void initialize(){
        loadList();
        doseSelectComboBox();
        vaccineSelectComboBox();
        selectListItem();
    }

    public void cmbSelectDoseOnAction(ActionEvent actionEvent) {

    }

    public void btnVaccinatedOnAction(ActionEvent actionEvent) {

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


}
